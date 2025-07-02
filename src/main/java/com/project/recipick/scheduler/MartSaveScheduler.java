package com.project.recipick.scheduler;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.service.MartInfoService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MartSaveScheduler {

    @Value("${martinfoapi.key}")
    private String martInfoApiKey;

    private final RestTemplate restTemplate;

    private final MartInfoService martInfoService;

    public MartSaveScheduler(MartInfoService martInfoService, RestTemplate restTemplate) {
        this.martInfoService = martInfoService;
        this.restTemplate = restTemplate;
    }

    /*
    @Scheduled(cron = "55 59 5 * * *") // 매일 오전 5시 59분 55초에 마트 삭제 스케줄러 실행
    public void deleteAllMartInfo() throws ParseException {
        martInfoService.deleteAllMartInfo();
        System.out.println("삭제됐습니다");
    }
    */

    @Scheduled(cron = "* * 6 * * *") // 매일 오전 6시에 마트 저장 스케줄러 실행
    public void saveMartInfo() throws ParseException {
        String apiKey = martInfoApiKey;
        int pageSize = 1000;
        int start = 1;
        int lastEnd = 0;

        // 현재 월 기준의 "yyyy-MM" 문자열 생성
        String currentYearMonth = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));

        while (true) {
            int end = start + pageSize - 1;
            lastEnd = end;

            //String url = "http://openAPI.seoul.go.kr:8088/" + apiKey + "/json/ListNecessariesPricesService/" + start + "/" + end + "/?P_YEAR_MONTH=2025-06";
            String url = "http://openAPI.seoul.go.kr:8088/" + apiKey + "/json/ListNecessariesPricesService/" + start + "/" + end + "/?P_YEAR_MONTH=" + currentYearMonth;

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            String jsonString = responseBody;
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            String ListNecessariesPricesService = jsonObject.get("ListNecessariesPricesService").toString();

            if (ListNecessariesPricesService == null) break; // 서비스 자체가 없으면 종료


            JSONObject jsonObj = (JSONObject) parser.parse(ListNecessariesPricesService);
            String row = jsonObj.get("row").toString();

            if (row == null) break; // 서비스 자체가 없으면 종료

            JSONArray jsonArray = (JSONArray) parser.parse(row);

            if (jsonArray.isEmpty()) break; // 빈 배열이면 종료

            boolean hasTargetMonth = false;

            System.out.println(jsonArray.get(0));

            for(int i=0; i<jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);

                // 가격이 '0'이면 저장하지 않음
                String price = obj.get("A_PRICE").toString();
                if ("0".equals(price)) continue;

                // 현재 월이 아니라면 continue
                String pYearMonth = obj.get("P_YEAR_MONTH").toString();
                if (!currentYearMonth.equals(pYearMonth)) continue;

                String mName = obj.get("M_NAME").toString();
                String aName = obj.get("A_NAME").toString();
                String pDate = obj.get("P_DATE").toString();

                MartInfo latest = martInfoService.findLatestByMartAndItem(mName, aName);
                boolean shouldSave = false;

                if (latest == null) {
                    shouldSave = true;
                } else if (pDate.compareTo(latest.getpDate()) > 0) {
                    martInfoService.deleteMartInfo(latest); // 기존 데이터 삭제
                    shouldSave = true;
                }

                if (shouldSave) {
                    hasTargetMonth = true;

                    MartInfo martInfo = new MartInfo();

                    martInfo.setpSeq(obj.get("P_SEQ").toString());
                    martInfo.setmSeq(obj.get("M_SEQ").toString());
                    martInfo.setmName(obj.get("M_NAME").toString());
                    martInfo.setaSeq(obj.get("A_SEQ").toString());
                    martInfo.setaName(obj.get("A_NAME").toString());
                    martInfo.setaUnit(obj.get("A_UNIT").toString());
                    martInfo.setaPrice(price);
                    martInfo.setpYearMonth(obj.get("P_YEAR_MONTH").toString());
                    martInfo.setAddCol(obj.get("ADD_COL").toString());
                    martInfo.setpDate(obj.get("P_DATE").toString());
                    martInfo.setmTypeCode(obj.get("M_TYPE_CODE").toString());
                    martInfo.setmTypeName(obj.get("M_TYPE_NAME").toString());
                    martInfo.setmGuCode(obj.get("M_GU_CODE").toString());
                    martInfo.setmGuName(obj.get("M_GU_NAME").toString());

                    System.out.println("start & end = " + start + ", " + lastEnd);
                    martInfoService.saveMartInfo(martInfo);
                }

            }

            // 이번 페이지에 2025-06이 없으면 반복 종료
            if (!hasTargetMonth) break;

            // 다음 페이지로 이동
            start += pageSize;
        }

        System.out.println("마트 저장 완료.");
    }


}

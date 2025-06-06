package com.project.recipick.service;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.MartNameAndLocation;
import com.project.recipick.mapper.MartInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.recipick.dto.MartItemDTO;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MartInfoService {

    @Value("${kakao.restapi.key}")
    private String kakaoRestApiKey;

    @Autowired
    final MartInfoMapper martInfoMapper;
    @Autowired
    private final RestTemplate restTemplate;

    public MartInfoService(MartInfoMapper martInfoMapper, RestTemplate restTemplate) {
        this.martInfoMapper = martInfoMapper;
        this.restTemplate = restTemplate;
    }

    public List<MartNameAndLocation> getAllMartName() {
        return martInfoMapper.getAllMartName();
    }

    public void saveMartInfo(MartInfo martInfo) {
        martInfoMapper.saveMartInfo(martInfo);
    }

    public List<MartInfo> getAllMartInfo() {
        return martInfoMapper.findAll();
    }

    public List<MartInfo> findMartInfo(String gu_name) {
        return martInfoMapper.findSameMartInfo(gu_name);
    }

    public List<String> getProductByCuCode(String gu_name) {


        List<String> guNameList = martInfoMapper.getProductByCuCode(gu_name);

        for(String name : guNameList) {

            System.out.println(name);

        }
        return guNameList;
    }

    public List<MartItemDTO> getMartItemsByMartName(String martName) {
        return martInfoMapper.selectItemsByMartName(martName);
    }

    public List<MartInfo> getIrdntPrice(String ingredient) {

        List<MartInfo> list = martInfoMapper.getIrdntPrice(ingredient);
        System.out.println("service " + list);

        return martInfoMapper.getIrdntPrice(ingredient);
    }

    public List<MartInfo> findByMName(String martName) {
        return martInfoMapper.findByMName(martName);
    }

    public List<Object> searchMartInKakao(List<MartInfo> martList, String ingredient) {
        String apiKey = "KakaoAK "  + kakaoRestApiKey;
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<Object> resultList = new ArrayList<>();

        for (MartInfo mart : martList) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("query", mart.getmName()) //
                    .queryParam("category_group_code", "MT1") // 마트
                    .queryParam("rect", "126.76601,37.41329,127.23523,37.71513"); // 서울/경기 범위

            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // 원하면 여기서 JSON을 파싱해서 필요한 데이터만 추려내기
            resultList.add(response.getBody());
        }

        return resultList;
    }

    public List<MartItemDTO> getMartItemsByFuzzyMatch(String martName) {
        List<MartInfo> list = martInfoMapper.findByMName(martName);
        return list.stream()
                .map(m -> new MartItemDTO(m.getaName(), m.getaPrice()))
                .collect(Collectors.toList());
    }

}

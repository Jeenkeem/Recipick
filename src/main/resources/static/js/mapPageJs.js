const urlParams = new URLSearchParams(window.location.search);
const fromCompare = urlParams.get("from") === "compare";
const highlightMarket = new URLSearchParams(window.location.search).get("highlight");


const RED_MARKER = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
let selectedMarts = []; // 선택한 마트 이름 2개 저장
let selectedMarkers = {}; // 마트 이름 → 마커 객체 연결

const mapContainer = document.getElementById('map');
const mapOption = {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level: 7
};

const map = new kakao.maps.Map(mapContainer, mapOption);
const ps = new kakao.maps.services.Places();

console.log(martNames);

martNames.forEach(mart => {
    ps.keywordSearch(mart, function (data, status) {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            const place = data[0];

            const marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(place.y, place.x),
                title: place.place_name
            });

            // 인포윈도우 생성
            const infowindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px; font-size:13px;">${place.place_name}</div>`
            });

            // 마커 클릭 이벤트 등록
            kakao.maps.event.addListener(marker, 'click', function () {
                infowindow.open(map, marker);
            });

        } else {
            console.warn(`❌ ${mart} 검색 결과 없음`);
        }
    }, {
        rect: "126.76,37.41,127.23,37.71"
    });
});


// 비교 장보기에서 선택한 시장의 마커 하이라이트
if (highlightMarket) {
  console.log("📌 하이라이트 마트:", highlightMarket);
  searchMarket(highlightMarket, true); // 추가 인자 전달
}


function searchMarket(keyword, focus = false) {
    const kakaoApiKey = 'c3c9b9b585c852112db76e368206e453'; // 여기에 REST 키 넣기

    fetch(`https://dapi.kakao.com/v2/local/search/keyword.json?query=${encodeURIComponent(keyword)}`, {
        method: 'GET',
        headers: {
            'Authorization': 'KakaoAK ' + kakaoApiKey
        }
    })
    .then(res => res.json())
    .then(result => {
        if (result.documents && result.documents.length > 0) {
            console.log(`"${keyword}" 검색 결과:`, result.documents);

            // 결과를 지도에 마커로 찍는 예시
            const place = result.documents[0]; // 가장 첫 번째 결과 사용
            const marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(place.y, place.x),
                title: place.place_name
            });

            const infowindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px;">${place.place_name}</div>`
            });
            infowindow.open(map, marker);

            // 👉 마커 객체 저장 (클릭 시 접근 위해)
            selectedMarkers[place.place_name] = marker;

            // 👉 마커 클릭 이벤트 연결
            kakao.maps.event.addListener(marker, 'click', function () {
                handleMarkerClick(place.place_name);
            });
            if (focus) {
                map.setLevel(5); // 확대
                map.panTo(new kakao.maps.LatLng(place.y, place.x)); // 카메라 이동
            }
        } else {
            console.warn(`"${keyword}"에 대한 검색 결과가 없습니다.`);
        }
    })
    .catch(err => {
        console.error(`"${keyword}" 검색 중 오류 발생:`, err);
    });
}


function handleMarkerClick(martName) {
    if (!fromCompare) {
        console.log("❌ 비교장보기 진입이 아님. 마커 선택 불가");
        return;
      }

    const marker = selectedMarkers[martName];

    // 이미 선택된 경우: 제거 + 마커 원래대로
    if (selectedMarts.includes(martName)) {
        selectedMarts = selectedMarts.filter(m => m !== martName);
        marker.setImage(null); // 기본 파란 마커로 복원
        console.log(`❌ 선택 해제: ${martName}`);
        return;
    }

    // 2개 초과 선택 방지
    if (selectedMarts.length >= 2) {
        alert("2개까지만 선택할 수 있어요.");
        return;
    }

    // 새로 선택: 빨간색 마커 적용
    selectedMarts.push(martName);
    const markerImage = new kakao.maps.MarkerImage(
        RED_MARKER,
        new kakao.maps.Size(24, 35)
    );
    marker.setImage(markerImage);
    console.log(`✅ 선택됨: ${martName}`);

    // 2개 다 선택되면 localStorage에 저장
    if (selectedMarts.length === 2) {
        localStorage.setItem("mart1", selectedMarts[0]);
        localStorage.setItem("mart2", selectedMarts[1]);
        console.log(`📝 저장됨: mart1=${selectedMarts[0]}, mart2=${selectedMarts[1]}`);

        if(confirm(`${selectedMarts[0]}, ${selectedMarts[1]}을 선택했습니다.\n비교장보기 페이지로 이동할까요?`)) {
            window.location.href = "/recipick/comparePage";
        }
    }
}

// 토스트 메시지 출력 함수
function showCompareToast() {
    const toast = document.getElementById('toast');
    if (!toast) return;

    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// 페이지 로딩 시 쿼리 파라미터 확인
window.addEventListener('DOMContentLoaded', () => {
    if (fromCompare) {
        showCompareToast();
    }
});
document.addEventListener('DOMContentLoaded', function () {
// ---------- [공통 변수 및 DOM] ----------
// 지도/마커 관련
const urlParams = new URLSearchParams(window.location.search);
const fromCompare = urlParams.get("from") === "compare";
const highlightMarket = urlParams.get("highlight");
const martNames = window.martNames || []; // Thymeleaf에서 주입된 시장 이름 배열

// 패널 및 버튼
const panelMart = document.getElementById('slidePanelIngreSearch'); // 마트정보(지도 클릭시)
const panelIngredient = document.getElementById('slidePanel');      // 식재료(버튼 클릭시)
const openBtn = document.getElementById('openPanelBtn');
const arrowBtn = document.getElementById('panelArrowBtn');
const martPanelArrowBtn = document.getElementById('martPanelArrowBtn');
const martPanelOpenBtn = document.getElementById('martPanelOpenBtn')

// 마트 정보 패널 내부
const martTitle = document.getElementById('martTitle');
const martInfoContainer = document.getElementById('martInfoContainer');

// 식재료 패널 내부
const ingredientButtonsContainer = document.getElementById('ingredient-buttons');
const searchInput = document.querySelector('.search-input');
const ingredientName = document.getElementById('ingredientName');
const storeList = document.getElementById('storeList');
const guFilter = document.getElementById('guFilter');
const sortSelect = document.querySelector('.sort-options');

// 상태 변수
let selectedButton = null;       // 식재료 버튼
let isPanelOpen = false;         // 식재료 패널
let isMartPanelOpen = false;     // 마트 패널
let openBtnTimeout = null;

let activeMarker = null;
let activeOverlay = null;

let activeInfowindow = null;
let selectedMarts = [];
let selectedMarkers = {};
let selectedOverlays = {};

// kakao map 초기화
const mapContainer = document.getElementById('map');
const mapOption = { center: new kakao.maps.LatLng(37.5665, 126.9780), level: 7 };
const map = new kakao.maps.Map(mapContainer, mapOption);
const ps = new kakao.maps.services.Places();

// 마인
        if(confirm(`${selectedMarts[0]}, ${selectedMarts[1]}을 선택했습니다.\n비교장보기 페이지로 이동할까요?`)) {
            window.location.href = "/recipick/comparePage";
        }
        else {
            // 마지막 선택한 것만 남기고 나머지 해제
            const last = selectedMarts[1];  // 마지막에 선택된 마트
            const prev = selectedMarts[0];  // 그 전에 선택된 마트
            const prevMarker = selectedMarkers[prev];
            if (prevMarker) prevMarker.setImage(defaultMarkerImage);
            if (selectedOverlays[prev]) selectedOverlays[prev].setMap(null);

            // selectedMarts를 마지막 것만 남김
            selectedMarts = [last];
            return;
        }
    }
}

// 비교장보기 토스트 메시지
function showCompareToast() {
    const toast = document.getElementById('toast');
    if (!toast) return;
    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// 마트 하이라이트 처리
if (highlightMarket) {
    console.log("📌 하이라이트 마트:", highlightMarket);

    const tryHighlight = () => {
        const marker = selectedMarkers[highlightMarket];
        const overlay = selectedOverlays[highlightMarket];

        if (marker && overlay) {
            // 기존 마커/오버레이 닫기
            if (activeMarker && activeMarker !== marker) activeMarker.setImage(defaultMarkerImage);
            if (activeOverlay && activeOverlay !== overlay) activeOverlay.setMap(null);

            // 마커 빨간색, 지도 중심 이동, 오버레이/패널 열기
            marker.setImage(redMarkerImage);
            map.setLevel(3); // 더 확대하려면 더 작은 숫자(2 등)
            map.panTo(marker.getPosition());
            overlay.setMap(map);

            fetchMartInfo(highlightMarket, marker, overlay);
            openMartPanel(marker, overlay);

            activeMarker = marker;
            activeOverlay = overlay;

            clearInterval(highlightInterval); // 더 이상 반복 안함
        }
    };

    const highlightInterval = setInterval(tryHighlight, 100);
    setTimeout(() => clearInterval(highlightInterval), 2000); // 2초 뒤 종료
}

// 비교장보기 진입 토스트
if (fromCompare) {
    showCompareToast();
}

// 패널 초기 상태
panelMart.style.display = 'none';
isMartPanelOpen = false;
panelIngredient.style.display = 'none';
isPanelOpen = false;
updateArrowBtns();
updateOpenBtnVisibility();

// irdntNames 파라미터가 있으면 자동으로 식재료 추가
const irdntsFromUrl = getIrdntsFromUrl();
if (irdntsFromUrl.length > 0) {
    irdntsFromUrl.forEach(name => addIngredient(name));

    // 첫 번째 식재료를 선택된 버튼으로 설정하여 자동 조회
    const firstButton = ingredientButtonsContainer.querySelector('.ingredient-button:not(.all-button)');
    if (firstButton) {
        firstButton.classList.add('selected');
        selectedButton = firstButton;
        ingredientName.textContent = firstButton.firstChild.textContent;
        openPanel(); // 패널 열기
        sendSelectedIngredient(firstButton.firstChild.textContent);
    }
}

function getIrdntsFromUrl() {
    const params = new URLSearchParams(window.location.search);
    const irdnts = params.get('irdntNames');
    if (!irdnts) return [];
    return irdnts.split(',').map(name => decodeURIComponent(name.trim()));
}


});

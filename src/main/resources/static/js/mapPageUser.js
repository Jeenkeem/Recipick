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

// 마커 이미지
const DEFAULT_MARKER = '/resources/image/marker.svg';
const RED_MARKER = '/resources/image/red_marker.svg';
const defaultMarkerImage = new kakao.maps.MarkerImage(DEFAULT_MARKER, new kakao.maps.Size(39, 44), { offset: new kakao.maps.Point(19, 44) });
const redMarkerImage = new kakao.maps.MarkerImage(RED_MARKER, new kakao.maps.Size(39, 44), { offset: new kakao.maps.Point(19, 44) });

// ---------- [1. 시장 마커 생성 및 클릭시 패널 오픈] ----------
martNames.forEach(mart => {
    ps.keywordSearch(mart, function (data, status) {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            const place = data[0];

            const marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(place.y, place.x),
                title: place.place_name,
                image: defaultMarkerImage
            });

            // 오버레이 컨텐츠 (간단히 place_name만 표시)
            var overlayContent = `<div class="markerPlaceName">${place.place_name}</div>`;
            var overlay = new kakao.maps.CustomOverlay({
                content: overlayContent,
                position: marker.getPosition(),
                yAnchor: 2.3
            });
            overlay.setMap(null);

            // 마커 클릭시
            kakao.maps.event.addListener(marker, 'click', function () {
                // 1. 같은 마커 다시 클릭시 닫기
                if (activeMarker === marker) {
                    marker.setImage(defaultMarkerImage);
                    overlay.setMap(null);
                    activeMarker = null;
                    activeOverlay = null;
                    updateArrowBtns();
                    closeMartPanel();
                    return;
                }
                // 2. 기존 마커/오버레이 닫기
                if (activeMarker) {
                    activeMarker.setImage(defaultMarkerImage);
                }
                if (activeOverlay) {
                    activeOverlay.setMap(null);
                }
                // 3. 현 마커/오버레이 열기
                marker.setImage(redMarkerImage);
                overlay.setMap(map);
                fetchMartInfo(place.place_name, marker, overlay);
                openMartPanel();

                activeMarker = marker;
                activeOverlay = overlay;
            });

            selectedMarkers[place.place_name] = marker;
            selectedOverlays[place.place_name] = overlay;

            // 비교장보기 모드 마커 선택/해제
            kakao.maps.event.addListener(marker, 'click', function () {
                handleMarkerClick(place.place_name);
            });
        }
    }, { rect: "126.76,37.41,127.23,37.71" });
});

// ---------- [2. 마트 상세 패널 기능] ----------

function fetchMartInfo(martName, marker, overlay) {
    fetch(`/recipick/martInfo?martName=${encodeURIComponent(martName)}`)
        .then(response => response.json())
        .then(data => {
            renderMartInfo(martName, data);
            openMartPanel(marker, overlay); // 마커, 오버레이 넘기기
        })
        .catch(err => {
            console.error(`❌ ${martName}에 대한 데이터 불러오기 실패`, err);
        });
}

function renderMartInfo(martName, martItems) {
    martTitle.textContent = martName;

    martInfoContainer.innerHTML = '';
    if (!martItems || martItems.length === 0) {
        martInfoContainer.innerHTML = `<div class="empty-message">텅..</div>`;
        return;
    }
    martItems.forEach(item => {
        const div = document.createElement('div');
        div.classList.add('ingredient-item');
        div.innerHTML = `
            <div class="ingredient-list">
                <div class="ingredient">${item.aName}</div>
                <div class="ingredient-price">가격 ${item.aPrice.toLocaleString()}원</div>
            </div>
        `;
        martInfoContainer.appendChild(div);
    });
}

function openMartPanel(marker, overlay) {
    closePanel();
    deselectIngredientButtons();

    // 기존 마커 비활성화
    if (activeMarker && activeMarker !== marker) {
        activeMarker.setImage(defaultMarkerImage);
        if (activeOverlay) activeOverlay.setMap(null);
    }

    // 새 마커 활성화
    if (marker && overlay) {
        marker.setImage(redMarkerImage);
        overlay.setMap(map);
        activeMarker = marker;
        activeOverlay = overlay;
    } else {
        activeMarker = null;
        activeOverlay = null;
    }

    if (isMartPanelOpen) return;

    panelMart.style.display = 'block';
    requestAnimationFrame(() => {
        panelMart.classList.add('open');
    });

    isMartPanelOpen = true;
    updateArrowBtns();
    updateOpenBtnVisibility();
}


function closeMartPanel() {
    if (!isMartPanelOpen) return;
    panelMart.classList.remove('open');
    isMartPanelOpen = false;
    if (activeMarker) {
        activeMarker.setImage(defaultMarkerImage);
        activeMarker = null;
    }
    if (activeInfowindow) {
        activeInfowindow.close();
        activeInfowindow = null;
    }
    panelMart.addEventListener('transitionend', function handler(event) {
        if (event.propertyName === 'transform') {
            if (!isMartPanelOpen) panelMart.style.display = 'none';
            panelMart.removeEventListener('transitionend', handler);
        }
    });
    updateArrowBtns();
    updateOpenBtnVisibility();
}

// 마트 패널 열 때(즉, 마커 클릭할 때) 식재료 버튼 선택 해제
function deselectIngredientButtons() {
    const selected = ingredientButtonsContainer.querySelector('.ingredient-button.selected');
    if (selected) selected.classList.remove('selected');
    selectedButton = null;
}

// ---------- [3. 식재료 패널 기능 (검색/추가/삭제/전체조회)] ----------
// 전체조회 버튼 동적생성
const allButton = document.createElement('div');
allButton.className = 'ingredient-button';
allButton.textContent = '전체 조회하기';
allButton.style.display = 'none';
ingredientButtonsContainer.appendChild(allButton);

allButton.addEventListener('click', function () {
    const allIngredientButtons = ingredientButtonsContainer.querySelectorAll('.ingredient-button');
    if (selectedButton === allButton) {
        allButton.classList.remove('selected');
        selectedButton = null;
        closePanel();
        updateOpenBtnVisibility();
        return;
    }
    allIngredientButtons.forEach(btn => btn.classList.remove('selected'));
    allButton.classList.add('selected');
    selectedButton = allButton;
    ingredientName.textContent = '전체 식재료';
    openPanel();

    const selectedIngredients = Array.from(ingredientButtonsContainer.children)
        .filter(btn => btn !== allButton)
        .map(btn => btn.firstChild.textContent);
    sendMultipleIngredients(selectedIngredients);
});

// 식재료 버튼 추가, 삭제, 선택
function createIngredientButton(name) {
    const button = document.createElement('div');
    button.className = 'ingredient-button';
    const label = document.createElement('span');
    label.textContent = name;
    const removeBtn = document.createElement('span');
    removeBtn.textContent = '×';
    removeBtn.className = 'remove-btn';
    button.appendChild(label);
    button.appendChild(removeBtn);
    handleButtonClick(button);
    return button;
}

function addIngredient(name) {
    const exists = Array.from(ingredientButtonsContainer.children).some(
        btn => btn !== allButton && btn.firstChild.textContent === name
    );

    if (!exists) {
        const button = createIngredientButton(name);
        ingredientButtonsContainer.insertBefore(button, allButton);

        updateAllButtonVisibility();
        updateOpenBtnVisibility();
    }
}

function handleButtonClick(button) {
    button.addEventListener('click', function (event) {
        if (event.target.classList.contains('remove-btn')) return;

        if (selectedButton === button) {
            button.classList.remove('selected');
            closePanel();
            selectedButton = null;
            updateOpenBtnVisibility();
            return;
        }

        if (selectedButton) selectedButton.classList.remove('selected');
        button.classList.add('selected');
        selectedButton = button;
        sendSelectedIngredient(button.firstChild.textContent);
        openPanel();
        ingredientName.textContent = button.firstChild.textContent;
    });

    const removeBtn = button.querySelector('.remove-btn');

    removeBtn.addEventListener('click', function (event) {
        event.stopPropagation();
        if (selectedButton === button) {
            closePanel();
            selectedButton = null;
        }
        button.remove();
        updateAllButtonVisibility();
        updateOpenBtnVisibility();
    });
}


// Enter 키로 검색
searchInput.addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        handleSearch();
    }
});

const searchIcon = document.getElementById('search-icon');

// 돋보기 클릭으로 검색
searchIcon.addEventListener('click', function () {
    handleSearch();
});

// 검색 처리 함수
function handleSearch() {
    const ingredient = searchInput.value.trim();
    if (ingredient) {
        addIngredient(ingredient);
        searchInput.value = '';
    }
}

function updateAllButtonVisibility() {
    const ingredientButtons = Array.from(ingredientButtonsContainer.children).filter(
        el => el !== allButton
    );
    allButton.style.display = ingredientButtons.length >= 2 ? 'inline-flex' : 'none';
}

// 식재료 패널 서버 전송 함수
let currentStoreData = [];

function sendSelectedIngredient(name) {
    fetch('/recipick/select', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ingredient: name })
    })
    .then(response => response.json())
    .then(data => {
        updateStoreList(data);
    });
}
function sendMultipleIngredients(ingredientList) {
    fetch('/recipick/selectAll', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ingredients: ingredientList })
    })
    .then(response => response.json())
    .then(data => {
        updateStoreList(data);
    });
}

// 식재료 패널 데이터 렌더링 (기존 mapPageSearch.js)
function updateStoreList(storeData) {
    currentStoreData = storeData;
    if (selectedButton === allButton) {
        updateAllStoreSummary();
    } else {
        applyGuFilter();
    }
}
function applyGuFilter() {
    const selectedGu = guFilter.value;
    const selectedSort = sortSelect.value;
    storeList.innerHTML = '';
    let filtered = selectedGu === 'all'
        ? currentStoreData
        : currentStoreData.filter(item => item.mGuName === selectedGu);

    if (selectedSort === '가격순') {
        filtered = filtered.slice().sort((a, b) => a.aPrice - b.aPrice);
    }
    if (filtered.length === 0) {
        storeList.innerHTML = '<div>선택한 자치구에 해당하는 가게가 없습니다.</div>';
        return;
    }
    filtered.forEach(item => {
        const storeItem = document.createElement('div');
        storeItem.className = 'store-item';
        const formattedPrice = item.aPrice.toLocaleString();
        storeItem.innerHTML = `
          <div class="store-header">
            <div class="store-name">${item.mName || '가게'}</div>
            <div class="gu-name">${item.mGuName}</div>
          </div>
          <div class="store-contents">
            <div class="ingredient">${item.aName}</div>
            <div class="ingredient-price">가격 ${formattedPrice}원(개)</div>
          </div>
        `;
        storeList.appendChild(storeItem);
    });
}
function updateAllStoreSummary() {
    const selectedGu = guFilter.value;
    const selectedSort = sortSelect.value;
    storeList.innerHTML = '';
    if (!Array.isArray(currentStoreData) || currentStoreData.length === 0) {
        storeList.innerHTML = '<div>검색된 가게가 없습니다.</div>';
        return;
    }
    const selectedIngredients = Array.from(ingredientButtonsContainer.children)
        .filter(btn => btn !== allButton)
        .map(btn => btn.firstChild.textContent);
    if (selectedIngredients.length === 0) {
        storeList.innerHTML = '<div>선택된 식재료가 없습니다.</div>';
        return;
    }
    let matchingMarts = currentStoreData.filter(mart => {
        return selectedIngredients.every(ing => mart.ingredientsIncluded.includes(ing));
    });
    if (selectedGu !== 'all') {
        matchingMarts = matchingMarts.filter(mart => mart.mGuName === selectedGu);
    }
    if (selectedSort === '가격순') {
        matchingMarts.sort((a, b) => a.totalPrice - b.totalPrice);
    }
    if (matchingMarts.length === 0) {
        storeList.innerHTML = '<div>모든 식재료를 포함한 마트가 없습니다.</div>';
        return;
    }
    matchingMarts.forEach(mart => {
        const guName = mart.mGuName || '정보 없음';
        const storeItem = document.createElement('div');
        storeItem.className = 'store-item';
        storeItem.innerHTML = `
            <div>
                <div class="store-header">
                    <div class="store-name">${mart.mName}</div>
                    <div class="gu-name">${guName}</div>
                </div>
                <div class="ingredient-price">총합 가격: ${mart.totalPrice.toLocaleString()}원</div>
            </div>
        `;
        storeList.appendChild(storeItem);
    });
}

// ---------- [4. 패널/화살표/열기버튼 상태관리] ----------

function openPanel() {

    // 마트 패널이 열려있으면 닫기 + 마커 기본화
    closeMartPanel();

    // 마커가 빨간색(activeMarker 존재)면 비활성화! ⭐️
    if (activeMarker) {
        activeMarker.setImage(defaultMarkerImage);
        activeMarker = null;
    }

    if (activeOverlay) {
            activeOverlay.setMap(null);
            activeOverlay = null;
        }

    // 식재료 패널 오픈
    if (isPanelOpen) return;
    panelIngredient.style.display = 'block';
    requestAnimationFrame(() => {
        panelIngredient.classList.add('open');
    });
    isPanelOpen = true;
    updateArrowBtns();
    updateOpenBtnVisibility();
}

function closePanel() {
    if (!isPanelOpen) return;

    panelIngredient.classList.remove('open');
    isPanelOpen = false;

    panelIngredient.addEventListener('transitionend', function handler(event) {
        if (event.propertyName === 'transform') {
            if (!isPanelOpen) panelIngredient.style.display = 'none';
            panelIngredient.removeEventListener('transitionend', handler);
        }
    });

    updateArrowBtns();
    updateOpenBtnVisibility();
}



if (martPanelArrowBtn) {
   martPanelArrowBtn.addEventListener('click', function() {
       panelMart.classList.remove('open');
       isMartPanelOpen = false;
       // 패널만 숨김 (마커는 그대로!)
       panelMart.addEventListener('transitionend', function handler(event) {
           if (event.propertyName === 'transform') {
               if (!isMartPanelOpen) panelMart.style.display = 'none';
               panelMart.removeEventListener('transitionend', handler);
           }
       });
       updateArrowBtns();
   });
}

if (martPanelOpenBtn) {
   martPanelOpenBtn.addEventListener('click', function() {
       // 다시 패널 오픈 (마커와 인포윈도우는 이미 활성화된 상태)
       panelMart.style.display = 'block';
       requestAnimationFrame(() => {
           panelMart.classList.add('open');
       });
       isMartPanelOpen = true;
       updateArrowBtns();
   });
}


function updateArrowBtns() {
    if (isPanelOpen) {
        openBtn.style.display = 'none';
        arrowBtn.style.display = 'flex';
        if (martPanelArrowBtn) martPanelArrowBtn.style.display = 'none';
        if (martPanelOpenBtn) martPanelOpenBtn.style.display = 'none';
    } else if (isMartPanelOpen) {
        openBtn.style.display = 'none';
        if (martPanelArrowBtn) martPanelArrowBtn.style.display = 'flex';
        if (martPanelOpenBtn) martPanelOpenBtn.style.display = 'none';
        arrowBtn.style.display = 'none';
    } else if (activeMarker) {
        openBtn.style.display = 'none';
        if (martPanelArrowBtn) martPanelArrowBtn.style.display = 'none';
        if (martPanelOpenBtn) martPanelOpenBtn.style.display = 'flex';
        arrowBtn.style.display = 'none';
    } else {
        // *** 여기서 조건을 강화한다 ***
        // 선택된 식재료 버튼이 있을 때만 보여준다
        const hasSelectedIngredient = !!ingredientButtonsContainer.querySelector('.ingredient-button.selected');
        openBtn.style.display = hasSelectedIngredient ? 'flex' : 'none';
        if (martPanelArrowBtn) martPanelArrowBtn.style.display = 'none';
        if (martPanelOpenBtn) martPanelOpenBtn.style.display = 'none';
        arrowBtn.style.display = 'none';
    }
}




function updateOpenBtnVisibility() {
    const hasSelectedIngredient = !!ingredientButtonsContainer.querySelector('.ingredient-button.selected');
    if (openBtnTimeout) {
        clearTimeout(openBtnTimeout);
        openBtnTimeout = null;
    }
    if (hasSelectedIngredient && !isPanelOpen) {
        openBtn.style.display = 'none';
        openBtnTimeout = setTimeout(() => {
            openBtn.style.display = 'flex';
            openBtnTimeout = null;
        }, 300);
    } else {
        openBtn.style.display = 'none';
    }
}

// 열기/닫기 버튼 이벤트 연결
openBtn.addEventListener('click', openPanel);
arrowBtn.addEventListener('click', closePanel);

// ---------- [5. 필터, 검색, 기타] ----------
guFilter.addEventListener('change', () => {
    if (selectedButton === allButton) {
        updateAllStoreSummary();
    } else {
        applyGuFilter();
    }
});

sortSelect.addEventListener('change', () => {
    if (selectedButton === allButton) {
        updateAllStoreSummary();
    } else {
        applyGuFilter();
    }
});


// 마트 패널 내 식재료 검색
document.getElementById('ingredientSearch').addEventListener('input', function (e) {
    const keyword = e.target.value.trim().toLowerCase();
    const items = martInfoContainer.querySelectorAll('.ingredient-item');
    items.forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = !keyword || text.includes(keyword) ? 'block' : 'none';
    });
});

// 비교장보기 마커 클릭용 함수
function handleMarkerClick(martName) {
    if (!fromCompare) return;

    const marker = selectedMarkers[martName];

    if (selectedMarts.includes(martName)) {
        selectedMarts = selectedMarts.filter(m => m !== martName);
        marker.setImage(defaultMarkerImage);
        activeMarker = null;
        activeOverlay = null;
        return;
    }
    if (selectedMarts.length >= 2) {
        alert("2개까지만 선택할 수 있어요.");
        return;
    }

    selectedMarts.push(martName);

    if (selectedMarts.length === 2) {
        localStorage.setItem("mart1", selectedMarts[0]);
        localStorage.setItem("mart2", selectedMarts[1]);
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

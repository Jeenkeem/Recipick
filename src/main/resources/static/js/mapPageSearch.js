document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.querySelector('.search-input');
    const ingredientButtonsContainer = document.getElementById('ingredient-buttons');
    const panel = document.getElementById('slidePanel');
    const ingredientName = document.getElementById('ingredientName');
    const storeList = document.getElementById('storeList');

    let selectedButton = null;
    let isPanelOpen = false;

    const allButton = document.createElement('div');
    allButton.className = 'ingredient-button';
    allButton.textContent = '전체 조회하기';
    allButton.style.display = 'none'; // 초기 숨김
    ingredientButtonsContainer.appendChild(allButton); // 항상 맨 마지막에 위치

    allButton.addEventListener('click', function () {
        // 기존 선택된 버튼 초기화
        const allIngredientButtons = ingredientButtonsContainer.querySelectorAll('.ingredient-button');
        allIngredientButtons.forEach(btn => btn.classList.remove('selected'));

        allButton.classList.add('selected');
        selectedButton = allButton;

        ingredientName.textContent = '전체 식재료';
        openPanel();

        // 전체 식재료 목록 추출
        const selectedIngredients = Array.from(ingredientButtonsContainer.children)
            .filter(btn => btn !== allButton)
            .map(btn => btn.firstChild.textContent);

        // 서버에 전체 식재료 요청
        sendMultipleIngredients(selectedIngredients);
    });

    function sendMultipleIngredients(ingredientList) {
        fetch('/recipick/selectAll', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredients: ingredientList })  // 배열로 전송
        })
        .then(response => {
            if (!response.ok) throw new Error('서버 오류');
            return response.json();
        })
        .then(data => {
            console.log('전체 식재료 서버 응답:', data);
            updateStoreList(data);     // 내부적으로 applyGuFilter() 실행됨
            //updateAllStoreSummary();   // 마트별 가격 합계 계산 및 출력
        })
        .catch(error => {
            console.error('전체 식재료 전송 실패:', error);
        });
    }


    // 슬라이드 패널 열기
    function openPanel() {
        if (isPanelOpen) return;
        panel.style.display = 'block';
        requestAnimationFrame(() => {
            panel.classList.add('open');
        });
        isPanelOpen = true;
    }

    // 슬라이드 패널 닫기
    function closePanel() {
        if (!isPanelOpen) return;
        panel.classList.remove('open');
        isPanelOpen = false;

        panel.addEventListener('transitionend', function handler(event) {
            if (event.propertyName === 'transform') {
                if (!isPanelOpen) {
                    panel.style.display = 'none';
                }
                panel.removeEventListener('transitionend', handler);
            }
        });
    }

    function updateAllButtonVisibility() {
        const ingredientButtons = Array.from(ingredientButtonsContainer.children).filter(
            el => el !== allButton
        );
        allButton.style.display = ingredientButtons.length >= 2 ? 'inline-flex' : 'none';
    }

    // 버튼 클릭 시 슬라이드 패널 보여주는 이벤트
    function handleButtonClick(button) {
        button.addEventListener('click', function (event) {
            if (event.target.classList.contains('remove-btn')) return;

            if (selectedButton === button) {
                button.classList.remove('selected');
                closePanel();
                selectedButton = null;
                return;
            }

            if (selectedButton) {
                selectedButton.classList.remove('selected');
            }

            button.classList.add('selected');
            selectedButton = button;

            sendSelectedIngredient(button.firstChild.textContent); // 서버 전송

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
        });
    }

    let currentStoreData = [];  // 전체 store data 저장용

    function updateStoreList(storeData) {
        currentStoreData = storeData;
        if (selectedButton === allButton) {
            updateAllStoreSummary();  //  전체 선택 시에는 특별 출력 함수 호출
        } else {
            applyGuFilter();          //  개별 식재료 선택 시 기본 출력
        }
    }

    function applyGuFilter() {
        const guFilter = document.getElementById('guFilter');
        const sortSelect = document.querySelector('.sort-options');
        const selectedGu = guFilter.value;
        const selectedSort = sortSelect.value;
        storeList.innerHTML = '';

        // 자치구 필터
        let filtered = selectedGu === 'all'
            ? currentStoreData
            : currentStoreData.filter(item => item.mGuName === selectedGu);

        // 가격순 정렬 옵션
        if (selectedSort === '가격순') {
            filtered = filtered.slice().sort((a, b) => a.aPrice - b.aPrice);
        }

        // 결과 없을 때
        if (filtered.length === 0) {
            storeList.innerHTML = '<div>선택한 자치구에 해당하는 가게가 없습니다.</div>';
            return;
        }

        filtered.forEach((item, index) => {
            const storeItem = document.createElement('div');
            storeItem.className = 'store-item';
            storeItem.innerHTML = `
                <div>
                    <div class="store-name">${item.mName || '가게'}</div>
                    <div>${item.aName} 가격 ${item.aPrice}원</div>
                    <div>${item.mGuName}</div>
                </div>
                <!-- <div class="check-icon">✔</div> -->
            `;
            storeList.appendChild(storeItem);
        });
    }



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
            ingredientButtonsContainer.insertBefore(button, allButton); // always before allButton
            updateAllButtonVisibility();
        }
    }

    // 검색창에서 Enter 입력 시 버튼 추가
    searchInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            const ingredient = searchInput.value.trim();
            if (ingredient) {
                addIngredient(ingredient);
                searchInput.value = '';
            }
        }
    });

    function sendSelectedIngredient(name) {
        fetch('/recipick/select', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredient: name })
        })
        .then(response => {
            if (!response.ok) throw new Error('서버 오류');
            return response.json();
        })
        .then(data => {
            console.log('서버 응답:', data);
            updateStoreList(data);
        })
        .catch(error => {
            console.error('전송 실패:', error);
        });
    }

    function updateAllStoreSummary() {
        const guFilter = document.getElementById('guFilter');
        const sortSelect = document.querySelector('.sort-options');
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

        // 모든 식재료를 포함한 마트만 필터링
        let matchingMarts = currentStoreData.filter(mart => {
            return selectedIngredients.every(ing => mart.ingredientsIncluded.includes(ing));
        });

        if (matchingMarts.length === 0) {
            storeList.innerHTML = '<div>모든 식재료를 포함한 마트가 없습니다.</div>';
            return;
        }

        // 자치구 필터 적용
        if (selectedGu !== 'all') {
            matchingMarts = matchingMarts.filter(mart => mart.mGuName === selectedGu);
        }

        //  정렬 적용
        if (selectedSort === '가격순') {
            matchingMarts.sort((a, b) => a.totalPrice - b.totalPrice);
        }

        //  결과 출력
        if (matchingMarts.length === 0) {
            storeList.innerHTML = '<div>해당 조건에 맞는 마트가 없습니다.</div>';
            return;
        }

        matchingMarts.forEach(mart => {
            const guName = mart.mGuName || '정보 없음';
            const storeItem = document.createElement('div');
            storeItem.className = 'store-item';
            storeItem.innerHTML = `
                <div>
                    <div class="store-name">${mart.mName}</div>
                    <div>자치구: ${guName}</div>
                    <div>총합 가격: ${mart.totalPrice.toLocaleString()}원</div>
                </div>
            `;
            storeList.appendChild(storeItem);
        });
    }





    const guFilter = document.getElementById('guFilter');
    guFilter.addEventListener('change', () => {
        if (selectedButton === allButton) {
            updateAllStoreSummary();
        } else {
            applyGuFilter();
        }
    });

    const sortSelect = document.querySelector('.sort-options');
    sortSelect.addEventListener('change', () => {
        if (selectedButton === allButton) {
            updateAllStoreSummary();
        } else {
            applyGuFilter();
        }
    });

});
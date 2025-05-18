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

    function updateStoreList(storeData) {
        storeList.innerHTML = ''; // 기존 리스트 제거

        if (!Array.isArray(storeData) || storeData.length === 0) {
            storeList.innerHTML = '<div>검색된 가게가 없습니다.</div>';
            return;
        }

        storeData.forEach((item, index) => {
            const storeItem = document.createElement('div');
            storeItem.className = 'store-item';
            storeItem.innerHTML = `
                <div>
                    <div class="store-name">${item.mName || '가게'}</div>
                    <div>${item.aName} 가격 ${item.aPrice}원</div>
                </div>
                <div class="check-icon">✔</div>
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

});
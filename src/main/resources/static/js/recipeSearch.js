document.addEventListener("DOMContentLoaded", function() {
    // 레시피 데이터 가져오기 (예시: /recipeCards 엔드포인트에서 데이터를 받음)
    fetch('/recipeCards')
        .then(response => response.json()) // 서버에서 JSON 형식으로 데이터 받기
        .then(data => {
            const container = document.getElementById("recipe-container");

            // 데이터로 카드 요소 동적으로 생성
            data.forEach(recipe => {
                const card = document.createElement("div");
                card.classList.add("recipe-card");

                // 카드 내용 구성
                card.innerHTML = `
                    <img src="${recipe.rcpImgUrl}" alt="레시피 이미지" />
                    <h3>${recipe.recipeNmKo}</h3>
                    <div class="recipe-meta">
                        <span class="difficulty">${recipe.levelNm}</span>
                        <span class="time">${recipe.cookingTime}</span>
                    </div>
                `;

                // 카드를 컨테이너에 추가
                container.appendChild(card);
            });
        })
        .catch(error => {
            console.error("레시피 데이터 로드 오류:", error);
        });
});
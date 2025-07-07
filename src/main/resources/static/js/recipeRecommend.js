function redirectToRecipe(name, id) {
    const encodedName = encodeURIComponent(name);
    const url = `http://recipick.kro.kr:8099/recipick/recipeInfo?recipe_name=${encodedName}&recipe_id=${id}`;
    location.href = url;
}

document.addEventListener('DOMContentLoaded', function () {

    new Vue({
        el: '#recipeRecommend-page',
        data: {
            ingredientName: '',
            allIngredients: [],
            filteredIngredients: [],
            clickedIngredient: [],
            recipeList: [],
            searched: false
        },
        mounted() {
            document.addEventListener('click', this.handleClickOutside);
        },
        beforeDestroy() {
            document.removeEventListener('click', this.handleClickOutside);
        },
        methods: {
            onInput(event) {
                const value = event.target.value;

                // 입력창이 비었으면 자동완성도 제거하고 종료
                if (!value.trim()) {
                    this.filteredIngredients = [];
                    return;
                }

                if (this.ingredientName === value) return;

                this.ingredientName = value;

                clearTimeout(this.inputDebounceTimer);
                this.inputDebounceTimer = setTimeout(() => {
                    this.searchIngredient(value);
                }, 200);
            },
            //input 실시간 검색 요청
            async searchIngredient(keyword){
                const trimmed = keyword.trim();
                if(!trimmed){
                    this.filteredIngredients = [];
                    return;
                }

                try{
                    const response = await axios.get('/recipick/rec/ingrnm',{
                        params:{ inputIngredient: trimmed}
                    });
                    //검색한 재료 리스트
                    this.filteredIngredients = response.data.map(item => item.IRDNT_NM)

                } catch (error){
                    console.log('실패', error);
                    this.filteredIngredients = [];
                }
            },

            selectedIngredient(item){
                //중복 선택 방지
                if(!this.clickedIngredient.includes(item)){
                    this.clickedIngredient.push(item);
                    this.filteredRecipe();
                }
                this.filteredIngredients = [];
            },

            removeIrdnt(index){
                this.clickedIngredient.splice(index, 1);
                this.filteredIngredients = []; // 자동완성 닫기
                this.filteredRecipe();
            },

            async filteredRecipe(){

                const trimmed = this.ingredientName.trim();

                // 입력값이 재료 리스트에 정확히 존재하는 경우만 추가
                if (trimmed && this.filteredIngredients.includes(trimmed)) {
                    // 중복 방지
                    if (!this.clickedIngredient.includes(trimmed)) {
                        this.clickedIngredient.push(trimmed);
                    }
                    this.ingredientName = '';            // 입력창 초기화
                }
                // else {
                //     return; // 재료리스트에 없으면 아무것도 안 함
                // }

                this.filteredIngredients = [];

                //칩 갱신후 레시피 검색
                if (!this.clickedIngredient.length) {
                    this.recipeList = [];
                    this.searched = false;  // ★ 빈칩이면 검색 안함 + 상태 초기화
                    return;
                }

                try{
                    const response = await axios.post('/recipick/rec/result', this.clickedIngredient);
                    this.recipeList = response.data;
                    this.searched = true;
                }
                catch(error){
                    console.error('레시피 검색 실패', error);
                    this.recipeList = [];
                    this.searched = false;
                }
            },

            getStars(levelNm){
                const levels = {
                    '초보환영': 1,
                    '보통': 2,
                    '어려움': 3
                };
                const count = levels[levelNm] || 0;
                return '★'.repeat(count) + '☆'.repeat(3 - count);
            },
            handleClickOutside(event) {
                const searchBox = this.$el.querySelector('.search-box');
                if (searchBox && !searchBox.contains(event.target)) {
                    this.filteredIngredients = [];
                }
            },
        }
    });
})

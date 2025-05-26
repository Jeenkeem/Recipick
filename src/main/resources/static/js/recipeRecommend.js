function redirectToRecipe(name, id) {
    const encodedName = encodeURIComponent(name);
    const url = `http://localhost:8080/recipick/recipeInfo?recipe_name=${encodedName}&recipe_id=${id}`;
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
        methods: {
            //input 실시간 검색 요청
            async searchIngredient(){
                const keyword = this.ingredientName.trim();
                if(keyword === ''){
                    this.filteredIngredients = [];
                    return;
                }

                try{
                    const response = await axios.get('/recipick/rec/ingrnm',{
                        params:{ inputIngredient: keyword}
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
                }
                this.filteredIngredients = [];
            },

            removeIrdnt(index){
                this.clickedIngredient.splice(index, 1);
            },

            async filteredRecipe(){
                if(!this.clickedIngredient.length) return;

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
            }
        }
    });
})

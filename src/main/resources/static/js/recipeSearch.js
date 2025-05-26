function redirectToRecipe(name, id) {
    const encodedName = encodeURIComponent(name);
    const url = `http://localhost:8080/recipick/recipeInfo?recipe_name=${encodedName}&recipe_id=${id}`;
    location.href = url;
}

document.addEventListener('DOMContentLoaded', function () {

new Vue({
    el: '#recipeSearch-page',
    data:{
        searchWord: '',
        results: [],
        searched: false
    },
    methods: {
        search(){
            //검색어가 비어있을시 검색x
            if(!this.searchWord.trim()) return;

            axios.get('/recipick/search', {
                params: {searchWord: this.searchWord}
            })
                .then(response =>{
                    console.log(response.data);
                    this.results = response.data;
                    this.searched = true;
                })
                .catch(error =>{
                    console.error('검색 오류', error);
                });
        }
    }
})
})

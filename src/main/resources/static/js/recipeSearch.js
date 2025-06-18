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
        },
        getStars(levelNm){
            const levels = {
                '초보환영': 1,
                '보통': 2,
                '어려움': 3
            };
            const count = levels[levelNm] || 0;
            return '★'.repeat(count) + '☆'.repeat(3 - count);
        }
    },
    mounted(){
        const urlParams = new URLSearchParams(window.location.search);
        const keyword = urlParams.get('keyword');
        if (keyword) {
            this.searchWord = decodeURIComponent(keyword);
            this.search(); // 자동 검색 실행
        }
    }
})
})

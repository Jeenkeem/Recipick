document.addEventListener('DOMContentLoaded', function () {

    new Vue({
        el: '#mainPage',
        data: {
            homeIngredientNm: ''
        },
        methods: {
            goToSearchPage(){
                if(this.homeIngredientNm.trim()){
                    const query = encodeURIComponent(this.homeIngredientNm.trim());
                    window.location.href = `/recipick/cards?keyword=${query}`
                }
            }
        }
    });
})
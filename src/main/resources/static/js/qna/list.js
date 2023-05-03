$(function(){
    var custid = sessionStorage.getItem("custid");
    $('#a_write').click(function (e){
        if(custid==='none' || custid===null || custid===''){
            e.preventDefault()
            alert('로그인 해주세요!')
        }
    })
})
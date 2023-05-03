$(function(){
    var custid = sessionStorage.getItem("custid");

    if(custid !== 'none' && custid!==null && custid!=='' && custid!==undefined){
        $("#login").css('display', 'none');
        $("#regist").css('display', 'none');
        $("#logout").css('display','inline-block');
        $("#mypage").css('display','inline-block');
    }

    $("#logout").click(function(e){
        if(confirm("로그아웃 하시겠습니까?")){
            sessionStorage.setItem("custid", 'none');
        }else{
            return false;
        }
    });
})

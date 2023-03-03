$(function(){
    // 알림 갯수
    $.ajax({
        url:"/countNChecked",
        success:function (cnt){
            $('#notif_not_checked').html(cnt)
        }
    })

    //알림 목록 불러오기
    function listNotification(){
        $("#notif_container").empty();
        $.ajax({
            url:"/listNotification",
            success:function (list){
                $.each(list,function (){
                    let div_no=$("<div></div>").text(this.notif_no)
                    let div_title=$("<a></a>").text(this.qna_title+"에 답글이 달렸습니다.")
                        .attr("href","/qna/detail/"+this.qna_no)
                    let div_x=$("<a></a>").text("X").addClass("x").attr("notif_no",this.notif_no)
                    let div_no_title=$("<div></div>").append(div_no, div_title, div_x)
                    $('#notif_container').append(div_no_title)
                })
                $('#notif_not_checked').html("")
            }
        })
    }

    // QNA 답변 알림
    let cnt_click=0
    $('#bell').click(function(){
        cnt_click += 1
            if (cnt_click % 2 == 1) {

                $("#notif_container").css("display", "block")
                listNotification()

                // DB 'checked' 칼럼 업데이트 n->y
                $.ajax({
                    url: "/updateCheckedToY",
                    success: function (re) {
                        console.log(re)
                    }
                })

            } else {
                $("#notif_container").css("display", "none")
            }
        console.log("cnt_click:",cnt_click)
    })

    $(document).on("click",".x",function (){
        $.ajax({
            url:"/deleteNotification",
            data:{notif_no:$(this).attr("notif_no")},
            success:function (){
                listNotification()
            }
        })
    })
})
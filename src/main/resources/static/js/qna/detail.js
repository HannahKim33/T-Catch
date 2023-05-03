$(function(){
    //답글 등록 클릭 시 처리
    $('#answer_submit').click(function (){
        let answer_input=$('.textarea_qna_answer').val()

        // null이면 insert, null 아니면 update
        let div_qna_answer=$('#div_qna_answer').html()
        let insertOrUpdate='insert'
        if(div_qna_answer!=""){
            insertOrUpdate='update'
        }
        let qna_no=$('#qna_no').text()
        let custid=$('#div_custid').text()
        //입력 내용 길이가 0보다 커야 ajax 실행
        if(answer_input.length>0) {
            $.ajax({
                url: "/qna/answer/update",
                data: {qna_no: qna_no,
                    qna_answer: answer_input,
                    custid: custid},
                success: function (re) {
                    if(re==1){
                        alert('등록했습니다.')
                        $('#div_qna_answer').text($('.textarea_qna_answer').val())
                        $('#answer_delete').removeAttr("hidden")

                        //알림생성과 이메일 발송
                        $.ajax({
                            url:"/qna/answer/notif_and_email",
                            data:{qna_no: qna_no,
                                insertOrUpdate: insertOrUpdate,
                                custid: custid},
                            success: function (re){
                                if(re==-1){
                                    alert('삭제된 글입니다.')
                                }
                            }
                        })
                    }
                }
            })
        }else{
            alert('내용을 입력해주세요')
        }
    })
    //답글 삭제
    $('#answer_delete').click(function (){
        if(confirm("정말로 삭제하시겠습니까?")) {
            $.ajax({
                url: "/qna/answer/delete",
                data: {qna_no: $('#qna_no').text()},
                success: function (re) {
                    if (re == 1) {
                        alert("삭제했습니다.")
                        // 삭제하면 화면에 내용 사라지게
                        $('#div_qna_answer').text("")
                        $('.textarea_qna_answer').val("")
                        // 삭제하면 삭제 버튼도 사라짐
                        $('#answer_delete').attr("hidden","hidden")
                    }else{
                        alert('삭제 실패')
                    }
                }
            })
        }
    })

    //답글 삭제 버튼 보이기/안 보이기 (답글이 있으면 보이고 없으면 안 보이게)
    let answer_length=$('#div_qna_answer').html().length
    console.log(answer_length)
    if(answer_length==0){
        $('#answer_delete').attr("hidden","hidden")
    }

    // 삭제(QNA 전체 삭제)하기 전에 묻기
    $('#a_delete').click(function (e){
        if(confirm('정말로 삭제하시겠습니까?')==false){
            e.preventDefault()
        }
    })
})
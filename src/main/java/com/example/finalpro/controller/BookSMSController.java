package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.service.CustomerService;
import com.example.finalpro.service.EmailService;
import com.example.finalpro.vo.BookVO;
import com.example.finalpro.vo.TicketVO;
import kr.co.youiwe.webservice.BitSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookSMSController {
    @Autowired
    private EmailService es;
    @Autowired
    private CustomerService cs;
    @GetMapping("/sendBookingConfirmation")
    public String sendBookingConfirmation(String custid, int ticketid, int seatid, String phone, String email){
        BookVO b=new BookVO(0, custid, ticketid, seatid);
        int bookid= DBManager.findBookidByOthers(b);
        TicketVO t=DBManager.findByTicketid(ticketid);
        String ticket_date=t.getTicket_date();
        String ticket_name=t.getTicket_name();
        String loc=t.getLoc();
        ticket_date=ticket_date.substring(0,16);
        String msg="[T-CATCH]\""+ticket_name+"\" 예약 완료. "+"예약번호: "+bookid+" ("
                +ticket_date+" "+loc+")";

        //SMS 보내기
        BitSms sms=new BitSms();
        sms.sendMsg("01025598279", phone, msg);

        //email 보내기
        String email_subject="[T-CATCH] 예약이 완료되었습니다";
        String msg_email="<h2>예약 확정</h2>"
                +"<div>"+"예약번호: "+bookid+"</div>"
                +"<div>"+"작품명: "+ticket_name+"</div>"
                +"<div>"+"상영일: "+ticket_date+"</div>"
                +"<div>"+"상영장소: "+loc+"</div>"
                +"<a href='http://localhost:8088/myPageBook"+"'>확인하기</a>";
        es.sendHtmlEmail(email, email_subject, msg_email);
        return msg;
    }
}
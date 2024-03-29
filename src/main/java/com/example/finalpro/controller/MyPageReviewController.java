package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Customer;
import com.example.finalpro.entity.MyPageReview;
import com.example.finalpro.service.CustomerService;
import com.example.finalpro.service.ReviewService;
import com.example.finalpro.service.TicketService;
import com.example.finalpro.util.Page;
import com.example.finalpro.vo.ReviewVO;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@Setter
public class MyPageReviewController {

    @Autowired
    private ReviewService rs;

    @Autowired
    private CustomerService cs;

    @Autowired
    private TicketService ts;

    @GetMapping({"/myPageReview/{pageNum}", "/myPageReview"})
    public ModelAndView MyPageReview(HttpSession session, @PathVariable(required = false) Integer pageNum){

        ModelAndView mav=new ModelAndView("/myPage/myPageReview");
        String loginId=(String) session.getAttribute("id");
        Customer loginCustomer=cs.findByCustid(loginId);
        List<MyPageReview> list=rs.findByCustid(loginCustomer);

        Page page=new Page(list.size(),3,5,pageNum);

        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("firstRecord",page.getStartNo());
        map.put("lastRecord",page.getEndNo());
        map.put("custid",loginId);

        mav.addObject("firstPage",page.getFirstPage());
        mav.addObject("lastPage",page.getLastPage());

        mav.addObject("totalPage",page.getTotalPage());
        mav.addObject("list",DBManager.listReviewByCustid(map));
        return mav;
    }

    //리뷰 등록 폼
    @GetMapping("/myPage/insertReview/{ticketid}")
    public ModelAndView insertForm(@PathVariable String ticketid){
        ModelAndView mav=new ModelAndView("/myPage/insertReview");
        mav.addObject("ticketid",ticketid);
        return mav;
    }

    @PostMapping("/myPage/insertReview")
    public ModelAndView insertSubmit(HttpSession session, ReviewVO r){
        ModelAndView mav=new ModelAndView();
        String loginId=(String) session.getAttribute("id");
        r.setCustid(loginId);
        int re=DBManager.insertReview(r);
        mav.setViewName("redirect:/myPageReview");
        return mav;
    }

    @GetMapping("/myPage/updateReview/{reviewid}")
    public ModelAndView updateForm(@PathVariable int reviewid){
        ModelAndView mav=new ModelAndView();
        Optional<MyPageReview> optionalMyPageReview=rs.findById(reviewid);
        MyPageReview r;
        if(optionalMyPageReview.isPresent()){
            r=optionalMyPageReview.get();
            mav.addObject("r",r);
            mav.setViewName("/myPage/updateReview");
        }else{
            mav.addObject("msg","존재하지 않는 리뷰입니다.");
            mav.setViewName("/error");
        }
        return mav;
    }

    @PostMapping("/myPage/updateReview")
    public ModelAndView updateSubmit(MyPageReview r, String custid, int ticketid){
        ModelAndView mav=new ModelAndView("redirect:/myPageReview");
        r.setCustomer(cs.findByCustid(custid));
        r.setTicket(ts.findByTicketid(ticketid).get());
        rs.save(r);
        return mav;
    }

    @GetMapping("/myPage/deleteReview/{reviewid}")
    public ModelAndView delete(@PathVariable int reviewid){
        ModelAndView mav=new ModelAndView("redirect:/myPageReview");
        Optional<MyPageReview> optionalMyPageReview=rs.findById(reviewid);
        MyPageReview r=new MyPageReview();
        if(optionalMyPageReview.isPresent()){
            r=optionalMyPageReview.get();
        }else{
            mav.setViewName("/error");
            mav.addObject("msg","존재하지 않는 리뷰입니다.");
        }
        rs.delete(r);
        return mav;
    }

//     같은 사용자, 같은 티켓의 리뷰가 있나 확인
    @GetMapping("/CheckReviewByTicketidCustid")
    @ResponseBody
    public int CheckReviewByTicketidCustid(String custid, int ticketid){
        ReviewVO r=new ReviewVO();
        r.setCustid(custid);
        r.setTicketid(ticketid);
        return DBManager.checkReviewByTicketidCustid(r);
    }

//    // 리뷰 등록 submit AJAX
//    @PostMapping("/InsertReview")
//    @ResponseBody
//    public int insertSubmit(String custid, int ticketid, int score, String review_content){
//        ReviewVO r=new ReviewVO(0, custid, ticketid, score, review_content);
//        return DBManager.insertReview(r);
//    }
}

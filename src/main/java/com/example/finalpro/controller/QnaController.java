package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Customer;
import com.example.finalpro.entity.Qna;
import com.example.finalpro.entity.Ticket;
import com.example.finalpro.function.page.Paging;
import com.example.finalpro.service.*;
import com.example.finalpro.util.Page;
import com.example.finalpro.util.Search;
import com.example.finalpro.vo.NotificationByCustidVO;
import com.example.finalpro.vo.NotificationVO;
import com.example.finalpro.vo.QnaVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@Setter
public class QnaController {
    @Autowired
    private QnaService qs;

    @Autowired
    private TicketService ts;

    @Autowired
    private Search search;

    @Autowired
    private EmailService es;

    @Autowired
    private CustomerService cs;

    @GetMapping("/qna/resetSearch")
    public ModelAndView resetSearch(HttpSession session){
        ModelAndView mav=new ModelAndView("redirect:/qna/list");
        session.removeAttribute("qnaKeyword");
        session.removeAttribute("qnaSearchColumn");
        return mav;
    }

    @RequestMapping({"/qna/list","/qna/list/{pageNum}", "/qna/list/1/{category}"})
    public ModelAndView list(@PathVariable(required = false) Integer pageNum,
                             @PathVariable(required = false) String category,
                             String keyword, String searchColumn,
                             HttpSession session){
        ModelAndView mav=new ModelAndView("/qna/list");
        //쿼리문에 넣을 변수들을 담을 맵 생성
        HashMap<String, Object> hashMap=search.searchProcess(category, session, keyword,
                searchColumn, "qna");

        // 페이징

        Page page=new Page(DBManager.getTotalQnaRecord(hashMap),10,5,pageNum);

        int totalPage=page.getTotalPage();

        mav.addObject("totalPage",totalPage);

        // 해당 페이지의 시작 글번호, 끝 글번호
        hashMap.put("startNo",page.getStartNo());
        hashMap.put("endNo",page.getEndNo());

        // 해당 페이지에서 보여줄 페이지 번호 첫 번째와 마지막
        int firstPage=page.getFirstPage();
        int lastPage=page.getLastPage();

        mav.addObject("firstPage",firstPage);
        mav.addObject("lastPage",lastPage);

        List<QnaVO> list=DBManager.findAllQna(hashMap);
        if(list.size()!=0) {
            mav.addObject("list", list);
        }else{
            mav.addObject("msg","게시글이 없습니다.");
        }
        return mav;
    }

    @GetMapping("/qna/detail/{qna_no}")
    public ModelAndView detail(@PathVariable int qna_no){
        ModelAndView mav=new ModelAndView("/qna/detail");
        Qna q;
        try {
            q = qs.findById(qna_no);
        }catch(Exception e){
            mav.setViewName("/error");
            mav.addObject("msg","존재하지 않는 글입니다.");
            return mav;
        }
//            String custidByQna_no=q.getCustomer().getCustid();
//            String custidInSession=(String)session.getAttribute("id");
//            if(!custidInSession.equals(custidByQna_no) && !custidInSession.equals("admin") && q.getQna_open().equals("n")){
//                mav.addObject("msg","비공개 글입니다.");
//                mav.setViewName("/error");

        DBManager.updateQNAHit(qna_no);
        q.setQna_hit(q.getQna_hit()+1);
        mav.addObject("q",q);



//          다른 방법 (이게 더 복잡한 듯)
//        //비공개 글일 경우
//        if(qna_open.equals("n")){
//            //작성자가 맞는지 확인
//            QnaVO q=new QnaVO();
//            q.setQna_no(qna_no);
//            q.setCustid((String) session.getAttribute("id"));
//
//            // 작성자가 아니면
//            if(DBManager.checkWriter(q)!=1){
//                mav.addObject("msg","비공개 글입니다.");
//                mav.setViewName("/error");
//                //작성자면
//            }else{
//                mav.addObject("q",qs.findById(qna_no).get());
//            }
//        }else{
//            mav.addObject("q",qs.findById(qna_no).get());
//        }
        return mav;
    }

    @GetMapping("/qna/insert")
    public ModelAndView insertForm(HttpSession session){
        ModelAndView mav=new ModelAndView();

        // 세션에 저장된 아이디로 유저가 예매한 티켓 VO 목록 가져오기
        String loginId="";
        try {
            loginId = (String) session.getAttribute("id");
            List<Integer> ticketidList = DBManager.findTicketidByCustid(loginId);
            List<Ticket> ticketVOList = new ArrayList<>();
            for (int ticketid : ticketidList) {
                Optional<Ticket> optionalTicket = ts.findByTicketid(ticketid);
                if (optionalTicket.isPresent()) {
                    ticketVOList.add(optionalTicket.get());
                }
            }
            mav.addObject("ticketVOList", ticketVOList);
        }catch(Exception e){
            mav.addObject("msg","로그인이 필요한 서비스입니다.");
            mav.setViewName("/error");
        }
        return mav;
    }

    @PostMapping("/qna/insert")
    public ModelAndView insertSubmit(QnaVO q, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/qna/list");

        if(q.getQna_answer()==null){
            q.setQna_answer("");
        }

        //파일 업로드
        String path=request.getServletContext().getRealPath("qna_files");
        MultipartFile uploadFile=q.getUploadFile();
        String fname=uploadFile.getOriginalFilename();
        q.setQna_fname(fname);

        int re= DBManager.insertQna(q);

        if(re!=1) {
            //실패하면
            mav.setViewName("/error");
        }else{
            //성공하면
            if(fname != null && !fname.equals("")) {
                //fname이 있으면 (= 파일 업로드 했으면)
                try {
                    FileOutputStream fos = new FileOutputStream(path + "/" + fname);
                    FileCopyUtils.copy(uploadFile.getBytes(), fos);
                    fos.close();
                }catch (Exception e) {
                    System.out.println("예외발생:"+e.getMessage());
                }
            }
        }
        return mav;
    }

    @GetMapping("/qna/update/{qna_no}")
    public ModelAndView updateForm(@PathVariable int qna_no){
        ModelAndView mav=new ModelAndView("/qna/update");
        Qna q;
        try {
            q = qs.findById(qna_no);
        }catch(Exception e){
            mav.setViewName("/error");
            mav.addObject("msg","존재하지 않는 글입니다.");
            return mav;
        }
        mav.addObject("q",q);

        // 작성자가 예매한 티켓 VO 목록 가져오기
        // 작성자 아이디
        String writer=q.getCustomer().getCustid();
        List<Integer> ticketidList = DBManager.findTicketidByCustid(writer);
        List<Ticket> ticketVOList = new ArrayList<>();
        for (int ticketid : ticketidList) {
            Optional<Ticket> optionalTicket=ts.findByTicketid(ticketid);
            if(optionalTicket.isPresent()) {
                ticketVOList.add(optionalTicket.get());
            }
        }
        mav.addObject("ticketVOList", ticketVOList);

        return mav;
    }

    @PostMapping("/qna/update")
    public ModelAndView updateSubmit(QnaVO qnaVO, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/qna/detail/"+qnaVO.getQna_no());

        if(qnaVO.getQna_answer()==null){
            qnaVO.setQna_answer("");
        }

        //파일 업로드
        String path=request.getServletContext().getRealPath("qna_files");
        MultipartFile uploadFile=qnaVO.getUploadFile();
        String oldFname=qnaVO.getQna_fname();
        String newFname=uploadFile.getOriginalFilename();

        //          Fname 처리:
        //         새로운 파일이 있으면 fname을 set
        //         새로운 파일 없고 예전 파일 있으면 그대로
        //         새로운 파일 없고 예전 파일 없으면 ""을 set

        //새로운 파일이 있으면 저장한다. 이 때 예전 파일이 있으면 지운다.
        //새로운 파일이 있으면
        if(newFname != null && !newFname.equals("")) {
            //새로운 파일을 저장한다.
            try {
                FileOutputStream fos = new FileOutputStream(path + "/" + newFname);
                FileCopyUtils.copy(uploadFile.getBytes(), fos);
                fos.close();
            }catch (Exception e) {
                System.out.println("예외발생:"+e.getMessage());
            }
            //새로운 파일 이름을 set
            qnaVO.setQna_fname(newFname);
            //예전 파일이 있으면
            if(oldFname!=null && !oldFname.equals("")){
                //예전 파일을 지운다.
                File file=new File(path+"/"+oldFname);
                file.delete();
            }
        }else{
            if(oldFname==null){
                qnaVO.setQna_fname("");
            }
        }

        DBManager.updateQna(qnaVO);
        return mav;
    }

    @GetMapping("/qna/delete/{qna_no}")
    public ModelAndView delete(@PathVariable int qna_no){
        ModelAndView mav=new ModelAndView("redirect:/qna/list");
        try {
            qs.delete(qna_no);
        }catch (Exception e){
            mav.setViewName("/error");
            mav.addObject("msg","존재하지 않는 글입니다.");
        }
        return mav;
    }

    //답글 등록 Ajax
    @ResponseBody
    @GetMapping("/qna/answer/update")
    public int updateAnswer(int qna_no, String qna_answer){
        QnaVO q=new QnaVO();
        q.setQna_no(qna_no);
        q.setQna_answer(qna_answer);
        return DBManager.updateAnswer(q);
    }

    @ResponseBody
    @GetMapping("/qna/answer/notif_and_email")
    public int createNotifAndSendEmail(int qna_no, String insertOrUpdate, String custid){
        // insert일 경우 notification 추가
        if(insertOrUpdate.equals("insert")) {

            Qna q;
            try {
                q = qs.findById(qna_no);
            }catch(Exception e){
                e.getStackTrace();
                return -1;
            }

            // 답변 등록 알림 생성
            NotificationVO notificationVO = new NotificationVO(0, custid, qna_no, null);
            DBManager.insertNotification(notificationVO);

            // 답글 알림 이메일 보내기
            String to=cs.findByCustid(custid).getEmail();
            String subject="[T-CATCH] 문의에 답변이 등록되었습니다";
            String text="<h2>QNA 답변 등록 알림</h2>"
                    +"<div>"+q.getQna_title()+"에 답변이 등록되었습니다.</div>"
                    +"<a href='http://localhost:8088/qna/detail/"+qna_no+"'>확인하기</a>";
            es.sendHtmlEmail(to, subject, text);
        }
        return 0;
    }

    //답글 삭제 Ajax
    @ResponseBody
    @GetMapping("/qna/answer/delete")
    public int deleteAnswer(int qna_no){
        return DBManager.deleteAnswer(qna_no);
    }

    // 답글 알림 list Ajax
    @ResponseBody
    @GetMapping("/listNotification")
    public List<NotificationByCustidVO> listNotification(HttpSession session){
        List<NotificationByCustidVO> notificationList=new ArrayList<>();
        String sessionId;
        if(session.getAttribute("id")!=null) {
            sessionId = (String) session.getAttribute("id");
            notificationList=DBManager.findNotificationByCustid(sessionId);
        }
        return notificationList;
    }

    // 답글 알림 갯수 계산 Ajax
    @ResponseBody
    @GetMapping("/countNChecked")
    public int countNChecked(HttpSession session){
        String loginId=(String)session.getAttribute("id");
        if(loginId!=null){
            return DBManager.countNChecked(loginId);
        }else{
            return 0;
        }
    }

    // DB 'checked' 칼럼 업데이트 n->y
    @ResponseBody
    @GetMapping("/updateCheckedToY")
    public int updateCheckedToY(HttpSession session){
        String loginId=(String)session.getAttribute("id");
        int re=-1;
        if(loginId!=null){
            re=DBManager.updateCheckedToY(loginId);
        }
        return re;
    }

    // 알림 지우기
    @ResponseBody
    @GetMapping("/deleteNotification")
    public int deleteNotification(int notif_no){
        return DBManager.deleteNotification(notif_no);
    }

    // 답글 알림 뷰 (임시)
    @GetMapping("/qna/notification")
    public void notif_view(){

    }

    // myPage에서 내가 쓴 1대1 문의 보기
    @GetMapping("/myPageQnA")
    public ModelAndView myPageQnaList(HttpSession session, @RequestParam(defaultValue = "1") int page){
        ModelAndView mav = new ModelAndView("/myPage/myPageQnA");
        String loginId=(String) session.getAttribute("id");
        Customer loginCustomer=cs.findByCustid(loginId);

        // 페이징 처리
        // int page : 현재 페이지
        // int totalRecord : 총 ticket 숫자
        // int startRecord : 현재 page에서 출력되는 record의 시작 rownum
        // int endRecord : 현재 page에서 출력되는 record의 끝 rownum
        // int startPage : '이전'을 누르기 전에 출력되는 가장 작은 페이지 버튼 숫자
        // int endPage : '다음'을 누르기 전에 출력되는 가장 큰 페이지 버튼 숫자
        int totalRecord = DBManager.getTotalQnaRecord(loginCustomer.getCustid());
        Paging paging = new Paging(totalRecord, page);
        int startRecord = paging.getStartRecord();
        int endRecord = paging.getEndRecord();
        int startPage = paging.getStartPage();
        int endPage = paging.getEndPage();

        mav.addObject("listQna", DBManager.listQnaByCustid(loginCustomer.getCustid(), startRecord, endRecord));
        mav.addObject("paging", paging);
        return mav;
    }


}

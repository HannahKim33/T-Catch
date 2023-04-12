package com.example.finalpro.controller;

import com.example.finalpro.db.DBManager;
import com.example.finalpro.entity.Notice;
import com.example.finalpro.service.NoticeService;
import com.example.finalpro.util.Search;
import com.example.finalpro.util.Page;
import com.example.finalpro.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@Setter
public class NoticeController {
    @Autowired
    private NoticeService ns;

    @Autowired
    private Search search;

    @GetMapping("/notice/resetSearch")
    public ModelAndView resetSearch(HttpSession session){
        ModelAndView mav=new ModelAndView("redirect:/notice/list");
        session.removeAttribute("noticeKeyword");
        session.removeAttribute("noticeSearchColumn");
        return mav;
    }
    @RequestMapping(value={"/notice/list", "/notice/list/{pageNum}", "/notice/list/1/{category}"})
    public ModelAndView list(@PathVariable(required = false) Integer pageNum,
                             @PathVariable(required = false) String category,
                             String keyword, String searchColumn,
                             HttpSession session){
        ModelAndView mav=new ModelAndView("/notice/list");

        //쿼리문에 넣을 변수들을 담을 맵 생성
        HashMap<String, Object> hashMap= search.searchProcess(category, session, keyword,
                searchColumn, "notice");
        
        // 페이징
        if (pageNum==null){
            pageNum=1;
        }

        Page page=new Page(DBManager.getTotalNoticeRecord(hashMap),10,5,pageNum);

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
        List<NoticeVO> list=DBManager.findAllNotice(hashMap);
        if(list.size()!=0) {
            mav.addObject("list", list);
        }else{
            mav.addObject("msg","게시글이 없습니다.");
        }
        return mav;
    }

    @GetMapping("/notice/detail/{notice_no}")
    public ModelAndView detail(@PathVariable int notice_no){
        ModelAndView mav=new ModelAndView();
        Optional<Notice> optionalNotice=ns.findById(notice_no);
        if(optionalNotice.isPresent()){
            Notice n=optionalNotice.get();
            DBManager.updateNoticeHit(notice_no);
            n.setNotice_hit(n.getNotice_hit()+1);
            mav.addObject("n",n);
            mav.setViewName("/notice/detail");
        }else{
            mav.addObject("msg","삭제된 글입니다.");
            mav.setViewName("/error");
        }
        return mav;
    }

    @GetMapping("/admin/notice/insert")
    public void insertForm(){}

    @PostMapping("/admin/notice/insert")
    public ModelAndView insertSubmit(NoticeVO n, HttpServletRequest request){
        ModelAndView mav=new ModelAndView("redirect:/notice/list/1/all");

        //파일 업로드
        String path=request.getServletContext().getRealPath("notice_files");
        MultipartFile uploadFile=n.getUploadFile();
        String fname=uploadFile.getOriginalFilename();
        n.setNotice_fname(fname);

        int re=DBManager.insertNotice(n);
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

    @GetMapping("/admin/notice/update/{notice_no}")
    public ModelAndView updateForm(@PathVariable int notice_no){
        ModelAndView mav=new ModelAndView();
        Optional<Notice> optionalNotice=ns.findById(notice_no);
        if(optionalNotice.isPresent()){
            Notice n=optionalNotice.get();
            mav.addObject("n",n);
            mav.setViewName("/admin/notice/update");
        }else{
            mav.addObject("msg","잘못된 접근입니다.");
            mav.setViewName("/error");
        }
        return mav;
    }

    @PostMapping("/admin/notice/update")
    public ModelAndView updateSubmit(NoticeVO n, HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        //파일 업로드
        String path=request.getServletContext().getRealPath("notice_files");
        MultipartFile uploadFile=n.getUploadFile();
        String oldFname=n.getNotice_fname();
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
            n.setNotice_fname(newFname);
            //예전 파일이 있으면
            if(oldFname!=null && !oldFname.equals("")){
                //예전 파일을 지운다.
                File file=new File(path+"/"+oldFname);
                file.delete();
            }
        }else{
            if(oldFname==null){
                n.setNotice_fname("");
            }
        }
        int re=DBManager.updateNotice(n);
        if(re==1){
            mav.setViewName("redirect:/notice/list/1/all");
        }else{
            mav.setViewName("/error");
        }
        return mav;
    }

    @GetMapping("/admin/notice/delete/{notice_no}")
    public ModelAndView delete(@PathVariable int notice_no){
        ModelAndView mav=new ModelAndView("redirect:/notice/list/1/all");
        ns.delete(notice_no);
        return mav;
    }
}

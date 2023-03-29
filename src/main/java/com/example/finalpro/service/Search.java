package com.example.finalpro.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Search {

    public HashMap<String, Object> searchProcess(String category, HttpSession session, String keyword,
                                                 String searchColumn, String tableName){
        HashMap<String, Object> hashMap=new HashMap<String,Object>();

        // 카테고리별로 보기
        if(category!=null) {
            switch (category) {
                case "book":
                    category = "예매/드로우";
                    break;
                case "transaction":
                    category = "결제/환불";
                    break;
                case "etc":
                    category = "기타";
                    break;
            }

            // 카테고리가 all이면 세션에 저장된 카테고리 지우기
            if(category.equals("all")) {
                if(session.getAttribute(tableName+"Category")!=null) {
                    session.removeAttribute(tableName+"Category");
                }
                category = null;
            }else{  // all이 아니라 특정 카테고리를 선택했을 경우
                //카테고리를 세션에 저장한다.
                session.setAttribute(tableName+"Category",category);
                //세션에 원래 있던 카테는 필요없음.
            }
            // 카테고리를 클릭 안했을 경우
        }else{
            // 그 전에 클릭했던 게 있다면 (=세션에 저장되어 있다면)
            if(session.getAttribute(tableName+"Category")!=null) {
                //세션에 저장되어 있는 카테를 가져온다.
                category = (String) session.getAttribute(tableName+"Category");

                //둘다 없다면 => 카테고리별로 보기 x => null => 처리 필요 없음.
            }
        }

        hashMap.put("category", category);

        //검색
        if(keyword!=null) {
            session.setAttribute(tableName+"Keyword",keyword);
            session.setAttribute(tableName+"SearchColumn", searchColumn);
            hashMap.put("searchColumn", searchColumn);
            hashMap.put("keyword", keyword);
        }else{
            if(session.getAttribute(tableName+"Keyword")!=null){
                keyword= (String) session.getAttribute(tableName+"Keyword");
                searchColumn= (String) session.getAttribute(tableName+"SearchColumn");
                hashMap.put("searchColumn", searchColumn);
                hashMap.put("keyword", keyword);
            }
        }
        return hashMap;
    }

}

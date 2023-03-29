package com.example.finalpro.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Page {
    //전체 레코드 수
    private int totalRecord;
    //한 페이지에 보여줄 글의 수
    private int pageSize;
    //한 페이지에 보여줄 페이지 번호의 개수 ( 12345 / 678910 / ...)
    private int pageGroupSize;
    //요청한 페이지 번호
    private int pageNum;
    //해당 페이지에서 보여줄 글 목록 중 첫 번째와 마지막, 보여줄 페이지 번호 중 첫 번째와 마지막
//    int startNo, endNo, firstPage, lastPage;

    public int getTotalPage(){
        return (int)Math.ceil((double)totalRecord/pageSize);
    }
    public int getStartNo(){
        return (pageNum-1)*pageSize+1;
    }

    public int getEndNo(){
        return pageNum*pageSize;
    }

    public int getFirstPage(){
        return ((pageNum-1)/pageGroupSize)*pageGroupSize+1;
    }

    public int getLastPage(){
        return ((pageNum-1)/pageGroupSize)*pageGroupSize+pageGroupSize;
    }

}

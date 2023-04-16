package com.example.finalpro.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer pageNum;
    //해당 페이지에서 보여줄 글 목록 중 첫 번째와 마지막, 보여줄 페이지 번호 중 첫 번째와 마지막
//    int startNo, endNo, firstPage, lastPage;

    public Page(int totalRecord, int pageSize, int pageGroupSize, Integer pageNum) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.pageGroupSize = pageGroupSize;
        if(pageNum==null){
            this.pageNum = 1;
        }else {
            this.pageNum = pageNum;
        }
    }

    public int getTotalPage(){
        int totalPage=(int)Math.ceil((double)totalRecord/pageSize);
        if(totalPage==0){
            totalPage=1;
        }
        return totalPage;
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
        int lastPage=((pageNum-1)/pageGroupSize)*pageGroupSize+pageGroupSize;
        if(lastPage>getTotalPage()){
            lastPage=getTotalPage();
        }
        return lastPage;
    }

}

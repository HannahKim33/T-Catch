package com.example.finalpro.db;

import com.example.finalpro.entity.Customer;
import com.example.finalpro.vo.CustomerVO;
import com.example.finalpro.entity.Qna;
import com.example.finalpro.vo.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			System.out.println("ok1");
			String resource = "db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			System.out.println("ok2");
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<CustomerVO> findAllCustomer() {
		List<CustomerVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("customer.findAll");
		System.out.println(list);
		session.close();
		return list;
	}

	public static int updateCustomer(CustomerVO customer){
		int i = -1;
		System.out.println("DB가동");
		System.out.println(customer);
		SqlSession session = sqlSessionFactory.openSession();
		i = session.update("customer.updateCustomer", customer);
		System.out.println(i);
		session.commit();
		session.close();
		return i;
	}

	public static List<TicketVO> findAllTicket(){
		List<TicketVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findAll");
		session.close();
		return list;
	}

	public static List<BookVO> findAllBook(){
		List<BookVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.findAll");
		session.close();
		return list;
	}

	public static List<CategoryVO> findAllCategory(){
		List<CategoryVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("category.findAll");
		session.close();
		return list;
	}

	public static List<DrawVO> findAllDraw(){
		List<DrawVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("draw.findAll");
		session.close();
		return list;
	}

	public static List<NoticeVO> findAllNotice(){
		List<NoticeVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("notice.findAll");
		session.close();
		return list;
	}
	public static List<PlaceVO> findAllPlace(){
		List<PlaceVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("place.findAll");
		session.close();
		return list;
	}

	public static List<QnaVO> findAllQna(){
		List<QnaVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("qna.findAll");
		session.close();
		return list;
	}

	public static List<ReviewVO> findAllReview(){
		List<ReviewVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.findAll");
		session.close();
		return list;
	}

	public static List<SeatVO> findAllSeat(){
		List<SeatVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("seat.findAll");
		session.close();
		return list;
	}

	// 특정 좌석의 잔여좌석 조회
	public static int findLeftSeatByTicketid(int ticketid){
		int num = 0;
		SqlSession session = sqlSessionFactory.openSession();
		num = session.selectOne("seat.selectLeftSeat",ticketid);
		System.out.println("잔여좌석DBM에서:"+num);
		session.close();
		return num;
	}

	// 티켓 상세 정보 조회
	public static TicketVO findByTicketid(int ticketid){
		TicketVO t = null;
		SqlSession session = sqlSessionFactory.openSession();
		t = session.selectOne("ticket.findByTicketid", ticketid);
		System.out.println("ticket객체:"+t);
		session.close();
		return t;
	}

	// 메인 페이지에서 카테고리 , 시간 별로 상영작 출력하기
	// time=0은 과거, time=1은 현재, time=2는 미래
	public static List<TicketVO> findAllTicketByCategory(int time, int cateid){
		List<TicketVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		map.put("cateid", cateid);

		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findAllTicketByCategory", map);
		session.close();

		return list;
	}

	public static List<RankingVO> findAllRankingOrderByScore(int cateid){
		List<RankingVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ranking.findAllRankingOrderByScore", cateid);
		session.close();
		return list;
	}

	public static List<TicketVO> findSearchTicket(String keyword){
		List<TicketVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("ticket.findSearchTicket", keyword);
		session.close();
		return list;
	}

	// 티켓의 리뷰 출력, 정렬
	public static List<ReviewVO> findReviewByTicketid(int ticketid, int re){
		List<ReviewVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ticketid", ticketid);
		map.put("re", re);
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.findReviewByTicketid",map);
		session.close();
		return list;
	}

	// 사용자의 티켓리뷰 출력
	public static List<MyReviewVO> findReviewByTicketAndCust(String custid, int ticketid){
		List<MyReviewVO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("ticketid", ticketid);
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.reviewByTicketAndCust",map);
		session.close();
		return list;
	}

	// ticketid로 리뷰내역이 있나 확인
	public static List<MyReviewVO> checkReviewByTicketid(int ticketid){
		List<MyReviewVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("review.checkReview",ticketid);
		session.close();
		return list;
	}

	// 티켓 후기의 평균별점 구하기
	public static int findAvgScore(int ticketid){
		int avg = 0;
		SqlSession session = sqlSessionFactory.openSession();
		avg = session.selectOne("review.findAvgScore",ticketid);
		session.close();
		return avg;
	}

	// 성별별로 예약자수 구하기
	public static List<CountGenderVO> countGender(int ticketid){
		List<CountGenderVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.countGender",ticketid);
		System.out.println("countGender:"+list);
		session.close();
		return list;
	}

	// 세대별로 예약자수 구하기
	public static List<CountGenerationVO> countGeneration(int ticketid){
		List<CountGenerationVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("book.countGeneration",ticketid);
		System.out.println("countGeneration:"+list);
		session.close();
		return list;
	}

    // Tikcetid의 전체 좌석 목록 출력
    public static List<SeatVO> listSeatByTicketid(int ticketid){
        List<SeatVO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        list = session.selectList("seat.listSeatByTicketid",ticketid);
        System.out.println("listSeatByTicketid:"+list);
        session.close();
        return list;
    }

	// 예매를 위해 ticketid와 seatname으로 좌석 아이디 찾기
	public static int findSeatId(int ticketid, String seatname){
		int seatid = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ticketid", ticketid);
		map.put("seatname", seatname);
		SqlSession session = sqlSessionFactory.openSession();
		seatid = session.selectOne("seat.findSeatId",map);
		System.out.println("seatid:"+seatid);
		return seatid;
	}

	// 좌석예매
	public static int registSeat(int seatid){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.update("seat.registSeat",seatid);
		System.out.println("registSeat_re:"+re);
		session.close();
		return re;
	}

	// 티켓예매
	public static int bookTicket(String custid, int ticketid, int seatid){
		int re = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("custid", custid);
		map.put("ticketid", ticketid);
		map.put("seatid", seatid);
		SqlSession session = sqlSessionFactory.openSession(true);
		re = session.insert("book.bookTicket",map);
		System.out.println("registSeat_re:"+re);
		session.close();
		return re;
	}

	public static List drawTest(int ticketid){
		List<DrawVO> draw = null;
		SqlSession session = sqlSessionFactory.openSession(true);
		draw = session.selectList("draw.drawTest",ticketid);
		session.close();
		return draw;
	}

	public static int drawTest2(int ticketid) {
		int count = -1;
		SqlSession session = sqlSessionFactory.openSession(true);
		count = session.selectOne("seat.seatTest");
		System.out.println(count);
		session.close();
		return count;
	}


//	public static List<CustomerVO> findAll() {
//		List<CustomerVO> list = null;
//		SqlSession session = sqlSessionFactory.openSession();
//		list = session.selectList("customer.findAll");
//		session.close();
//		return list;
//	}
}

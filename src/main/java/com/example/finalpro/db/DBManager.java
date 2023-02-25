package com.example.finalpro.db;

import com.example.finalpro.vo.CustomerVO;
import com.example.finalpro.vo.RankingVO;
import com.example.finalpro.vo.TicketVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DBManager {
	public static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "db/sqlMapConfig.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory =
			  new SqlSessionFactoryBuilder().build(inputStream);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<CustomerVO> findAll() {
		List<CustomerVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		list = session.selectList("customer.findAll");
		session.close();
		return list;
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

	public static int insertTicket(TicketVO ticket){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.insert("ticket.insertTicket", ticket);
		session.commit();
		session.close();
		return re;
	}

	public static int updateTicket(TicketVO ticket){
		int re = -1;
		SqlSession session = sqlSessionFactory.openSession();
		re = session.insert("ticket.updateTicket", ticket);
		session.commit();
		session.close();
		return re;
	}

//	public static List<CustomerVO> findAll() {
//		List<CustomerVO> list = null;
//		SqlSession session = sqlSessionFactory.openSession();
//		list = session.selectList("customer.findAll");
//		session.close();
//		return list;
//	}
}

package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.domain.Apartment;
import com.domain.RentedBy;
import com.util.ConnectionFactory;

public class AptDAO {
	
	public AptDAO() {
		
	}

	public List<Apartment> getAptList() {
		SqlSession session = ConnectionFactory.getSqlSessionFactory().openSession();
		List<Apartment> aptList = new ArrayList<Apartment>();
		
		try {
			aptList = session.selectList("com.mapper.AptMapper.apts");
			} finally {
			  session.close();
			}
		
		return aptList;
	}
	
	public List<Apartment> getAptListByAvailable(String anum) {
		SqlSession session = ConnectionFactory.getSqlSessionFactory().openSession();
		List<Apartment> aptList = new ArrayList<Apartment>();
		
		try {
			aptList = session.selectList("com.mapper.AptMapper.isAvailable", anum);
			} finally {
			  session.close();
			}
		
		return aptList;
	} 
	public List<RentedBy> getRBListByAnum(String anum) {
		SqlSession session = ConnectionFactory.getSqlSessionFactory().openSession();
		List<RentedBy> rbList = new ArrayList<RentedBy>();
		
		try {
			rbList = session.selectList("com.mapper.AptMapper.info", anum);
			} finally {
			  session.close();
			}
		
		return rbList;
	}
}

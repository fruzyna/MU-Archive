package com.service;

import java.util.ArrayList;
import java.util.List;

import com.dao.AptDAO;
import com.domain.Apartment;
import com.domain.RentedBy;

public class AptService {
	
	public AptService() {
		
	}

	public List<Apartment> getAptList() {
		List<Apartment> aptList = new ArrayList<Apartment>();
		AptDAO empDAO = new AptDAO();
		aptList = empDAO.getAptList();
		return aptList;
	} 
	
	public List<Apartment> getAptListByAvail(String anum) {
		List<Apartment> aptList = new ArrayList<Apartment>();
		AptDAO empDAO = new AptDAO();
		aptList = empDAO.getAptListByAvailable(anum);
		return aptList;
	} 
	
	public List<RentedBy> getRBListByAnum(String anum) {
		List<RentedBy> rbList = new ArrayList<RentedBy>();
		AptDAO empDAO = new AptDAO();
		rbList = empDAO.getRBListByAnum(anum);
		return rbList;
	}

}

package com.domain;

public class RentedBy {
	private String id;
	private String cid;
	private String anum;
	private String startrent;
	private int rent;
	private String periodrent;
	private int deposit;
	private int balance;
	
	
	public RentedBy() {
		super();
	}
	
	public RentedBy(String id, String cid, String anum, String startrent, int rent, String periodrent, int deposit, int balance) {
		super();
		this.id = id;
		this.cid = cid;
		this.anum = anum;
		this.startrent = startrent;
		this.rent = rent;
		this.periodrent = periodrent;
		this.deposit = deposit;
		this.balance = balance;
	}
	
	public String getId() {
		return cid;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAnum() {
		return anum;
	}

	public void setAnum(String anum) {
		this.anum = anum;
	}

	public String getStartrent() {
		return startrent;
	}

	public void setStartrent(String startrent) {
		this.startrent = startrent;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public String getPeriodrent() {
		return periodrent;
	}

	public void setPeriodrent(String periodrent) {
		this.periodrent = periodrent;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}

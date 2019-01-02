package com.domain;

public class Apartment {

	private String anum;
	private String atype;
	private int numroom;
	private int buildingnum;
	private int floornum;
	private String available;
	
	
	public Apartment() {
		super();
	}
	
	public Apartment(String anum, String atype, int numroom, int buildingnum, int floornum, String available) {
		super();
		this.anum = anum;
		this.atype = atype;
		this.numroom = numroom;
		this.buildingnum = buildingnum;
		this.floornum = floornum;
		this.available = available;
	}

	public String getAnum() {
		return anum;
	}

	public void setAnum(String anum) {
		this.anum = anum;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public int getNumroom() {
		return numroom;
	}

	public void setNumroom(int numroom) {
		this.numroom = numroom;
	}

	public int getBuildingnum() {
		return buildingnum;
	}

	public void setBuildingnum(int buildingnum) {
		this.buildingnum = buildingnum;
	}

	public int getFloornum() {
		return floornum;
	}

	public void setFloornum(int floornum) {
		this.floornum = floornum;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
}

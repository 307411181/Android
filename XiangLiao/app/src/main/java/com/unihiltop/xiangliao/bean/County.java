package com.unihiltop.xiangliao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName ="county" )
public class County {
	@DatabaseField(generatedId = true)
	private int countyid;
	@DatabaseField
	private String county;
	@DatabaseField(foreign = true,columnName = "cityid")
	private City city; 
	public County(){}
	public County(int countyid, String county, City city) {
		super();
		this.countyid = countyid;
		this.county = county;
		this.city = city;
	}
	public int getCountyid() {
		return countyid;
	}
	public void setCountyid(int countyid) {
		this.countyid = countyid;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
    
}

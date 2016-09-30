package com.unihiltop.xiangliao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="city")
public class City {
	@DatabaseField(generatedId=true)
	private int cityid;
	
	@DatabaseField
	private String city;
	
	@DatabaseField(foreign=true, columnName="provinceid")
	private Province province;
	public City(){}
	public City(int cityid, String city, Province province) {
		super();
		this.cityid = cityid;
		this.city = city;
		this.province = province;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	
	
}

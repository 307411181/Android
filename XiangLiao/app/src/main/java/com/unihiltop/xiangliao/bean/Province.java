package com.unihiltop.xiangliao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="province")
public class Province {
	
	@DatabaseField(generatedId=true)
	private int provinceid;
	@DatabaseField
	private String province;
	
	public Province(){}
	
	public Province(int provinceid, String province) {
		super();
		this.provinceid = provinceid;
		this.province = province;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
}

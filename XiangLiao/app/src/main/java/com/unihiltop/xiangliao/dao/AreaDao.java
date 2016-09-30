package com.unihiltop.xiangliao.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.unihiltop.xiangliao.bean.City;
import com.unihiltop.xiangliao.bean.County;
import com.unihiltop.xiangliao.bean.Province;
import com.unihiltop.xiangliao.util.AreaHelper;

import java.sql.SQLException;
import java.util.List;

public class AreaDao {
	private Dao<Province, Integer> provinceDao;
	private Dao<City, Integer> cityDao;
	private Dao<County, Integer> countyDao;
	
	public AreaDao(Context context){
		try {
			provinceDao = AreaHelper.getInstance(context).getDao(Province.class);
			cityDao = AreaHelper.getInstance(context).getDao(City.class);
			countyDao = AreaHelper.getInstance(context).getDao(County.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Province> getProvinces(){
		try {
			return provinceDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<City> getCities(int provinceid){
		try {
			return cityDao.queryForEq("provinceid", provinceid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	public List<County> getCountes(int cityid){
		try {
			return countyDao.queryForEq("cityid", cityid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}

package com.weather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.weather.app.db.MyWeatherDB;
import com.weather.app.model.City;
import com.weather.app.model.County;
import com.weather.app.model.Province;
/* 
 * 服务器返回的都是“代号|城市 ，代号|城市”这种格式的数据，于是我们先以“,”
 *将数据分割，再以“|”将代号和城市分割开，得到各个数据 */
public class Utility {
 
	//解析和处理服务器返回的省级数据
	public synchronized static boolean handleProvincesResponse(MyWeatherDB myWeatherDB,String response){
		
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces=response.split(",");
			if(allProvinces!=null&&allProvinces.length>0){
				for(String p:allProvinces){
					String[] array=p.split("\\|");
					Province province=new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					
					myWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
		
	}
	
	//市级数据
	public static boolean handleCitiesResponse(MyWeatherDB myWeatherDB,String response,int provinceId){
		
		if(!TextUtils.isEmpty(response)){
			
			String[] allCities=response.split(",");
			if(allCities!=null&&allCities.length>0){
				
				for(String c:allCities){					
					String[] array=c.split("\\|");
					City city=new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);     
					city.setProvinceId(provinceId);
					myWeatherDB.saveCity(city);
				}
				return true;
			}
			
		}
		return false;
		
	}
	
	//县级数据
	public static boolean handleCountiesResponse(MyWeatherDB myWeatherDB,String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties=response.split(",");
			if(allCounties!=null&&allCounties.length>0){
				for(String c:allCounties){
					String[] array=c.split("\\|");
					County county=new County();
					
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					
					myWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 解析服务器返回的JSON格式数据，并将解析的数据存储到本地
	 * */
	public static void handleWeatherResponse(Context context,String response){
		try{
			JSONObject jsonObject =new JSONObject(response);
			JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
			String cityName=weatherInfo.getString("city");
			String weatherCode=weatherInfo.getString("cityid");
			String temp1=weatherInfo.getString("temp1");
			String temp2=weatherInfo.getString("temp2");
			String weatherDesp=weatherInfo.getString("weather");
			String publishTime=weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	//将服务器返回的天气信息存储到SharedPreferences文件中
	private static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);   //设置日期格式
		SharedPreferences.Editor editor =PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp",weatherDesp );
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
}

package com.weather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyWeatherDB {

	//数据库名
	public static final String DB_NAME="my_weather";
	
	//数据库版本
	public static final int VERSION=1;
	
	private static MyWeatherDB myWeatherDB;
	private SQLiteDatabase db;
	
	//将构造方法私有化
	private MyWeatherDB(Context context){
		MyWeatherOpenHelper dbHelper=new MyWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	//获取MyWeatherDB 的实例
	public synchronized static MyWeatherDB getInstance(Context context){
		
	}
}

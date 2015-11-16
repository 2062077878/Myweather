package com.weather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyWeatherDB {

	//���ݿ���
	public static final String DB_NAME="my_weather";
	
	//���ݿ�汾
	public static final int VERSION=1;
	
	private static MyWeatherDB myWeatherDB;
	private SQLiteDatabase db;
	
	//�����췽��˽�л�
	private MyWeatherDB(Context context){
		MyWeatherOpenHelper dbHelper=new MyWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	//��ȡMyWeatherDB ��ʵ��
	public synchronized static MyWeatherDB getInstance(Context context){
		
	}
}

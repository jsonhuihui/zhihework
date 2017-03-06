package com.zorgoom.zhihework.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final int DATABASEVERSION = 1;// 数据库版本 如果更改了安装的时候会执行
													// onupgrade方法

	// 创建数据库是在这个构造方法里的
	public DBOpenHelper(Context context) {
		super(context, "elinker.db", null, DATABASEVERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 商家表
		db.execSQL("CREATE TABLE shops(id INTEGER PRIMARY KEY "
				+ "AUTOINCREMENT,rid integer,num integer,name VARCHAR(50))");
		// 商家购物车表
		db.execSQL(
				"CREATE TABLE shopsCart(id INTEGER PRIMARY KEY AUTOINCREMENT,sid integer,gid integer,name VARCHAR(50),num integer,price VARCHAR(10))");
		// 更新消息表
		db.execSQL("CREATE TABLE msg(id INTEGER PRIMARY KEY " + "AUTOINCREMENT,rid integer,title VARCHAR(50))");
		// 更新商家推荐表
		db.execSQL("CREATE TABLE fff(id INTEGER PRIMARY KEY "
				+ "AUTOINCREMENT,rid integer,SHOPNAME VARCHAR(50),SHOPIMAGE VARCHAR(50),REMARK VARCHAR(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion >= oldVersion) {
			// 更新消息表
			db.execSQL("CREATE TABLE msg(id INTEGER PRIMARY KEY " + "AUTOINCREMENT,rid integer,title VARCHAR(50))");
			// 更新商家推荐表
			db.execSQL("CREATE TABLE fff(id INTEGER PRIMARY KEY "
					+ "AUTOINCREMENT,rid integer,SHOPNAME VARCHAR(50),SHOPIMAGE VARCHAR(50),REMARK VARCHAR(100))");
		}
	}

}
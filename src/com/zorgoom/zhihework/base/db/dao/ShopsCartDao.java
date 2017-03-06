package com.zorgoom.zhihework.base.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.base.db.DBOpenHelper;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 商家商品Dao层
 */
public class ShopsCartDao {
	private DBOpenHelper helper;

	public ShopsCartDao(Context context) {
		helper = new DBOpenHelper(context);
	}

	/**
	 * @param shops
	 */
	public void update(int sid, int gid, String name, String price, int num) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (isExistShops(gid)) {// 存在则不创建 修改
			if (num == 0) {// 数字为0则删除
				db.execSQL("delete from shopsCart where gid=" + gid);
				db.close();
				return;
			}
			ContentValues values = new ContentValues();
			values.put("num", num);
			db.update("shopsCart", values, "gid=?", new String[] { gid + "" });
			db.close();
			return;
		}
		ContentValues values = new ContentValues();
		values.put("gid", gid);
		values.put("sid", sid);
		values.put("num", num);
		values.put("price", price);
		values.put("name", name);
		db.insert("shopsCart", "id", values);
		db.close();
	}

	public void deleteShops(int sHOPID) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from shopsCart where sid=" + sHOPID);
		db.close();
	}

	public void deleteGood(int goodId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from shopsCart where gid=" + goodId);
		db.close();
	}

	/**
	 * 判断数据库中指定表的指定字段是否存在
	 * 
	 * @return
	 */
	public boolean isExistShops(int gid) {
		boolean isExis = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select id from shopsCart where gid =" + gid, null);
		if (c.moveToNext()) {
			isExis = true;
		}
		c.close();
		// db.close();
		return isExis;
	}

	/**
	 * 根据商家查询购物车的数量
	 * 
	 * @return
	 */
	public int queryBySidAll(int sid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		int num = 0;
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select sum(num) from shopsCart where sid = " + sid, null);
		if (c.moveToNext()) {
			num = c.getInt(0);
		}
		c.close();
		db.close();
		return num;
	}

	/**
	 * 根据商家查询购物车的商品
	 * 
	 * @return
	 */
	public List<ShopsCart> queryBySidGoodAll(int sid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<ShopsCart> shopsCarts = new ArrayList<ShopsCart>();
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select gid,name,num,price from shopsCart where sid = " + sid, null);
		while (c.moveToNext()) {
			shopsCarts.add(new ShopsCart(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3)));
		}
		c.close();
		db.close();
		return shopsCarts;
	}

	/**
	 * 根据商品查询购物车的数量
	 * 
	 * @return
	 */
	public int queryByGidAll(int gid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		int num = 0;
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select num from shopsCart where gid = " + gid, null);
		if (c.moveToNext()) {
			num = c.getInt(0);
		}
		c.close();
		db.close();
		return num;
	}
	// public void delete(int sid) {
	// SQLiteDatabase db = helper.getReadableDatabase();
	// db.execSQL("delete from shopsCart where sid=" + sid);
	// }

	// /**
	// * 修改商家表的数量
	// *
	// * @param shops
	// */
	// public void updateShopsNum(Shops shops) {
	// if (!isExistShops(shops.getRid())) {// 存在则不创建
	// return;
	// }
	// SQLiteDatabase db = helper.getWritableDatabase();
	// // 准备数据
	// ContentValues values = new ContentValues();
	// values.put("num", shops.getNum());
	// db.update("shops", values, "rid=?", new String[] { shops.getRid() + ""
	// });
	// db.close();
	// }

	// public void deleteAll() {
	// SQLiteDatabase db = helper.getReadableDatabase();
	// db.execSQL("delete from search");
	// }

	// /**
	// * 是否存在重复的
	// */
	// public Search isSearch(String name) {
	// Search search = null;
	// SQLiteDatabase db = helper.getReadableDatabase();
	// // 查询所有记录, 倒序
	// Cursor c = db.rawQuery("select id,name,timess from search where name ='"
	// + name + "'", null);
	// if (c.moveToNext()) {
	// search = new Search(c.getInt(0), c.getString(1), c.getLong(2));
	// return search;
	// }
	// c.close();
	// db.close();
	// return search;
	// }
	//
}

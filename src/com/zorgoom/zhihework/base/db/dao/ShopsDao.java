package com.zorgoom.zhihework.base.db.dao;

import com.zorgoom.zhihework.base.db.DBOpenHelper;
import com.zorgoom.zhihework.base.db.entity.Shops;
import com.zorgoom.zhihework.vo.SaleListVo.SaleVo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 商家Dao层
 */
public class ShopsDao {
	private DBOpenHelper helper;

	public ShopsDao(Context context) {
		helper = new DBOpenHelper(context);
	}

	public void insert(SaleVo shops) {
		
		if (isExistShops(shops.getRID())) {// 存在则不创建
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		// 准备数据
		ContentValues values = new ContentValues();
		values.put("rid", shops.getRID());
		values.put("num", 0);
		values.put("name", shops.getSHOPNAME());
		db.insert("shops", "id", values);
		db.close();
	}

	/**
	 * 判断数据库中指定表的指定字段是否存在
	 * 
	 * @return
	 */
	public boolean isExistShops(int rid) {
		boolean isExis = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select id from shops where rid =" + rid, null);
		if (c.moveToNext()) {
			isExis = true;
		}
		c.close();
		db.close();
		return isExis;
	}

	/**
	 * 修改商家表的数量
	 * 
	 * @param shops
	 */
	public void updateShopsNum(Shops shops) {
		if (!isExistShops(shops.getRid())) {// 存在则不创建
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		// 准备数据
		ContentValues values = new ContentValues();
		values.put("num", shops.getNum());
		db.update("shops", values, "rid=?", new String[] { shops.getRid() + "" });
		db.close();
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from search where id=" + id);
	}

	public void deleteAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from search");
	}

//	/**
//	 * 是否存在重复的
//	 */
//	public Search isSearch(String name) {
//		Search search = null;
//		SQLiteDatabase db = helper.getReadableDatabase();
//		// 查询所有记录, 倒序
//		Cursor c = db.rawQuery("select id,name,timess from search where name ='" + name + "'", null);
//		if (c.moveToNext()) {
//			search = new Search(c.getInt(0), c.getString(1), c.getLong(2));
//			return search;
//		}
//		c.close();
//		db.close();
//		return search;
//	}
//
//	public List<Search> queryMyFootprintAll() {
//		SQLiteDatabase db = helper.getReadableDatabase();
//		// 查询所有记录, 倒序
//		List<Search> searchs = new ArrayList<Search>();
//		db.execSQL("delete from search where" + " (select count(id) from search)> 30 "
//				+ "and id in (select id from search " + "order by id desc limit (select count(id)"
//				+ " from search) offset 30)");
//		Cursor c = db.rawQuery("select id,name,timess from search order by timess desc", null);
//		while (c.moveToNext()) {
//			searchs.add(new Search(c.getInt(0), c.getString(1), c.getLong(2)));
//		}
//		c.close();
//		db.close();
//		return searchs;
//	}
}

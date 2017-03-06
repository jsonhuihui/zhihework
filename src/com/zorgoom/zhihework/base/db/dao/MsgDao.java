package com.zorgoom.zhihework.base.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.base.db.DBOpenHelper;
import com.zorgoom.zhihework.base.db.entity.MsgPo;
import com.zorgoom.zhihework.vo.Msg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 消息Dao层
 */
public class MsgDao {
	private DBOpenHelper helper;

	public MsgDao(Context context) {
		helper = new DBOpenHelper(context);
	}

	public void insert(Msg shops) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// 准备数据
		ContentValues values = new ContentValues();
		values.put("rid", "0");
		values.put("title", shops.getNOTICETITLE());
		db.insert("msg", "id", values);
		db.close();
	}

	public void deleteAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from msg");
	}

	/**
	 * 根据商家查询购物车的商品
	 * 
	 * @return
	 */
	public List<MsgPo> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<MsgPo> shopsCarts = new ArrayList<MsgPo>();
		// 查询所有记录, 倒序
		Cursor c = db.rawQuery("select rid,title from msg", null);
		while (c.moveToNext()) {
			shopsCarts.add(new MsgPo(c.getInt(0), c.getString(1)));
		}
		c.close();
		db.close();
		return shopsCarts;
	}
}

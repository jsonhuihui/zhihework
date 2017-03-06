package com.zorgoom.util;

import com.google.gson.Gson;

import android.util.Log;

/**
 * 此类用来解析和转化json、xml数据
 * 
 */
public class DataPaser {

	/**
	 * 将json数据序列化到实体类对象中
	 * 
	 * @param json字符串
	 * @param clazz所要解析的类名称
	 * @return 解析后的对象
	 */
	public static Gson gson;

	public static Gson getGson() {
		if (gson == null) {
			gson = new Gson();
			return gson;
		} else {
			return gson;
		}
	}

	public static <T> T json2Bean(String json, Class<T> clazz) {
		try {
			return getGson().fromJson(json, clazz);
		} catch (Exception exception) {
			Log.i("DataPaser", exception.getMessage());
		}
		return null;

	}

	/**
	 * 将实体类对象解析为json格式的字符串数据
	 * 
	 * @param <T>
	 *            所要解析的对象
	 * @return json字符串
	 */
	public static <T> String bean2Json(T t) {
		return getGson().toJson(t);
	}

	// /**
	// * 从xml字符串数据序列化到实体类对象中
	// * @param <T>
	// * @param xml
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public static <T> T fromXml(String xml,T t) {
	// // 1.创建Xstream对象
	// XStream x = new XStream();
	// // x.alias(标签, 类);
	// x.alias(t.getClass().getSimpleName(), t.getClass());
	// return (T) x.fromXML(xml);
	// }
	//
	// /**
	// * 将实体类对象解析为xml格式的字符串数据
	// * @param <T>
	// * @return
	// */
	// public static <T> String toXml(T t) {
	// // 1.创建Xstream对象
	//
	// XStream x = new XStream();
	// // x.alias(标签, 类);
	// x.alias(t.getClass().getSimpleName(), t.getClass());
	// return x.toXML(t);
	// }

}

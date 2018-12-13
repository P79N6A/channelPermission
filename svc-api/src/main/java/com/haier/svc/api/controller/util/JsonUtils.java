package com.haier.svc.api.controller.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

	/**
	 * 根据filed 获取JsonObject对象
	 * 
	 * @param resJson
	 *            json字符串
	 * @param filed
	 *            字段信息
	 * @return
	 */
	public static JsonObject toJsonObject(String resJson, String filed) {
		return new JsonParser().parse(resJson).getAsJsonObject()
				.getAsJsonObject(filed);
	}

	public static JsonArray toJsonObjectList(String resJson, String filed) {
		return new JsonParser().parse(resJson).getAsJsonObject()
				.getAsJsonArray(filed);
	}

	/**
	 * 根据filed 获取JsonObject对象，然后object字段取得数组object对象
	 * 
	 * @param resJson
	 *            json字符串
	 * @param filed
	 *            字段信息
	 * @return
	 */
	public static JsonArray toJsonArray(String resJson, String filed,
                                        String objectParent, String object) {
		return toJsonObject(resJson, filed).getAsJsonObject(objectParent)
				.getAsJsonArray(object);
	}
	public static JsonArray toJsonArray(String resJson, String filed, String object) {
		return toJsonObject(resJson, filed).getAsJsonArray(object);
	}

	/**
	 * 把map转换为json字符串处理
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String mapToJson(Map<?, ?> map) {
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	/**
	 * 把json转换为Map处理
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Map jsonToMap(String str) {
		Gson gson = new Gson();
		return gson.fromJson(str, new HashMap().getClass());
	}

	/**
	 * json转list
	 * @param str
	 * @return
	 */
	public static List jsonToList(String str){
		Gson gson = new Gson();
		return gson.fromJson(str, new ArrayList().getClass());
	}

	/**
	 * 把list转换为json字符串处理
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String listToJson(List<?> list) {
		Gson gson = new Gson();
		return gson.toJson(list);
	}

	/**
	 * 把json字符串转换成LIST集合
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	/**
	 * 把对象转换成json字符串
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static String toJsonString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 把json字符串转换成Object
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String jsonStr, Class<T> clazz) {
		return JSON.parseObject(jsonStr, clazz);
	}

	/***
	 * 根据key从JsonObject对象中获取value值
	 * 
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String getJsonValueByKey(JsonObject jsonObject, String key) {
		JsonElement je = jsonObject.get(key);
		if (je == null)
			return "";
		return nullSafeString(je.getAsString()).trim();
	}

	public static String nullSafeString(String value) {
		return value == null ? "" : value;
	}

}

package com.util;

import java.util.HashMap;

import org.json.JSONObject;

public class JsonCache {
	HashMap<String, JSONObject> mCache = new HashMap<String, JSONObject>();
	private static JsonCache mInstance;

	public static JsonCache getInstance() {
		if (mInstance == null) {
			mInstance = new JsonCache();
		}
		return mInstance;
	}

	public void put(String key, JSONObject value) {
		mCache.put(key, value);
	}

	public JSONObject get(String key) {
		return mCache.get(key);
	}
}

package com.peter.android.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peter.toolbox.IOUtil;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * 使用标准Json 格式实现配置
 */
/*
 * Example:
 * 
 * { "int1": 1, "double1": 2.34, "array1": [ "1", "2" ], "string1": "haha",
 * "boolean1": true }
 */
public class JsonResouceImp implements CustomResouce {

	private JSONObject jsonObject = new JSONObject();

	private String name;

	public JsonResouceImp(Context context, String fileName)  {
		if (context == null || fileName == null) {
			throw new NullPointerException("参数不能为Null");
		}
		try {
			init(context, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param fileName
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws Exception
	 */
	protected void init(Context context, String fileName) throws IOException, JSONException  {
		InputStream open = null;
		try {
			name = fileName;
			AssetManager am = context.getAssets();
			open = am.open(fileName);

			BufferedReader rd = new BufferedReader(new InputStreamReader(open));
			StringBuilder sb = new StringBuilder();
			for (String line = rd.readLine(); line != null; line = rd.readLine()) {
				if (line == null || line.length() == 0) {
					continue;
				}
				sb.append(line);
			}
			jsonObject = new JSONObject(sb.toString());
		} finally {
			IOUtil.closeSilently(open);
		}
	}

	@Override
	public int getInt(String key, int def) {
		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			return def;
		}
	}

	@Override
	public String getString(String key) {
		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public String[] getStrings(String key) {
		try {
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			int length = jsonArray.length();
			String[] strs = new String[length];
			for (int i = 0; i < length; i++) {
				strs[i] = jsonArray.getString(i);
			}
			return strs;
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			return def;
		}
	}

	@Override
	public double getDouble(String key, double def) {
		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			return def;
		}
	}

	@Override
	public String getName() {
		return name;
	}

}

package com.peter.android.config.cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.peter.android.AppException;
import com.peter.toolbox.IOUtil;

/**
 * 云端配置文案
 */
public class JsonCloudResouceImp implements CloudResource {

	private static final String FOLDER = "cloud_json_prefs";

	private static Object _lock = new Object();

	private JSONObject jsonObject = new JSONObject();

	private String name;

	public JsonCloudResouceImp(Context context, String fileName) throws AppException {
		if (context == null || fileName == null) {
			throw new NullPointerException("参数不能为Null");
		}
		init(context, fileName);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param fileName
	 */
	protected void init(Context context, String fileName) throws AppException {
		name = fileName;
		InputStream open = null;
		try {
			if (!checkFolder(context)) {
				throw new IOException("创建文件夹失败");
			}
			open = context.openFileInput(FOLDER + "/" + fileName);
			BufferedReader rd = new BufferedReader(new InputStreamReader(open));
			StringBuilder sb = new StringBuilder();
			for (String line = rd.readLine(); line != null; line = rd.readLine()) {
				if (line == null || line.length() == 0) {
					continue;
				}
				sb.append(line);
			}
			jsonObject = new JSONObject(sb.toString());
		} catch (IOException e) {
			throw AppException.resouce("IO", e);
		} catch (JSONException e) {
			throw AppException.resouce("Json Formatter error", e);
		} finally {
			IOUtil.closeSilently(open);
		}

	}

	private boolean checkFolder(Context context) {
		boolean valid = false;
		synchronized (_lock) {
			String folder = "/data/data/" + context.getPackageName() + "/" + FOLDER;
			File file = new File(folder);
			if (file.exists() && file.isDirectory()) {
				valid = true;
			} else {
				valid = file.mkdir();
			}
			return valid;
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

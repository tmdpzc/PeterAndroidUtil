package com.peter.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;

import com.ini.IniResolver;
import com.peter.android.AppContext;
import com.peter.toolbox.JSONUtil;

/**
 * 
 * @author peizicheng@gmail.com
 * @date 2013-10-26
 */
public class TestJSON {
	public static final String MAP_PARAM_CLASS = "PARAM";// Map中类名后缀

	public static void testJSON(Context context) throws IOException {
		// Map<String,String> map = new HashMap<String, String>();
		// for (int i = 0; i < 10; i++) {
		// map.put("pkn"+ i , "var" + i);
		// }
		// String json = JSONUtil.toJSON(map);
		// System.out.println(json);
//		AssetManager am = context.getAssets();
//		InputStream open = am.open("clearprocess.filter");
//		if (open == null) {
//			System.out.println("文件不存在");
//		}
//
//		BufferedReader rd = new BufferedReader(new InputStreamReader(open));
//		Map<String, String> map = new HashMap<String, String>();
//		for (String line = rd.readLine(); line != null; line = rd.readLine()) {
//			if (line == null || line.length() == 0) {
//				continue;
//			}
//			if (line.charAt(0) == '[') {
//				continue;
//			}
//			String[] split = line.split("=");
//			map.put(split[0], split[1]);
//			System.out.println(line);
//		}
//		String json = JSONUtil.toJSON(map);
//		System.out.println(json);
//		File exFile = context.getExternalCacheDir();
//		File file = new File(exFile, "process_list.json");
//		file.createNewFile();
//		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//		writer.write(json);
//		writer.flush();
//		writer.close();
		new TestThread2(1000).start();
	}

	/**
	 * 
	 * @author peizicheng@gmail.com
	 * @date 2013-10-28
	 */
	public static class TestThread extends Thread {

		
		private int  times = 0;
		private int  count = 0;
		

		public TestThread(int times) {
			super();
			this.times = times;
		}



		@Override
		public void run() {
			int i = 0;
			long _beginTime = System.currentTimeMillis();
			while ( count < times) {
				count++;
				doWork();
			}
			long _endTime = System.currentTimeMillis();
			System.out.println("用时：" + (_endTime - _beginTime));
		}
		
		private void doWork(){
			try {
				AssetManager am = AppContext.getInstance().getAssets();
				InputStream open = am.open("process.json");
				if (open == null) {
					System.out.println("文件不存在");
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(open));
				StringBuilder sb = new StringBuilder();
				for (String line = rd.readLine(); line != null; line = rd.readLine()) {
					if (line == null || line.length() == 0) {
						continue;
					}
					sb.append(line);
				}
				Map<String, Object> map = JSONUtil.getMap(sb.toString());
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 
	 * @author peizicheng@gmail.com
	 * @date 2013-10-28
	 */
	public static class TestThread2 extends Thread {

		private int  times = 0;
		private int  count = 0;
		

		public TestThread2(int times) {
			super();
			this.times = times;
		}



		@Override
		public void run() {
			long _beginTime = System.currentTimeMillis();
			while ( count < times) {
				count++;
				doWork();
			}
			long _endTime = System.currentTimeMillis();
			System.out.println("用时：" + (_endTime - _beginTime));
		}
		
		private void doWork(){
			try {
				AssetManager am = AppContext.getInstance().getAssets();
				InputStream open = am.open("clearprocess.filter");
				IniResolver resolver = new IniResolver();
				resolver.load(new InputStreamReader(open));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

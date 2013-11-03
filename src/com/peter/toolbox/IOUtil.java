package com.peter.toolbox;

import java.io.Closeable;
import java.util.zip.ZipFile;

import android.database.Cursor;

/**
 * IO 工具
 * @author peizicheng@gmail.com
 * @date 2013-10-28
 */
public class IOUtil {
	public static void closeSilently(Closeable closeable) {
		if (closeable == null)
			return;
		try {
			closeable.close();
		} catch (Exception e) {
		}
	}

	public static void closeSilently(ZipFile zipFile) {
		if (zipFile == null)
			return;
		try {
			zipFile.close();
		} catch (Exception e) {
		}
	}

	public static void closeSilently(Cursor cursor) {
		if (cursor == null)
			return;
		try {
			cursor.close();
		} catch (Exception e) {
		}
	}
	
}

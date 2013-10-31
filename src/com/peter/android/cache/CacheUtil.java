package com.peter.android.cache;

import com.example.peterandroidutil.BuildConfig;
/**
 * 
 * @author peizicheng@conew.com
 * @date 2013-9-16
 */
public class CacheUtil {
	public static class ImageSize {
		int height;
		int width;
		

		public ImageSize(int height, int width) {
			super();
			this.height = height;
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		private static ImageSize sDefault = new ImageSize(40, 40);
		
		public static ImageSize getDefault(){
			return sDefault;
		}
	}

	private static final String URI_AND_SIZE_SEPARATOR = "_";
	private static final String WIDTH_AND_HEIGHT_SEPARATOR = "x";

	public static String generateKey(String imageUri, ImageSize targetSize) {
		return new StringBuilder(imageUri).append(URI_AND_SIZE_SEPARATOR).append(targetSize.getWidth())
				.append(WIDTH_AND_HEIGHT_SEPARATOR).append(targetSize.getHeight()).toString();
	}
	
	
	public static final boolean DBG = BuildConfig.DEBUG;
}

package com.yxld.xzs.utils.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Bimp {
	public static int max = 0;

	public static int imgCount = 0;
	public static int imgTousuCount = 0;
	public static int imgJianyiCount = 0;

	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //维修选择的图片的临时列表
	public static ArrayList<ImageItem> tempTousuSelectBitmap = new ArrayList<ImageItem>();   //投诉选择的图片的临时列表
	public static ArrayList<ImageItem> tempJianyiSelectBitmap = new ArrayList<ImageItem>();   //建议选择的图片的临时列表
	public static ArrayList<ImageItem> tempShouhouSelectBitmap = new ArrayList<ImageItem>();   //售后图片的临时列表


	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
}
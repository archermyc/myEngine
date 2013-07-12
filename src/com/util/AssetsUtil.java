package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myc.engine.GameApp;

public class AssetsUtil {

	public static Bitmap getBitmap(String name) {
		String filePath = name;
		Context context = GameApp.getInstnce().getApplicationContext();
		AssetManager assetManager = context.getAssets();
		InputStream io;
		Bitmap m = null;
		try {
			io = assetManager.open(filePath);
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			m = BitmapFactory.decodeStream(io, null, opt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m = null;
		}
		return m;
	}

	public static JSONObject getJson(String fileName) {
		JSONObject cache = JsonCache.getInstance().get(fileName);
		if (cache != null) {
			return cache;
		}
		Context context = GameApp.getInstnce().getApplicationContext();
		AssetManager assetManager = context.getAssets();

		InputStream inputStream = null;

		try {

			inputStream = assetManager.open(fileName);
			String config = readTextFile(inputStream);
			JSONObject jObject;
			try {
				jObject = new JSONObject(config);
				JsonCache.getInstance().put(fileName, jObject);
				return jObject;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (IOException e) {

			Log.e("tag", e.getMessage());
		}

		return null;
	}

	private static String readTextFile(InputStream inputStream) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte buf[] = new byte[1024];

		int len;

		try {

			while ((len = inputStream.read(buf)) != -1) {

				outputStream.write(buf, 0, len);

			}
			outputStream.close();

			inputStream.close();

		} catch (IOException e) {

		}

		return outputStream.toString();

	}
}

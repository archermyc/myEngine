package com.engine.texture;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.myc.engine.GameApp;
import com.util.AssetsUtil;

public class TextureCache {
	HashMap<Integer, SoftReference<ResourceTexture>> mBitmapTexture = null;

	HashMap<String, SoftReference<ResourceTexture>> mCache = null;

	static TextureCache instance = null;

	public static TextureCache getInstance() {
		if (instance == null) {
			instance = new TextureCache();
		}
		return instance;
	}

	@SuppressLint("UseSparseArrays")
	private TextureCache() {
		mBitmapTexture = new HashMap<Integer, SoftReference<ResourceTexture>>();
		mCache = new HashMap<String, SoftReference<ResourceTexture>>();
	}

	public void put(int resId, ResourceTexture bt) {
		mBitmapTexture.put(resId, new SoftReference<ResourceTexture>(bt));
	}

	public ResourceTexture get(int resId) {
		SoftReference<ResourceTexture> ref = mBitmapTexture.get(Integer
				.valueOf(resId));
		ResourceTexture bTexture = null;
		if (null != ref) {
			bTexture = ref.get();
			if (bTexture == null) {
				Context context = GameApp.getInstnce().getApplicationContext();
				bTexture = new ResourceTexture(context, resId);
				bTexture.getBitmap();
				put(resId, bTexture);
			}
		} else {
			Context context = GameApp.getInstnce().getApplicationContext();
			bTexture = new ResourceTexture(context, resId);
			bTexture.getBitmap();
			put(resId, bTexture);
		}
		return bTexture;
	}

	public ResourceTexture remove(int key) {
		SoftReference<ResourceTexture> ref = mBitmapTexture.remove(key);
		ResourceTexture bTexture = null;
		if (null != ref) {
			bTexture = ref.get();
		} else {

		}
		return bTexture;
	}

	public void free() {
		for (int resId : mBitmapTexture.keySet()) {
			SoftReference<ResourceTexture> ref = mBitmapTexture.get(resId);
			if (ref == null)
				continue;
			BitmapTexture bTexture = ref.get();
			if (bTexture != null) {
				bTexture.deleteFromGL();
			}
		}
	}

	public ResourceTexture get(String key) {
		SoftReference<ResourceTexture> ref = mCache.get(key);
		ResourceTexture bTexture = null;
		if (null != ref) {
			bTexture = ref.get();
			if (bTexture == null) {
				Bitmap bitmap = AssetsUtil.getBitmap(key);
				bTexture = new ResourceTexture(GameApp.getInstnce()
						.getApplicationContext(), bitmap);
				put(key, bTexture);
			}
		} else {
			Bitmap bitmap = AssetsUtil.getBitmap(key);
			bTexture = new ResourceTexture(GameApp.getInstnce()
					.getApplicationContext(), bitmap);
			put(key, bTexture);
		}
		return bTexture;
	}

	public void put(String key, ResourceTexture bt) {
		mCache.put(key, new SoftReference<ResourceTexture>(bt));
	}
}

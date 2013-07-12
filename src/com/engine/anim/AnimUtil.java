package com.engine.anim;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.engine.texture.ResourceTexture;
import com.engine.texture.TextureCache;

public class AnimUtil {
	/** �����ƶ����� **/
	public final static int ANIM_DOWN = 0;
	/** �����ƶ����� **/
	public final static int ANIM_LEFT = 1;
	/** �����ƶ����� **/
	public final static int ANIM_RIGHT = 2;
	/** �����ƶ����� **/
	public final static int ANIM_UP = 3;

	/**
	 * 读取图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	// 设置图片大小
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

	/**
	 * 截取图片一部分
	 * 
	 * @param bitmap
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap BitmapClipBitmap(Bitmap bitmap, int x, int y, int w,
			int h) {
		Bitmap tmp = Bitmap.createBitmap(bitmap, x, y, w, h);
		// if (w > 64 && h > 64) {
		tmp = resizeImage(tmp, 64, 64);
		// }
		return tmp;
	}
	
	public static GLAnimation[] loadAnimBitmap(Context context, Bitmap testmap,
			int count) {
		Bitmap[][] bitmap = new Bitmap[count][count];
		GLAnimation mEnemyAnim[] = new GLAnimation[count];
		int tileWidth = testmap.getWidth() / count;
		int tileHeight = testmap.getHeight() / count;
		int i = 0, x = 0, y = 0;
		for (i = 0; i < count; i++) {
			y = 0;
			bitmap[ANIM_DOWN][i] = BitmapClipBitmap(testmap, x, y, tileWidth,
					tileHeight);
			y += tileHeight;
			bitmap[ANIM_LEFT][i] = BitmapClipBitmap(testmap, x, y, tileWidth,
					tileHeight);
			y += tileHeight;
			bitmap[ANIM_RIGHT][i] = BitmapClipBitmap(testmap, x, y, tileWidth,
					tileHeight);
			y += tileHeight;
			bitmap[ANIM_UP][i] = BitmapClipBitmap(testmap, x, y, tileWidth,
					tileHeight);
			x += tileWidth;
		}
		mEnemyAnim[ANIM_DOWN] = new GLAnimation(context, bitmap[ANIM_DOWN],
				true);
		mEnemyAnim[ANIM_LEFT] = new GLAnimation(context, bitmap[ANIM_LEFT],
				true);
		mEnemyAnim[ANIM_RIGHT] = new GLAnimation(context, bitmap[ANIM_RIGHT],
				true);
		mEnemyAnim[ANIM_UP] = new GLAnimation(context, bitmap[ANIM_UP], true);
		return mEnemyAnim;
	}

	public static GLAnimation loadAnim(int[] resId) {
		ResourceTexture res[] = new ResourceTexture[resId.length];
		for (int i = 0; i < resId.length; i++) {
			res[i] = TextureCache.getInstance().get(resId[i]);
		}
		return new GLAnimation(res, false);
	}
	
	public static GLAnimation loadAnim(String[] keys) {
		ResourceTexture res[] = getResList(keys);
		
		return new GLAnimation(res, false);
	}
	
	public static ResourceTexture[] getResList(String[] keys) {
		ResourceTexture res[] = new ResourceTexture[keys.length];
		for (int i = 0; i < keys.length; i++) {
			res[i] = TextureCache.getInstance().get(keys[i]);
		}
		return res;
	}

	public static Rect[][] getDrawingRect(int w, int h, int count) {
		int tileWidth = w;
		int tileHeight = h;
		Rect[][] rects = new Rect[count][count];
		int i = 0, x = 0, y = 0;
		for (i = 0; i < count; i++) {
			y = 0;
			rects[ANIM_DOWN][i] = new Rect(x, y, tileWidth, tileHeight);
			y += tileHeight;
			rects[ANIM_LEFT][i] = new Rect(x, y, tileWidth, tileHeight);
			y += tileHeight;
			rects[ANIM_RIGHT][i] = new Rect(x, y, tileWidth, tileHeight);
			y += tileHeight;
			rects[ANIM_UP][i] = new Rect(x, y, tileWidth, tileHeight);
			x += tileWidth;
		}
		return rects;
	}
	
}

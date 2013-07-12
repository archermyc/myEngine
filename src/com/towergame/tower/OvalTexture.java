package com.towergame.tower;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.engine.texture.CanvasTexture;

public class OvalTexture extends CanvasTexture {

	private int mColor;

	public OvalTexture(int width, int height) {
		super(width, height);
		mColor = Color.argb(75, 255, 0, 0);
	}

	public OvalTexture(int width, int height, int color) {
		super(width, height);
		mColor = color;
	}

	@Override
	protected void draw(Canvas canvas, Bitmap backing) {
		Paint paint = new Paint();
		// TODO Auto-generated method stub
		paint.setColor(mColor);
		canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
	}

}

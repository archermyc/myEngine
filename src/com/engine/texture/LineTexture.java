package com.engine.texture;

import android.graphics.Color;

import com.engine.entity.GLRootView;

public class LineTexture  implements Texture{

	private static final float LINEWIDTH_DEFAULT = 2.0f;

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mX2;
	protected float mY2;

	private float mLineWidth;
	
	protected int mColor = Color.RED;
	
	public LineTexture() {
		
	}
	

	@Override
	public void draw(GLRootView root, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GLRootView root, int x, int y, int endx, int endy) {
		// TODO Auto-generated method stub
		root.drawLine(x, y, endx, endy, mColor);
	}

}

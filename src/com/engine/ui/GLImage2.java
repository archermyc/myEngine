package com.engine.ui;

import javax.microedition.khronos.opengles.GL11;

import android.annotation.SuppressLint;
import android.content.Context;

import com.engine.entity.GLRootView;
import com.engine.entity.MeasureHelper;
import com.engine.texture.NinePatchTexture;
import com.example.myc.engine.GameApp;

public class GLImage2 extends GLNode {

	private NinePatchTexture mIcon;

	public GLImage2(int resId) {
		Context context = GameApp.getInstnce().getApplicationContext();
		mIcon = new NinePatchTexture(context, resId);
		layout(0, 0, mIcon.getWidth(), mIcon.getHeight());
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int height = mIcon.getHeight();
		int width = mIcon.getWidth();
		new MeasureHelper(this).setPreferredContentSize(width, height).measure(
				widthSpec, heightSpec);
		super.onMeasure(widthSpec, heightSpec);
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mIcon != null) {
			mIcon.draw(root, 0, 0, getWidth(), getHeight());
		} else {
		}
		super.render(root, gl);
	}

}

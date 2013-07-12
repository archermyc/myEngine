package com.engine.ui;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;

import com.engine.entity.GLRootView;
import com.engine.entity.MeasureHelper;
import com.engine.texture.StringTexture;

public class GLText extends GLNode {
	StringTexture mText;
	public GLText(String text) {
		this(text, 24, Color.WHITE);
	}

	public GLText(String text, int fontSize, int color) {
		mText = StringTexture.newInstance(text, fontSize, color);
		int w = mText.getWidth();
		int h = mText.getHeight();
		layout(0, 0, w, h);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int height = mText.getHeight();
		int width = mText.getWidth();
		new MeasureHelper(this).setPreferredContentSize(width, height).measure(
				widthSpec, heightSpec);
		super.onMeasure(widthSpec, heightSpec);
	}

	public void setText(String text) {
		mText = StringTexture.newInstance(text, mText.getFontSize(), mText.getColor());
		int w = mText.getWidth();
		int h = mText.getHeight();
		setSize(w, h);
	}

	public void setTextColor(int color) {
		String text = mText.getText();
		mText = StringTexture.newInstance(text, mText.getFontSize(), color);
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mText != null) {
			mText.draw(root, 0, 0);
		} else {
		}
		super.render(root, gl);
	}
}

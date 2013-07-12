package com.engine.ui;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;
import android.view.MotionEvent;

import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.engine.texture.StringTexture;
import com.engine.texture.TextureCache;
import com.example.myc.engine.R;

public class GLButton extends GLNode {
	private ResourceTexture mBg;
	StringTexture mText;
	private int mTextX = 0;
	private int mTextY = 0;
	protected boolean drawGrayFlag = false;

	public GLButton() {
		this(null);
	}
	
	public GLButton(int resid) {
		this(resid, null);
	}

	public GLButton(String text) {
		mBg = TextureCache.getInstance().get(R.drawable.btn_default);
		if (text != null && text != "") {
			mText = StringTexture.newInstance(text, 24, Color.WHITE);
			mTextX = (mBg.getWidth() - mText.getWidth()) / 2;
			mTextY = (mBg.getHeight() - mText.getHeight()) / 2;
		}
		layout(0, 0, mBg.getWidth(), mBg.getHeight());
	}

	public GLButton(String bgResId, String text) {
		mBg = TextureCache.getInstance().get(bgResId);
		if (text!=null && !text.equals("")) {
			mText = StringTexture.newInstance(text, 24, Color.RED);
		}
		layout(0, 0, mBg.getWidth(), mBg.getHeight());
	}
	
	public GLButton(int bgResId, String text) {
		mBg = TextureCache.getInstance().get(bgResId);
		if (text!=null && !text.equals("")) {
			mText = StringTexture.newInstance(text, 24, Color.RED);
		}
		layout(0, 0, mBg.getWidth(), mBg.getHeight());
	}
	
	public void setBg(int bgResId) {
		mBg = TextureCache.getInstance().get(bgResId);
	}

	public void setText(String text) {
		if (text != null && text != "") {
			if (mText != null) {
				mText = StringTexture.newInstance(text, mText.getFontSize(),
						Color.WHITE);
			} else {
				mText = StringTexture.newInstance(text, 24, Color.WHITE);

			}
			mTextX = (mBg.getWidth() - mText.getWidth()) / 2;
			mTextY = (mBg.getHeight() - mText.getHeight()) / 2;
		}
	}

	public void setTextColor() {

	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mBg != null) {
			mBg.draw(root, 0, 0, getWidth(), getHeight());
		} else {
		}
		if (mText != null) {
			mText.draw(root, mTextX, mTextY);
		} else {
		}
		if (drawGrayFlag) {
			root.drawColor(0, 0, getWidth(), getHeight(),
					Color.argb(100, 0, 0, 0));
		}
		super.render(root, gl);
	}

	@Override
	protected boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			drawGrayFlag = true;
		} else if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {
			drawGrayFlag = false;
		}

		super.onTouch(event);
		return true;
	}

	public void setOnClickListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		super.setOnClickListener(listener);
	}
}

package com.engine.ui;

import javax.microedition.khronos.opengles.GL11;

import android.R.color;
import android.R.mipmap;
import android.graphics.Color;

import com.engine.entity.GLRootView;
import com.engine.entity.MeasureHelper;
import com.engine.texture.NinePatchTexture;
import com.engine.texture.ResourceTexture;
import com.engine.texture.Texture;
import com.engine.texture.TextureCache;

public class GLImage extends GLNode {
	private ResourceTexture mIcon;
	protected Texture mBackground;

	public GLImage(int resId) {
		mIcon = TextureCache.getInstance().get(resId);
		layout(0, 0, mIcon.getWidth(), mIcon.getHeight());
	}

	public GLImage(String resId) {
		mIcon = TextureCache.getInstance().get(resId);
		layout(0, 0, mIcon.getWidth(), mIcon.getHeight());
	}

	public void setFile(String filename) {
		mIcon = TextureCache.getInstance().get(filename);
		setSize(mIcon.getWidth(), mIcon.getHeight());
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int height = 0;
		int width = 0;
		if (mIcon != null) {
			height = mIcon.getHeight();
			width = mIcon.getWidth();
		}
		new MeasureHelper(this).setPreferredContentSize(width, height).measure(
				widthSpec, heightSpec);
		super.onMeasure(widthSpec, heightSpec);
	}

	public void setBackground(Texture background) {
		if (background == mBackground)
			return;
		mBackground = background;
		if (background != null && background instanceof NinePatchTexture) {
			setPaddings(((NinePatchTexture) mBackground).getPaddings());
		} else {
			setPaddings(0, 0, 0, 0);
		}
		invalidate();
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mBackground != null) {
			mBackground.draw(root, 0, 0, getWidth(), getHeight());
		}
		if (mIcon != null) {
//			root.drawColor(0, 0, getWidth(), getHeight(), Color.YELLOW);
			mIcon.draw(root, 0, 0, getWidth(), getHeight());
		}

		super.render(root, gl);
	}

}

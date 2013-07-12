package com.engine.anim;

import android.content.Context;
import android.graphics.Bitmap;

import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;

/**
 * ����֡������ ��N��ͼƬ��ϵ�һ�𲥷�
 * @author YCMO
 *
 */
public class GLAnimation extends GLAnimationBase{

	ResourceTexture[] mResTextureList = null;
	int width;
	int height;

	/**
	 * ���캯��
	 * 
	 * @param context
	 * @param frameBitmap
	 * @param isloop
	 */
	public GLAnimation(Context context, Bitmap[] frameBitmap, boolean isloop) {
		mFrameCount = frameBitmap.length;
		width = frameBitmap[0].getWidth();
		height = frameBitmap[0].getHeight();
		mResTextureList = new ResourceTexture[mFrameCount];
		for (int i = 0; i < frameBitmap.length; i++) {
			mResTextureList[i] = new ResourceTexture(context, frameBitmap[i]);
		}
		mIsLoop = isloop;
	}
	
	public GLAnimation(ResourceTexture[] res, boolean isloop) {
		mFrameCount = res.length;
		width = res[0].getWidth();
		height = res[0].getHeight();
		mResTextureList = res;
		mIsLoop = isloop;
	}
	
	public void setResourceTexture(ResourceTexture[] res) {
		mFrameCount = res.length;
		width = res[0].getWidth();
		height = res[0].getHeight();
		mResTextureList = res;
	}
	


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void drawAnimation(GLRootView root, int x, int y, int w, int h) {
		// 如果没有播放结束则继续播放
		if (!mIsend) {
			mResTextureList[mPlayID].draw(root, x, y, w, h);
			mLastPlayTime += FPS.getDeltaTime();
			if (mLastPlayTime > mAnimTime) {
				mPlayID++;
				mLastPlayTime = 0;
				if (mPlayID >= mFrameCount) {
					// 标志动画播放结束
					mIsend = true;
					mPlayID = 0;
					doAnimListener();
					if (mIsLoop) {
						// 设置循环播放
						mIsend = false;
						mPlayID = 0;
					}
				}
			}
		} else {
			mResTextureList[mPlayID].draw(root, x, y, w, h);
		}
	}
	public void drawOneFrame(GLRootView root, int index, int x, int y, int w,
			int h) {
		mResTextureList[index].draw(root, x, y, w, h);
	}

}

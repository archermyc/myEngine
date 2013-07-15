package com.engine.anim;

import android.graphics.Rect;

import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.example.myc.engine.GameApp;

/**
 * 4*4 ����ƴͼ����
 * 
 * @author YCMO
 * 
 */
public class GLAnimation2 extends GLAnimationBase {

	public final static int ANIM_DOWN = 0;
	/** 向左移动动画 **/
	public final static int ANIM_LEFT = 1;
	/** 向右移动动画 **/
	public final static int ANIM_RIGHT = 2;
	/** 向上移动动画 **/
	public final static int ANIM_UP = 3;

	ResourceTexture mResTexture;// // 绘制的原始资源
	Rect[][] mDrawingRect; // 绘制矩阵坐标，比如绘制4*4图片右上角一块区域
	private int mDirection; // 移动方向

	public void setDrawingRect(Rect[][] mDrawingRect) {
		this.mDrawingRect = mDrawingRect;
	}

	public GLAnimation2(ResourceTexture res, Rect[][] drawRects) {
		mResTexture = res;
		mDrawingRect = drawRects;
		mDirection = ANIM_RIGHT; // 默认方向向右
		mFrameCount = 4;
		mIsLoop = true;
	}

	@Override
	public void drawAnimation(GLRootView root, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		if (!mIsend) {
			int x1 = 0;
			int y1 = 0;
			if (mDrawingRect != null) {
//				int clipX = x;
////				int clipY = (int) (y - GameApp.getInstnce().ScaleYoffset
////						/ GameApp.getInstnce().ScreenScale);
//				int clipY = (int) (y - GameApp.getInstnce().ScaleYoffset
//						/ 1);
				root.clipRect(0, 0, w, h);
				x1 = mDrawingRect[mDirection][mPlayID].left;
				y1 = mDrawingRect[mDirection][mPlayID].top;
			}
			mResTexture.draw(root, x - x1, y - y1, w * 4, h * 4);
			root.clearClip();
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
						mIsend = false;
						mPlayID = 0;
					}
				}
			}
		}
	}

	/**
	 * 设置方向
	 * 
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.mDirection = direction;
	}

	@Override
	public void drawOneFrame(GLRootView root, int index, int x, int y, int w,
			int h) {
		if (mDrawingRect != null) {
			int x1 = -mDrawingRect[mDirection][index].left;
			int y1 = -mDrawingRect[mDirection][index].top;
			// root.clipRect(x, y-32, w, h);
			// mResTexture.draw(root, x1, y1+32, w * 4, h * 4);
			// root.clipRect(0, 0, w, h);
			mResTexture.draw(root, 0, 0, w * 4, h * 4);
			// root.clearClip();
		}
	}
}

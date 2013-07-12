package com.engine.anim;

import com.engine.entity.GLRootView;

public abstract class GLAnimationBase {

	/** 上一帧播放时间 **/
	protected float mLastPlayTime = 0;
	/** 播放当前帧的ID **/
	protected int mPlayID = 0;
	/** 动画frame数量 **/
	protected int mFrameCount = 0;
	/** 用于储存动画资源图片 **/
	/** 是否循环播放 **/
	protected boolean mIsLoop = false;
	/** 播放结束 **/
	protected boolean mIsend = false;
	/** 动画播放间隙时间 **/
	protected float mAnimTime = 0.1f;

	protected FrameAnimationListener mFrameAnimationListener;

	public void setAnimTime(float time) {
		mAnimTime = time;
	}

	public void setFrameAnimationListener(FrameAnimationListener listener) {
		this.mFrameAnimationListener = listener;
	}

	public void doAnimListener() {
		if (mFrameAnimationListener != null) {
			mFrameAnimationListener.onAnimEnd(0);
		}
	}

	/**
	 * 重置动画， 从第一张图片开始播放
	 */
	public void reStart() {
		mPlayID = 0;
		mIsend = false;
	}

	public boolean isAnimEnd() {
		return mIsend;
	}

	public void setAnimEnd(boolean mIsend) {
		this.mIsend = mIsend;
	}

	public void setAnimLoop(boolean isLoop) {
		this.mIsLoop = isLoop;
	}

	public boolean isLoop() {
		return mIsLoop;
	}

	/**
	 * 播放动画
	 * @param root
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public abstract void drawAnimation(GLRootView root, int x, int y, int w,
			int h);

	/**
	 * 播放一帧
	 * @param root
	 * @param index
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public abstract void drawOneFrame(GLRootView root, int index, int x, int y,
			int w, int h);
}

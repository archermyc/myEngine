package com.towergame.tower;

import javax.microedition.khronos.opengles.GL11;

import com.engine.anim.AnimUtil;
import com.engine.anim.FrameAnimationListener;
import com.engine.anim.GLAnimation;
import com.engine.entity.GLRootView;

public abstract class GLTowerSprite extends GLTower {

	GLAnimation mSpriteAnim;
	public boolean playFlag = false;
	

	public GLTowerSprite(int[] resId) {
		mSpriteAnim = AnimUtil.loadAnim(resId);
		mSpriteAnim.setAnimLoop(false); //设置是否循环
		int w = mSpriteAnim.getWidth();
		int h = mSpriteAnim.getHeight();
		playFlag = false;
		layout(0, 0, w, h);
	}
	
	public GLTowerSprite(String[] resId) {
		mSpriteAnim = AnimUtil.loadAnim(resId);
		mSpriteAnim.setAnimLoop(false); //设置是否循环
		int w = mSpriteAnim.getWidth();
		int h = mSpriteAnim.getHeight();
		playFlag = false;
		layout(0, 0, w, h);
	}

	public void setFrameAnimationListener(FrameAnimationListener l) {
		mSpriteAnim.setFrameAnimationListener(l);
	}

	public void setAnimTime(Float time) {
		if (mSpriteAnim != null) {
			mSpriteAnim.setAnimTime(time);
		}
	}

	public GLAnimation getGLAnimation() {
		return mSpriteAnim;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (playFlag == true) {
			mSpriteAnim.drawAnimation(root, 0, 0, getWidth(), getHeight());
		} else {
			mSpriteAnim.drawOneFrame(root, 0, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}

}

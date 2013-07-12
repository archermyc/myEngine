package com.towergame.bullet;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.opengles.GL11;

import com.engine.anim.AnimUtil;
import com.engine.anim.FrameAnimationListener;
import com.engine.anim.GLAnimation;
import com.engine.entity.GLRootView;
import com.towergame.enemy.GLEnemy;

public abstract class GLBulletSprite extends GLBullet {

	GLAnimation mSpriteAnim;
	protected boolean playFlag = false;

	protected WeakReference<GLEnemy> mTarEnemy; // 当前敌人

	public GLBulletSprite(int[] resId) {
		mSpriteAnim = AnimUtil.loadAnim(resId);
		mSpriteAnim.setAnimLoop(true);
		int w = mSpriteAnim.getWidth();
		int h = mSpriteAnim.getHeight();
		playFlag = false;
		layout(0, 0, w, h);
	}
	
	public GLBulletSprite(String[] resId) {
		mSpriteAnim = AnimUtil.loadAnim(resId);
		mSpriteAnim.setAnimLoop(true);
		int w = mSpriteAnim.getWidth();
		int h = mSpriteAnim.getHeight();
		playFlag = false;
		layout(0, 0, w, h);
	}

	public void setAnimTime(Float time) {
		if (mSpriteAnim != null) {
			mSpriteAnim.setAnimTime(time);
		}

	}

	public void setAnimEndlistener(FrameAnimationListener l) {
		mSpriteAnim.setFrameAnimationListener(l);
	}

	public GLAnimation getGLAnimation() {
		return mSpriteAnim;
	}

	public void startPlay() {
		playFlag = true;
	}
	

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (playFlag == true) {
			mSpriteAnim.drawAnimation(root, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}
}

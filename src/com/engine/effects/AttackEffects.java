package com.engine.effects;

import javax.microedition.khronos.opengles.GL11;

import com.engine.anim.AnimUtil;
import com.engine.anim.GLAnimation;
import com.engine.entity.GLRootView;
import com.engine.ui.GLNode;
import com.towergame.enemy.GLEnemy;

/**
 * 攻击特效动画
 * 
 * @author YCMO
 * 
 */
public class AttackEffects extends GLNode {
	GLAnimation mSpriteAnim;
	protected boolean playFlag = false;
	
	protected GLEnemy mTarEnemy; // 攻击目标

	public AttackEffects(int[] resId) {

		mSpriteAnim = AnimUtil.loadAnim(resId);
		mSpriteAnim.setAnimLoop(true); // 设置动画循环
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
	
	
	
	/**
	 * 
	 */
	public void setTarget(GLEnemy enemy) {
		this.mTarEnemy = enemy;
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

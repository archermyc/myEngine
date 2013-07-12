package com.towergame.bullet;

import java.lang.ref.WeakReference;

import com.towergame.TowerGameUtil;
import com.towergame.enemy.GLEnemy;
import com.towergame.enemy.GLEnemy.EnemyState;

public class GLLightning extends GLBulletSprite {

	public GLLightning(int[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		
	}
	
	public GLLightning(String[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void update() {
		GLEnemy enemy = null;
		if (mTarEnemy != null) {
			enemy = mTarEnemy.get();
		}
		if (enemy != null && enemy.getState() != EnemyState.Death) {
			int distance = TowerGameUtil.getTwoPointDistance(getX(), getY(),
					enemy.getCenterX(), enemy.getCenterY());
			float rotation = (float) TowerGameUtil.getRotation(getX(), getY(),
					enemy.getCenterX(), enemy.getCenterY());
			setRotate(rotation);
			setSize(distance, getHeight());
		} else {
			if (mTarEnemy != null) {
				mTarEnemy.clear();
				mTarEnemy = null;
			}
		}
		super.update();
	}

	@Override
	public void setTarget(GLEnemy enemy) {
		mTarEnemy = new WeakReference<GLEnemy>(enemy);;
		setSize(0, getHeight());
	}

}

package com.towergame.bullet;

import com.engine.texture.TextureCache;
import com.towergame.enemy.GLEnemy;

/**
 * 普通子弹
 * @author YCMO
 *
 */
public class GLNormalBullet extends GLBullet {

	@Override
	public void setTarget(GLEnemy enemy) {
		// TODO Auto-generated method stub

	}

	public GLNormalBullet(int resId, int damage) {
		setBackground(TextureCache.getInstance().get(resId));
		this.damage = damage;
		bulletMoveTime = 0.15f;
	}
	
	public GLNormalBullet(String resId, int damage) {
		setBackground(TextureCache.getInstance().get(resId));
		this.damage = damage;
		bulletMoveTime = 0.15f;
	}
}

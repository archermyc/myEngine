package com.towergame.bullet;

import javax.microedition.khronos.opengles.GL11;

import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.engine.ui.GLNode;
import com.towergame.enemy.GLEnemy;

public abstract class GLBullet extends GLNode {
	float speed = 1200.0f;
	
	float bulletMoveTime = 0.500f; // 子弹移动时间

	int damage; // 子弹破坏力

	protected ResourceTexture mBackground;

	public void setBackground(ResourceTexture background) {
		if (background == mBackground)
			return;
		mBackground = background;
		setSize(mBackground.getWidth(), mBackground.getHeight());
	}
	
	/**
	 * 获得子弹移动时间，时间越短速度越快
	 * @return
	 */
	public float getBulletMoveTime() {
		return bulletMoveTime;
	}
	
	public void setBulletMoveTime(float bulletMoveTime) {
		this.bulletMoveTime = bulletMoveTime;
	}

	/**
	 * 
	 */
	public abstract void setTarget(GLEnemy enemy);

	public float getSpeed() {
		return speed;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	
	@Override
	protected void render(GLRootView root, GL11 gl) {
		// TODO Auto-generated method stub
		if (mBackground != null) {
			mBackground.draw(root, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}

}

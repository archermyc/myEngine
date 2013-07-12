package com.towergame.enemy;

import com.engine.entity.FPS;

/**
 * 盗贼
 * @author YCMO
 *
 */
public class GLRobberEnemy extends GLEnemy{

	float HPUpTime = 0;
	public GLRobberEnemy(int resId) {
		super(resId);
		// TODO Auto-generated constructor stub
	}
	public GLRobberEnemy(String resId) {
		super(resId);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 每0.1秒回复一点生命值
	 */
	protected void update() {
		HPUpTime += FPS.getDeltaTime();
		if (HPUpTime >0.1f) {
			if (state != EnemyState.Death) {
				if (this.mData.curHP < mHpBar.getHpMax()) {
					this.mData.curHP+=1;
				}
			}
			HPUpTime = 0;
		}
		super.update();
	}

}

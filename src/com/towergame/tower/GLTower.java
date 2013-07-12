package com.towergame.tower;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.engine.ui.GLNode;
import com.towergame.enemy.GLEnemy;

public abstract class GLTower extends GLNode {

	// **********炮台属性**********
	private String mTowerName = "";
	protected int mAttackRange = 100; // 攻击范围
	int mDamage; // 攻击力
	float attackTime = 20.0f; 
	float attackMaxTime = 0.3f;
	private int money = 20;
	protected int cost = 10; // 升级花费的金币
	protected int mLevel = 1;
	// ********************

	protected boolean drawOvalFlag = false;
	OvalTexture oval; // 攻击范围
	protected ResourceTexture mBackground;

	/**
	 * shift + alt + r 全局替换函数名
	 */
	protected boolean isAttacking = false; // 标记是否正在攻击
	/**
	 * 是否可以攻击
	 */
	protected boolean canAttack = false;

	public GLTower() {
		// TODO Auto-generated constructor stub
		oval = new OvalTexture((int) mAttackRange * 2, (int) mAttackRange * 2);
		mDamage = 5; // 默认攻击力
		mTowerName = "炮台";
		layout(0, 0, 96, 96); //默认大小
	}

	/**
	 * 自己实现炮台的攻击动作
	 */
	public abstract void startAttack(GLEnemy tar);

	/**
	 炮台停止攻击
	 */
	public abstract void stopAttack();

	/**
	 *必须实现炮台的更新
	 */
	public abstract void updateTower();

	public void setBackground(ResourceTexture background) {
		if (background == mBackground)
			return;
		mBackground = background;
	}

	public String getTowerName() {
		return mTowerName;
	}

	public void setAttackR(int attackR) {
		this.mAttackRange = attackR;
		oval = new OvalTexture((int) attackR * 2, (int) attackR * 2);
	}

	public int getAttackRange() {
		return mAttackRange;
	}

	public void setDamage(int damage) {
		this.mDamage = damage;
	}

	public int getDamage() {
		return mDamage;
	}
	
	public int getLevel() {
		return mLevel;
	}
	
	public void setLevel(int level) {
		this.mLevel = level;
	}

	/**
	 * 判断敌人是否进入攻击范围
	 * @param enemy
	 * @return 
	 */
	public boolean isIntoAttackRange(GLEnemy enemy) {
		float x1, x2;
		float y1, y2;
		float r2;
		x1 = enemy.getCenterX();
		y1 = enemy.getCenterY();
		x2 = getCenterX();
		y2 = getCenterY();
		r2 = mAttackRange;
		double tem = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		if (tem <= r2) {
			return true;
		}
		return false;
	}

	protected void update() {
		/**
		 * 如果正在攻击 则不计算时间
		 */
		if (isAttacking == true) {
			return;
		}
		attackTime += FPS.getDeltaTime();
		if (attackTime > attackMaxTime) {
			attackTime = 0;
			isAttacking = true;
			canAttack = true;
			return;
		} else {
			canAttack = false;
		}
		super.update();
	}

	/**
	 *是否可以攻击
	 * 
	 * @return
	 */
	public boolean isCanAttack() {
		return canAttack;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		// TODO Auto-generated method stub
		if (drawOvalFlag) {
			oval.draw(root, getWidth() / 2 - mAttackRange, getHeight() / 2
					- mAttackRange);
		}
		
		if (mBackground != null) {
			mBackground.draw(root, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}

	protected boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			drawOvalFlag = !drawOvalFlag;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			drawOvalFlag = false;
		}
		return super.onTouch(event);
	}

	public void setOnClickListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		super.setOnClickListener(listener);
	}
	
	public int getMoney() {
		return money;
	}

}

package com.towergame.tower;

import android.view.MotionEvent;

import com.engine.anim.FrameAnimationListener;
import com.engine.entity.GLView;
import com.game.data.GameResConfig;
import com.towergame.GLMenu;
import com.towergame.bullet.GLLightning;
import com.towergame.enemy.GLEnemy;
import com.towergame.player.PlayerInfo;

public class GLLightTower extends GLTowerSprite {

	GLLightning mBullet;

	GLEnemy mEnemy;

	public GLLightTower(int[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		cost = 15;
		init();
	}

	public GLLightTower(String[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		cost = 15;
		init();
	}

	private void init() {
		setDamage(20);
		attackMaxTime = 0.2f;
		// 监听帧动画
		mSpriteAnim.setFrameAnimationListener(new FrameAnimationListener() {
			@Override
			public void onAnimEnd(int times) {
				// TODO Auto-generated method stub
				if (mBullet != null) {
					if (mEnemy != null) {
						mBullet.startPlay();
						mBullet.getGLAnimation().reStart();
						mBullet.setTarget(mEnemy);
						mBullet.setVisibility(VISIBLE);
					}
				}
			}
		});

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(GLView view, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					GLMenu.getInstance().setTarget((GLTower) view);
				}
				return true;
			}
		});
	}

	public void initBullet() {
		mBullet = new GLLightning(GameResConfig.getBulletRes(2));
		mBullet.setPos(getX() + 40, getY() + 20);
		mBullet.setAnimEndlistener(new FrameAnimationListener() {
			public void onAnimEnd(int times) {
				isAttacking = false;
				if (mEnemy != null) {
					mEnemy.reduceHP(getDamage());
					mEnemy = null;
				}
				mBullet.setVisibility(INVISIBLE); // 动画执行完设置不可见
				mBullet.setTarget(null);
			}
		});
		mBullet.addToRoot(); // 添加到场景
	}

	public void updateTower() {
		int money = PlayerInfo.getInstance().getMoney();
		if (money - cost >= 0) {
			PlayerInfo.getInstance().setMoney(money - cost);
			mDamage += 5;
			mLevel++;
			money += cost;
		}

	}

	@Override
	public void startAttack(GLEnemy tar) {
		// TODO Auto-generated method stub
		isAttacking = true; // 正在攻击
		canAttack = false;
		if (mBullet == null) {
			initBullet();
		}
		mEnemy = tar;
		mSpriteAnim.reStart();
		playFlag = true;
	}

	@Override
	public void stopAttack() {
	}
}

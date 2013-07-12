package com.towergame.tower;

import java.util.Random;

import android.view.MotionEvent;

import com.engine.action.Action;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.anim.FrameAnimationListener;
import com.engine.entity.GLView;
import com.game.data.GameResConfig;
import com.towergame.GLMenu;
import com.towergame.TowerGameUtil;
import com.towergame.bullet.GLMagicBullet;
import com.towergame.enemy.GLEnemy;
import com.towergame.player.PlayerInfo;

public class GLMagicTower extends GLTowerSprite {

	GLEnemy mEnemy;
	GLMagicTower mInstance;

	int critProbability = 0; // 暴击概率

	public GLMagicTower(int[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		mSpriteAnim.setAnimTime(0.15f);
		mInstance = this;
		mSpriteAnim.setFrameAnimationListener(new FrameAnimationListener() {

			@Override
			public void onAnimEnd(int times) {
				// TODO Auto-generated method stub
				isAttacking = false;
				doTowerAttack(mInstance, mEnemy);
			}
		});
		setAttackR(400);
	}

	public GLMagicTower(String[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		mSpriteAnim.setAnimTime(0.15f);
		mInstance = this;
		mSpriteAnim.setFrameAnimationListener(new FrameAnimationListener() {

			@Override
			public void onAnimEnd(int times) {
				// TODO Auto-generated method stub
				isAttacking = false;
				doTowerAttack(mInstance, mEnemy);
			}
		});
		setAttackR(200);
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

	@Override
	public void startAttack(GLEnemy tar) {
		// TODO Auto-generated method stub
		isAttacking = true; // 正在攻击
		canAttack = false;

		mSpriteAnim.reStart();
		playFlag = true;
		mEnemy = tar;
	}

	@Override
	public void stopAttack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTower() {
		// TODO Auto-generated method stub
		int money = PlayerInfo.getInstance().getMoney();
		if (money - cost >= 0) {
			PlayerInfo.getInstance().setMoney(money - cost);
			mDamage += 10;
			mAttackRange += 5;
			oval = new OvalTexture((int) mAttackRange * 2, (int) mAttackRange * 2);
			mLevel++;
			if (critProbability < 50) {
				critProbability += 10; // 增加暴击概率
			}
			money += cost;
		}
	}

	private void doTowerAttack(final GLMagicTower tower, final GLEnemy enemy) {
		if (tower == null || enemy == null) {
			return;
		}
		final GLMagicBullet Bullet = new GLMagicBullet(
				GameResConfig.getBulletRes(3));
		Bullet.setDamage(tower.getDamage());
		Bullet.setSize(Bullet.getWidth() * 2, Bullet.getHeight() * 2);
		Bullet.startPlay();
		Bullet.setPos(tower.getCenterX(), tower.getY() + 10);
		float[] dst = TowerGameUtil.getBulletDst(enemy, Bullet);
		Bullet.addAction(MoveTo.$(dst[0], dst[1], Bullet.getBulletMoveTime())
				.setCompletionListener(new OnActionCompleted() {
					@Override
					public void completed(Action action) {
						// TODO Auto-generated method stub
						// 产生暴击
						if (tower.isCrit()) {
							enemy.reduceHP(Bullet.getDamage() * 2);
							System.err.println("产生暴击");
						} else {
							enemy.reduceHP(Bullet.getDamage());
						}
						Bullet.removeFromParent();
					}
				}));
		Bullet.addToRoot();
	}

	/**
	 * 是否暴击
	 * 
	 * @return
	 */
	public boolean isCrit() {
		Random random = new Random();
		int temp = random.nextInt(100);
		if (temp <= critProbability) {
			return true;
		}
		return false;
	}

}

package com.towergame.tower;

import android.view.MotionEvent;

import com.assetsres.AssertRes;
import com.engine.action.Action;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.anim.AnimUtil;
import com.engine.anim.FrameAnimationListener;
import com.engine.entity.GLView;
import com.engine.entity.GLView.OnTouchListener;
import com.game.data.GameResConfig;
import com.towergame.GLMenu;
import com.towergame.TowerGameUtil;
import com.towergame.bullet.GLNormalBullet;
import com.towergame.enemy.GLEnemy;
import com.towergame.player.PlayerInfo;

/**
 * 减速塔
 * 
 * @author YCMO
 * 
 */
public class GLSlowdownTower extends GLTowerSprite {

	GLEnemy mEnemy;
	GLSlowdownTower mInstance = null;
	boolean isUpImage = false;

	public GLSlowdownTower(int[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		attackMaxTime = 0.6f; // 最大攻击间隔
		mInstance = this;
		mSpriteAnim.setFrameAnimationListener(new FrameAnimationListener() {
			public void onAnimEnd(int times) {
				// TODO Auto-generated method stub
				isAttacking = false;
				doTowerAttack(mInstance, mEnemy);
			}
		});
	}

	public GLSlowdownTower(String[] resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		attackMaxTime = 0.6f; // 最大攻击间隔
		mInstance = this;
		mSpriteAnim.setFrameAnimationListener(new FrameAnimationListener() {
			public void onAnimEnd(int times) {
				// TODO Auto-generated method stub
				isAttacking = false;
				doTowerAttack(mInstance, mEnemy);
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
		cost = 25;
	}

	@Override
	public void startAttack(GLEnemy tar) {
		// TODO Auto-generated method stub
		isAttacking = true; // 标示炮台正在攻击 等播放完攻击的动画之后才开始计算攻击间隔
		canAttack = false;
		mSpriteAnim.reStart();
		playFlag = true;
		mEnemy = tar;
	}

	@Override
	public void updateTower() {
		// TODO Auto-generated method stub

		/**
		 * 升级之后替换炮台图片资源
		 */
		if (!isUpImage) {
			int money = PlayerInfo.getInstance().getMoney();
			if (money - cost >= 0) {
				PlayerInfo.getInstance().setMoney(money - cost);
				mDamage += 5;
				mAttackRange += 20;
				oval = new OvalTexture((int) mAttackRange * 2, (int) mAttackRange * 2);
				mLevel++;
				money += cost;
				mSpriteAnim.setResourceTexture(AnimUtil
						.getResList(GameResConfig.getTowerRes(9)));
				isUpImage = true;
			}
		}
		// 还可以做各种事 比如增加攻击距离，攻击强度，增强攻击特效等

	}

	@Override
	public void stopAttack() {
		// TODO Auto-generated method stub
		if (isAttacking == false) {
			mEnemy = null;
		}
	}

	private void doTowerAttack(GLTower tower, final GLEnemy enemy) {
		if (tower == null || enemy == null) {
			return;
		}
		final GLNormalBullet bullet = new GLNormalBullet(
				AssertRes.images.bullet.bullet2_png, tower.getDamage());
		bullet.setScale(1.5f, 1.5f);
		bullet.setPos(tower.getCenterX(), tower.getY() + 10);
		float[] dst = TowerGameUtil.getBulletDst(enemy, bullet);
		float dstX = dst[0];
		float dstY = dst[1];
		bullet.addAction(MoveTo.$(dstX, dstY, bullet.getBulletMoveTime())
				.setCompletionListener(new OnActionCompleted() {
					@Override
					public void completed(Action action) {
						// TODO Auto-generated method stub
						enemy.reduceHP(bullet.getDamage());
						enemy.startSlowDown();
						bullet.removeFromParent();
					}
				}));
		bullet.addToRoot();
	}
}

package com.towergame.tower;

import android.view.MotionEvent;

import com.assetsres.AssertRes;
import com.engine.action.Action;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.entity.GLView;
import com.example.myc.engine.R;
import com.towergame.GLMenu;
import com.towergame.TowerGameUtil;
import com.towergame.bullet.GLNormalBullet;
import com.towergame.enemy.GLEnemy;

/**
 * 箭塔
 * @author YCMO
 *
 */
public class GLArrowTower extends GLTower{

	public GLArrowTower() {
		mDamage = 5;
		attackMaxTime = 0.3f;
	}

	@Override
	public void startAttack(GLEnemy enemy) {
		// TODO Auto-generated method stub
		isAttacking = false; // 正在攻击
		canAttack = false;
		doTowerAttack(this, enemy);
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(GLView view, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					GLMenu.getInstance().setTarget((GLTower)view);
				}
				return true;
			}
		});
	
	}

	@Override
	public void updateTower() {
		// TODO Auto-generated method stub
//		mDamage += 5;
		mAttackRange += 5;
		oval = new OvalTexture((int) mAttackRange * 2, (int) mAttackRange * 2);
	}

	@Override
	public void stopAttack() {
		// TODO Auto-generated method stub
	}

	private void doTowerAttack(GLTower tower, final GLEnemy enemy) {
		if (tower == null || enemy == null) {
			return;
		}
		final GLNormalBullet bullet = new GLNormalBullet(
				AssertRes.images.bullet.game_bullet3_png, tower.getDamage());
		bullet.setSize((int)(bullet.getWidth() * 0.7), (int)(bullet.getHeight()*0.8));
		bullet.setPos(tower.getCenterX(), tower.getY() + 15);
		float[] dst = TowerGameUtil.getBulletDst(enemy, bullet);
		float dstX = dst[0];
		float dstY = dst[1];
		float degree = (float) TowerGameUtil.getRotation(bullet.getX(),
				bullet.getY(), dstX, dstY);
		bullet.setRotate(degree);
		bullet.addAction(MoveTo.$(dstX, dstY, bullet.getBulletMoveTime())
				.setCompletionListener(new OnActionCompleted() {
					@Override
					public void completed(Action action) {
						// TODO Auto-generated method stub
						enemy.reduceHP(bullet.getDamage());
						bullet.removeFromParent();
					}
				}));
		bullet.addToRoot();
	}
}

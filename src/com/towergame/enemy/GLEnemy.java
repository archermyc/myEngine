package com.towergame.enemy;

import java.util.Random;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;

import com.assetsres.AssertRes;
import com.engine.action.Action;
import com.engine.action.FadeOut;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.ui.GLImage;
import com.engine.ui.GLText;
import com.towergame.enemy.EnemyData.EnemyEffect;
import com.towergame.player.PlayerInfo;

public class GLEnemy extends GLSprite {

	HPBar mHpBar;
	protected float slowDownTime = 0.0f;
	protected float mMaxSlowDownTime = 3.0f;

	private float drawHPtime = 0;
	private boolean drawHP = false;

	protected EnemyData mData;

	public enum EnemyState {
		Death, // 死亡
		Free, // 释放 暂时没用
		Alive, // 存活
		Move, // 移动
		Slowdown // 减速

	}

	public void setEnemyData(EnemyData data) {
		mData = data;
		mHpBar.setHpMax(mData.maxHP);

	}

	EnemyState state = null;

	public GLEnemy(int resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		mHpBar = new HPBar(200, getWidth(), 10);
		state = EnemyState.Alive;
		mData = new EnemyData();
	}
	
	public GLEnemy(String resId) {
		super(resId);
		// TODO Auto-generated constructor stub
		mHpBar = new HPBar(200, getWidth(), 10);
		state = EnemyState.Alive;
		mData = new EnemyData();
	}
	
	@Override
	public void setSize(int w, int h) {
		// TODO Auto-generated method stub
		super.setSize(w, h);
		mHpBar.length = w;
	}

	/**
	 * 减少血量
	 * 
	 * @param num
	 */
	public void reduceHP(int num) {
		if (state == EnemyState.Death) {
			return;
		}
		// 如果怪物特性等于减伤 则减伤30%
		if (mData.effect == EnemyEffect.jianshang) {
			num = (int)(num *0.7);
		}
		if (mData.effect == EnemyEffect.dodge) {
			Random random = new Random();
			if(random.nextInt(100) < 30) {
				showDodge();
				return; // 闪避
			}
		}
		
		mData.curHP = mData.curHP - num;
		if (mData.curHP <= 0) {
			state = EnemyState.Death;
			mData.speed = 0;
			FadeOut fadeOut = FadeOut.$(1.0f, 1.0f);
			fadeOut.setCompletionListener(new OnActionCompleted() {
				@Override
				public void completed(Action action) {
					// TODO Auto-generated method stub
					removeFromParent();
				}
			});
			addAction(fadeOut);
			PlayerInfo.getInstance().setMoney(
					PlayerInfo.getInstance().getMoney() + mData.money);
		} else {
			drawHP = true;
			drawHPtime = 0;
			showReduceHP(num);
		}

	}

	/**
	 * 显示闪避
	 * 
	 * @param hp
	 */
	private void showDodge() {
//		GLText text = new GLText("闪避", 24, Color.YELLOW);
		GLImage image = new GLImage(AssertRes.images.effect.miss_icon_png);
		image.setPos(-5, -30);
		image.setScale(1.5f, 1.5f);
		addChild(image);
		MoveTo moveTo = MoveTo.$(image.getX(), image.getY() - 30, 0.5f);
		moveTo.setCompletionListener(new OnActionCompleted() {
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				action.getTarget().removeFromParent();
			}
		});
		image.addAction(moveTo);
	}
	
	/**
	 * 显示正在扣的血量 比如 -5
	 * 
	 * @param hp
	 */
	private void showReduceHP(int hp) {
		GLText text = new GLText("-" + hp, 25, Color.RED);
		text.setPos(0, -40);
		addChild(text);
		MoveTo moveTo = MoveTo.$(text.getX(), text.getY() - 20, 0.5f);
		moveTo.setCompletionListener(new OnActionCompleted() {
			@Override
			public void completed(Action action) {
				// TODO Auto-generated method stub
				action.getTarget().removeFromParent();
			}
		});
		text.addAction(moveTo);
	}

	/**
	 * 开始减速
	 */
	public void startSlowDown() {
		if (state != EnemyState.Death && state != EnemyState.Slowdown) {
			slowDownTime = 0;
			state = EnemyState.Slowdown;
			setSpeed(getSpeed() / 2);
		}
	}

	public void setState(EnemyState state) {
		this.state = state;
	}

	public EnemyState getState() {
		return state;
	}

	public void setMaxHP(int maxhp) {
		mData.maxHP = maxhp;
		mHpBar.setHpMax(maxhp);
	}

	public void setHP(int HP) {
		this.mData.curHP = HP;
	}

	public int getHP() {
		return this.mData.curHP;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		// TODO Auto-generated method stub
		// root.drawColor(0, -10, getWidth(), 5, Color.argb(50, 255, 0, 0));
		if (drawHP) {
			mHpBar.drawHpBar(root, 0, -10, this.mData.curHP);
		}
		super.render(root, gl);
	}

	@Override
	protected void update() {
		if (state == EnemyState.Slowdown) {
			slowDownTime += FPS.getDeltaTime();
			if (slowDownTime >= mMaxSlowDownTime) {
				state = EnemyState.Move;
				setSpeed(mData.speedOrg);
			}
		}
		if (drawHP) {
			drawHPtime += FPS.getDeltaTime();
			if (drawHPtime >= 3f) {
				drawHPtime = 0;
				drawHP = false;
			}
		}
		super.update();
	}

	public void setSpeed(float speed) {
		if (speed <= 0) {
			speed = 0;
		}
		mData.speed = speed;
	}

	public float getSpeed() {
		return mData.speed;
	}

	public float getSpeedX() {
		if (direction == ANIM_RIGHT) {
			return mData.speed;
		} else if (direction == ANIM_LEFT) {
			return mData.speed;
		}
		return 0;
	}

	public float getSpeedY() {
		if (direction == ANIM_UP) {
			return -mData.speed;
		} else if (direction == ANIM_DOWN) {
			return mData.speed;
		}
		return 0;
	}
	
	@Override
	public boolean removeFromParent() {
		// TODO Auto-generated method stub
		state = EnemyState.Death;
		return super.removeFromParent();
	}
}

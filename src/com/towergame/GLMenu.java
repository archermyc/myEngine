package com.towergame;

import android.graphics.Color;
import android.view.MotionEvent;
import aurelienribon.tweenengine.Tween;

import com.assetsres.AssertRes;
import com.engin.tweenaccessors.SpriteAccessor;
import com.engine.action.ActionType;
import com.engine.action.Rotate;
import com.engine.entity.GLView;
import com.engine.ui.GLButton;
import com.engine.ui.GLImage2;
import com.engine.ui.GLNode;
import com.engine.ui.GLScene;
import com.engine.ui.GLSceneManager;
import com.engine.ui.GLText;
import com.example.myc.engine.R;
import com.towergame.map.GLMap;
import com.towergame.player.PlayerInfo;
import com.towergame.tower.GLTower;

public class GLMenu extends GLNode {
	GLImage2 bg;
	GLText mTowerName; // 炮台名称
	GLText mDamage;// 炮台攻击力
	GLText mAttackRange;// 炮台攻击范围
	GLText mLevel;

	GLButton upBtn; // 升级按钮
	GLButton sellBtn; // 卖出按钮
	GLButton closeBtn;

	int srcWidth = 320;
	int srcHeight = 270;
	int srcX = 0;
	int srcY = 0;

	GLTower mCurrTower; // 当前的炮台

	int offsetX = 20;

	boolean isShowing = false;

	final String damageString = "攻击力: %d";
	final String attackRangeString = "攻击范围: %d";

	private static GLMenu instance = null;

	public static GLMenu getInstance() {
		if (instance == null) {
			instance = new GLMenu(0, 0);
		}
		return instance;
	}

	private GLMenu(int x, int y) {
		layout(x, y, x + srcWidth, y + srcHeight);
		init();
		isShowing = false;
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(GLView view, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	public void setTarget(GLTower tower) {
		if (mCurrTower == tower) {
			if (isShowing == false) {
				isShowing = true;
				setVisibility(VISIBLE);
			} else {
				isShowing = false;
				setVisibility(INVISIBLE);
			}
		} else {
			mCurrTower = tower;
			setPos((960 - 250) / 2, (640 - 250) / 2);
			refresh();
			isShowing = true;
			setVisibility(VISIBLE);
		}
	}

	public GLTower getTarget() {
		return mCurrTower;
	}

	public void refresh() {
		mTowerName.setText(mCurrTower.getTowerName());
		mDamage.setText(String.format(damageString, mCurrTower.getDamage()));

		mAttackRange.setText(String.format(attackRangeString,
				mCurrTower.getAttackRange()));
		mLevel.setText(String.format("Lv.%d", mCurrTower.getLevel()));
	}

	private void init() {
		bg = new GLImage2(R.drawable.menu_popup);
		bg.setPos(0, 0);
		bg.setSize(srcWidth, srcHeight);
		addChild(bg);

		mTowerName = new GLText("xxx", 24, Color.WHITE);
		mTowerName.setPos(offsetX, 30);

		mDamage = new GLText(damageString);
		mDamage.setPos(offsetX, 70);

		mAttackRange = new GLText(attackRangeString, 24, Color.WHITE);
		mAttackRange.setPos(offsetX, 110);

		mLevel = new GLText(attackRangeString, 24, Color.WHITE);
		mLevel.setPos(offsetX, 140);

		upBtn = new GLButton(R.drawable.upgrade_button);
		upBtn.setPos(offsetX, 180);

		upBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				if (mCurrTower != null) {
					mCurrTower.updateTower(); // 升级炮台
					refresh();// 刷新界面
					// ScaleAnimation scaleAnimation = new ScaleAnimation(1,
					// 2,1, 2);
					// scaleAnimation.setDuration(1000);
					// startAnimation(scaleAnimation);
					startR();
				}
			}
		});

		sellBtn = new GLButton(R.drawable.sell_button);
		sellBtn.setPos(offsetX + upBtn.getX() + upBtn.getWidth(), 180);
		sellBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				if (mCurrTower != null) {
					if (GLMap.getInstance().deleteTower(mCurrTower)) { // 判断是否已经从地图移除
						mCurrTower.removeFromParent();// 从父节点移除
						int money = mCurrTower.getMoney();
						PlayerInfo.getInstance().setMoney(
								money + PlayerInfo.getInstance().getMoney());
						hide();
					}
				}
			}
		});

		closeBtn = new GLButton(AssertRes.images.ui.close_png, null);
		closeBtn.setPos(275, 0);
		closeBtn.setSize(45, 45);

		closeBtn.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				hide();
			}
		});

		addChild(mDamage);
		addChild(mAttackRange);
		addChild(mTowerName);
		addChild(mLevel);
		addChild(upBtn);
		addChild(sellBtn);
		addChild(closeBtn);
	}

	public void hide() {
		isShowing = false;
		setVisibility(INVISIBLE);
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void startR() {
		// Tween tween = Tween.to(this, SpriteAccessor.ROTATE_CENTER,
		// 2.0f).target(90).start(
		// GLSceneManager.getInstance().getRootView().getTweenManager());
		addAction(Rotate.$(90, ActionType.RotateCenter, 200));
	}
}

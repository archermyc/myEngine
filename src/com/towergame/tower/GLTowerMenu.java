package com.towergame.tower;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;

import com.engine.action.Action;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.entity.GLRootView;
import com.engine.entity.GLView;
import com.engine.ui.GLButton;
import com.engine.ui.GLImage2;
import com.engine.ui.GLNode;
import com.example.myc.engine.GameApp;
import com.example.myc.engine.R;
import com.towergame.map.GLMap;

public class GLTowerMenu extends GLNode {
	GLImage2 bg;
	GLButton button;
	GLButton arrowTower;
	GLButton lightTower;
	GLButton magicTower;
	GLButton slowdownTower;

	int mWidth = 730;
	int mHeight = 150;
	int mX = 260;
	int mY = GameApp.getInstnce().ScreenHeight - mHeight + 20;

	boolean posFlag = true; // 坐标标志

	int drawX = 0;
	int drawY = 0;

	int imageW = 96;

	public GLTowerMenu() {
		init();
		initButton();
		addTower();
	}

	private void init() {
		layout(mX, mY, mX + mWidth, mY + mHeight);
		bg = new GLImage2(R.drawable.menu_popup);
		bg.setPos(0, 0);
		bg.setSize(mWidth, mHeight);
		addChild(bg);
	}

	private void initButton() {
		button = new GLButton(R.drawable.domob_next);
		button.setPos(20, 25);
		button.setSize(imageW, imageW);
		addChild(button);
		button.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				if (posFlag) {
					MoveTo end = MoveTo.$(mWidth + 20 + 96, mY, 0.3f);
					end.setCompletionListener(new OnActionCompleted() {
						public void completed(Action action) {
							button.setBg(R.drawable.domob_preview);
						}
					});
					addAction(end);
					posFlag = false;
				} else {
					MoveTo start = MoveTo.$(mX, mY, 0.3f);
					start.setCompletionListener(new OnActionCompleted() {
						public void completed(Action action) {
							button.setBg(R.drawable.domob_next);
						}
					});
					addAction(start);
					posFlag = true;
				}
			}
		});
	}

	private void addTower() {
		arrowTower = new GLButton(
				TowerConfig.getTowerImage(TowerConfig.ArrowTower), null);
		drawX = 20 + imageW;
		drawY = 25;
		arrowTower.setPos(20 + imageW, 25);
		arrowTower.setSize(imageW, imageW);
		addChild(arrowTower);
		arrowTower.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				drawX = (int) view.getX();
				drawY = (int) view.getY();
				GLMap.getInstance().setDrawingTower(TowerConfig.ArrowTower);
			}
		});

		lightTower = new GLButton(
				TowerConfig.getTowerImage(TowerConfig.LightTower), null);
		lightTower.setPos(20 + imageW + 20 + imageW, 25);
		lightTower.setSize(imageW, imageW);
		lightTower.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				drawX = (int) view.getX();
				drawY = (int) view.getY();
				GLMap.getInstance().setDrawingTower(TowerConfig.LightTower);
			}
		});
		addChild(lightTower);

		magicTower = new GLButton(
				TowerConfig.getTowerImage(TowerConfig.MagicTower), null);
		magicTower.setPos(lightTower.getX() + imageW + 20, 25);
		magicTower.setSize(imageW, imageW);
		magicTower.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				drawX = (int) view.getX();
				drawY = (int) view.getY();
				GLMap.getInstance().setDrawingTower(TowerConfig.MagicTower);
			}
		});
		addChild(magicTower);

		slowdownTower = new GLButton(
				TowerConfig.getTowerImage(TowerConfig.SlowDownTower), null);
		slowdownTower.setPos(magicTower.getX() + imageW + 20, 25);
		slowdownTower.setSize(imageW, imageW);
		slowdownTower.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				drawX = (int) view.getX();
				drawY = (int) view.getY();
				GLMap.getInstance().setDrawingTower(TowerConfig.SlowDownTower);
			}
		});
		addChild(slowdownTower);
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		// TODO Auto-generated method stub
		super.render(root, gl);
		if (posFlag) {
			root.drawColor(drawX, drawY, imageW, imageW,
					Color.argb(50, 255, 0, 0));
		}
	}
}

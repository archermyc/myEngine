package com.towergame.enemy;

import android.graphics.Color;

import com.engine.action.Action;
import com.engine.action.Delay;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.action.TimeLine;
import com.engine.ui.GLImage2;
import com.engine.ui.GLNode;
import com.engine.ui.GLText;
import com.example.myc.engine.GameApp;
import com.example.myc.engine.R;

/**
 * 每一波敌人的属性信息
 * 
 * @author YCMO
 * 
 */
public class GLEnemyTips extends GLNode {
	GLImage2 bg;
	GLText text;
	GLText prop;
	GLText desc;

	int srcWidth = GameApp.getInstnce().ScreenWidth;
	int srcHeight = 180;
	int srcX = -srcWidth;
	int srcY = (GameApp.getInstnce().ScreenHeight - srcHeight) / 2;

	final String damageString = "攻击力: %d";
	final String attackRangeString = "攻击范围: %d";

	public GLEnemyTips() {
		layout(srcX, srcY, srcWidth, srcHeight);
		init();
		setVisibility(INVISIBLE);
	}

	private void init() {
		bg = new GLImage2(R.drawable.menu_popup);
		bg.setPos(0, 0);
		bg.setSize(srcWidth, srcHeight);
		addChild(bg);

		text = new GLText("地精", 24, Color.WHITE);
		int textW = text.getWidth();
		text.setPos((srcWidth - textW) / 2, 30);

		prop = new GLText("生命:300 速度:3");
		textW = prop.getWidth();
		prop.setPos((srcWidth - textW) / 2, 70);

		desc = new GLText("减少%30伤害", 24, Color.WHITE);
		textW = desc.getWidth();
		desc.setPos((srcWidth - textW) / 2, 110);
		addChild(prop);
		addChild(desc);
		addChild(text);
	}

	public void show() {
		setVisibility(VISIBLE);
		TimeLine timeline = new TimeLine();
		timeline.setTarget(this);
		MoveTo moveTo = MoveTo.$(0, srcY, 1.0f);
		Delay delay = Delay.$(1.0f);
		MoveTo srcMoveTo = MoveTo.$(srcX, srcY, 1.0f);
		srcMoveTo.setCompletionListener(new OnActionCompleted() {
			public void completed(Action action) {
				// TODO Auto-generated method stub
				setVisibility(INVISIBLE);
			}
		});
		
		timeline.pushAction(moveTo);
		timeline.pushAction(delay);
		timeline.pushAction(srcMoveTo);
		addAction(timeline);
	}
}

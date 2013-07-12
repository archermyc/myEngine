package com.engine.ui;

import com.engine.effects.AttackEffects;
import com.engine.entity.FPS;
import com.engine.entity.GLView;
import com.example.myc.engine.GameApp;
import com.example.myc.engine.R;
import com.game.data.GameResConfig;

public class GLSceneTest extends GLScene {

	public void load() {
		GLImage bg = new GLImage(R.drawable.f_bg);
		bg.setPos(0, 0);
		bg.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
		addComponent(bg);
		startAnim();
		GLButton button = new GLButton("�������");
		button.setPos(400, 0);
		button.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				FPS.setCoefficient(FPS.getCoefficient() * 2);
			}
		});
		addChild(button);
	}

	public void startAnim() {
		GLImage glImage = new GLImage(R.drawable.miao_wa_cao);
		glImage.setPos(0,
				(GameApp.getInstnce().ScreenHeight - glImage.getHeight()) / 2);
		addComponent(glImage);
		//
		GLImage glImage2 = new GLImage(R.drawable.huo_jing_lin);
		glImage2.setPos(600,
				(GameApp.getInstnce().ScreenHeight - glImage2.getHeight()) / 2);
		addComponent(glImage2);
		//
		final AttackEffects a = new AttackEffects(GameResConfig.getAttackEffect(0));
		a.setPos(-110, -100);

		AttackEffects a1 = new AttackEffects(GameResConfig.getAttackEffect(1));
		a1.setPos(-65, -50);
		glImage2.addChild(a1);
		a1.startPlay();
		// //
		AttackEffects a2 = new AttackEffects(GameResConfig.getAttackEffect(2));
		a2.setPos(-100, -200);
		glImage.addChild(a2);
		a2.startPlay();

		AttackEffects a21 = new AttackEffects(GameResConfig.getAttackEffect(21));
		a21.setPos(100, 200);
		a21.setAnimTime(0.12f);
		addChild(a21);
		a21.startPlay();

		AttackEffects a18 = new AttackEffects(GameResConfig.getAttackEffect(18));
		a18.setPos(300, 200);
		addChild(a18);
		a18.startPlay();

	}

	/**
	 * 用于测试
	 */
	public void load2() {
		GLImage bg = new GLImage(R.drawable.f_bg);
		bg.setPos(0, 0);
		bg.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
		addChild(bg);

		GLImage glImage = new GLImage(R.drawable.miao_wa_cao);
		glImage.setPos(600, 400);
		addComponent(glImage);

		GLButton button = new GLButton("���");
		button.setPos(400, 500);
		button.setOnClickListener(new OnClickListener() {
			public void OnClick(GLView view) {
			}
		});

		// gSprite.addAction(MoveTo.$(200, 400, 2.0f).setCompletionListener(
		// new OnActionCompleted() {
		// @Override
		// public void completed(Action action) {
		// // TODO Auto-generated method stub
		// removeComponent(gSprite);
		// }
		// }));
//		Tween.to(gSprite, SpriteAccessor.POSITION_XY, 2.0f).target(400, 200)
//				.start(getGLRootView().getTweenManager())
//				.setCallback(new TweenCallback() {
//					public void onEvent(int type, BaseTween<?> source) {
//						// TODO Auto-generated method stub
//						removeComponent(gSprite);
//					}
//				});
		addChild(button);

	}


	/**
	 * ���ڲ���
	 */
	public void load4() {
		GLImage bg = new GLImage(R.drawable.f_bg);
		bg.setPos(0, 0);
		bg.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
		addChild(bg);


//		GLTower tower = new GLTower(R.drawable.tower3);
//		tower.setPos(400, 300);
//		tower.setAttackR(250);
//		addChild(tower);
	}

	@Override
	protected void loadEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
	}

}

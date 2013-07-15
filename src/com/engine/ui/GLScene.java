package com.engine.ui;

import com.example.myc.engine.GameApp;

public abstract class GLScene extends GLNode {

	/**
	 * 引用root 原意是想通过缩放做适配 但是目前出现一些问题，需要改动的地方太多了
	 */
//	private GLNode root;

	public GLScene() {
		layout(0, 0, GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
//		initRoot();

	}

//	public GLNode initRoot() {
//		root = new GLNode();
//		root.setPos(0, 0);
//		root.setSize(GameApp.getInstnce().targetWidth,
//				GameApp.getInstnce().targetHeight);
//
//		addComponent(root);
//		return root;
//	}

	/**
	 * 加载资源
	 */
	protected abstract void load();

	/**
	 * 加载资源结束
	 */
	protected abstract void loadEnd();

	/**
	 * 开始执行
	 */
	protected abstract void run();

//	@Override
//	public void addChild(GLView view) {
//		// TODO Auto-generated method stub
//		root.addChild(view);
//	}
}

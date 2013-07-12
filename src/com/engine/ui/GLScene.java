package com.engine.ui;

import com.example.myc.engine.GameApp;

public abstract class GLScene extends GLNode {

	public GLScene() {
		layout(0, 0, GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
	}

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
}

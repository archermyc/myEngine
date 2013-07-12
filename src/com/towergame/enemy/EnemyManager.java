package com.towergame.enemy;

import com.engine.ui.GLNode;
import com.example.myc.engine.GameApp;
import com.towergame.tower.GLTower;

public class EnemyManager {
	private GLNode mRoot; // 所有敌人都添加到该节点

	private static EnemyManager mInstance;

	public static EnemyManager getInstance() {
		if (mInstance == null) {
			mInstance = new EnemyManager();
		}
		return mInstance;
	}

	private EnemyManager() {
		mRoot = new GLNode();
		mRoot.setPos(0, 0);
		mRoot.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
	}

	public GLNode getRoot() {
		return mRoot;
	}

	public void addEnemy(GLEnemy enemy) {
		mRoot.addChild(enemy);
	}

	public GLEnemy getChild(int index) {
		return (GLEnemy) mRoot.getComponent(index);
	}

	public int getChildCount() {
		return mRoot.getComponentCount();
	}

	public void removeAllEnemy() {
		mRoot.clearComponents();
	}
}

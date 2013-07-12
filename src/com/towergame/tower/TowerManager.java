package com.towergame.tower;

import com.engine.ui.GLNode;
import com.example.myc.engine.GameApp;

/**
 * 管理炮台类
 * @author YCMO
 *
 */
public class TowerManager {
	private GLNode mRoot; // 所有炮台都添加到该节点

	private static TowerManager mInstance;

	public static TowerManager getInstance() {
		if (mInstance == null) {
			mInstance = new TowerManager();
		}
		return mInstance;
	}

	private TowerManager() {
		mRoot = new GLNode();
		mRoot.setPos(0, 0);
		mRoot.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
	}

	public GLNode getRoot() {
		return mRoot;
	}

	public void addTower(GLTower tower) {
		mRoot.addChild(tower);
	}

	public GLTower getChild(int index) {
		return (GLTower) mRoot.getComponent(index);
	}

	public int getChildCount() {
		return mRoot.getComponentCount();
	}
	
	public void removeAllTower() {
		mRoot.clearComponents();
	}

}

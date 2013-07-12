package com.engine.ui;

import java.util.ArrayList;

import com.engine.entity.GLRootView;

public class GLSceneManager {
	private static GLSceneManager mInstance;

	private GLRootView mRootView;

	ArrayList<GLScene> mGLScenes = new ArrayList<GLScene>();

	public static GLSceneManager getInstance() {
		if (mInstance == null) {
			mInstance = new GLSceneManager();
		}
		return mInstance;
	}

	public void init(GLRootView rootView) {
		// TODO Auto-generated method stub
		mRootView = rootView;
	}
	
	public GLRootView getRootView() {
		return mRootView;
	}

	private GLSceneManager() {
	}

	public GLScene getCurScene() {
		if (mGLScenes.size() >= 1) {
			return mGLScenes.get(mGLScenes.size() - 1);
		}
		return null;
	}

	public void pushScene(GLScene scene) {
		mGLScenes.add(scene);
		doPushEvent();
	}

	public void pushScene(GLScene scene, GLScene loadingScene) {
		mGLScenes.add(scene);
		if (mRootView != null) {
			mRootView.setContentPane(loadingScene);
		}
		new Thread() {
			public void run() {
				doPushEvent();
			};

		}.start();
	}

	private void doPushEvent() {
		if (mRootView != null) {
			GLScene scene = getCurScene();
			scene.load();
			mRootView.setContentPane(scene);
			scene.loadEnd();
			scene.run();
			
		}
	}

	public GLScene popScene() {
		return null;
	}
}

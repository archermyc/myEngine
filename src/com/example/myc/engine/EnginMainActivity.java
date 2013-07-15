package com.example.myc.engine;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.ui.GLScene;
import com.engine.ui.GLSceneManager;
import com.towergame.LoadingScene;
import com.towergame.TowerGameScene;

public class EnginMainActivity extends Activity {

	private GLRootView mGlRootView;
	TowerGameScene scene;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		GameApp.getInstnce().ScreenHeight = display.getHeight();
		GameApp.getInstnce().ScreenWidth = display.getWidth();
		GameApp.getInstnce().init();
		setContentView(R.layout.activity_engin_main);
		attachHeadUpDisplay();
	}

	private void attachHeadUpDisplay() {
		FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
		mGlRootView = new GLRootView(this);
		frame.addView(mGlRootView);
		scene = new TowerGameScene();
		GLSceneManager.getInstance().init(mGlRootView);
		GLSceneManager.getInstance().pushScene(scene,
				LoadingScene.getInstance());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.engin_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_next:
			scene.next();
			break;
		case R.id.action_last:
			scene.last();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

}

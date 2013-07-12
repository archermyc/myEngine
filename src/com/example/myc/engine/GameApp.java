package com.example.myc.engine;

import android.app.Application;
import android.widget.Toast;

public class GameApp extends Application {
	static GameApp app = null;
	public int ScreenWidth = 0;
	public int ScreenHeight = 0;
	public static GameApp getInstnce() {
		if (app == null) {
			app = new GameApp();
		}
		return app;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		app=this;
		final float scale = this.getResources().getDisplayMetrics().density;  
		Toast.makeText(this, "density: " + scale, 500).show();
	}
}



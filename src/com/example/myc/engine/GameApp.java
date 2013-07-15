package com.example.myc.engine;

import android.app.Application;
import android.widget.Toast;

public class GameApp extends Application {
	static GameApp app = null;
	public int ScreenWidth = 0;
	public int ScreenHeight = 0;
	public float ScreenScale = 0;
	public float ScreenScaleW = 0;
	public float ScreenScaleH = 0;
	public static int defaultWidth = 800;
	public static int defaultHeight = 480;

	public int ScaleXoffset = 0;
	public int ScaleYoffset = 0;

	public int targetWidth = 0;
	public int targetHeight = 0;

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
		app = this;
		final float scale = this.getResources().getDisplayMetrics().density;
		Toast.makeText(this, "density: " + scale, 500).show();
	}

	public void init() {
		ScreenScale =  ((float)ScreenWidth / defaultWidth) >  ((float)ScreenHeight / defaultHeight) ? (float) ((float)ScreenHeight / defaultHeight)
				: (float) ((float)ScreenWidth / defaultWidth);
		ScreenScaleW = (float) ((float)ScreenWidth / defaultWidth);
		ScreenScaleH = (float) ((float)ScreenHeight / defaultHeight);

		targetWidth = (int) (ScreenScale * defaultWidth);
		targetHeight = (int) (ScreenScale * defaultHeight);
		
		targetWidth = ScreenWidth;
		targetHeight = ScreenHeight;

		ScaleXoffset = (ScreenWidth - targetWidth) / 2;
		ScaleYoffset = (ScreenHeight - targetHeight) / 2;

	}
}

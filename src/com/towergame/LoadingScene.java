package com.towergame;

import com.engine.ui.GLImage;
import com.engine.ui.GLScene;
import com.engine.ui.GLText;
import com.example.myc.engine.R;

public class LoadingScene extends GLScene {

	private static LoadingScene mInstance;
	GLImage mBg;
	GLText mTips;

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		mBg = new GLImage(R.drawable.f_bg);
		mBg.setPos(0, 0);
		mBg.setSize(960, 640);
		mTips = new GLText("加载中");
		mTips.setPos((960 - mTips.getWidth()) / 2,
				(640 - mTips.getHeight()) / 2);
		addChild(mBg);
		addChild(mTips);

	}

	@Override
	protected void loadEnd() {
		// TODO Auto-generated method stub
		System.err.println("loadEnd");
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		System.err.println("run");
	}

	public static LoadingScene getInstance() {
		if (mInstance == null) {
			mInstance = new LoadingScene();
			mInstance.load();
			mInstance.loadEnd();
			mInstance.run();
		}
		return mInstance;
	}

}

package com.towergame.dialog;

import com.assetsres.AssertRes;
import com.engine.entity.FPS;
import com.engine.ui.GLDialogBase;
import com.engine.ui.GLImage;

/**
 * 游戏胜利弹框
 * 
 * @author YCMO
 * 
 */
public class GLWinDialog extends GLDialogBase {

	static GLWinDialog mInstance = null;

	private final float maxShowTime = 3.0f;

	private float showTime = 0f;

	public GLWinDialog() {
		GLImage node = new GLImage(AssertRes.images.ui.win_bg_png);
		if (node != null) {
			setContentView(node);
		}
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		delete();
		mInstance = null;
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub

	}

	public static void staticShow() {
		if (mInstance == null) {
			mInstance = new GLWinDialog();
			mInstance.show();
		}
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		if (showTime < maxShowTime) {
			showTime += FPS.getDeltaTime();

		} else {
			close();
		}
		super.update();
	}

	public static void staticClose() {
		if (mInstance != null) {
			mInstance.close();
		}
	}

}

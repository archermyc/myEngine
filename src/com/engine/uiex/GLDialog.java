package com.engine.uiex;

import com.engine.entity.FPS;
import com.engine.ui.GLDialogBase;
import com.engine.ui.GLNode;

public class GLDialog extends GLDialogBase {

	static GLDialog mInstance = null;
	
	private final float maxShowTime = 3.0f;
	
	private float showTime = 0f;

	public GLDialog(GLNode node) {
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

	public static void staticShow(GLNode node) {
		if (mInstance == null) {
			mInstance = new GLDialog(node);
			mInstance.show();
		}
	}
	
	@Override
	protected void update() {
		// TODO Auto-generated method stub
		if (showTime < maxShowTime) {
			showTime += FPS.getDeltaTime();
			
		}else {
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

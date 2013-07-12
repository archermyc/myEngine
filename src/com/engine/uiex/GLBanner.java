package com.engine.uiex;

import com.assetsres.AssertRes;
import com.engine.action.MoveTo;
import com.engine.action.OnActionCompleted;
import com.engine.entity.GLView;
import com.engine.ui.GLButton;
import com.engine.ui.GLImage;
import com.engine.ui.GLNode;
import com.towergame.GameState;
import com.towergame.GameState.GameStates;

public class GLBanner extends GLNode {
	GLImage image;
	GLButton mContinue;

	float srcX;
	float srcY;

	public GLBanner() {
		image = new GLImage(AssertRes.images.ui.win_again_button_png);
		addChild(image);
		int w = (int) (image.getWidth() * 1.5f);
		int h = (int) (image.getHeight() * 1.5f);
		image.setSize(w, h);
		layout((960 - w) / 2, -h, w + (960 - w) / 2, -h + h);

		srcX = getX();
		srcY = -h;

		addToRoot();
		setVisibility(INVISIBLE);

		mContinue = new GLButton(AssertRes.images.ui.transparent_bg_png, null);
		mContinue.setPos(0, 120);
		mContinue.setSize(w, h / 3);
		mContinue.showDrawRect();
		mContinue.setOnClickListener(new OnClickListener() {

			@Override
			public void OnClick(GLView view) {
				// TODO Auto-generated method stub
				hideBanner();
				GameState.getInstance().setGameState(GameStates.gameStart);
			}
		});
		addChild(mContinue);
	}

	public void show(OnActionCompleted listener) {
		setVisibility(VISIBLE);
		MoveTo moveTo = MoveTo.$(srcX, 0, 1.0f);
		if (listener != null) {
			moveTo.setCompletionListener(listener);
		}
		addAction(moveTo);
	}
	
	public void show() {
		setVisibility(VISIBLE);
		MoveTo moveTo = MoveTo.$(srcX, 0, 1.0f);
		addAction(moveTo);
	}

	private void hideBanner() {
		MoveTo srcMoveTo = MoveTo.$(srcX, srcY, 1.0f);
		addAction(srcMoveTo);
	}

}

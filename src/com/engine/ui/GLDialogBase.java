package com.engine.ui;

import com.engine.action.ActionType;
import com.engine.action.ScaleTo;
import com.example.myc.engine.GameApp;

public abstract class GLDialogBase extends GLNode {
	GLNode mContentView;

	public GLDialogBase() {
		layout(0, 0, GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
		addToRoot();
	}

	public void setContentView(GLNode view) {
		this.mContentView = view;
		int w = view.getWidth();
		int h = view.getHeight();
		int x = (GameApp.getInstnce().ScreenWidth - w) / 2;
		int y = (GameApp.getInstnce().ScreenHeight - h) / 2;
		mContentView.setPos(x, y);
		mContentView.setVisibility(INVISIBLE);
		addChild(mContentView);
	}

	public void show() {
		mContentView.setVisibility(VISIBLE);
		showAnim();
		onShow();
	}

	public void showAnim() {
		mContentView.setScale(0.01f, 0.01f);
		mContentView.addAction(ScaleTo.$(1.01f, 1.01f, ActionType.ScaleCenter,
				0.3f));
	}

	public void close() {
		mContentView.setVisibility(INVISIBLE);
		onClose();
	}

	public abstract void onClose();

	public abstract void onShow();

	public void delete() {
		removeFromParent();
	}
}

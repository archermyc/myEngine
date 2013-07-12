package com.engine.action;

import com.engine.ui.GLNode;

public class ScaleTo extends AnimationAction {

	protected float scaleW;
	protected float scaleH;
	protected float startScaleW;
	protected float startScaleH;
	protected float deltaScaleW;
	protected float deltaScaleH;
	int scaleType;

	public static ScaleTo $(float scaleW, float scaleH, int scaleType,
			float duration) {
		ScaleTo action = new ScaleTo();
		action.scaleW = scaleW;
		action.scaleH = scaleH;
		action.duration = duration;
		action.invDuration = 1 / duration;
		action.scaleType = scaleType;
		return action;
	}

	@Override
	public void setTarget(GLNode actor) {
		this.target = actor;
		startScaleW = target.getScaleW();
		startScaleH = target.getScaleH();
		deltaScaleW = scaleW - startScaleW;
		deltaScaleH = scaleH - startScaleH;
		target.setScaleType(scaleType);
		taken = 0;
		done = false;

	}

	@Override
	public void act(float delta) {
		float alpha = createInterpolatedAlpha(delta);
		if (done) {
			target.setScale(scaleW, scaleH);
		} else {
			float sw = (startScaleW + deltaScaleW * alpha);
			float sh = (startScaleH + deltaScaleH * alpha);
			target.setScale(sw, sh);
		}
	}

	@Override
	public Action copy() {
		ScaleTo action = $(scaleW, scaleH, scaleType, duration);
		if (interpolator != null) {
			action.setInterpolator(interpolator.copy());
		}
		return action;
	}

}

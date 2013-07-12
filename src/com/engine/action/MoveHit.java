package com.engine.action;

import com.engine.ui.GLNode;

public class MoveHit extends AnimationAction{

	protected float x;
	protected float y;
	protected float startX;
	protected float startY;
	protected float deltaX;
	protected float deltaY;
	GLNode dstGlNode;

	public static MoveHit $(GLNode tar, float duration) {
		MoveHit action = new MoveHit();
		action.dstGlNode = tar;
		action.x = tar.getCenterX();
		action.y = tar.getCenterY();
		action.duration = duration;
		action.invDuration = 1 / duration;
		return action;
	}


	@Override
	public void setTarget(GLNode actor) {
		this.target = actor;
		this.startX = target.getCenterX();
		this.startY = target.getCenterY();
		this.x = dstGlNode.getCenterX() - target.getWidth() / 2;
		this.y = dstGlNode.getCenterY() - target.getHeight() / 2;
		this.deltaX = x - target.getCenterX();
		this.deltaY = y - target.getCenterY();
		this.taken = 0;
		this.done = false;
	}

	@Override
	public void act(float delta) {
		float alpha = createInterpolatedAlpha(delta);
		if (done) {
			target.setPos((int) x, (int) y);

		} else {
			this.startX = target.getCenterX(); // 当前中心坐标
			this.startY = target.getCenterY();
			
			this.x = dstGlNode.getCenterX() - target.getWidth() / 2; // 目标坐标
			this.y = dstGlNode.getCenterY() - target.getHeight() / 2;
			
			this.deltaX = this.x - target.getCenterX();
			this.deltaY = this.y - target.getCenterY();
			int mx = (int) (startX + deltaX * alpha);
			target.setPos(mx, (int) (startY + deltaY * alpha));
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	public int getStartX() {
		return (int) this.startX;
	}

	public int getStartY() {
		return (int) this.startY;
	}


//	@Override
//	public Action copy() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
	public Action copy() {
		MoveHit moveTo = $(dstGlNode, duration);
		if (interpolator != null)
			moveTo.setInterpolator(interpolator.copy());
		return moveTo;
	}

}

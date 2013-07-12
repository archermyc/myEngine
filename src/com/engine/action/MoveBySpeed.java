package com.engine.action;

import com.engine.ui.GLNode;
import com.towergame.enemy.GLEnemy;

public class MoveBySpeed extends AnimationAction {

	protected float x;
	protected float y;
	protected float startX;
	protected float startY;
	protected float deltaX;
	protected float deltaY;
	protected float speed;
	
	public final static float MOVETIME = 0.03f; 

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static MoveBySpeed $(float x, float y) {
		MoveBySpeed action = new MoveBySpeed();
		action.x = x;
		action.y = y;
		action.speed = 0;
		return action;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void setTarget(GLNode actor) {
		this.target = actor;
		this.startX = target.getX();
		this.startY = target.getY();
		this.deltaX = x - target.getX();
		this.deltaY = y - target.getY();
		this.taken = 0;
		this.done = false;
	}

	@Override
	public void act(float delta) {
		if (done) {
			target.setPos(x,  y);

		} else {
			taken += delta;
			if (taken > MOVETIME) {
				float mx = target.getX();
				speed = ((GLEnemy)target).getSpeed();
				if (this.x != target.getX()) {
					if (this.x > target.getX()) {
						mx = Math.abs((float) (target.getX() + speed));
						if (mx >= this.x) {
							mx = this.x;
							done = true;
						}
					} else {
						mx = Math.abs((float) (target.getX() - speed));
						if (mx <= this.x) {
							mx = this.x;
							done = true;
						}
					}
				}
				float my = target.getY();
				if (this.y != target.getY()) {
					if (this.y > target.getY()) {
						// my = target.getY() + speed;
						my = Math.abs((float) (target.getY() + speed));
						if (my >= this.y) {
							my = this.y;
							done = true;
						}
					} else {
						my = Math.abs((float) (target.getY() - speed));
						if (my <= this.y) {
							my = this.y;
							done = true;
						}
					}
				}

				target.setPos(mx, my);
				taken = 0;
			}

		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	public float getStartX() {
		return  this.startX;
	}

	public float getStartY() {
		return this.startY;
	}

	@Override
	public Action copy() {
		MoveBySpeed MoveBySpeed = $(x, y);
		if (interpolator != null)
			MoveBySpeed.setInterpolator(interpolator.copy());
		return MoveBySpeed;
	}

}

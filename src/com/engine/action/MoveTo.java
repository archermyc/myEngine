/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.engine.action;

import com.engine.ui.GLNode;

public class MoveTo extends AnimationAction {

	protected float x;
	protected float y;
	protected float startX;
	protected float startY;
	protected float deltaX;
	protected float deltaY;

	public static MoveTo $(float x, float y, float duration) {
		MoveTo action = new MoveTo();
		action.x = x;
		action.y = y;
		action.duration = duration;
		action.invDuration = 1 / duration;
		return action;
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
		float alpha = createInterpolatedAlpha(delta);
		if (done) {
			target.setPos((int) x, (int) y);

		} else {
			int x = (int) (startX + deltaX * alpha);
//			Math.abs(i)
			target.setPos(x, (int) (startY + deltaY * alpha));
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

	@Override
	public Action copy() {
		MoveTo moveTo = $(x, y, duration);
		if (interpolator != null)
			moveTo.setInterpolator(interpolator.copy());
		return moveTo;
	}

}

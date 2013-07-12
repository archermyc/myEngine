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

public class Rotate extends AnimationAction {

	protected float rotate;
	protected float srcRotate;
	protected float deltaR;
	protected int rotateType;

	/**
	 * rotatetype = actiontype 里面的 rotateCenter or rotateLeftTop
	 * 
	 * @param rotate
	 * @param rotateType
	 * @param duration
	 * @return
	 */
	public static Rotate $(float rotate, int rotateType, float duration) {
		Rotate action = new Rotate();
		action.rotate = rotate;
		action.duration = duration;
		action.invDuration = 1 / duration;
		action.rotateType = rotateType;
		return action;
	}

	@Override
	public void setTarget(GLNode actor) {
		this.target = actor;
		this.srcRotate = actor.getRotate();
		this.deltaR = rotate - target.getRotate();
		this.taken = 0;
		actor.setRotateType(rotateType);
		this.done = false;
	}

	@Override
	public void act(float delta) {
		float alpha = createInterpolatedAlpha(delta);
		if (done) {
			target.setRotate(rotate);

		} else {
			float r = (int) (srcRotate + deltaR * alpha);
			target.setRotate(r);
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Action copy() {
		Rotate Rotate = $(rotate, rotateType, duration);
		if (interpolator != null)
			Rotate.setInterpolator(interpolator.copy());
		return Rotate;
	}

}
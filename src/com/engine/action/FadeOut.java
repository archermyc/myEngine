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

/**
 * 渐出
 * @author YCMO
 *
 */
public class FadeOut extends AnimationAction {

	protected float startAlpha = 0;
	protected float deltaAlpha = 0;

	public static FadeOut $(float startAlpha, float duration) {
		FadeOut action = new FadeOut();
		action.duration = duration;
		action.startAlpha = startAlpha;
		action.invDuration = 1 / duration;
		return action;
	}

	@Override
	public void setTarget(GLNode UIView) {
		this.target = UIView;
		this.deltaAlpha = -startAlpha;
		this.taken = 0;
		this.done = false;
	}

	@Override
	public void act(float delta) {
		float alpha = createInterpolatedAlpha(delta);
		if (done) {
			target.setAlpha(0.0f);
		} else {
			target.setAlpha(startAlpha + deltaAlpha * alpha);
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Action copy() {
		FadeOut fadeOut = $(startAlpha, duration);
		if (interpolator != null)
			fadeOut.setInterpolator(interpolator.copy());
		return fadeOut;
	}
}

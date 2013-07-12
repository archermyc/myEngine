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

public class Repeat extends TemporalAction {

	protected int times;
	protected int finishedTimes;

	public static Repeat $(Action action, int times) {
		Repeat repeat = new Repeat();
		repeat.action = action;
		repeat.times = times;
		return repeat;
	}

	@Override
	public void reset() {
		super.reset();
		finishedTimes = 0;
		listener = null;
	}

	@Override
	public void setTarget(GLNode UIView) {
		action.setTarget(UIView);
		target = UIView;
	}

	@Override
	public void act(float delta) {
		action.act(delta);
		if (action.isDone()) {
			finishedTimes++;
			if (finishedTimes < times) {
				Action oldAction = action;
				target.setPos(((MoveTo)action).getStartX(), ((MoveTo)action).getStartY());
				action = action.copy();
				oldAction.finish();
//				target.setPos((int)((MoveTo)action).getStartX(), (int)((MoveTo)action).getStartY());
				action.setTarget(target);
			} else {
				callActionCompletedListener();
			}
		}
	}

	@Override
	public boolean isDone() {
		return finishedTimes >= times;
	}

	@Override
	public void finish() {
		action.finish();
		super.finish();
	}

	@Override
	public Action copy() {
		return $(action.copy(), times);
	}

	@Override
	public GLNode getTarget() {
		return target;
	}
}

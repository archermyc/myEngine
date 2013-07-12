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

package com.engine.interpolators;

/**
 * A very simple {@link Interpolator} which provides a linear progression by
 * just returning the current input.
 * 
 * @author Moritz Post <moritzpost@gmail.com>
 */
public class LinearInterpolator implements Interpolator {

	LinearInterpolator() {
		// hide constructor
	}

	/**
	 * Gets a new {@link LinearInterpolator} from a maintained pool of
	 * {@link Interpolator}s.
	 * 
	 * @return the obtained {@link LinearInterpolator}
	 */
	public static LinearInterpolator $() {
		return new LinearInterpolator();
	}

	@Override
	public void finished() {

	}

	@Override
	public float getInterpolation(float input) {
		return input;
	}

	@Override
	public Interpolator copy() {
		return $();
	}
}

/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 */

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.engine.entity;

import java.io.Closeable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Collection of utility functions used in this package.
 */
public class Util {
	private static final String TAG = "Util";
	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;
	
	private static float sPixelDensity = -1f;

	public static final String REVIEW_ACTION = "com.cooliris.media.action.REVIEW";

	private Util() {
	}

	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		return rotateAndMirror(b, degrees, false);
	}

	// Rotates and/or mirrors the bitmap. If a new bitmap is created, the
	// original bitmap is recycled.
	public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			if (mirror) {
				m.postScale(-1, 1);
				degrees = (degrees + 360) % 360;
				if (degrees == 0 || degrees == 180) {
					m.postTranslate(b.getWidth(), 0);
				} else if (degrees == 90 || degrees == 270) {
					m.postTranslate(b.getHeight(), 0);
				} else {
					throw new IllegalArgumentException("Invalid degrees="
							+ degrees);
				}
			}

			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	/*
	 * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that is tolerable in terms of memory usage.
	 * 
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 * 
	 * Also, the function rounds up the sample size to a power of 2 or multiple
	 * of 8 because BitmapFactory only honors sample size this way. For example,
	 * BitmapFactory downsamples an image by 2 even though the request is 3. So
	 * we round up the sample size to avoid OOM.
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == IImage.UNCONSTRAINED) ? 1
				: (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == IImage.UNCONSTRAINED) ? 128
				: (int) Math.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == IImage.UNCONSTRAINED)
				&& (minSideLength == IImage.UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == IImage.UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static <T> int indexOf(T[] array, T s) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public static void closeSilently(Closeable c) {
		if (c == null)
			return;
		try {
			c.close();
		} catch (Throwable t) {
			// do nothing
		}
	}

	public static Bitmap makeBitmap(byte[] jpegData, int maxNumOfPixels) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory
					.decodeByteArray(jpegData, 0, jpegData.length, options);
			if (options.mCancel || options.outWidth == -1
					|| options.outHeight == -1) {
				return null;
			}
			options.inSampleSize = computeSampleSize(options,
					IImage.UNCONSTRAINED, maxNumOfPixels);
			options.inJustDecodeBounds = false;

			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
					options);
		} catch (OutOfMemoryError ex) {
			Log.e(TAG, "Got oom exception ", ex);
			return null;
		}
	}

	public static void Assert(boolean cond) {
		if (!cond) {
			throw new AssertionError();
		}
	}

	public static Animation slideOut(View view, int to) {
		view.setVisibility(View.INVISIBLE);
		Animation anim;
		switch (to) {
		case DIRECTION_LEFT:
			anim = new TranslateAnimation(0, -view.getWidth(), 0, 0);
			break;
		case DIRECTION_RIGHT:
			anim = new TranslateAnimation(0, view.getWidth(), 0, 0);
			break;
		case DIRECTION_UP:
			anim = new TranslateAnimation(0, 0, 0, -view.getHeight());
			break;
		case DIRECTION_DOWN:
			anim = new TranslateAnimation(0, 0, 0, view.getHeight());
			break;
		default:
			throw new IllegalArgumentException(Integer.toString(to));
		}
		anim.setDuration(500);
		view.startAnimation(anim);
		return anim;
	}

	public static Animation slideIn(View view, int from) {
		view.setVisibility(View.VISIBLE);
		Animation anim;
		switch (from) {
		case DIRECTION_LEFT:
			anim = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
			break;
		case DIRECTION_RIGHT:
			anim = new TranslateAnimation(view.getWidth(), 0, 0, 0);
			break;
		case DIRECTION_UP:
			anim = new TranslateAnimation(0, 0, -view.getHeight(), 0);
			break;
		case DIRECTION_DOWN:
			anim = new TranslateAnimation(0, 0, view.getHeight(), 0);
			break;
		default:
			throw new IllegalArgumentException(Integer.toString(from));
		}
		anim.setDuration(500);
		view.startAnimation(anim);
		return anim;
	}

	public static <T> T checkNotNull(T object) {
		if (object == null)
			throw new NullPointerException();
		return object;
	}

	public static boolean equals(Object a, Object b) {
		return (a == b) || (a == null ? false : a.equals(b));
	}

	public static boolean isPowerOf2(int n) {
		return (n & -n) == n;
	}

	public static int nextPowerOf2(int n) {
		n -= 1;
		n |= n >>> 16;
		n |= n >>> 8;
		n |= n >>> 4;
		n |= n >>> 2;
		n |= n >>> 1;
		return n + 1;
	}

	public static float distance(float x, float y, float sx, float sy) {
		float dx = x - sx;
		float dy = y - sy;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public static int clamp(int x, int min, int max) {
		if (x > max)
			return max;
		if (x < min)
			return min;
		return x;
	}

	public static int getDisplayRotation(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		switch (rotation) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return 270;
		}
		return 0;
	}
	
	public synchronized static float dpToPixel(Context context, float dp) {
		if (sPixelDensity < 0) {
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			sPixelDensity = metrics.density;
		}
		return sPixelDensity * dp;
	}

	public static int dpToPixel(Context context, int dp) {
		return (int) (dpToPixel(context, (float) dp) + .5f);
	}


//	public DisplayMetrics getDisplayMetrics() {
//		DisplayMetrics mDisplayMetrics = null;
//		mDisplayMetrics = new DisplayMetrics();
//		((Activity) getContext()).getWindowManager().getDefaultDisplay()
//				.getMetrics(mDisplayMetrics);
//		return mDisplayMetrics;
//	}

}

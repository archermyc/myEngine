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
 * Copyright (C) 2010 The Android Open Source Project
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.microedition.khronos.opengles.GL11;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.engine.action.ActionType;

public class GLView {
	@SuppressWarnings("unused")
	private static final String TAG = "GLView";

	public static final int VISIBLE = 0;
	public static final int INVISIBLE = 1;

	public static final int FLAG_INVISIBLE = 1;
	public static final int FLAG_SET_MEASURED_SIZE = 2;
	public static final int FLAG_LAYOUT_REQUESTED = 4;

	protected final RectF mBounds = new RectF();
	protected final Rect mPaddings = new Rect();

	private GLRootView mRootView;
	private GLView mParent;
	private ArrayList<GLView> mComponents;
	private GLView mMotionTarget;

	private OnTouchListener mOnTouchListener;
	protected Animation mAnimation;

	protected int mViewFlags = 0;

	protected int mMeasuredWidth = 0;
	protected int mMeasuredHeight = 0;

	private int mLastWidthSpec = -1;
	private int mLastHeightSpec = -1;

	protected int mScrollY = 0;
	protected int mScrollX = 0;
	protected int mScrollHeight = 0;
	protected int mScrollWidth = 0;

	/**
	 * 额外扩展的属性,用于动画
	 */
	protected float rotate = 0.0f;
	private int rotateType = ActionType.RotateLefTop;
	protected int scaleType = ActionType.ScaleLefTop;
	protected float alpha = 1f;
	/**
	 * 宽缩放比
	 */
	protected float scaleW = 1f;
	protected float scaleH = 1f;
	private int mDrawLevel = 0;
	private int mDrawId = 0;

	public GLView() {
		mDrawLevel = 0;
	}

	public void startAnimation(Animation animation) {
		GLRootView root = getGLRootView();
		if (root == null)
			throw new IllegalStateException();

		mAnimation = animation;
		animation.initialize((int) getWidth(), (int) getHeight(),
				(int) mParent.getWidth(), (int) mParent.getHeight());
		mAnimation.start();
		root.registerLaunchedAnimation(animation);
		invalidate();
	}

	public void setVisibility(int visibility) {
		if (visibility == getVisibility())
			return;
		if (visibility == VISIBLE) {
			mViewFlags &= ~FLAG_INVISIBLE;
		} else {
			mViewFlags |= FLAG_INVISIBLE;
		}
		onVisibilityChanged(visibility);
		invalidate();
	}

	public int getVisibility() {
		return (mViewFlags & FLAG_INVISIBLE) == 0 ? VISIBLE : INVISIBLE;
	}

	public static interface OnTouchListener {
		/**
		 * 
		 * @param view
		 * @param event
		 * @return 返回true 则终止事件，返回false则事件继续传递下去c
		 */
		public boolean onTouch(GLView view, MotionEvent event);
	}

	public static interface OnClickListener {
		public void OnClick(GLView view);
	}

	private boolean setBounds(float left, float top, float right, float bottom) {
		boolean sizeChanged = (right - left) != (mBounds.right - mBounds.left)
				|| (bottom - top) != (mBounds.bottom - mBounds.top);
		mBounds.set(left, top, right, bottom);
		return sizeChanged;
	}

	protected void onAddToParent(GLView parent) {
		// TODO: enable the check
		// if (mParent != null) throw new IllegalStateException();
		mParent = parent;
		// 默认分配一个drawId 排序用到
		mDrawId = GLRootView.allocateDrawingId();

		if (parent != null && parent.mRootView != null) {
			onAttachToRoot(parent.mRootView);
		}
	}

	/**
	 * 设置绘制等级 等级越高 越后面才被绘制
	 * 
	 * @param drawLevel
	 */
	public void setDrawLevel(int drawLevel) {
		this.mDrawLevel = drawLevel;
		if (mParent != null) {
			mParent.sort();
		}
	}

	public int getDrawLevel() {
		return mDrawLevel;
	}

	public int getDrawingId() {
		return mDrawId;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	protected void onRemoveFromParent(GLView parent) {
		if (parent != null && parent.mMotionTarget == this) {
			long now = SystemClock.uptimeMillis();
			dispatchTouchEvent(MotionEvent.obtain(now, now,
					MotionEvent.ACTION_CANCEL, 0, 0, 0));
			parent.mMotionTarget = null;
		}
		onDetachFromRoot();
		mParent = null;
	}

	public void clearComponents() {
		mComponents = null;
	}

	public GLView getParent() {
		return mParent;
	}

	public int getComponentCount() {
		return mComponents == null ? 0 : mComponents.size();
	}

	public GLView getComponent(int index) {
		if (mComponents == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return mComponents.get(index);
	}

	protected void addComponent(GLView component) {
		if (mComponents == null) {
			mComponents = new ArrayList<GLView>();
		}
		if (component.mParent == null) {

		} else {
			// 为了便于管理 每一个view 只有一个父节点 所以先移除
			component.mParent.removeComponent(component);
		}
		mComponents.add(component);
		component.onAddToParent(this);
		if (component.getDrawLevel() != 0) {
			sort();
		}
	}

	public boolean removeFromParent() {
		if (mParent != null) {
			mParent.removeComponent(this);
		}
		return false;
	}

	public boolean removeComponent(GLView component) {
		if (mComponents == null)
			return false;
		if (mComponents.remove(component)) {
			component.onRemoveFromParent(this);
			return true;
		}
		return false;
	}

	/**
	 * 设置角度
	 * 
	 * @param rotate
	 */
	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	/**
	 * 获得选择角度
	 * 
	 * @return
	 */
	public float getRotate() {
		return this.rotate;
	}

	public void setScale(float sw, float sh) {
		scaleW = sw;
		scaleH = sh;
	}

	/**
	 * 设置旋转类型 默认是按照左顶点旋转，可以改成中心点旋转
	 * 
	 * @param rotateType
	 */
	public void setRotateType(int rotateType) {
		this.rotateType = rotateType;
	}

	/**
	 * 设置旋转类型 默认是按照左顶点旋转，可以改成中心点旋转
	 * 
	 * @param rotateType
	 */
	public void setScaleType(int scaleType) {
		this.scaleType = scaleType;
	}

	// public RectF getBounds() {
	// return mBounds;
	// }

	public int getWidth() {
		return (int) (mBounds.right - mBounds.left);
	}

	public int getHeight() {
		return (int) (mBounds.bottom - mBounds.top);
	}

	public float getX() {
		return mBounds.left;
	}

	public float getY() {
		return mBounds.top;
	}

	public GLRootView getGLRootView() {
		return mRootView;
	}

	public void setOnTouchListener(OnTouchListener listener) {
		mOnTouchListener = listener;
	}

	public void invalidate() {
		GLRootView root = getGLRootView();
		if (root != null)
			root.requestRender();
	}

	public void requestLayout() {
		mViewFlags |= FLAG_LAYOUT_REQUESTED;
		if (mParent != null) {
			mParent.requestLayout();
		} else {
			// Is this a content pane ?
			GLRootView root = getGLRootView();
			if (root != null)
				root.requestLayoutContentPane();
		}
	}

	protected void render(GLRootView view, GL11 gl) {
		renderBackground(view, gl);
		for (int i = 0; i < getComponentCount(); i++) {
			GLView component = getComponent(i);
			if (component.getVisibility() != GLView.VISIBLE
					&& component.mAnimation == null)
				continue;
			renderChild(view, gl, component);

		}
	}

	protected void renderBackground(GLRootView view, GL11 gl) {
	}

	protected void update() {
		for (int i = 0; i < getComponentCount(); i++) {
			GLView component = getComponent(i);
			if (component.getVisibility() != GLView.VISIBLE
					&& component.mAnimation == null)
				continue;
			component.update();
		}
	}

	protected void renderChild(GLRootView root, GL11 gl, GLView component) {
		float xoffset = component.getX() - mScrollX;
		float yoffset = component.getY() - mScrollY;
		float rotate = component.getRotate();
		float mMatrixValues[] = new float[16];
		Transformation transform = root.getTransformation();
		float srcAlpha = transform.getAlpha();
		if (component.alpha != 1f) {
			transform.setAlpha(component.alpha);
		}
		Matrix matrix = transform.getMatrix();
		matrix.getValues(mMatrixValues); // 获取矩阵数据
		matrix.preTranslate(xoffset, yoffset);

		/**
		 * 处理旋转
		 */
		switch (component.rotateType) {
		case ActionType.RotateLefTop: // 左顶点旋转
			matrix.preRotate(rotate);
			break;
		case ActionType.RotateCenter:// 中心点旋转
			matrix.preRotate(rotate, component.getWidth() / 2,
					component.getHeight() / 2);
			break;
		default:
			matrix.preRotate(rotate);
			break;
		}

		/**
		 * 处理缩放
		 */
		float scalew = component.scaleW;
		float scaleh = component.scaleH;
		if (scalew != 1.0f || scaleh != 1.0f) {
			switch (component.scaleType) {
			case ActionType.ScaleCenter:
				matrix.preScale(scalew, scaleh, component.getWidth() / 2,
						component.getHeight() / 2);
				break;
			case ActionType.ScaleLefTop:
				matrix.preScale(scalew, scaleh);
				break;

			default:
				matrix.preScale(scalew, scaleh);
				break;
			}

		}

		Animation anim = component.mAnimation;
		if (anim != null) {
			long now = root.currentAnimationTimeMillis();
			Transformation temp = root.obtainTransformation();
			if (!anim.getTransformation(now, temp)) {
				component.mAnimation = null;
			}
			invalidate();
			root.pushTransform();
			transform.compose(temp);
			root.freeTransformation(temp);
		}
		component.render(root, gl);
		if (anim != null)
			root.popTransform();
		// matrix.preTranslate(-xoffset, -yoffset);

		matrix.setValues(mMatrixValues);// 还原矩阵信息
		transform.setAlpha(srcAlpha);

	}

	protected boolean onTouch(MotionEvent event) {
		if (mOnTouchListener != null) {
			return mOnTouchListener.onTouch(this, event);
		}
		return false;
	}

	private boolean dispatchTouchEvent(MotionEvent event, int x, int y,
			GLView component, boolean checkBounds) {
		// RectF rect = component.mBounds;
		RectF rect = getRectF(component);
		float left = rect.left;
		float top = rect.top;

		if (!checkBounds || rect.contains(x, y)) {
			event.offsetLocation(-left, -top);
			if (component.dispatchTouchEvent(event)) {
				event.offsetLocation(left, top);
				return true;
			}
			event.offsetLocation(left, top);
		}
		return false;
	}

	private RectF getRectF(GLView component) {
		RectF rect = new RectF();
		float left = component.getX();
		float top = component.getY();
		float right = 0;
		float bottom = 0;
		int width = component.getWidth();
		int height = component.getHeight();
		float scaleW = component.scaleW;
		float scaleH = component.scaleH;
		switch (component.scaleType) {
		case ActionType.ScaleLefTop:
			right = left + width * scaleW;
			bottom = top + height * scaleH;
			break;
		case ActionType.ScaleCenter:
			left = left - (width * (scaleW - 1) / 2);
			top = top - (height * (scaleH - 1) / 2);
			right = left + width * scaleW;
			bottom = top + height * scaleH;
			break;
		default:
			break;
		}

		rect.left = left;
		rect.top = top;
		rect.right = right;
		rect.bottom = bottom;
		return rect;
	}

	@SuppressLint("Recycle")
	protected boolean dispatchTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int action = event.getAction();
		if (mMotionTarget != null) {
			if (action == MotionEvent.ACTION_DOWN) {
				MotionEvent cancel = MotionEvent.obtain(event);
				cancel.setAction(MotionEvent.ACTION_CANCEL);
				mMotionTarget = null;
			} else {
				// move超过边界 接受up事件
				if (dispatchTouchEvent(event, x, y, mMotionTarget, true) == false) {
					mMotionTarget.dispatchTouchEvent(event);
				}
				if (action == MotionEvent.ACTION_CANCEL
						|| action == MotionEvent.ACTION_UP) {
					mMotionTarget = null;
				}
				return true;
			}
		}

		if (action == MotionEvent.ACTION_DOWN) {
			for (int i = getComponentCount() - 1; i >= 0; i--) {
				GLView component = getComponent(i);
				if (component.getVisibility() != GLView.VISIBLE)
					continue;
				if (dispatchTouchEvent(event, x, y, component, true)) {
					mMotionTarget = component;
					return true;
				}
			}
		}

		return onTouch(event);
	}

	public Rect getPaddings() {
		return mPaddings;
	}

	public void setPaddings(Rect paddings) {
		mPaddings.set(paddings);
	}

	public void setPaddings(int left, int top, int right, int bottom) {
		mPaddings.set(left, top, right, bottom);
	}

	@SuppressLint("WrongCall")
	public void layout(float left, float top, float right, float bottom) {
		boolean sizeChanged = setBounds(left, top, right, bottom);
		if (sizeChanged) {
			mViewFlags &= ~FLAG_LAYOUT_REQUESTED;
			onLayout(true, left, top, right, bottom);
		} else if ((mViewFlags & FLAG_LAYOUT_REQUESTED) != 0) {
			mViewFlags &= ~FLAG_LAYOUT_REQUESTED;
			onLayout(false, left, top, right, bottom);
		}
	}

	@SuppressLint("WrongCall")
	public void measure(int widthSpec, int heightSpec) {
		if (widthSpec == mLastWidthSpec && heightSpec == mLastHeightSpec
				&& (mViewFlags & FLAG_LAYOUT_REQUESTED) == 0) {
			return;
		}

		mLastWidthSpec = widthSpec;
		mLastHeightSpec = heightSpec;

		mViewFlags &= ~FLAG_SET_MEASURED_SIZE;
		onMeasure(widthSpec, heightSpec);
		if ((mViewFlags & FLAG_SET_MEASURED_SIZE) == 0) {
			throw new IllegalStateException(getClass().getName()
					+ " should call setMeasuredSize() in onMeasure()");
		}
	}

	protected void onMeasure(int widthSpec, int heightSpec) {
	}

	protected void setMeasuredSize(int width, int height) {
		mViewFlags |= FLAG_SET_MEASURED_SIZE;
		mMeasuredWidth = width;
		mMeasuredHeight = height;
	}

	public int getMeasuredWidth() {
		return mMeasuredWidth;
	}

	public int getMeasuredHeight() {
		return mMeasuredHeight;
	}

	protected void onLayout(boolean changeSize, float left, float top,
			float right, float bottom) {
	}

	/**
	 * Gets the bounds of the given descendant that relative to this view.
	 */
	public boolean getBoundsOf(GLView descendant, RectF out) {
		int xoffset = 0;
		int yoffset = 0;
		GLView view = descendant;
		while (view != this) {
			if (view == null)
				return false;
			RectF bounds = view.mBounds;
			xoffset += bounds.left;
			yoffset += bounds.top;
			view = view.mParent;
		}
		out.set(xoffset, yoffset, xoffset + descendant.getWidth(), yoffset
				+ descendant.getHeight());
		return true;
	}

	protected void onVisibilityChanged(int visibility) {
		for (int i = 0, n = getComponentCount(); i < n; ++i) {
			GLView child = getComponent(i);
			if (child.getVisibility() == GLView.VISIBLE) {
				child.onVisibilityChanged(visibility);
			}
		}
	}

	protected void onAttachToRoot(GLRootView root) {
		mRootView = root;
		for (int i = 0, n = getComponentCount(); i < n; ++i) {
			getComponent(i).onAttachToRoot(root);
		}
	}

	protected void onDetachFromRoot() {
		for (int i = 0, n = getComponentCount(); i < n; ++i) {
			getComponent(i).onDetachFromRoot();
		}
		mRootView = null;
	}

	/**
	 * 根据绘制等级排序
	 */
	public void sort() {
		Comparator<GLView> comparator = new Comparator<GLView>() {
			public int compare(GLView v1, GLView v2) {
				// 默认按照绘制等级排序
				if (v1.getDrawLevel() != v2.getDrawLevel()) {
					return v1.getDrawLevel() - v2.getDrawLevel();
				} else {
					return v1.getDrawingId() - v2.getDrawingId();
				}
			}
		};
		if (mComponents != null && mComponents.size() > 0) {
			try {
				Collections.sort(mComponents, comparator);
			} catch (Exception e) {
				// TODO: handle exception
			}
			for (int i = 0; i < mComponents.size(); i++) {
				GLView child = mComponents.get(i);
				child.sort();
			}
		}
	}
}

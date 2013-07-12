package com.engine.ui;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;

import com.engine.action.Action;
import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.entity.GLView;
import com.engine.entity.MeasureHelper;

public class GLNode extends GLView {

	private OnClickListener mOnClickListener = null;

	protected ArrayList<Action> mActions = new ArrayList<Action>();

	boolean isShowDrawRect = false;

	/**
	 * 表示节点是否在更新逻辑，之前说过，未加入树的节点是不会显示到屏幕上的，其实它的逻辑运算也是一样，就是说没有父节点的节点不会显示，
	 * 也不会处理自己的逻辑
	 * ，它的一切活动都会停止。那么当节点被加入到树中的时候，isRunning_会被自动置为Yes，此时它会开始执行自己的运算，当节点离开树时
	 * ，isRunning_会被置为No，如果想在它在树中的时候暂停运算，就需要将它的isRunning_手动置为No。
	 */
	protected boolean mIsRunning = true;

	public void setPos(float x, float y) {
		float w = getWidth();
		float h = getHeight();
		layout(x, y, w + x, h + y);
	}

	public void setSize(int w, int h) {
		float x = getX();
		float y = getY();
		layout(x, y, w + x, h + y);
	}

	public void addAction(Action action) {
		if (mActions != null) {
			action.setTarget(this);
			mActions.add(action);
		}
	}

	public void addChild(GLView view) {
		addComponent(view);
	}

	public void addToRoot() {
		GLScene scene = GLSceneManager.getInstance().getCurScene();
		if (scene != null) {
			scene.addChild(this);
		}
	}

	public void doAction() {
		if (mActions != null) {
			for (int i = 0; i < mActions.size(); i++) {
				Action action = mActions.get(i);
				action.act(FPS.getDeltaTime());
				if (action.isDone()) {
					action.finish();
					mActions.remove(i);
				}
			}
		}
	}

	protected void update() {
		if (mIsRunning) {
			doAction();
			super.update();
		}
	}

	/**
	 * 停止更新操作 诸如移动 旋转等，以及在update内部的操作逻辑
	 */
	public void stopUpdate() {
		mIsRunning = false;
	}

	public void resumeUpdate() {
		mIsRunning = true;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		// doAction();
		if (isShowDrawRect) {
			root.drawLine(0, 0, getWidth(), 0, Color.YELLOW);
			root.drawLine(0, 0, 0, getHeight(), Color.YELLOW);
			root.drawLine(getWidth(), 0, getWidth(), getHeight(), Color.YELLOW);
			root.drawLine(0, getHeight(), getWidth(), getHeight(), Color.YELLOW);
		}

		super.render(root, gl);

	}

	public float getCenterX() {
		return mBounds.left + (mBounds.right - mBounds.left) / 2;
	}

	public float getCenterY() {
		return mBounds.top + (mBounds.bottom - mBounds.top) / 2;
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int width = 0;
		int height = 0;
		for (int i = 0, n = getComponentCount(); i < n; ++i) {
			GLView view = getComponent(i);
			view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			width = Math.max(width, view.getMeasuredWidth());
			height += view.getMeasuredHeight();
		}
		new MeasureHelper(this).setPreferredContentSize(width, height).measure(
				widthSpec, heightSpec);

	}

	public interface OnClickListener {
		public void OnClick(GLView view);
	}

	@Override
	protected boolean onTouch(MotionEvent event) {
		boolean flag = onClick(event);
		flag = flag || super.onTouch(event);
		return flag;
	}

	protected void setOnClickListener(OnClickListener listener) {
		this.mOnClickListener = listener;
	}

	protected boolean onClick(MotionEvent event) {
		if (mOnClickListener != null) {
			int Action = event.getAction();
			if (Action == MotionEvent.ACTION_UP
					|| Action == MotionEvent.ACTION_CANCEL) {
				mOnClickListener.OnClick(this);

			}
			return true;
		}
		return false;
	}

	public float getScaleW() {
		return scaleW;
	}

	public float getScaleH() {
		return scaleH;
	}

	/**
	 * 显示绘制区域，默认不显示
	 */
	public void showDrawRect() {
		isShowDrawRect = true;
	}

	/**
	 * 隐藏绘制区域，默认不显示
	 */
	public void hideDrawRect() {
		isShowDrawRect = false;
	}

}

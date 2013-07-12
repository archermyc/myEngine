package com.towergame.enemy;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;

import com.engine.action.Action;
import com.engine.action.MoveBySpeed;
import com.engine.action.OnActionCompleted;
import com.engine.anim.AnimUtil;
import com.engine.anim.GLAnimation;
import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.ui.GLNode;
import com.example.myc.engine.GameApp;

/**
 * �����ƶ����� û�л���
 * @author YCMO
 *
 */
public class GLSprite2 extends GLNode{

	GLAnimation[] mSpriteAnim;
	/** �����ƶ����� **/
	public final static int ANIM_DOWN = 0;
	/** �����ƶ����� **/
	public final static int ANIM_LEFT = 1;
	/** �����ƶ����� **/
	public final static int ANIM_RIGHT = 2;
	/** �����ƶ����� **/
	public final static int ANIM_UP = 3;

	protected Path movePath;
	// �ƶ�·���±�
	protected int pathIndex = 0;

	protected float speed = 3f; // Ĭ���ٶ�

	protected float speedOrg = 3f; // Ĭ���ٶ�

	protected MoveEndListener moveEnd = null;

	protected GLSprite2 instance = null;
	
	int direction = 0;

	public GLSprite2(int resId) {
		Context context = GameApp.getInstnce().getApplicationContext();
		Bitmap testmap = AnimUtil.ReadBitMap(context, resId);
		mSpriteAnim = AnimUtil.loadAnimBitmap(context, testmap, 4);
		instance = this;
		layout(0, 0, mSpriteAnim[0].getWidth(), mSpriteAnim[0].getHeight());
	}

	public void setAnimTime(float time) {
		if (mSpriteAnim != null) {
			for (int i = 0; i < mSpriteAnim.length; i++) {
				mSpriteAnim[i].setAnimTime(time);
			}
		}
	}
	
	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
	}

	/**
	 * Ϊ�˷������
	 * 
	 * @param times
	 */
	public void moveByPath(int times) {
		// ���ڵ�����
		final float[] mCoordinatesX;
		final float[] mCoordinatesY;

		mCoordinatesX = movePath.getCoordinatesX();
		mCoordinatesY = movePath.getCoordinatesY();
		float X = getX();
		float Y = getY();
		int dstX = (int) mCoordinatesX[pathIndex];
		int dstY = (int) mCoordinatesY[pathIndex];
		// ����ƶ�����
		if (dstX > X) {
			direction = ANIM_RIGHT;
		} else if (dstX < X) {
			direction = ANIM_LEFT;
		} else if (dstY > Y) {
			direction = ANIM_DOWN;
		} else if (dstY < Y) {
			direction = ANIM_UP;
		}
		MoveBySpeed action;
		action = MoveBySpeed.$(dstX, dstY);
		action.setCompletionListener(new OnActionCompleted() {
			public void completed(Action action) {
				// TODO Auto-generated method stub
				pathIndex++;
				if (pathIndex == movePath.getSize()) {
					pathIndex = 0;
					removeFromParent();
				}
				moveByPath(1);
			}
		});
		addAction(action);
	}

	public void setSpeed(float speed) {
		if (speed <= 0) {
			speed = 0;
		}
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
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

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMovePath(Path path) {
		this.movePath = path;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mSpriteAnim != null) {
			mSpriteAnim[direction].drawAnimation(root, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}

	public void setMoveEndListener(MoveEndListener listener) {
		this.moveEnd = listener;
	}

	public interface MoveEndListener {
		public void onMoveEnd(GLSprite sprite);
	}
	
	public int getSpeedX() {
		if (direction == ANIM_RIGHT) {
			return (int)speed;
		}else if (direction == ANIM_LEFT) {
			return -(int)speed;
		}
		return 0;
	}
	
	public int getSpeedY() {
		if (direction == ANIM_UP) {
			return -(int)speed;
		}else if (direction == ANIM_DOWN) {
			return (int)speed;
		}
		return 0;
	}
}

package com.towergame.enemy;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Rect;

import com.engine.action.Action;
import com.engine.action.MoveBySpeed;
import com.engine.action.OnActionCompleted;
import com.engine.anim.AnimUtil;
import com.engine.anim.GLAnimation2;
import com.engine.entity.FPS;
import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.engine.texture.TextureCache;
import com.engine.ui.GLNode;
import com.example.myc.engine.GameApp;
import com.towergame.player.PlayerInfo;

/**
 * 敌人基类
 * 
 * @author YCMO
 * 
 */
public class GLSprite extends GLNode {
	GLAnimation2 mSpriteAnim;
	/** 向下移动动画 **/
	public final static int ANIM_DOWN = 0;
	/** 向左移动动画 **/
	public final static int ANIM_LEFT = 1;
	/** 向右移动动画 **/
	public final static int ANIM_RIGHT = 2;
	/** 向上移动动画 **/
	public final static int ANIM_UP = 3;

	/**
	 * 移动路径
	 */
	protected Path movePath;
	protected int pathIndex = 0;

	protected MoveEndListener moveEnd = null;

	protected GLSprite instance = null;

	int direction = 0;

	public GLSprite(int resId) {
		ResourceTexture resourceTexture = TextureCache.getInstance().get(resId);
		int w = resourceTexture.getWidth() / 4;
		int h = resourceTexture.getHeight() / 4;
		Rect[][] rects = AnimUtil.getDrawingRect(w, h, 4);
		mSpriteAnim = new GLAnimation2(resourceTexture, rects);
		instance = this;
		layout(0, 0, w, h);
	}

	public GLSprite(String resId) {
		ResourceTexture resourceTexture = TextureCache.getInstance().get(resId);
		int w = resourceTexture.getWidth() / 4;
		int h = resourceTexture.getHeight() / 4;
		Rect[][] rects = AnimUtil.getDrawingRect(w, h, 4);
		mSpriteAnim = new GLAnimation2(resourceTexture, rects);
		instance = this;
		layout(0, 0, w, h);
	}

	public void setAnimTime(float time) {
		if (mSpriteAnim != null) {
			mSpriteAnim.setAnimTime(time);
		}
	}

	@Override
	public void setSize(int w, int h) {
		if (mSpriteAnim != null) {
			mSpriteAnim.setDrawingRect(AnimUtil.getDrawingRect(w, h, 4));
		}
		super.setSize(w, h);
	}

	/**
	 * 根据路径移动
	 * 
	 * 
	 */
	public void moveByPath() {
		final float[] mCoordinatesX;
		final float[] mCoordinatesY;

		mCoordinatesX = movePath.getCoordinatesX();
		mCoordinatesY = movePath.getCoordinatesY();
		float X = getX();
		float Y = getY();
		int dstX = (int) mCoordinatesX[pathIndex];
		int dstY = (int) mCoordinatesY[pathIndex];
		if (pathIndex == 0) {
			pathIndex++;
			setPos(dstX, dstY);
			moveByPath();
			return;
		}
		// 得到移动方向
		// if (dstX > X) {
		// direction = ANIM_RIGHT;
		// } else if (dstX < X) {
		// direction = ANIM_LEFT;
		// } else if (dstY > Y) {
		// direction = ANIM_DOWN;
		// } else if (dstY < Y) {
		// direction = ANIM_UP;
		// }
		if (Math.abs(dstX - X) > Math.abs(dstY - Y)) {
			if (dstX > X) {
				direction = ANIM_RIGHT;
			} else if (dstX < X) {
				direction = ANIM_LEFT;
			}
		} else {
			if (dstY > Y) {
				direction = ANIM_DOWN;
			} else if (dstY < Y) {
				direction = ANIM_UP;
			}
		}
		mSpriteAnim.setDirection(direction);
		MoveBySpeed action;
		action = MoveBySpeed.$(dstX, dstY);
		action.setCompletionListener(new OnActionCompleted() {
			public void completed(Action action) {
				// TODO Auto-generated method stub
				pathIndex++;
				if (pathIndex == movePath.getSize()) {
					pathIndex = 0;
					removeFromParent();
					PlayerInfo.getInstance().setHP(
							PlayerInfo.getInstance().getHP() - 1);
					return;
				}
				moveByPath();
			}
		});
		addAction(action);
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
		mSpriteAnim.setDirection(direction);
	}

	public int getDirection() {
		return direction;
	}

	public void setMovePath(Path path) {
		this.movePath = path;
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mSpriteAnim != null) {
			
			mSpriteAnim.drawAnimation(root, 0, 0, getWidth(), getHeight());
		}
		super.render(root, gl);
	}

	public void setMoveEndListener(MoveEndListener listener) {
		this.moveEnd = listener;
	}

	public interface MoveEndListener {
		public void onMoveEnd(GLSprite sprite);
	}
}

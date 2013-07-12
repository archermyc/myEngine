package com.engin.tweenaccessors;

import aurelienribon.tweenengine.TweenAccessor;

import com.engine.ui.GLNode;

/**
 * 朋友介绍的一个运动类
 * @author YCMO
 *
 */
public class SpriteAccessor implements TweenAccessor<GLNode> {

	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;
	public static final int ROTATE = 4;
	public static final int ALPHA = 5;
	public static final int SCALE = 6;
	public static final int ROTATE_CENTER = 7;

	@Override
	public int getValues(GLNode target, int tweenType, float[] returnValue) {
		int rtn = 0;
		switch (tweenType) {
		case POSITION_XY:
			rtn = 2;
			returnValue[0] = target.getX();
			returnValue[1] = target.getY();
			break;
		case ROTATE:
			rtn = 4;
			returnValue[1] = target.getRotate();
			break;
		case ROTATE_CENTER:
			target.setRotateType(ROTATE_CENTER);
			returnValue[1] = target.getRotate();
			rtn = ROTATE_CENTER;
			break;
		default:
			assert false;
			return -1;
		}
		return rtn;
	}

	@Override
	public void setValues(GLNode target, int tweenType, float[] newValues) {
		float degrees = 0;
		switch (tweenType) {
		case POSITION_XY:
			int x = (int) newValues[0];
			int y = (int) newValues[1];
			target.setPos(x, y);
			break;
		case ROTATE:
			degrees = newValues[0];
			target.setRotate(degrees);
			break;
		case ROTATE_CENTER:
			degrees = newValues[0];
			target.setRotate(degrees);
			break;
		default:
			assert false;
			break;
		}
	}
}
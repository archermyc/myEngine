package com.towergame;

import com.engine.action.MoveBySpeed;
import com.towergame.bullet.GLBullet;
import com.towergame.enemy.GLEnemy;

public class TowerGameUtil {

	public static double getRotation(float x1, float y1, float x2, float y2) {
		return Math.toDegrees(Math.atan2((y2 - y1), (x2 - x1)));

	}

	public static int getTwoPointDistance(float x1, float y1, float x2, float y2) {
		int distance = (int) Math.sqrt(Math.pow(x2 - x1, 2)
				+ Math.pow(y2 - y1, 2));
		return distance;
	}

	public static float[] getBulletDst(GLEnemy enemy, GLBullet bullet) {
		float[] a = new float[2];
		float bulletMoveTime = bullet.getBulletMoveTime();
		int offse = (int) (bulletMoveTime / MoveBySpeed.MOVETIME);
		float dstX = enemy.getX() + offse * enemy.getSpeedX();
		float dstY = enemy.getY() + offse * enemy.getSpeedY();
		a[0] = dstX;
		a[1] = dstY;
		return a;
	}

}

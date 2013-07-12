package com.game.data;

import com.example.myc.engine.GameApp;

public class GameResConfig {
	public static int[] getAttackEffect(int index) {
		String nameBase = "gun_bullet4";
		int len = getEffectLength(index);
		int[] temp = new int[len];
		for (int i = 0; i < len; i++) {
			String name = nameBase + i;
			String pname = GameApp.getInstnce().getPackageName();
			int resid = GameApp.getInstnce().getResources()
					.getIdentifier(name, "drawable", pname);
			temp[i] = resid;
		}
		return temp;
	}

	/**
	 * ���ö�����Ч����
	 * 
	 * @param index
	 * @return
	 */
	public static int getEffectLength(int index) {
		switch (index) {
		case 0:
			return 5;
		case 1:
			return 14;
		case 2:
			return 19;
		case 7:
			return 13;
		case 18:
			return 15;
		case 21:
			return 17;
		}
		return index;
	}

	public static int[] getTowerSprite(int index) {
		String nameBase = "gun_tower" + index;
		int len = getTowerSpriteLength(index);
		int[] temp = new int[len];
		for (int i = 1; i <= len; i++) {
			String name = nameBase + i;
			String pname = GameApp.getInstnce().getPackageName();
			int resid = GameApp.getInstnce().getResources()
					.getIdentifier(name, "drawable", pname);
			temp[i - 1] = resid;
		}
		return temp;
	}

	public static String[] getTowerRes(int index) {
		String nameBase = "gun_tower" + index;
		int len = getTowerSpriteLength(index);
		String[] temp = new String[len];
		for (int i = 1; i <= len; i++) {
			String name = nameBase + i;
			String pname = "images/tower/" + name + ".png";
			temp[i - 1] = pname;
		}
		return temp;
	}

	/**
	 * 获得炮台动画长度
	 * 
	 * @param index
	 * @return
	 */
	public static int getTowerSpriteLength(int index) {
		switch (index) {
		case 1:
			return 5;
		case 2:
			return 5;
		case 9:
			return 5;
		}
		return index;
	}

	public static int[] getBulletSprite(int index) {
		String nameBase = "gun_bullet" + index;
		int len = getTowerSpriteLength(index);
		int[] temp = new int[len];
		for (int i = 1; i <= len; i++) {
			String name = nameBase + i;
			String pname = GameApp.getInstnce().getPackageName();
			int resid = GameApp.getInstnce().getResources()
					.getIdentifier(name, "drawable", pname);
			temp[i - 1] = resid;
		}
		return temp;
	}
	
	public static String[] getBulletRes(int index) {
		String nameBase = "gun_bullet" + index;
		int len = getBulletSpriteLength(index);
		String[] temp = new String[len];
		for (int i = 1; i <= len; i++) {
			String name = nameBase + i;
			String pname = "images/bullet/" + name + ".png";
			temp[i - 1] = pname;
		}
		return temp;
	}

	/**
	 * 获得子弹动画长度
	 * 
	 * @param index
	 * @return
	 */
	public static int getBulletSpriteLength(int index) {
		switch (index) {
		case 3:
			return 4;
		case 2:
			return 5;
		}
		return index;
	}
}

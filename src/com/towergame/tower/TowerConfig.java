package com.towergame.tower;

import com.assetsres.AssertRes;

public class TowerConfig {
	/**
	 * 箭塔
	 */
	public static final int ArrowTower = 0x01;

	/**
	 * 闪电塔
	 */
	public static final int LightTower = ArrowTower+1;

	/**
	 * 减速塔
	 */
	public static final int SlowDownTower = LightTower +1;

	/**
	 * 魔法塔
	 */
	public static final int MagicTower = SlowDownTower + 1;

	public static String getTowerImage(int id) {
		String path = "";
		switch (id) {
		case ArrowTower:
			path = AssertRes.images.tower.tower3_png;
			break;
		case LightTower:
			path = AssertRes.images.tower.gun_tower21_png;
			break;
		case SlowDownTower:
			path = AssertRes.images.tower.gun_tower11_png;
			break;
		case MagicTower:
			path = AssertRes.images.tower.gun_tower31_png;
			break;
		default:
			break;
		}
		return path;
	}
}

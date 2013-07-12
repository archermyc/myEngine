package com.towergame.map;

import com.assetsres.AssertRes;
import com.towergame.enemy.Path;

public class MapConfig {

	private static int map1[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 1, 1, 1, 1, 0, 1, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private static int map2[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 0, 1, 1, 0 }, { 0, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static Path getPath(int mapLevel) {
		Path path = null;
		switch (mapLevel) {
		case 1:
			path = new Path(6).start(0,64).to(155, 64).to(155, 340).to(730, 340)
					.to(730, 143).to(930, 143);
			break;

		case 2:
			path = new Path(10).start(0, 85).to(145, 85).to(145, 270).to(380, 270)
					.to(380, 465).to(570, 465)
					.to(570, 15).to(760, 15)
					.to(760, 355).to(960, 355);
			break;

		default:
			path = new Path(5).to(155, 64).to(200, 340).to(730, 330)
					.to(730, 143).to(930, 143);
			break;
		}
		return path;
	}

	public static int[][] getMapData(int mapLevel) {
		int[][] map = null;
		switch (mapLevel) {
		case 1:
			map = map1;
			break;
		case 2:
			map = map2;
			break;

		default:
			map = map1;
			break;
		}
		return map;
	}
	
	public static String getMapBackground(int mapLevel) {
		String mapFile = "";
		switch (mapLevel) {
		case 1:
			mapFile = AssertRes.images.map.map00_png;
			break;
		case 2:
			mapFile = AssertRes.images.map.map01_png;
			break;
		default:
			break;
		}
		return mapFile;
	}
}

package com.towergame.tower;

import com.assetsres.AssertRes;
import com.engine.texture.TextureCache;
import com.game.data.GameResConfig;

public class TowerFacrory {
	public static GLTower createTower(int id) {
		GLTower tower = null;
		switch (id) {
		case TowerConfig.ArrowTower:
			tower = new GLArrowTower();
			tower.setBackground(TextureCache.getInstance().get(
					AssertRes.images.tower.tower3_png));
			tower.setAttackR(175);
			break;
		case TowerConfig.LightTower:
			tower = new GLLightTower(GameResConfig.getTowerRes(2));
			tower.setSize(96, 96);
			tower.setAttackR(250);
			break;
		case TowerConfig.MagicTower:
			tower = new GLMagicTower(GameResConfig.getTowerRes(3));
			tower.setSize(96, 96);
			tower.setAttackR(250);
			break;
		case TowerConfig.SlowDownTower:
			tower = new GLSlowdownTower(GameResConfig.getTowerRes(1));
			tower.setSize(96, 96);
			tower.setAttackR(250);
			break;

		default:
			return tower;
		}
		return tower;
	}
}

package com.towergame.enemy;

import java.util.Random;

import com.assetsres.AssertRes;
import com.towergame.enemy.EnemyData.EnemyEffect;

public class EnemyFactory {
	public static GLEnemy CreatEnemy(int id) {
		GLEnemy enemy = null;
		switch (id) {
		case EnemyConfig.Gnome:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy11_png);
			enemy.setEnemyData(new EnemyData(200, 3.0f, 10, EnemyEffect.none));
			enemy.setDirection(GLSprite.ANIM_RIGHT);
			return enemy;

		case EnemyConfig.Footman:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy20_png);
			enemy.setHP(800);
			enemy.setSpeed(1);
			enemy.setAnimTime(0.25f);
			enemy.setDirection(GLSprite.ANIM_RIGHT);
			return enemy;

		default:
			break;
		}
		return null;
	}

	/**
	 * 创建随机敌人,测试
	 * 
	 * @return
	 */
	public static GLEnemy CreatRandomEnemy() {
		int id = new Random().nextInt(7);
		GLEnemy enemy = null;
//		id = 3;
		switch (id) {
		case EnemyConfig.Gnome:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy11_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Gnome));
			break;

		case EnemyConfig.Footman:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy20_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Footman));

			break;
		case EnemyConfig.Robber:
			enemy = new GLRobberEnemy(AssertRes.images.enemy.enemy22_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Robber));
			enemy.setSize((int) (enemy.getWidth() * 1.3 / 1.5),
					(int) (enemy.getHeight() * 1.3 / 1.5));
			break;
		case EnemyConfig.Ghoul:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy1_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Ghoul));
			enemy.setSize((int) (enemy.getWidth() * 1.3 / 1.5),
					(int) (enemy.getHeight() * 1.3 / 1.5));
			break;
		case EnemyConfig.Sakya:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy15_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Sakya));
			break;
		case EnemyConfig.Kanon:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy2_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Kanon));
			break;
		case EnemyConfig.Aiolia:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy13_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Aiolia));
			break;

		default:
			enemy = new GLEnemy(AssertRes.images.enemy.enemy11_png);
			enemy.setEnemyData(EnemyConfig.getEnemyData(EnemyConfig.Gnome));
		}
		enemy.setSize((int) (enemy.getWidth() * 1.5),
				(int) (enemy.getHeight() * 1.5));
		enemy.setDirection(GLSprite.ANIM_RIGHT);
		enemy.setAnimTime(0.2f);
		return enemy;
	}
}

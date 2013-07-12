package com.towergame.enemy;

import com.towergame.enemy.EnemyData.EnemyEffect;

public class EnemyConfig {
	/**
	 * 地精
	 */
	public static final int Gnome = 0x001;

	/**
	 * 步兵, 穿着厚重的铠甲 物理减免50%伤害 但惧怕魔法打击
	 */
	public static final int Footman = Gnome + 1;

	/**
	 * 盗贼
	 */
	public static final int Robber = Footman + 1;

	/**
	 * 食尸鬼
	 */
	public static final int Ghoul = Robber + 1;
	
	/**
	 * 沙迦
	 */
	public static final int Sakya = Ghoul + 1;
	
	/**
	 * 加隆
	 */
	public static final int Kanon = Sakya + 1;
	
	/**
	 * 艾欧里亚
	 * 特性，30%概率闪避
	 */
	public static final int Aiolia = Kanon + 1;
	
	

	public static EnemyData getEnemyData(int id) {
		EnemyData data = null;
		switch (id) {
		case Gnome:
			data = new EnemyData(200, 3.0f, 10, EnemyEffect.none);
			break;
		case Footman:
			data = new EnemyData(800, 1.0f, 20, EnemyEffect.jianshang);
			break;
		case Robber:
			data = new EnemyData(400, 4.0f, 15, EnemyEffect.dodge);
			break;
		case Ghoul:
			data = new EnemyData(500, 3f, 15, EnemyEffect.jianshang);
			break;
		case Sakya:
			data = new EnemyData(600, 3f, 20, EnemyEffect.dodge);
			break;
		case Kanon:
			data = new EnemyData(500, 2f, 20, EnemyEffect.dodge);
			break;
		case Aiolia:
			data = new EnemyData(300, 2f, 20, EnemyEffect.dodge);
			break;
		default:
			data = new EnemyData();
		}
		return data;
	}
}

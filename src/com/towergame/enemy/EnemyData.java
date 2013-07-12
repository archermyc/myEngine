package com.towergame.enemy;

public class EnemyData {
	public int maxHP; // 最大生命值
	public int curHP;// 当前生命值
	public float speed = 3f; // 默认移动速度
	public float speedOrg = 3f; // 原始移动速度
	public int money; // 被杀死获得的金钱

	public enum EnemyEffect {
		/**
		 * 没有特效
		 */
		none,
		/**
		 * 减伤
		 */
		jianshang,
		
		/**
		 * 30%闪避
		 */
		dodge,
	}

	public EnemyEffect effect;

	public EnemyData(int hp, float speed, int money, EnemyEffect effect) {
		maxHP = hp;
		curHP = hp;
		this.speed = speed;
		this.speedOrg = speed;
		this.money = money;
		this.effect = effect;
		
	}

	public EnemyData() {
		maxHP = 200;
		curHP = 200;
		speed = 3.0f;
		speedOrg = 3.0f;
		money = 5;
		effect = EnemyEffect.none;
	}
}

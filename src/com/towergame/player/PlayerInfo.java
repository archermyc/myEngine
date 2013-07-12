package com.towergame.player;

import com.towergame.GameState;
import com.towergame.GameState.GameStates;

public class PlayerInfo {
	private static PlayerInfo mInstance;

	private int mHP = 3;
	private int mMoney = 200;

	public static PlayerInfo getInstance() {
		if (mInstance == null) {
			mInstance = new PlayerInfo();
		}
		return mInstance;
	}

	public void setHP(int HP) {
		this.mHP = HP;
		PlayerView.getInstance().refresh();
		if (mHP <= 0) {
			if (GameState.getInstance().getGameStates() != GameStates.gameLost) {
				GameState.getInstance().setGameState(GameStates.gameLost);
			}
		}
	}

	public int getHP() {
		return mHP;
	}

	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
		PlayerView.getInstance().refresh();
	}

	public void reset() {
		mHP = 20;
		mMoney = 200;
	}
}

package com.towergame;

import java.util.ArrayList;

public class GameState {
	private ArrayList<GameStateChangeListener> listeners = new ArrayList<GameStateChangeListener>();

	public enum GameStates {
		gameStart, gameRuning, gameWin, gameLost, gamePause, gameResume,
	}

	static GameState instance;
	GameStates mStates;

	public interface GameStateChangeListener {
		public void onGameStateChange(GameStates state);
	}

	public static GameState getInstance() {
		if (instance == null) {
			instance = new GameState();
		}
		return instance;
	}

	public void addGameStateChangeListener(GameStateChangeListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	public void setGameState(GameStates states) {
		mStates = states;
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onGameStateChange(mStates);
			}
		}
	}

	public GameStates getGameStates() {
		return mStates;
	}
}

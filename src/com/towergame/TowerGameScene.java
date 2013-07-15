package com.towergame;

import com.assetsres.AssertRes;
import com.engine.entity.FPS;
import com.engine.ui.GLImage;
import com.engine.ui.GLNode;
import com.engine.ui.GLScene;
import com.engine.uiex.GLBanner;
import com.example.myc.engine.GameApp;
import com.towergame.GameState.GameStateChangeListener;
import com.towergame.GameState.GameStates;
import com.towergame.dialog.GLWinDialog;
import com.towergame.enemy.EnemyFactory;
import com.towergame.enemy.EnemyManager;
import com.towergame.enemy.GLEnemy;
import com.towergame.enemy.GLEnemy.EnemyState;
import com.towergame.enemy.GLEnemyTips;
import com.towergame.map.GLMap;
import com.towergame.map.MapConfig;
import com.towergame.player.PlayerInfo;
import com.towergame.player.PlayerView;
import com.towergame.tower.GLTower;
import com.towergame.tower.GLTowerMenu;
import com.towergame.tower.TowerManager;

/**
 * 
 * @author YCMO
 * 
 */
public class TowerGameScene extends GLScene {
	GLEnemyTips tips;
	float time = 0.0f;

	GLMap map;
	GLImage bg;

	float times = 0;

	boolean test = false;

	int mGameLevel = 1;

	public boolean pauseFlag = false;

	GameStateChangeListener gameStateChangeListener;

	public TowerGameScene() {
		// layout(0, 0, GameApp.getInstnce().ScreenWidth,
		// GameApp.getInstnce().ScreenHeight);
		gameStateChangeListener = new GameStateChangeListener() {
			public void onGameStateChange(GameStates state) {
				// TODO Auto-generated method stub
				switch (state) {
				case gameStart:
					onGameStart();
					break;
				case gameWin:
					break;
				case gameLost:
					onGameLost();
					break;
				case gamePause:
					break;
				case gameResume:
					break;
				default:
					break;
				}
			}
		};
		GLNode root = initRoot();
		root.setScale(GameApp.getInstnce().ScreenScale,
				GameApp.getInstnce().ScreenScale);
	}

	private void onGameLost() {
		// pause();
		GLBanner banner = new GLBanner();
		banner.show();

	}

	private void onGameStart() {
		reset();
	}

	private void init() {
		GLNode towerRoot = TowerManager.getInstance().getRoot();
		addChild(towerRoot);

		GLNode enemyRoot = EnemyManager.getInstance().getRoot();
		addChild(enemyRoot);

	}

	/**
	 * 加载场景所需资源
	 */
	protected void load() {
		if (!test) {
			bg = new GLImage(AssertRes.images.map.map00_png);
			bg.setPos(0, 0);
			// bg.setSize(GameApp.getInstnce().ScreenWidth,
			// GameApp.getInstnce().ScreenHeight);
			bg.setSize(GameApp.getInstnce().defaultWidth,
					GameApp.getInstnce().defaultHeight);

			addChild(bg);

			map = GLMap.getInstance();
			addChild(map);
			map.setMapData(MapConfig.getMapData(mGameLevel));
			tips = new GLEnemyTips();
			tips.setDrawLevel(11);
			addChild(tips);
			tips.show();

			GLTowerMenu tMenu = new GLTowerMenu();
			addChild(tMenu);

			init();

			final GLMenu menu = GLMenu.getInstance();
			menu.setPos(0, 0);
			menu.setDrawLevel(10);
			menu.setVisibility(INVISIBLE);
			addChild(menu);

			addPlayView();

		} else {
		}
	}

	public void addPlayView() {
		addChild(PlayerView.getInstance().getRoot());
	}

	public void testFunc() {
		GLImage image = new GLImage(AssertRes.images.ui.win_again_button_png);
		image.setPos(150, 150);
		addChild(image);

		image.showDrawRect();
	}

	@Override
	protected void update() {
		if (!pauseFlag) {

			if (!test) {
				TowerManager towerManager = TowerManager.getInstance();
				for (int i = 0; i < towerManager.getChildCount(); i++) {
					GLTower tower = towerManager.getChild(i);
					if (tower.isCanAttack()) {
						EnemyManager enemyManager = EnemyManager.getInstance();
						for (int j = 0; j < enemyManager.getChildCount(); j++) {
							GLEnemy enemy = enemyManager.getChild(j);
							if (enemy.getState() == EnemyState.Death) {
								continue;
							}
							if (tower.isIntoAttackRange(enemy)) { // 进入炮台攻击范围
								tower.startAttack(enemy); // 执行攻击之后跳出循环，执行下一个炮台动作判断
								break;
							}
						}
					}
				}

				times += FPS.getDeltaTime();
				if (times >= 2.0f) {
					autoAddEnemy();
					times = 0;
				}
			}

			super.update();
		}

	}

	public void autoAddEnemy() {
		EnemyManager enemyManager = EnemyManager.getInstance();
		if (enemyManager.getChildCount() <= 0
				|| enemyManager.getChildCount() <= 1) {
			GLEnemy enemy = EnemyFactory.CreatRandomEnemy();
			enemy.setMovePath(MapConfig.getPath(mGameLevel));
			enemy.moveByPath();
			enemyManager.addEnemy(enemy);
		}
	}

	@Override
	protected void loadEnd() {
		// TODO Auto-generated method stub
		GameState.getInstance().setGameState(GameStates.gameStart);
		GameState.getInstance().addGameStateChangeListener(
				gameStateChangeListener);
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		if (test) {
			testFunc();
		}
	}

	public void next() {
		mGameLevel = 2;
		reset();
	}

	private void reset() {
		EnemyManager.getInstance().removeAllEnemy();
		TowerManager.getInstance().removeAllTower();
		GLMap.getInstance().reset();
		PlayerInfo.getInstance().reset();
		PlayerView.getInstance().refresh();
		map.setMapData(MapConfig.getMapData(mGameLevel));
		// map.setOffset(0, 0);
		bg.setFile(MapConfig.getMapBackground(mGameLevel));
		bg.setSize(GameApp.getInstnce().ScreenWidth,
				GameApp.getInstnce().ScreenHeight);
	}

	public void last() {
		// GLBanner banner = new GLBanner();
		// banner.show();
		GLWinDialog.staticShow();
	}

	public void pause() {
		// FPS.setCoefficient(0);
		pauseFlag = !pauseFlag;
	}
}

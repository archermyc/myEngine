package com.towergame.map;

import javax.microedition.khronos.opengles.GL11;

import android.graphics.Color;
import android.view.MotionEvent;

import com.assetsres.AssertRes;
import com.engine.entity.GLRootView;
import com.engine.entity.GLView;
import com.engine.texture.ResourceTexture;
import com.engine.texture.TextureCache;
import com.engine.ui.GLNode;
import com.example.myc.engine.R;
import com.towergame.GLMenu;
import com.towergame.player.PlayerInfo;
import com.towergame.tower.GLTower;
import com.towergame.tower.TowerConfig;
import com.towergame.tower.TowerFacrory;
import com.towergame.tower.TowerManager;

public class GLMap extends GLNode {
	int w = 960;
	int h = 640;
	int tW = 96;
	int tH = 96;
	int yOffset = -40;
	int xOffset = 30;
	// 0表示不可放置炮塔， 1 表示可以放置炮塔， 2表示炮塔已经存在
	private int map[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 1, 1, 1, 1, 0, 1, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	boolean drawFlag = false;

	ResourceTexture image;
	int imageID = 0;
	int imagex = 0;
	int imagey = 0;

	private static GLMap mInstance;

	public static GLMap getInstance() {
		if (mInstance == null) {
			mInstance = new GLMap();
		}
		return mInstance;
	}

	private GLMap() {
		layout(0, 0, w, h);
		image = TextureCache.getInstance().get(
				AssertRes.images.tower.tower3_png);

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(GLView view, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (GLMenu.getInstance().isShowing()) {
						GLMenu.getInstance().hide();
						return false;
					}
					drawFlag = true;
					imagex = (int) event.getX() - 48;
					imagey = (int) event.getY() - 48;

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					drawFlag = false;
					imagex = (int) event.getX();
					imagey = (int) event.getY();
					if (checkTower(imagex, imagey)) {
						addTower(imagex, imagey);
					}
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					imagex = (int) event.getX() - 48;
					imagey = (int) event.getY() - 48;
				}
				return true;
			}
		});
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		// TODO Auto-generated method stub
		if (drawFlag) {
			for (int i = 1; i <= h / tH; i++) {
				root.drawLine(0, i * tH + yOffset, 960, i * tH + yOffset,
						Color.WHITE);
			}
			for (int i = 1; i <= w / tH; i++) {
				root.drawLine(i * tW + xOffset, 0, i * tW + xOffset, 640,
						Color.WHITE);
			}
			drawMap(root);
			if (image != null) {
				image.draw(root, imagex, imagey, 96, 96);
			}
		}

		super.render(root, gl);
	}

	public void setDrawingTower(int id) {
		imageID = id;
		String path = TowerConfig.getTowerImage(id);
		image = TextureCache.getInstance().get(path);
	}

	private void drawMap(GLRootView root) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				if (map[i][j] == 1) {
					root.drawColor(getBlockX(j), getBlockY(i), 96, 96,
							Color.argb(50, 0, 255, 0));
				}
			}
		}
	}

	/**
	 * 获得某一块的X坐标
	 * 
	 * @param index
	 * @return
	 */
	public int getBlockX(int index) {
		return tW * index + xOffset;
	}

	/**
	 * 获得某一块的Y坐标
	 * 
	 * @param index
	 * @return
	 */
	public int getBlockY(int index) {
		return tH * index + yOffset;
	}

	public void upMapdata(int col, int row, int v) {
		map[row][col] = v;
	}

	/**
	 * 检查炮台是否可以放在该位置
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkTower(int x, int y) {
		int col = (x - xOffset) / tW;
		int row = (y - yOffset) / tH;
		row = row >= 7 ? 6 : row;
		if (map[row][col] == 1) {
			System.err.println("行:" + row + " 列:" + col);
			return true;
		}
		return false;
	}

	private void addTower(int x, int y) {
		int col = (x - xOffset) / tW;
		int row = (y - yOffset) / tH;
		int tx = getBlockX(col);
		int ty = getBlockY(row);

		GLTower tower = TowerFacrory.createTower(imageID);
		if (tower != null) {
			int money = PlayerInfo.getInstance().getMoney();
			int leftmoney = money - tower.getMoney();
			if (leftmoney >= 0) {
				tower.setPos(tx, ty);
				TowerManager.getInstance().addTower(tower);
				upMapdata(col, row, 2);
				PlayerInfo.getInstance().setMoney(leftmoney);
			}
		}
	}

	public boolean deleteTower(GLTower tower) {
		int x = (int) tower.getX();
		int y = (int) tower.getY();
		int col = (x - xOffset) / tW;
		int row = (y - yOffset) / tH;
		if (map[row][col] == 2) {
			map[row][col] = 1;
			return true;
		}
		return false;
	}

	public void setMapData(int[][] data) {
		int[][] temp = new int[7][10];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				temp[i][j] = data[i][j];
			}
		}
		map = temp;
	}

	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
	}

	public void reset() {
		int tt[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
				{ 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 1, 1, 1, 1, 0, 1, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		map = tt;
	}
}

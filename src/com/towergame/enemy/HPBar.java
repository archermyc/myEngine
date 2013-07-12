package com.towergame.enemy;

import android.graphics.Color;

import com.engine.entity.GLRootView;

public class HPBar {
	int height;
	int hpMax;
	int length;

	public HPBar(int hp, int lenght, int height) {
		this.hpMax = hp;
		this.length = lenght;
		this.height = height;

	}

	public void drawHpBar(GLRootView root, int x, int y, int hp) {
		if (hp > 0) {
			float scale = ((float) (hpMax - hp) / hpMax);
			if (scale < 0) {
				return;
			}
			if (scale < 1.0f) {
				root.drawColor(x, y, (int) (length * scale), height,
						Color.argb(125, 255, 0, 0));
				root.drawColor((int) (length * scale), y,
						(int) (length - length * scale), height,
						Color.argb(125, 0, 255, 0));
			} else {
				root.drawColor(0, y, length, height,
						Color.argb(125, 0, 255, 0));
			}
		}

	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpOrg) {
		this.hpMax = hpOrg;
	}
}

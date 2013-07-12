package com.towergame.player;

import com.engine.ui.GLImage;
import com.engine.ui.GLNode;
import com.engine.ui.GLText;
import com.engine.uiex.GLNum;
import com.example.myc.engine.R;

public class PlayerView {
	private static PlayerView mInstance;
	private GLNode mRoot;

	GLImage money;
	GLNum moneyText;
	GLImage hp;
	GLText hpText;

	public static PlayerView getInstance() {
		if (mInstance == null) {
			mInstance = new PlayerView();
		}
		return mInstance;
	}

	private PlayerView() {
		initView();
	}

	private void initView() {
		mRoot = new GLNode();
		mRoot.setPos(700, 20);
		mRoot.setSize(180, 60);
		money = new GLImage(R.drawable.money_icon);
		money.setPos(0, 0);
//		moneyText = new GLText(PlayerInfo.getInstance().getMoney() + "");
//		moneyText.setTextColor(Color.YELLOW);
//		moneyText.setPos(money.getX() + money.getWidth() + 5, 0);
		moneyText = new GLNum();
		moneyText.setNum(PlayerInfo.getInstance().getMoney());
		moneyText.setPos(money.getX() + money.getWidth(), 2);
		mRoot.addChild(moneyText);
		mRoot.addChild(money);
		hp = new GLImage(R.drawable.life_icon);
		hp.setPos(moneyText.getX() + moneyText.getWidth() + 10, 0);
		mRoot.addChild(hp);
		hpText = new GLText(PlayerInfo.getInstance().getHP() + "");
		hpText.setPos(hp.getX() + hp.getWidth() + 5, 0);
		mRoot.addChild(hpText);
	}

	public void refresh() {
		moneyText.setNum(PlayerInfo.getInstance().getMoney());
		hp.setPos(moneyText.getX() + moneyText.getWidth() + 10, 0);
		hpText.setPos(hp.getX() + hp.getWidth() + 5, 0);
		hpText.setText(PlayerInfo.getInstance().getHP() + "");
	}

	public GLNode getRoot() {
		return mRoot;
	}
}

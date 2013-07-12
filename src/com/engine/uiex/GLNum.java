package com.engine.uiex;

import java.util.ArrayList;

import org.json.JSONObject;

import com.engine.ui.GLNode;
import com.engine.ui.GLPackerImage;
import com.example.myc.engine.R;
import com.util.AssetsUtil;

/**
 * 拼图数字控件
 * @author YCMO
 *
 */
public class GLNum extends GLNode {
	JSONObject aJsonObject;
	public GLNum() {
		layout(0, 0, 0, 0);
		aJsonObject = AssetsUtil.getJson("json/num.json");
		loadNum(52);
	
	}

	public void setNum(int num) {
		clearComponents();
		loadNum(num);
	}

	private void loadNum(int num) {
		int x = 0;
		int h = 0;
		ArrayList<Integer> list = getNumList(num);
		for (int i = list.size() - 1; i >= 0; i--) {
			String name = "n" + list.get(i) + ".png";
			GLPackerImage image = new GLPackerImage(R.drawable.num);
			image.setPos(x, 0);
			image.setDrawRectByJsonFmt(aJsonObject, name);
			image.setSizeScale(1.3f);
			addChild(image);
			x += image.getWidth();
			h = image.getHeight();
		}
		setSize(x, h);
	}
	
	public ArrayList<Integer> getNumList(int num) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int n = num;
		while (n / 10 != 0) {
			int temp = n % 10;
			list.add(temp);
			n = n / 10;
		}
		list.add(n);
		return list;
	}

}

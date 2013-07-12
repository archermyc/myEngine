package com.towergame;

import javax.microedition.khronos.opengles.GL11;

import com.engine.entity.GLRootView;
import com.engine.texture.ResourceTexture;
import com.engine.ui.GLNode;

/**
 *
 * @author YCMO
 *
 */
public class GLtest extends GLNode {

	ResourceTexture mRes;

	public GLtest(int resid) {
		// TODO Auto-generated constructor stub
		mRes = new ResourceTexture(resid);
		mRes.getBitmap();
		layout(0, 0, mRes.getWidth(), mRes.getHeight());

	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {

		root.clipRect(0, 0, getWidth()/ 4, getHeight() / 4);
		mRes.draw(root, -getWidth()/ 4, 0, getWidth(), getHeight());
		super.render(root, gl);
	}
}

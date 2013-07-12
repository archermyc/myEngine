package com.engine.ui;

import javax.microedition.khronos.opengles.GL11;

import org.json.JSONException;
import org.json.JSONObject;

import com.engine.entity.GLRootView;
import com.engine.entity.MeasureHelper;
import com.engine.texture.ResourceTexture;
import com.engine.texture.Texture;
import com.engine.texture.TextureCache;

/**
 * 支持拼图，拼图的格式是json
 * @author YCMO
 *
 */
public class GLPackerImage extends GLNode{
	private ResourceTexture mIcon;
	protected Texture mBackground;
	
	/**
	 * 为了支持拼图，把多张图片用TexturePacker拼成一张图
	 */
	private JSONObject mJsonObject;
	private int sourceW = 0;
	private int sourceH = 0;
	private int sourceX = 0;
	private int sourceY = 0;
	private int frameW = 0;
	private int frameH = 0;
	float scale = 1.0f; // 为了缩放绘制大小
	

	public GLPackerImage(int resId) {
		mIcon = TextureCache.getInstance().get(resId);
		layout(0, 0, mIcon.getWidth(), mIcon.getHeight());
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		int height = 0;
		int width = 0;
		if (mIcon != null) {
			height = mIcon.getHeight();
			width = mIcon.getWidth();
		}
		new MeasureHelper(this).setPreferredContentSize(width, height).measure(
				widthSpec, heightSpec);
		super.onMeasure(widthSpec, heightSpec);
	}
	
	public void setDrawRectByJsonFmt(JSONObject jsonObject, String name) {
		try {
			mJsonObject = jsonObject.getJSONObject("frames").getJSONObject(name);
			frameW = jsonObject.getJSONObject("meta").getJSONObject("size").optInt("w");
			frameH = jsonObject.getJSONObject("meta").getJSONObject("size").optInt("h");
			sourceW = mJsonObject.getJSONObject("sourceSize").optInt("w");
			sourceH = mJsonObject.getJSONObject("sourceSize").optInt("h");
			sourceX = mJsonObject.getJSONObject("frame").optInt("x");
			sourceY = mJsonObject.getJSONObject("frame").optInt("y");
			setSize(sourceW, sourceH);
			setSizeScale(this.scale);
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSizeScale(float scale) {
		this.scale = scale;
		if (mJsonObject != null) {
			sourceW = (int)(sourceW*scale);
			sourceH = (int)(sourceH*scale);
			sourceX = (int)(sourceX *scale);
			sourceY = (int)(sourceY *scale);
			frameW  = (int)(frameW * scale);
			frameH  = (int)(frameH * scale);
			setSize(sourceW, sourceH);
		}else {
		}
	}
	
	@Override
	public void setSize(int w, int h) {
		// TODO Auto-generated method stub
		if (mJsonObject != null) {
			float sw = ((float)w)/ sourceW;
			float sh = ((float)h)/ sourceH;
			sourceW = (int)(sourceW*sw);
			sourceH = (int)(sourceH*sh);
			sourceX = (int)(sourceX *sw);
			sourceY = (int)(sourceY *sh);
			frameW  = (int)(frameW * sw);
			frameH  = (int)(frameH * sh);
		}
		super.setSize(w, h);
	}

	@Override
	protected void render(GLRootView root, GL11 gl) {
		if (mBackground != null) {
			mBackground.draw(root, 0, 0, getWidth(), getHeight());
		}
		if (mIcon != null) {
			if (mJsonObject != null) {
					root.clipRect(0, 0, sourceW, sourceH);
					mIcon.draw(root, -sourceX, -sourceY, frameW,frameH);
					root.clearClip();
			}else {
				mIcon.draw(root, 0, 0, (int)(getWidth()*scale), (int)(getHeight()*scale));
			}
			
		}
		super.render(root, gl);
	}
}

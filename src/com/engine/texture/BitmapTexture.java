/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 */

package com.engine.texture;


import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.engine.entity.GLOutOfMemoryException;
import com.engine.entity.GLRootView;
import com.engine.entity.Util;
import com.engine.ui.GLSceneManager;

public abstract class BitmapTexture extends BasicTexture {

    @SuppressWarnings("unused")
    private static final String TAG = "Texture";
    int[] textureId = new int[1];
    protected BitmapTexture() {
        super(null, 0, STATE_UNLOADED);
    }

    @Override
    public int getWidth() {
        if (mWidth == UNSPECIFIED) getBitmap();
        return mWidth;
    }

    @Override
    public int getHeight() {
        if (mWidth == UNSPECIFIED) getBitmap();
        return mHeight;
    }

    protected abstract Bitmap getBitmap();

    protected abstract void freeBitmap(Bitmap bitmap);

    private void uploadToGL(GL11 gl) throws GLOutOfMemoryException {
        Bitmap bitmap = getBitmap();
        int glError = GL10.GL_NO_ERROR;
        if (bitmap != null) {
            try {
                // Define a vertically flipped crop rectangle for
                // OES_draw_texture.
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] cropRect = {0,  height, width, -height};

                // Upload the bitmap to a new texture.
                gl.glGenTextures(1, textureId, 0);
                gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
                
                gl.glTexParameteriv(GL10.GL_TEXTURE_2D,
                        GL11Ext.GL_TEXTURE_CROP_RECT_OES, cropRect, 0);
                gl.glTexParameteri(GL10.GL_TEXTURE_2D,
                        GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
                gl.glTexParameteri(GL10.GL_TEXTURE_2D,
                        GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                        GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                        GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

                int widthExt = Util.nextPowerOf2(width);
                int heightExt = Util.nextPowerOf2(height);
                int format = GLUtils.getInternalFormat(bitmap);
                int type = GLUtils.getType(bitmap);

                mTextureWidth = widthExt;
                mTextureHeight = heightExt;
                gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, format,
                        widthExt, heightExt, 0, format, type, null);
                GLUtils.texSubImage2D(
                        GL10.GL_TEXTURE_2D, 0, 0, 0, bitmap, format, type);
            } finally {
                freeBitmap(bitmap);
            }
            if (glError == GL10.GL_OUT_OF_MEMORY) {
                throw new GLOutOfMemoryException();
            }
            if (glError != GL10.GL_NO_ERROR) {
                mId = 0;
                mState = STATE_UNLOADED;
                throw new RuntimeException(
                        "Texture upload fail, glError " + glError);
            } else {
                // Update texture state.
                mGL = gl;
                mId = textureId[0];
                mState = BasicTexture.STATE_LOADED;
            }
        } else {
            mState = STATE_ERROR;
            throw new RuntimeException("Texture load fail, no bitmap");
        }
    }

    @Override
	public boolean bind(GLRootView root, GL11 gl) {
        if (mState == BasicTexture.STATE_UNLOADED || mGL != gl) {
            mState = BasicTexture.STATE_UNLOADED;
            try {
                uploadToGL(gl);
            } catch (GLOutOfMemoryException e) {
                root.handleLowMemory();
                return false;
            }
        } else {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, getId());
        }
        return true;
    }
    
    @Override
    // 垃圾回收的时候会调用 可以用来删除纹理
    protected void finalize() throws Throwable {
    	// TODO Auto-generated method stub
    	Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				System.err.println("释放资源");
				deleteFromGL();
			}
		};
    	if (GLSceneManager.getInstance().getRootView()!= null) {
    		GLSceneManager.getInstance().getRootView().runInGLThread(runnable);
		}
    	super.finalize();
    }
    
   
}

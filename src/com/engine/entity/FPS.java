package com.engine.entity;



public class FPS {

	private static int fps = 0;
	private static int frames = 0;
	private static float lastTime;
	private static float spent;
	private static float deltaTime;
	
	private static float coefficient = 1.0f; // 系数 
	
	public static void init() {
		lastTime = System.nanoTime() / 1000000000f;
	}
	public static void calculateFPS() {
		float time = System.nanoTime() / 1000000000f;
		deltaTime = time - lastTime;
		spent += deltaTime;
		lastTime = time;
		frames++;
		if (spent >= 1.0f) {
			fps = frames;
			frames = 0;
			spent -= 1.0f;
		}
	}

	public static int getFPS() {
//		Log.e("FPS", "fps: " + fps);
		return fps;
		
	}
	
	public static void setCoefficient(float coefficient) {
		FPS.coefficient = coefficient;
	}
	public static float getCoefficient() {
		return coefficient;
	}
	
	public static float getDeltaTime() {
		return deltaTime * coefficient;
	}
	
	public static void drawFps(GLRootView root) {
//		StringTexture mText = StringTexture.newInstance(FPS.getFPS() + "", 24, Color.RED);
//		mText.draw(root, 20, 20);
//		root.requestRender();
	}
}

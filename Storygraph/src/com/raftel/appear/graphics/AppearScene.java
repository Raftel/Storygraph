package com.raftel.appear.graphics;

import java.util.ArrayList;

import android.graphics.Color;

public class AppearScene {

	public static final int INITIAL_COLOR = 0xFFFFFFFF;
	
	private ArrayList<AppearNode> mRenderNodeList;
	private int mBgColor;

	public AppearScene() {
		mRenderNodeList = new ArrayList<AppearNode>();
		mBgColor = INITIAL_COLOR;
	}

	public boolean addRenderNode(AppearNode root) {
		if (root != null)
			return mRenderNodeList.add(root);
		return false;
	}

	public boolean removeRenderNode(AppearNode root) {
		if (root != null)
			return mRenderNodeList.remove(root);
		return false;
	}

	public void removeAllRenderNodes() {
		mRenderNodeList.clear();
	}

	public ArrayList<AppearNode> getRenderNodeList() {
		return mRenderNodeList;
	}
	
	public int getBackgroundColor() {
		return mBgColor;
	}
	
	public void setBackgroundColor(int color) {
		mBgColor = color;
	}

	public void setBackgroundColor(int alpha, int red, int green, int blue) {
		// range = 0 ~ 255
		mBgColor = Color.argb(alpha, red, green, blue);
	}

	public void setBackgroundColor(float alpha, float red, float green, float blue) {
		// range = 0.0f ~ 1.0f
		mBgColor = Color.argb((int) (alpha * 255f), (int) (255f * red), (int) (255f * green), (int) (255f * blue));
	}
}

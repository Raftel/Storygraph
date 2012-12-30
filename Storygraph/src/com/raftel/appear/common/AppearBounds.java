package com.raftel.appear.common;

public class AppearBounds {
	private float mX= 0;
	private float mY = 0;
	private float mWidth = 0;
	private float mHeight = 0;		

	public AppearBounds(float x, float y, float width, float height) {
		mX= x;
		mY = y;
		mWidth = width;
		mHeight = height;		
	}

	public float getX() {
		return mX;
	}

	public float getY() {
		return mY;
	}

	public float getWidth() {
		return mWidth;
	}

	public float getHeight() {
		return mHeight;
	}
}

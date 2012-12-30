package com.raftel.appear.common;

public class AppearPoint {
	private float mX= 0;
	private float mY = 0;

	public AppearPoint(float x, float y) {
		mX= x;
		mY = y;
	}

	public float getX() {
		return mX;
	}

	public float getY() {
		return mY;
	}

	public void setX(float x) {
		mX = x;
	}

	public void setY(float y) {
		mY = y;
	}

	public void setXY(float x, float y) {
		mX = x;
		mY = y;
	}
}

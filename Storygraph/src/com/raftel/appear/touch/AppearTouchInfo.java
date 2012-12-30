package com.raftel.appear.touch;

import com.raftel.appear.common.AppearPoint;

public class AppearTouchInfo {
	private AppearTouchAction mTouchAction = AppearTouchAction.APPEAR_TOUCH_DOWN;
	private AppearPoint mTouchPoint = null;

	public static enum AppearTouchAction {APPEAR_TOUCH_DOWN, 
								APPEAR_TOUCH_MOVE, 
								APPEAR_TOUCH_UP,
								APPEAR_TOUCH_CANCEL;}
	
	public AppearTouchInfo() {
		mTouchPoint = new AppearPoint(0, 0);
	}

	public AppearTouchAction getTouchAction() {
		return mTouchAction;
	}

	public void setTouchAction(AppearTouchAction touchAction) {
		mTouchAction = touchAction;
	}

	public AppearPoint getTouchPoint() {
		return mTouchPoint;
	}
}

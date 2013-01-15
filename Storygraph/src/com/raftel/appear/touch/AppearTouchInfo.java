package com.raftel.appear.touch;

import com.raftel.appear.common.AppearPoint;

public class AppearTouchInfo {
	private TouchAction mTouchAction = TouchAction.TOUCH_DOWN;
	private AppearPoint mTouchPoint = null;

	public static enum TouchAction {TOUCH_DOWN, 
								TOUCH_MOVE, 
								TOUCH_UP,
								TOUCH_CANCEL;}
	
	public AppearTouchInfo() {
		mTouchPoint = new AppearPoint(0, 0);
	}

	public TouchAction getTouchAction() {
		return mTouchAction;
	}

	public void setTouchAction(TouchAction touchAction) {
		mTouchAction = touchAction;
	}

	public AppearPoint getTouchPoint() {
		return mTouchPoint;
	}
}

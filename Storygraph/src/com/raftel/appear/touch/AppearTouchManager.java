package com.raftel.appear.touch;

import java.util.ArrayList;

import com.raftel.appear.system.AppearUX;
import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.touch.AppearTouchGraph;

public class AppearTouchManager {
	private AppearUX mAppearUX= null;
	private AppearTouchGraph mTouchGraph = null;

	public AppearTouchManager(AppearUX appearUX) {
		mAppearUX = appearUX;
	}

	public void setTouchGraph(AppearTouchGraph touchGraph) {
		mTouchGraph = touchGraph;
	}

	public AppearTouchGraph getTouchGraph() {
		return mTouchGraph;
	}

	public boolean onTouch(AppearTouchInfo touchInfo) {
		AppearTouchTarget touchTarget = getTouchGraph().getRootTarget();
		return onTouchToTarget(touchTarget, touchInfo);
	}

	private boolean callTouchHandler(AppearTouchHandler touchHandler, AppearTouchInfo touchInfo) {
		if (touchHandler == null) {
			return false;
		}

		switch (touchInfo.getTouchAction()) {
		case APPEAR_TOUCH_DOWN:
			return touchHandler.onTouchDown(touchInfo);

		case APPEAR_TOUCH_MOVE:
			return touchHandler.onTouchMove(touchInfo);

		case APPEAR_TOUCH_UP:
			return touchHandler.onTouchUp(touchInfo);

		case APPEAR_TOUCH_CANCEL:
			return touchHandler.onTouchCancel(touchInfo);
		}

		return false;
	}

	private boolean onTouchToTarget(AppearTouchTarget touchTarget, AppearTouchInfo touchInfo) {
		boolean touchHandled = false;
		AppearTouchHandler touchHandler = touchTarget.getTouchHandler();
		if (touchHandler != null)  {
			touchHandled = callTouchHandler(touchHandler, touchInfo);
			if (touchHandled == true) {
				return true;
			}
		}

		ArrayList<AppearTouchTarget> childTouchTargetList = touchTarget.getChildTargetList();
		for (int i = 0; i < childTouchTargetList.size(); i++) {
			AppearTouchTarget childTouchTarget = childTouchTargetList.get(i);
			touchHandled = onTouchToTarget(childTouchTarget, touchInfo);
			if (touchHandled == true) {
				return true;
			}
		}		
		return false;
	}
}

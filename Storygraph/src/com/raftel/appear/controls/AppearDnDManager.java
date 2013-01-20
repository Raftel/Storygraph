package com.raftel.appear.controls;

import com.raftel.appear.common.AppearObject;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.controls.AppearDnDSource;
import com.raftel.appear.controls.AppearDnDTarget;

public class AppearDnDManager extends AppearTouchHandler {
	public AppearDnDManager() {
	}

	public boolean startDragging(AppearDnDSource source) {
		return false;
	}
	
	public boolean cancelDragging() {
		return false;
	}

	public boolean IsDragging() {
		return false;
	}
	
	public AppearObject getCurrentSourceObject() {
		return null;
	}
	
	public AppearObject getCurrentTargetObject() {
		return null;
	}

	public boolean onTouchDown(AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchMove(AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchUp(AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchCancel(AppearTouchInfo touchInfo) {
		return false;
	}

	private AppearDnDTarget lookupDnDTarget(float x, float y) {
		return null;
	}
}

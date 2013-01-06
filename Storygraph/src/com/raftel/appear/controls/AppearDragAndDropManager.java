package com.raftel.appear.controls;

import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;

public class AppearDragAndDropManager extends AppearTouchHandler {
	public AppearDragAndDropManager() {
		// FIXME : Need a dummy AppearModel.....?
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
}

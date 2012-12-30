package com.raftel.appear.controls;

import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.graphics.expand.AppearRenderTarget;

public class AppearDragAndDrop {
	public class __DragAndDropTouchHandler extends AppearTouchHandler {
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

	__DragAndDropTouchHandler mDragAndDropTouchHandler = null;
	AppearTouchTarget mRootTouchTarget = null;
	AppearRenderTarget mRootRenderTarget = null;

	public AppearDragAndDrop(AppearTouchTarget rootTouchTarget, AppearRenderTarget rootRenderTarget) {
		if ((rootTouchTarget != null) && (rootRenderTarget != null)) {
			mDragAndDropTouchHandler = new __DragAndDropTouchHandler();
			if (mDragAndDropTouchHandler != null) {
				rootTouchTarget.setTouchHandler(mDragAndDropTouchHandler);
			}
			mRootTouchTarget = rootTouchTarget;
			mRootRenderTarget = rootRenderTarget;
			// FIXME : Need a dummy AppearModel.....?
		}
	}
}

package com.raftel.appear.touch;

import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.touch.AppearTouchHandler;

public class AppearTouchGraph {
	private AppearTouchTarget mRootTarget = null;

	public AppearTouchGraph() {
	}

	public void setRootTarget(AppearTouchTarget rootTarget) {
		mRootTarget = rootTarget;
		mRootTarget.setGraph(this);
	}
	
	public AppearTouchTarget getRootTarget() {
		return mRootTarget;
	}

	public AppearTouchTarget newTouchTarget(AppearTouchTarget parent, AppearTouchHandler handler) {
		AppearTouchTarget parentTouchTarget = null;
		AppearTouchTarget newTouchTarget = null;

		if ((parent != null) && (handler != null) && (parent.getGraph() == this)) {
			parentTouchTarget = parent.getTouchHandler().getTargetOnTouchGraph(this);
			if (parentTouchTarget != null) {
				newTouchTarget = new AppearTouchTarget();
				if (newTouchTarget != null ) {
					newTouchTarget.setTouchHandler(handler);
					parentTouchTarget.addChild(newTouchTarget);
				}
			}
		}

		return newTouchTarget;
	}

	public boolean deleteTouchTarget(AppearTouchHandler handler) {
		AppearTouchTarget thisTouchTarget = handler.getTargetOnTouchGraph(this);
		if (thisTouchTarget != null) {
			AppearTouchTarget parentTouchTarget = thisTouchTarget.getParent();
			if (parentTouchTarget != null) {
				parentTouchTarget.removeChild(thisTouchTarget); 
				return true;
			}
		}
		return false;
	}
}


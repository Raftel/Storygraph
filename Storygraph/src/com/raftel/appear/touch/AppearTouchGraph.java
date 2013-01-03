package com.raftel.appear.touch;

import java.util.ArrayList;

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
		AppearTouchTarget newTouchTarget = null;

		if ((parent != null) && (handler != null) && (parent.getGraph() == this)) {
			if (parent != null) {
				newTouchTarget = new AppearTouchTarget();
				if (newTouchTarget != null ) {
					newTouchTarget.setTouchHandler(handler);
					parent.addChildTarget(newTouchTarget);
				}
			}
		}

		return newTouchTarget;
	}

	public boolean deleteTouchTarget(AppearTouchHandler handler) {
		if (handler != null) {
			AppearTouchTarget touchTarget = handler.getTargetOnGraph(this);
			if (touchTarget != null) {
				AppearTouchTarget parentTouchTarget = touchTarget.getParentTarget();
				if (parentTouchTarget != null) {
					parentTouchTarget.removeChildTarget(touchTarget); 

					ArrayList<AppearTouchTarget> childrenList = touchTarget.getChildTargetList();
					if (childrenList != null) {
						for (int i = 0; i < childrenList.size(); i++) {
							childrenList.get(i).setParentTarget(parentTouchTarget);
						}
					}
					return true;
				}
			}
		}

		return false;
	}
}


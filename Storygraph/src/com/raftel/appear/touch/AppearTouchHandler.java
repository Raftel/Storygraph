package com.raftel.appear.touch;

import java.util.ArrayList;

import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.touch.AppearTouchGraph;

public abstract class AppearTouchHandler {
	private ArrayList<AppearTouchTarget> mTargetList = null;

	public AppearTouchHandler() {
		mTargetList = new ArrayList<AppearTouchTarget>();
	}

	public boolean addTarget(AppearTouchTarget target) {
		if (target == null)
			return false;

		mTargetList.add(target);
		return true;
	}

	public boolean removeTarget(AppearTouchTarget target) {
		if (target == null)
			return false;

		mTargetList.remove(target);
		return true;
	}

	public void removeAllTarget() {
		mTargetList.clear();
	}

	public AppearTouchTarget getTargetOnGraph(AppearTouchGraph touchGraph) {
		for (int i = 0; i < mTargetList.size(); i++) {
			AppearTouchTarget touchTarget = mTargetList.get(i);
			if (touchTarget.getGraph() == touchGraph) {
				return touchTarget;
			}
		}
		return null;
	}
	
	public abstract boolean onTouchDown(AppearTouchInfo touchInfo);
	public abstract boolean onTouchMove(AppearTouchInfo touchInfo);
	public abstract boolean onTouchUp(AppearTouchInfo touchInfo);
	public abstract boolean onTouchCancel(AppearTouchInfo touchInfo);
}

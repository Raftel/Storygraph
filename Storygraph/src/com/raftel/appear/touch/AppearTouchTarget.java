package com.raftel.appear.touch;

import java.util.ArrayList;

import com.raftel.appear.touch.AppearTouchGraph;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchTarget;

public class AppearTouchTarget {
	private AppearTouchGraph mGraph = null;
	private AppearTouchTarget mParent = null;
	private ArrayList<AppearTouchTarget> mChildList = null;
	private AppearTouchHandler mTouchHandler = null;

	public AppearTouchTarget() {
		mChildList = new ArrayList<AppearTouchTarget>();
	}

	public AppearTouchGraph getGraph() {
		return mGraph;
	}

	public void setGraph(AppearTouchGraph graph) {
		mGraph = graph;
	}

	public AppearTouchTarget getParentTarget() {
		return mParent;
	}

	public void setParentTarget(AppearTouchTarget parent) {
		if (mParent != parent) {
			mParent = parent;
			setGraph(mParent.getGraph());
		}
	}

	public ArrayList<AppearTouchTarget> getChildTargetList() {
		return mChildList;
	}

	public boolean addChildTarget(AppearTouchTarget child) {
		if (child == null)
			return false;

		AppearTouchTarget prevParent = child.getParentTarget();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChildTarget(child);
		}

		mChildList.add(child);
		child.setParentTarget(this);

		return true;
	}

	public boolean removeChildTarget(AppearTouchTarget child) {
		if (child == null)
			return false;

		if (child.getParentTarget() != this)
			return false;

		mChildList.remove(child);
		child.setParentTarget(null);

		return true;
	}

	public void removeAllChildTarget() {
		for (int i = 0; i < mChildList.size(); i++) {
			mChildList.get(i).setParentTarget(null);
		}
		mChildList.clear();
	}

	public void setTouchHandler(AppearTouchHandler touchHandler) {
		if (mTouchHandler == touchHandler) {
			return;
		}

		if (mTouchHandler != null) {
			mTouchHandler.removeTarget(this);
		}

		touchHandler.addTarget(this);
		mTouchHandler = touchHandler;
	}
	public AppearTouchHandler getTouchHandler() {
		return mTouchHandler;
	}
}

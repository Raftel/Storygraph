package com.raftel.appear.touch;

import java.util.ArrayList;

import com.raftel.appear.touch.AppearTouchGraph;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchTarget;

public class AppearTouchTarget {
	private AppearTouchGraph mGraph = null;
	private AppearTouchTarget mParent = null;
	private ArrayList<AppearTouchTarget> mChildrenList = null;
	private AppearTouchHandler mTouchHandler = null;

	public AppearTouchTarget() {
		mChildrenList = new ArrayList<AppearTouchTarget>();
	}

	public AppearTouchGraph getGraph() {
		return mGraph;
	}

	public void setGraph(AppearTouchGraph graph) {
		mGraph = graph;
	}

	public AppearTouchTarget getParent() {
		return mParent;
	}

	public void setParent(AppearTouchTarget parent) {
		if (mParent != parent) {
			mParent = parent;
			setGraph(mParent.getGraph());
		}
	}

	public ArrayList<AppearTouchTarget> getChildrenList() {
		return mChildrenList;
	}

	public boolean addChild(AppearTouchTarget child) {
		if (child == null)
			return false;

		AppearTouchTarget prevParent = child.getParent();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChild(child);
		}

		mChildrenList.add(child);
		child.setParent(this);

		return true;
	}

	public boolean removeChild(AppearTouchTarget child) {
		if (child == null)
			return false;

		if (child.getParent() != this)
			return false;

		mChildrenList.remove(child);
		child.setParent(null);

		return true;
	}

	public void removeChildren() {
		for (int i = 0; i < mChildrenList.size(); i++) {
			mChildrenList.get(i).setParent(null);
		}
		mChildrenList.clear();
	}

	public void setTouchHandler(AppearTouchHandler touchHandler) {
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

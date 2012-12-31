package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import com.raftel.appear.graphics.AppearNode;

public class AppearRenderTarget extends AppearNode {
	private AppearRenderGraph mGraph = null;
	private AppearRenderTarget mParent = null;
	private ArrayList<AppearRenderTarget> mChildrenList = null;
	private AppearRenderModel mRenderModel = null;


	public AppearRenderTarget() {
		mChildrenList = new ArrayList<AppearRenderTarget>();
	}

	public void setGraph(AppearRenderGraph graph) {
		mGraph = graph;
	}

	public AppearRenderGraph getGraph() {
		return mGraph;
	}

	public AppearRenderTarget getParent() {
		return mParent;
	}

	public void setParent(AppearRenderTarget parent) {
		if (mParent != parent) {
			mParent = parent;
			setGraph(mParent.getGraph());

			// For Graphics
			if (parent == null) {
				setParent(null);
			}
			else {
				setParent(parent);
			}
		}
	}

	public ArrayList<AppearRenderTarget> getChildrenList() {
		return mChildrenList;
	}

	public boolean addChild(AppearRenderTarget child) {
		if (child == null)
			return false;

		AppearRenderTarget prevParent = child.getParent();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChild(child);
		}

		mChildrenList.add(child);
		child.setParent(this);

		// For Graphics
		addChild(child);

		return true;
	}

	public boolean removeChild(AppearRenderTarget child) {
		if (child == null)
			return false;

		if (child.getParent() != this)
			return false;

		mChildrenList.remove(child);
		child.setParent(null);

		// For Graphics
		removeChild(child);

		return true;
	}

	public void removeChildren() {
		for (int i = 0; i < mChildrenList.size(); i++) {
			mChildrenList.get(i).setParent(null);
		}
		mChildrenList.clear();

		// For Graphics
		removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		mRenderModel = renderModel;
	}
	
	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}
}

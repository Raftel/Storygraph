package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.AppearNode;

public class AppearRenderTarget extends AppearNode {
	private AppearRenderGraph mGraph = null;
	private AppearRenderTarget mParentTarget = null;
	private ArrayList<AppearRenderTarget> mChildList = null;
	private AppearRenderModel mRenderModel = null;

	public AppearRenderTarget() {
		mChildList = new ArrayList<AppearRenderTarget>();
	}

	public void setGraph(AppearRenderGraph graph) {
		mGraph = graph;
	}

	public AppearRenderGraph getGraph() {
		return mGraph;
	}

	public AppearRenderTarget getParentTarget() {
		return mParentTarget;
	}

	public void setParentTarget(AppearRenderTarget parent) {
		if (mParentTarget != parent) {
			mParentTarget = parent;
			setGraph(mParentTarget.getGraph());

			// For Graphics
			if (parent == null) {
				super.setParent(null);
			} else {
				super.setParent(parent);
			}
		}
	}

	public ArrayList<AppearRenderTarget> getChildTargetList() {
		return mChildList;
	}

	public boolean addChildTarget(AppearRenderTarget child) {
		if (child == null)
			return false;

		AppearRenderTarget prevParent = child.getParentTarget();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChildTarget(child);
		}
		
		child.setParentTarget(this);

		mChildList.add(child);
		super.addChild(child);

		return true;
	}

	public boolean removeChildTarget(AppearRenderTarget child) {
		if (child == null)
			return false;

		if (child.getParentTarget() != this)
			return false;
		
		child.setParentTarget(null);

		mChildList.remove(child);
		super.removeChild(child);

		return true;
	}

	public void removeAllChildTarget() {
		for (int i = 0; i < mChildList.size(); i++) {
			mChildList.get(i).setParentTarget(null);
		}

		mChildList.clear();
		super.removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		mRenderModel = renderModel;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}

	@Override
	@Deprecated
	public AppearNode getParent() {
		return null;
	}

	@Override
	@Deprecated
	public void setParent(AppearNode parent) {
	}

	@Override
	@Deprecated
	public boolean addChild(AppearNode node) {
		return false;
	}

	@Override
	@Deprecated
	public boolean removeChild(AppearNode node) {
		return false;
	}

	@Override
	@Deprecated
	public boolean removeAllChild() {
		return false;
	}
}

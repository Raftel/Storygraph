package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearModel;

public class AppearRenderTarget {
	private AppearRenderGraph mGraph = null;
	private AppearRenderTarget mParent = null;
	private ArrayList<AppearRenderTarget> mChildrenList = null;
	private AppearRenderModel mRenderModel = null;

	// For Graphics
	public AppearModel mAppearModel_ForGraphics = null;

	public AppearRenderTarget() {
		mChildrenList = new ArrayList<AppearRenderTarget>();

		// For Graphics
		mAppearModel_ForGraphics = new AppearModel();
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
				mAppearModel_ForGraphics.setParent(null);
			}
			else {
				mAppearModel_ForGraphics.setParent(parent.mAppearModel_ForGraphics);
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
		mAppearModel_ForGraphics.addChild(child.mAppearModel_ForGraphics);

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
		mAppearModel_ForGraphics.removeChild(child.mAppearModel_ForGraphics);

		return true;
	}

	public void removeChildren() {
		for (int i = 0; i < mChildrenList.size(); i++) {
			mChildrenList.get(i).setParent(null);
		}
		mChildrenList.clear();

		// For Graphics
		mAppearModel_ForGraphics.removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		mRenderModel = renderModel;

		// For Graphics
		mAppearModel_ForGraphics.setMaterial(renderModel.getMaterial());
		mAppearModel_ForGraphics.setMesh(renderModel.getMesh());
	}
	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}
}

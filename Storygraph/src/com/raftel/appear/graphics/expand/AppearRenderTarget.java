package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearNode;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.AppearMesh;

public class AppearRenderTarget extends AppearModel {
	private AppearRenderGraph mGraph = null;
	private AppearRenderTarget mParentTarget = null;
	private ArrayList<AppearRenderTarget> mChildTargetList = null;
	private AppearRenderModel mRenderModel = null;

	public AppearRenderTarget() {
		mChildTargetList = new ArrayList<AppearRenderTarget>();
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
		}
	}

	public ArrayList<AppearRenderTarget> getChildTargetList() {
		return mChildTargetList;
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
		mChildTargetList.add(child);

		// For Graphics
		super.addChild(child);

		return true;
	}

	public boolean removeChildTarget(AppearRenderTarget child) {
		if (child == null)
			return false;

		if (child.getParentTarget() != this)
			return false;
		
		child.setParentTarget(null);
		mChildTargetList.remove(child);

		// For Graphics
		super.removeChild(child);

		return true;
	}

	public void removeAllChildTarget() {
		for (int i = 0; i < mChildTargetList.size(); i++) {
			mChildTargetList.get(i).setParentTarget(null);
		}

		mChildTargetList.clear();
		
		// For Graphics
		super.removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		if (mRenderModel == renderModel) {
			return;
		}

		if (mRenderModel != null) {
			mRenderModel.removeTarget(this);
			super.removeChild(mRenderModel);
		}

		if (renderModel != null) {
			renderModel.addTarget(this);
			ArrayList<AppearNode> childList = super.getChildList();
			childList.add(0, renderModel);
		}

		mRenderModel = renderModel;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}


	// AppearModel
	@Override
	public void setMesh(AppearMesh mesh) {
		if (mRenderModel != null) {
			mRenderModel.setMesh(mesh);
		}
		else {
			super.setMesh(mesh);
		}
 	}

	@Override
	public AppearMesh getMesh() {
		if (mRenderModel != null) {
			return mRenderModel.getMesh();
		}
		else {
			return super.getMesh();
		}
	}

	@Override
	public void setMaterial(AppearMaterial material) {
		if (mRenderModel != null) {
			mRenderModel.setMaterial(material);
		}
		else {
			super.setMaterial(material);
		}
	}

	@Override
	public AppearMaterial getMaterial() {
		if (mRenderModel != null) {
			return mRenderModel.getMaterial();
		}
		else {
			return super.getMaterial();
		}
	}

	@Override
	public void setTexture(Bitmap bitmap) {
		if (mRenderModel != null) {
			mRenderModel.setTexture(bitmap);
		}
		else {
			super.setTexture(bitmap);
		}
	}

	@Override
	public void setTexture(Bitmap bitmap, boolean doRecycle) {
		if (mRenderModel != null) {
			mRenderModel.setTexture(bitmap, doRecycle);
		}
		else {
			super.setTexture(bitmap, doRecycle);
		}
	}

	@Override
	public boolean isTextureLoaded() {
		if (mRenderModel != null) {
			return mRenderModel.isTextureLoaded();
		}
		else {
			return super.isTextureLoaded();
		}
	}

	@Override
	protected float isPicked(float[] matVP, float[] inNear, float[] inFar) {
		return super.isPicked(matVP, inNear, inFar);
	}

	// AppearNode
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

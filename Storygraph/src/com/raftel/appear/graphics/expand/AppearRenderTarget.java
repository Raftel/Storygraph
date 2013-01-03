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
	private ArrayList<AppearRenderTarget> mChildList = null;
	private AppearRenderModel mRenderModel = null;

	public AppearRenderTarget() {
		mChildList = new ArrayList<AppearRenderTarget>();

		// Create the default model.
		mRenderModel = new AppearRenderModel();
		if (mRenderModel != null) {
			mRenderModel.addTarget(this);
		}
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
			super.setParent(parent);
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
		mChildList.remove(child);

		// For Graphics
		super.removeChild(child);

		return true;
	}

	public void removeAllChildTarget() {
		for (int i = 0; i < mChildList.size(); i++) {
			mChildList.get(i).setParentTarget(null);
		}

		mChildList.clear();
		
		// For Graphics
		super.removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		if (mRenderModel == renderModel) {
			return;
		}

		if (mRenderModel != null) {
			mRenderModel.removeTarget(this);
		}

		renderModel.addTarget(this);			
		mRenderModel = renderModel;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}


	// AppearModel
	@Override
	public void setMesh(AppearMesh mesh) {
		super.setMesh(mesh);
	}

	@Override
	public AppearMesh getMesh() {
		return super.getMesh();
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

	@Override
	public float[] getPosition() {
		return super.getPosition();
	}

	@Override
	public void setTranslation(float x, float y, float z) {
		super.setTranslation(x, y, z);
	}

	@Override
	public void setTranslationX(float x) {
		super.setTranslationX(x);
	}

	@Override
	public void setTranslationY(float y) {
		super.setTranslationY(y);
	}

	@Override
	public void setTranslationZ(float z) {
		super.setTranslationZ(z);
	}

	@Override
	public float[] getTranslation() {
		return super.getTranslation();
	}

	@Override
	public void addTranslation(float x, float y, float z) {
		super.addTranslation(x, y, z);
	}

	@Override
	public void setRotation(float x, float y, float z) {
		super.setRotation(x, y, z);
	}

	@Override
	public void setRotationX(float x) {
		super.setRotationX(x);
	}

	@Override
	public void setRotationY(float y) {
		super.setRotationY(y);
	}

	@Override
	public void setRotationZ(float z) {
		super.setRotationZ(z);
	}

	@Override
	public float[] getRotation() {
		return super.getRotation();
	}

	@Override
	public void addRotation(float x, float y, float z) {
		super.addRotation(x, y, z);
	}

	@Override
	public void setScale(float x, float y, float z) {
		super.setScale(x, y, z);
	}

	@Override
	public void setScaleX(float x) {
		super.setScaleX(x);
	}

	@Override
	public void setScaleY(float y) {
		super.setScaleY(y);
	}

	@Override
	public void setScaleZ(float z) {
		super.setScaleZ(z);
	}

	@Override
	public float[] getScale() {
		return super.getScale();
	}

	@Override
	public void addScale(float x, float y, float z) {
		super.addScale(x, y, z);
	}

	@Override
	public void setMatrixDirty() {
		super.setMatrixDirty();
	}

	@Override
	public float[] getModelMatrix() {
		return super.getModelMatrix();
	}
	
	@Override
	public float[] getViewMatrix() {
		return super.getViewMatrix();
	}

	@Override
	public boolean isVisible() {
		return super.isVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public PickedNode findPickedNode(PickedNode pickedNode, float[] matVP, float[] inNear, float[] inFar) {
		return super.findPickedNode(pickedNode, matVP, inNear, inFar);
	}

	@Override
	public boolean isPickable() {
		return super.isPickable();
	}

	@Override
	public void setPickable(boolean pickable) {
		super.setPickable(pickable);
	}
}

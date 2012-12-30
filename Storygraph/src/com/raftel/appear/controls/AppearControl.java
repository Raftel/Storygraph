package com.raftel.appear.controls;

import java.util.ArrayList;

import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchGraph;
import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;

public class AppearControl {
	private AppearControl mParent = null;
	private ArrayList<AppearControl> mChildrenList = null;
	private AppearTouchGraph mTouchGraph = null;
	private AppearTouchHandler mTouchHandler = null;
	private AppearRenderGraph mRenderGraph = null;
	private AppearRenderModel mRenderModel = null;

	public AppearControl() {
		mChildrenList = new ArrayList<AppearControl>();
	}

	public AppearControl getParent() {
		return mParent;
	}

	public void setParent(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (mParent != parent) {
			if ((syncToTouchGraph == true) && (mTouchHandler != null) && (mTouchGraph != null)) {
				if (parent != null) {
					AppearTouchTarget parentTarget = parent.getTouchHandler().getTargetOnTouchGraph(mTouchGraph);
					mTouchGraph.newTouchTarget(parentTarget, mTouchHandler);
				}
				mTouchGraph.deleteTouchTarget(mTouchHandler);
			}
			
			if ((syncToRenderGraph == true) && (mRenderModel != null) && (mRenderGraph != null)) {
				if (parent != null) {
					AppearRenderTarget parentTarget = parent.getRenderModel().getTargetOnRenderGraph(mRenderGraph);
					mRenderGraph.newRenderTarget(parentTarget, mRenderModel);
				}
				mRenderGraph.deleteRenderTarget(mRenderModel);
			}

			mParent = parent;
		}
	}

	public void setParent(AppearControl parent) {
		setParent(parent, false, false);		
	}

	public ArrayList<AppearControl> getChildrenList() {
		return mChildrenList;
	}

	public boolean addChild(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null)
			return false;

		AppearControl prevParent = child.getParent();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChild(child, false, false);
		}

		mChildrenList.add(child);
		child.setParent(this, false, false);

		AppearTouchGraph childTouchGraph = child.getTouchGraph();
		AppearTouchHandler childTouchHandler = child.getTouchHandler();
		if ((syncToTouchGraph == true) && (childTouchHandler != null) && (childTouchGraph != null)) {
			childTouchGraph.deleteTouchTarget(childTouchHandler);
			AppearTouchTarget parentTarget = this.getTouchHandler().getTargetOnTouchGraph(mTouchGraph);
			mTouchGraph.newTouchTarget(parentTarget, childTouchHandler);
		}

		AppearRenderGraph childRenderGraph = child.getRenderGraph();
		AppearRenderModel childRenderModel = child.getRenderModel();
		if ((syncToRenderGraph == true) && (childRenderModel != null) && (childRenderGraph != null)) {
			childRenderGraph.deleteRenderTarget(childRenderModel);
			AppearRenderTarget parentTarget = this.getRenderModel().getTargetOnRenderGraph(mRenderGraph);
			mRenderGraph.newRenderTarget(parentTarget, childRenderModel);
		}

		return true;
	}

	public boolean addChild(AppearControl child) {
		return addChild(child, false, false);		
	}

	public boolean removeChild(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null)
			return false;

		if (child.getParent() != this)
			return false;

		mChildrenList.remove(child);
		child.setParent(null, syncToTouchGraph, syncToRenderGraph);

		return true;
	}

	public boolean removeChild(AppearControl child) {
		return removeChild(child, false, false);
	}

	public void removeChildren(boolean syncToTouchGraph, boolean syncToRenderGraph) {
		for (int i = 0; i < mChildrenList.size(); i++) {
			mChildrenList.get(i).setParent(null, syncToTouchGraph, syncToRenderGraph);
		}
		mChildrenList.clear();
	}

	public void removeChildren() {
		removeChildren(false, false);
	}
	
	public void setTouchHandler(AppearTouchHandler touchHandler) {
		mTouchHandler = touchHandler;
	}

	public AppearTouchHandler getTouchHandler() {
		return mTouchHandler;
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		mRenderModel = renderModel;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}

	public void setTouchGraph(AppearTouchGraph touchGraph) {
		mTouchGraph = touchGraph;
	}

	public AppearTouchGraph getTouchGraph() {
		return mTouchGraph;
	}

	public void setRenderGraph(AppearRenderGraph renderGraph) {
		mRenderGraph = renderGraph;
	}

	public AppearRenderGraph getRenderGraph() {
		return mRenderGraph;
	}
}

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
	private AppearTouchHandler mTouchHandler = null;
	private AppearRenderModel mRenderModel = null;
	private AppearTouchGraph mTouchGraph = null;
	private AppearRenderGraph mRenderGraph = null;

	public AppearControl() {
		mChildrenList = new ArrayList<AppearControl>();
	}

	public AppearControl getParentControl() {
		return mParent;
	}

	public void setParentControl(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (mParent != parent) {
			mParent = parent;
		}

		if ((syncToTouchGraph == true) && (mTouchHandler != null)) {
			if (parent != null) {
				AppearTouchTarget parentTarget = getParentTouchTarget();
				if (parentTarget != null) {
					AppearTouchGraph parentGraph = parentTarget.getGraph();
					if (parentGraph != null) {
						parentGraph.newTouchTarget(parentTarget, mTouchHandler);
						mTouchGraph = parentGraph;
					}
				}
			}
			else {
				mTouchGraph.deleteTouchTarget(mTouchHandler);
				mTouchGraph = null;
			}
		}
		
		if ((syncToRenderGraph == true) && (mRenderModel != null)) {
			if (parent != null) {
				AppearRenderTarget parentTarget = getParentRenderTarget();
				if (parentTarget != null) {
					AppearRenderGraph parentGraph = parentTarget.getGraph();
					if (parentGraph != null) {
						parentGraph.newRenderTarget(parentTarget, mRenderModel);
						mRenderGraph = parentGraph;
					}
				}
			}
			else {
				mRenderGraph.deleteRenderTarget(mRenderModel);
				mRenderGraph = null;
			}
		}
	}

	public void setParentControl(AppearControl parent) {
		setParentControl(parent, true, true);		
	}

	public ArrayList<AppearControl> getChildControlList() {
		return mChildrenList;
	}

	public boolean addChildControl(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null) {
			return false;
		}

		AppearControl prevParent = child.getParentControl();
		if (prevParent == this) {
			return true;
		}

		if (prevParent != null) {
			prevParent.removeChildControl(child, syncToTouchGraph, syncToRenderGraph);
		}

		mChildrenList.add(child);
		child.setParentControl(this, syncToTouchGraph, syncToRenderGraph);

		return true;
	}

	public boolean addChildControl(AppearControl child) {
		return addChildControl(child, true, true);		
	}

	public boolean removeChildControl(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null)
			return false;

		if (child.getParentControl() != this)
			return false;

		mChildrenList.remove(child);
		child.setParentControl(null, syncToTouchGraph, syncToRenderGraph);

		return true;
	}

	public boolean removeChildControl(AppearControl child) {
		return removeChildControl(child, true, true);
	}

	public void removeAllChildControl(boolean syncToTouchGraph, boolean syncToRenderGraph) {
		for (int i = 0; i < mChildrenList.size(); i++) {
			mChildrenList.get(i).setParentControl(null, syncToTouchGraph, syncToRenderGraph);
		}
		mChildrenList.clear();
	}

	public void removeAllChildControl() {
		removeAllChildControl(true, true);
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

	private AppearTouchTarget getAssociatedTouchTarget() {
		if ((mTouchGraph != null) && (mTouchHandler != null)) {
			return mTouchHandler.getTargetOnGraph(mTouchGraph);
		}
		
		return null;
	}

	private AppearRenderTarget getAssociatedRenderTarget() {
		if ((mRenderGraph != null) && (mRenderModel != null)) {
			return mRenderModel.getTargetOnGraph(mRenderGraph);
		}
	
		return null;
	}

	private AppearTouchTarget getParentTouchTarget() {
		AppearTouchGraph parentTouchGraph = null;
		AppearTouchHandler parentTouchHandler = null;
		AppearControl parent = mParent;
		while (parent != null) {
			parentTouchGraph = parent.getTouchGraph();
			parentTouchHandler = parent.getTouchHandler();
			if ((parentTouchGraph != null) && (parentTouchHandler != null)) {
				return parentTouchHandler.getTargetOnGraph(parentTouchGraph);
			}

			parent = parent.getParentControl();
		}
		
		return null;
	}
	
	private AppearRenderTarget getParentRenderTarget() {
		AppearRenderGraph parentRenderGraph = null;
		AppearRenderModel parentRenderModel = null;
		AppearControl parent = mParent;
		while (parent != null) {
			parentRenderGraph = parent.getRenderGraph();
			parentRenderModel = parent.getRenderModel();
			if ((parentRenderGraph != null) && (parentRenderModel != null)) {
				return parentRenderModel.getTargetOnGraph(parentRenderGraph);
			}

			parent = parent.getParentControl();
		}
		
		return null;
	}
}

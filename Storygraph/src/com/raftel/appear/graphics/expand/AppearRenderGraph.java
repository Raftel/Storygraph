package com.raftel.appear.graphics.expand;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.graphics.AppearNode;

public class AppearRenderGraph {
	private AppearRenderTarget mRootTarget = null;

	// For Graphics
	public AppearNode mRenderNode = null;
	
	public AppearRenderGraph() {
		mRenderNode = new AppearNode();
	}

	public void setRootTarget(AppearRenderTarget rootTarget) {
		mRootTarget = rootTarget;
		mRootTarget.setGraph(this);

		// For Graphics
		mRenderNode.addChild(rootTarget);
	}
	
	public AppearRenderTarget getRootTarget() {
		return mRootTarget;
	}

	public AppearRenderTarget newRenderTarget(AppearRenderTarget parent, AppearRenderModel model) {
		AppearRenderTarget parentRenderTarget = null;
		AppearRenderTarget newRenderTarget = null;

		if ((parent != null) && (model != null) && (parent.getGraph() == this)) {
			parentRenderTarget = parent.getRenderModel().getTargetOnRenderGraph(this);
			if (parentRenderTarget != null) {
				newRenderTarget = new AppearRenderTarget();
				if (newRenderTarget != null ) {
					newRenderTarget.setRenderModel(model);
					parentRenderTarget.addChild(newRenderTarget);
				}
			}
		}

		return newRenderTarget;
	}

	public boolean deleteRenderTarget(AppearRenderModel model) {
		AppearRenderTarget thisRenderTarget = model.getTargetOnRenderGraph(this);
		if (thisRenderTarget != null) {
			AppearRenderTarget parentRenderTarget = thisRenderTarget.getParent();
			if (parentRenderTarget != null) {
				parentRenderTarget.removeChild(thisRenderTarget); 
				return true;
			}
		}
		return false;
	}
}

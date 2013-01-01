package com.raftel.appear.graphics.expand;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.graphics.AppearNode;

public class AppearRenderGraph extends AppearNode {
	private AppearRenderTarget mRootTarget = null;

	public AppearRenderGraph() {
	}

	public void setRootTarget(AppearRenderTarget rootTarget) {
		mRootTarget = rootTarget;
		mRootTarget.setGraph(this);

		// For Graphics
		addChild(rootTarget.mAppearModel_ForGraphics);
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

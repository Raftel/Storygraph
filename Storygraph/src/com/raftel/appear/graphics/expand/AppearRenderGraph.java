package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

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

		super.addChild(rootTarget);
	}
	
	public AppearRenderTarget getRootTarget() {
		return mRootTarget;
	}

	public AppearRenderTarget newRenderTarget(AppearRenderTarget parent, AppearRenderModel model) {
		AppearRenderTarget newRenderTarget = null;

		if ((parent != null) && (model != null) && (parent.getGraph() == this)) {
			if (parent != null) {
				newRenderTarget = new AppearRenderTarget();
				if (newRenderTarget != null ) {
					newRenderTarget.setRenderModel(model);
					parent.addChildTarget(newRenderTarget);
				}
			}
		}

		return newRenderTarget;
	}

	public boolean deleteRenderTarget(AppearRenderModel model) {
		if (model != null) {
			AppearRenderTarget renderTarget = model.getTargetOnGraph(this);
			if (renderTarget != null) {
				AppearRenderTarget parentRenderTarget = renderTarget.getParentTarget();
				if (parentRenderTarget != null) {
					parentRenderTarget.removeChildTarget(renderTarget); 

					ArrayList<AppearRenderTarget> childrenList = renderTarget.getChildTargetList();
					if (childrenList != null) {
						for (int i = 0; i < childrenList.size(); i++) {
							childrenList.get(i).setParentTarget(parentRenderTarget);
						}
					}
					return true;
				}
			}
		}

		return false;
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

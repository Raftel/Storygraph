package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearModel;

public class AppearRenderModel extends AppearModel {
	private ArrayList<AppearRenderTarget> mTargetList = null;

	public AppearRenderModel() {
		mTargetList = new ArrayList<AppearRenderTarget>();
	}

	public boolean addTarget(AppearRenderTarget target) {
		if (target == null)
			return false;

		mTargetList.add(target);
		return true;
	}

	public boolean removeTarget(AppearRenderTarget target) {
		if (target == null)
			return false;

		mTargetList.remove(target);
		return true;
	}

	public void removeAllTarget() {
		mTargetList.clear();
	}

	public AppearRenderTarget getTargetOnRenderGraph(AppearRenderGraph renderGraph) {
		for (int i = 0; i < mTargetList.size(); i++) {
			AppearRenderTarget renderTarget = mTargetList.get(i);
			if (renderTarget.getGraph() == renderGraph) {
				return renderTarget;
			}
		}
		return null;
	}
}

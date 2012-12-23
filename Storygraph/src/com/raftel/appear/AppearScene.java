package com.raftel.appear;

import java.util.ArrayList;

public class AppearScene {

	private ArrayList<AppearNode> mRenderNodeList;

	public AppearScene() {
		mRenderNodeList = new ArrayList<AppearNode>();
	}

	public boolean addRenderNode(AppearNode root) {
		if (root != null)
			return mRenderNodeList.add(root);
		return false;
	}

	public boolean removeRenderNode(AppearNode root) {
		if (root != null)
			return mRenderNodeList.remove(root);
		return false;
	}

	public void removeAllRenderNodes() {
		mRenderNodeList.clear();
	}

	public ArrayList<AppearNode> getRenderNodeList() {
		return mRenderNodeList;
	}
}

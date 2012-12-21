package com.raftel.graphics.opengl;

import java.util.ArrayList;

public class RaftelGLScene {

	private ArrayList<RaftelGLNode> mRenderNodeList;

	public RaftelGLScene() {
		mRenderNodeList = new ArrayList<RaftelGLNode>();
	}

	public boolean addRenderNode(RaftelGLNode root) {
		if (root != null)
			return mRenderNodeList.add(root);
		return false;
	}

	public boolean removeRenderNode(RaftelGLNode root) {
		if (root != null)
			return mRenderNodeList.remove(root);
		return false;
	}

	public void removeAllRenderNodes() {
		mRenderNodeList.clear();
	}

	public ArrayList<RaftelGLNode> getRenderNodeList() {
		return mRenderNodeList;
	}
}

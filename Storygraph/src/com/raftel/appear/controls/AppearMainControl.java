package com.raftel.appear.controls;

import com.raftel.appear.graphics.AppearScene;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.system.AppearUX;
import com.raftel.appear.touch.AppearTouchGraph;
import com.raftel.appear.touch.AppearTouchTarget;

public class AppearMainControl extends AppearControl {
	private AppearUX mAppearUX= null;
	private AppearTouchTarget mRootTouchTarget = null;
	private AppearRenderTarget mRootRenderTarget = null;
	private AppearDragAndDrop mDragAndDrop = null;
	private AppearTouchGraph mMainTouchGraph = null;
	private AppearRenderGraph mMainRenderGraph = null;
	private AppearScene mMainScene = null;
	
	public AppearMainControl(AppearUX appearUX) {
		mAppearUX = appearUX;
		mRootTouchTarget = new AppearTouchTarget();
		mRootRenderTarget = new AppearRenderTarget();
		mDragAndDrop = new AppearDragAndDrop(mRootTouchTarget, mRootRenderTarget);

		mMainTouchGraph = new AppearTouchGraph();
		mMainTouchGraph.setRootTarget(mRootTouchTarget);
		mAppearUX.getTouchManager().setTouchGraph(mMainTouchGraph);

		mMainRenderGraph = new AppearRenderGraph();
		mMainRenderGraph.setRootTarget(mRootRenderTarget);
		mMainScene = new AppearScene();
		// For Graphics
		// Set the main render graph to the main scene.
		mMainScene.addRenderNode(mMainRenderGraph);
		mAppearUX.getRenderer().setScene(mMainScene);
	}

	public AppearTouchGraph getMainTouchGraph() {
		return mMainTouchGraph;
	}

	public AppearRenderGraph getMainRenderGraph() {
		return mMainRenderGraph;
	}

	public AppearScene getMainScene() {
		return mMainScene;
	}
}

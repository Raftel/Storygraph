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
	private AppearDragAndDropManager mDragAndDropManager = null;
	private AppearTouchGraph mMainTouchGraph = null;
	private AppearRenderGraph mMainRenderGraph = null;
	private AppearScene mMainScene = null;
	
	public AppearMainControl(AppearUX appearUX) {
		mAppearUX = appearUX;
		mDragAndDropManager = new AppearDragAndDropManager();
		setTouchHandler(mDragAndDropManager);

		mRootTouchTarget = new AppearTouchTarget();
		mRootTouchTarget.setTouchHandler(mDragAndDropManager);

		mRootRenderTarget = new AppearRenderTarget();
		mRootRenderTarget.setRenderModel(getRenderModel());

		mMainTouchGraph = new AppearTouchGraph();
		mMainTouchGraph.setRootTarget(mRootTouchTarget);
		setTouchGraph(mMainTouchGraph);
		mAppearUX.getTouchManager().setTouchGraph(mMainTouchGraph);

		mMainRenderGraph = new AppearRenderGraph();
		mMainRenderGraph.setRootTarget(mRootRenderTarget);
		setRenderGraph(mMainRenderGraph);
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

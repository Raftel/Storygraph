package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import android.util.Log;
import android.view.MotionEvent;

import com.raftel.appear.graphics.AppearNode;
import com.raftel.appear.graphics.AppearRenderer;
import com.raftel.appear.graphics.AppearScene;
import com.raftel.appear.graphics.AppearSurface;

public class AppearTouchableScene extends AppearScene {
	
	private final static float INVALID_TOUCH_POS = -9999;
	
	private AppearSurface mSurface;
	private AppearRenderer mRenderer;
	
	protected AppearNode mDragDropNode;
	protected AppearNode mTouchedNode;
	protected AppearNode mTouchedNodeParent;
	
	protected TouchInfo mTouchInfo;
	
	protected class TouchInfo {
		float mTouchDownX;
		float mTouchDownY;
		float mTouchPrevX;
		float mTouchPrevY;
	}
	
	public AppearTouchableScene(AppearSurface surface) {
		mSurface = surface;
		mRenderer = surface.getRenderer();
		
		mTouchInfo = new TouchInfo();
		mDragDropNode = new AppearNode();
	}
	
	public AppearNode pick(float x, float y) {

		float[] inNear = new float[4];
		float[] inFar = new float[4];

		inNear[0] = (x / (float) mSurface.getWidth()) * 2 - 1;
		inNear[1] = 1 - (y / (float) mSurface.getHeight()) * 2;
		inNear[2] = -1;
		inNear[3] = 1;

		inFar[0] = (x / (float) mSurface.getWidth()) * 2 - 1;
		inFar[1] = 1 - (y / (float) mSurface.getHeight()) * 2;
		inFar[2] = 1;
		inFar[3] = 1;

		AppearNode.PickedNode pickedNode = new AppearNode.PickedNode();

		ArrayList<AppearNode> renderNodeList = getRenderNodeList();
		for (int i = 0; i < renderNodeList.size(); i++) {
			renderNodeList.get(i).findPickedNode(pickedNode, mRenderer.getVPMatrix(), inNear, inFar);
		}

		return pickedNode.mNode;
	}
	
	public boolean onTouch(MotionEvent event) {
				
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			mTouchInfo.mTouchDownX = event.getX();
			mTouchInfo.mTouchDownY = event.getY();
			
			mTouchInfo.mTouchPrevX = event.getX();
			mTouchInfo.mTouchPrevY = event.getY();
			
			mTouchedNode = pick(mTouchInfo.mTouchDownX, mTouchInfo.mTouchDownY);
			
			if (mTouchedNode != null) {
				mTouchedNodeParent = mTouchedNode.getParent();
				mTouchedNode.setParent(mDragDropNode);
			}
			
			onTouchDown(event);
			break;
			
		case MotionEvent.ACTION_MOVE:
			if (mTouchedNode != null)
				mTouchedNode.addTranslation(event.getX() - mTouchInfo.mTouchPrevX, event.getY() - mTouchInfo.mTouchPrevY, 0.0f);
			
			onTouchMove(event);
			
			mTouchInfo.mTouchPrevX = event.getX();
			mTouchInfo.mTouchPrevY = event.getY();
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			onTouchUp(event);
			AppearNode newParentNode = pick(event.getX(), event.getY());
			
			if (mTouchedNode != null) {
				if (newParentNode != null && mTouchedNode != newParentNode)
					mTouchedNode.setParent(newParentNode);
				else 
					mTouchedNode.setParent(mTouchedNodeParent);
			}
			
			mTouchedNode = null;
			mTouchedNodeParent = null;
			
			mTouchInfo.mTouchDownX = INVALID_TOUCH_POS;
			mTouchInfo.mTouchDownY = INVALID_TOUCH_POS;
			mTouchInfo.mTouchPrevX = INVALID_TOUCH_POS;
			mTouchInfo.mTouchPrevY = INVALID_TOUCH_POS;
			break;
		}
		
		return true;
	}
	
	public boolean onTouchDown(MotionEvent event) {
		return false;
	}
	
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}
	
	public boolean onTouchUp(MotionEvent event) {
		return false;
	}
	
	public AppearSurface getSurface() {
		return mSurface;
	}
	
	public AppearRenderer getRenderer() {
		return mRenderer;
	}
}

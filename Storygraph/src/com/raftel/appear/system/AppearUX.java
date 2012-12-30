package com.raftel.appear.system;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.raftel.appear.common.AppearPoint;
import com.raftel.appear.system.AppearApplication;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.touch.AppearTouchManager;
import com.raftel.appear.graphics.AppearRenderer;
import com.raftel.appear.graphics.AppearSurface;
import com.raftel.appear.animation.AppearAnimationManager;

public class AppearUX implements AppearRenderer.Callback, OnTouchListener {
	AppearApplication mApplication = null;
	AppearTouchManager mTouchManager = null;
	AppearTouchInfo mTouchInfo = null;
	AppearSurface mGlSurface = null;
	AppearRenderer mGlRenderer = null;
	AppearAnimationManager mAnimationManager = null;
	static boolean mInitialized = false;

	public AppearUX(AppearApplication application) {
		mApplication = application;

		mTouchManager = new AppearTouchManager(this);
		mTouchInfo = new AppearTouchInfo();
		mAnimationManager = new AppearAnimationManager();
		mGlSurface = new AppearSurface(mApplication.getCurrentContext()); 
		mGlSurface.setOnTouchListener(this);

		mGlRenderer = mGlSurface.getRenderer();
		mGlRenderer.setCallback(this);
		mGlRenderer.setAnimManager(mAnimationManager);

		mApplication.getCurrentActivity().setContentView(mGlSurface);
	}

	// AppearRenderer.Callback
	public void onSurfaceCreated() {
		if (mInitialized == false) {
			mInitialized = true;
			mApplication.onInitialize(mGlSurface.getWidth(), mGlSurface.getHeight());
		}

		mApplication.onSurfaceCreated(mGlSurface.getWidth(), mGlSurface.getHeight());
	}

	public void onSurfaceChanged() {
		mApplication.onSurfaceChanged(mGlSurface.getWidth(), mGlSurface.getHeight());
	}
	
	public void onDrawFrame() {
	}

	//OnTouchListener
	public boolean onTouch(View v, MotionEvent event) {
		AppearPoint touchPoint = mTouchInfo.getTouchPoint();
		touchPoint.setX(event.getX());
		touchPoint.setY(event.getY());

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			mTouchInfo.setTouchAction(AppearTouchInfo.AppearTouchAction.APPEAR_TOUCH_DOWN);
			break;
			
		case MotionEvent.ACTION_MOVE:
			mTouchInfo.setTouchAction(AppearTouchInfo.AppearTouchAction.APPEAR_TOUCH_MOVE);
			break;
			
		case MotionEvent.ACTION_UP:
			mTouchInfo.setTouchAction(AppearTouchInfo.AppearTouchAction.APPEAR_TOUCH_UP);
			break;

		case MotionEvent.ACTION_CANCEL:
			mTouchInfo.setTouchAction(AppearTouchInfo.AppearTouchAction.APPEAR_TOUCH_CANCEL);
			break;
		}
	
		return mTouchManager.onTouch(mTouchInfo);
	}

	public AppearTouchManager getTouchManager() {
		return mTouchManager;
	}

	public AppearRenderer getRenderer() {
		return mGlRenderer;
	}
	
	public AppearSurface getSurface() {
		return mGlSurface;
	}

	public AppearAnimationManager getAnimationManager() {
		return mAnimationManager;
	}
}

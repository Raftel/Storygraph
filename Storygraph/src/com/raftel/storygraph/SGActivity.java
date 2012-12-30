package com.raftel.storygraph;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.raftel.appear.common.AppearBounds;
import com.raftel.appear.system.AppearUX;
import com.raftel.appear.system.AppearApplication;
import com.raftel.appear.controls.AppearMainControl;
import com.raftel.storygraph.scenebrowser.SGSceneBrowser;

import com.raftel.storygraph.R;

public class SGActivity extends Activity implements AppearApplication, OnTouchListener{
	private AppearUX mAppearUX = null;
	private AppearMainControl mMainControl = null;
	private SGSceneBrowser mSceneBrowser = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAppearUX = new AppearUX(this);
//		mAppearUX.getSurface().setOnTouchListener(this);
		
		setContentView(mAppearUX.getSurface());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sg, menu);
		return true;
	}


	// AppearApp
	public boolean onInitialize(int width, int height) {
		// Bind to AppearUX the SGSceneBrowser
		mMainControl = new AppearMainControl(mAppearUX);

		mSceneBrowser = new SGSceneBrowser();
		AppearBounds sceneBrowserBounds = new AppearBounds(0, 0, width, height);

		mSceneBrowser.setBounds(sceneBrowserBounds);
		mMainControl.addChild(mSceneBrowser);
		mMainControl.getMainScene().getRenderNodeList().get(0).addChild(mSceneBrowser.getRenderModel().getModel());

		Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_earth);
		Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.test_book);
		mSceneBrowser.addScene(bitmap1);
		mSceneBrowser.addScene(bitmap2);
		mSceneBrowser.addScene(bitmap1);
		mSceneBrowser.addScene(bitmap2);

		return true;
	}

	public boolean onSurfaceCreated(int width, int height) {
		AppearBounds bounds = new AppearBounds(0, 0, (float)width, (float)height);
		mSceneBrowser.setBounds(bounds);
		return true;
	}

	public boolean onSurfaceChanged(int width, int height) {
		AppearBounds bounds = new AppearBounds(0, 0, (float)width, (float)height);
		mSceneBrowser.setBounds(bounds);
		return true;
	}
	
	public Activity getCurrentActivity() {
		return this;
	}
	
	public Context getCurrentContext() {
		return this;
	}

	public boolean onTouch(View v, MotionEvent event) {
		return mAppearUX.onTouch(v, event);
	}
}

/*
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import com.raftel.appear.animation.AppearAnimationManager;
import com.raftel.appear.graphics.AppearRenderer;
import com.raftel.appear.graphics.AppearSurface;

import com.raftel.storygraph.scenebrowser.SGSceneBrowser;

public class SGActivity extends Activity implements AppearRenderer.Callback, OnTouchListener {

	AppearSurface mGlSurface; 
	AppearRenderer mGlRenderer;
	
	SGSceneBrowser mSceneBrowser;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGlSurface = new AppearSurface(this); 
        mGlSurface.setOnTouchListener(this);
        
        mGlRenderer = mGlSurface.getRenderer();
        mGlRenderer.setCallback(this);
        mGlRenderer.setAnimManager(AppearAnimationManager.getInstance());
        
        setContentView(mGlSurface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sg, menu);
        return true;
    }

	public void onSurfaceCreated() {
		mSceneBrowser = new SGSceneBrowser(this, mGlSurface);
		mGlRenderer.setScene(mSceneBrowser);
	}

	public void onDrawFrame() {
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (mSceneBrowser != null)
			return mSceneBrowser.onTouch(event);
		
		return false;
	}
}
*/

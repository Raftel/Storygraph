package com.raftel.storygraph;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
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

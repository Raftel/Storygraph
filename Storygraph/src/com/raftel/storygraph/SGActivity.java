package com.raftel.storygraph;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.raftel.appear.AppearRenderer;
import com.raftel.appear.AppearSurface;
import com.raftel.storygraph.scenebrowser.SGSceneBrowser;

public class SGActivity extends Activity implements AppearRenderer.Callback {

	AppearSurface mGlSurface; 
	AppearRenderer mGlRenderer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGlSurface = new AppearSurface(this); 
        mGlRenderer = mGlSurface.getRenderer();
        mGlRenderer.setCallback(this);
        
        setContentView(mGlSurface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sg, menu);
        return true;
    }

	public void onSurfaceCreated() {
		SGSceneBrowser sceneBrowser = new SGSceneBrowser(this);
		mGlRenderer.setScene(sceneBrowser);
	}

	public void onDrawFrame() {
	}
}

package com.raftel.appear.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AppearSurface extends GLSurfaceView {

	AppearRenderer mRenderer;
	
	public AppearSurface(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new AppearRenderer(this);
        setRenderer(mRenderer);
	}
	
	public AppearRenderer getRenderer() {
		return mRenderer;
	}
}

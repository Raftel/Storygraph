package com.raftel.graphics.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class RaftelGLSurface extends GLSurfaceView {

	public RaftelGLSurface(Context context) {
		super(context);
		
		// Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new RaftelGLRenderer(this));
        
		setEGLContextClientVersion(2);
	}

}

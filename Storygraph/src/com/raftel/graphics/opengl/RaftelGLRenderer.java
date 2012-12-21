package com.raftel.graphics.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

public class RaftelGLRenderer implements Renderer {

	private RaftelGLSurface mSurface;
	private RaftelGLShader mShader;

	private RaftelGLScene mScene;

	private Callback mCallback;

	public interface Callback {
		public void onSurfaceCreated();

		public void onDrawFrame();
	}

	public RaftelGLRenderer(RaftelGLSurface surface) {
		mSurface = surface;
	}

	private void initialize() {
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
	}

	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		// Set the background frame color
		initialize();
		
		mShader = new RaftelGLShader(RaftelGLShaderStr.strVertexShaderDefault, RaftelGLShaderStr.strFragmentShaderDefault);
		
		if (mCallback != null)
			mCallback.onSurfaceCreated();
	}

	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		float ratio = (float) width / height;

	    // this projection matrix is applied to object coordinates
	    // in the onDrawFrame() method
	    //Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
	}

	public void onDrawFrame(GL10 arg0) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		mShader.useProgram();

		mShader.unuseProgram();

		if (mCallback != null)
			mCallback.onDrawFrame();
	}

	public void setScene(RaftelGLScene scene) {
		mScene = scene;
	}

	public RaftelGLScene getScene() {
		return mScene;
	}

	public void setCallback(Callback cb) {
		mCallback = cb;
	}

	public Callback getCallback() {
		return mCallback;
	}
}

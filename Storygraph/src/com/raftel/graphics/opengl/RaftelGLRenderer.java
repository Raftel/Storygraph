package com.raftel.graphics.opengl;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class RaftelGLRenderer implements Renderer {

	private RaftelGLSurface mSurface;
	private RaftelGLShader mShader;
	private RaftelGLScene mScene;
	private RaftelGLMesh mPrevMesh;
	private RaftelGLMaterial mPrevMaterial;

	private Callback mCallback;
	
	private final float[] mVPMatrix = new float[16];
	private final float[] mIdentityMatrix = new float[16];

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
		resize(width, height);
	}

	public void onDrawFrame(GL10 arg0) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		mShader.useProgram();

		ArrayList<RaftelGLNode> renderNodeList = mScene.getRenderNodeList();
		for (int i = 0; i < renderNodeList.size(); i++) {
			renderModel(renderNodeList.get(i));
		}
		
		mShader.unuseProgram();

		if (mCallback != null)
			mCallback.onDrawFrame();
	}
	
	private void renderModel(RaftelGLNode node) {
		if (node == null || node.isVisible() == false)
			return;

		synchronized (node) {

			if (node instanceof RaftelGLModel) {
				RaftelGLModel model = (RaftelGLModel) node;
				RaftelGLMesh mesh = model.getMesh();
				final RaftelGLMaterial material = model.getMaterial();

				if (mesh != null) {

					mShader.updateMatrix(model.getModelMatrix(), mVPMatrix);
										
					if (mPrevMesh != mesh) {
						mShader.updateMesh(mesh);
						mPrevMesh = mesh;
					}


					if (material != null) {
						if (mPrevMaterial != material) {
							mShader.updateMaterial(material);
							mPrevMaterial = material;
						}
						
						GLES20.glDrawElements(GLES20.GL_TRIANGLES,
								mesh.getIndexCount(), GLES20.GL_UNSIGNED_SHORT,
								mesh.getIndexBuffer());
					}
				}
			}

			// render child
			ArrayList<RaftelGLNode> childList = node.getChildList();
			for (int i = 0; i < childList.size(); i++) {
				renderModel(childList.get(i));
			}
		}
	}
	
	private void resize(int width, int height) {
		// Adjust the viewport based on geometry changes,
		// such as screen rotation
		GLES20.glViewport(0, 0, width, height);

		Matrix.setIdentityM(mVPMatrix, 0);
		// Matrix.orthoM(mProjMatrix, 0, -width*0.5f, width*0.5f, -height*0.5f,
		// height*0.5f, height*0.5f, height*10.0f);

		Matrix.frustumM(mVPMatrix, 0, -width * 0.25f, width * 0.25f, -height * 0.25f, height * 0.25f, height * 0.5f, height * 10.0f);

		// mirror
		mVPMatrix[5] = -mVPMatrix[5];

		Matrix.translateM(mVPMatrix, 0, mVPMatrix, 0, -width * 0.5f, -height * 0.5f, -height);
	}
	
	public RaftelGLNode pick(float x, float y) {

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

		RaftelGLNode.PickedNode pickedNode = new RaftelGLNode.PickedNode();

		ArrayList<RaftelGLNode> renderNodeList = mScene.getRenderNodeList();
		for (int i = 0; i < renderNodeList.size(); i++) {
			renderNodeList.get(i).findPickedNode(pickedNode, mVPMatrix, inNear, inFar);
		}

		return pickedNode.mNode;
	}

	public RaftelGLNode pick(RaftelGLNode renderNode, float x, float y) {

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

		RaftelGLNode.PickedNode pickedNode = new RaftelGLNode.PickedNode();

		ArrayList<RaftelGLNode> renderNodeList = mScene.getRenderNodeList();
		for (int i = 0; i < renderNodeList.size(); i++) {
			if (renderNodeList.get(i) == renderNode) {
				renderNodeList.get(i).findPickedNode(pickedNode, mVPMatrix, inNear, inFar);
			}
		}

		return pickedNode.mNode;
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

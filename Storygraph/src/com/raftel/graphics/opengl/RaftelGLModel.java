package com.raftel.graphics.opengl;

import android.graphics.Bitmap;
import android.opengl.Matrix;

public class RaftelGLModel extends RaftelGLNode {

	private RaftelGLMesh mMesh;
	private RaftelGLMaterial mMaterial;

	public RaftelGLModel() {
		super();
	}

	public void setMesh(RaftelGLMesh mesh) {
		mMesh = mesh;
	}

	public RaftelGLMesh getMesh() {
		return mMesh;
	}

	public void setMaterial(RaftelGLMaterial material) {
		mMaterial = material;
	}

	public RaftelGLMaterial getMaterial() {
		return mMaterial;
	}

	public void setTexture(Bitmap bitmap) {
		if (mMaterial != null) {
			mMaterial.setTexture(bitmap, false);
		}
	}

	public void setTexture(Bitmap bitmap, boolean doRecycle) {
		if (mMaterial != null) {
			mMaterial.setTexture(bitmap, doRecycle);
		}
	}

	public boolean isTextureLoaded() {
		if (getMaterial() == null)
			return false;

		return getMaterial().getTexture() != RaftelGLMaterial.INVALID_TEXTURE_ID;
	}

	@Override
	protected float isPicked(float[] matVP, float[] inNear, float[] inFar) {

		if (getMesh() == null)
			return -1.0f;

		float[] matModel = getModelMatrix();
		if (matModel == null)
			return -1.0f;

		float distSq = -1.0f;
		float[] matInv = new float[16];
		float[] outNear = new float[4];
		float[] outFar = new float[4];
		float[] ray = new float[4];

		Matrix.multiplyMM(matInv, 0, matVP, 0, matModel, 0);
		Matrix.invertM(matInv, 0, matInv, 0);

		Matrix.multiplyMV(outNear, 0, matInv, 0, inNear, 0);
		Matrix.multiplyMV(outFar, 0, matInv, 0, inFar, 0);

		if (outNear[3] == 0 || outFar[3] == 0)
			return -1.0f;

		float div = 1.0f / outNear[3];
		outNear[0] *= div;
		outNear[1] *= div;
		outNear[2] *= div;
		outNear[3] = 1.0f;

		div = 1.0f / outFar[3];
		outFar[0] *= div;
		outFar[1] *= div;
		outFar[2] *= div;
		outFar[3] = 1.0f;

		ray[0] = outFar[0] - outNear[0];
		ray[1] = outFar[1] - outNear[1];
		ray[2] = outFar[2] - outNear[2];
		ray[3] = 0.0f;

		float[] point = getMesh().getIntersectionPoint(outNear, ray);
		if (point == null)
			return -1.0f;

		float[] vpVector = new float[4];
		vpVector[0] = point[0] - outNear[0];
		vpVector[1] = point[1] - outNear[1];
		vpVector[2] = point[2] - outNear[2];
		vpVector[3] = 0.0f;

		Matrix.multiplyMV(vpVector, 0, matModel, 0, vpVector, 0);

		// distance^2
		distSq = vpVector[0] * vpVector[0] + vpVector[1] * vpVector[1]
				+ vpVector[2] * vpVector[2];

		return distSq;
	}
}

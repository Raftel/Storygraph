package com.raftel.appear.graphics.mesh;

import com.raftel.appear.graphics.AppearMesh;

public class AppearSphereMesh extends AppearMesh {

	private float mRadius;
	private int mSplit;

	public AppearSphereMesh() {

	}

	public AppearSphereMesh(float radius, int split) {
		mRadius = radius;
		mSplit = split;
		
		int stride = getVertexStride();
		int nVertex = mSplit + 1;
		float vertexArray[] = new float[stride * nVertex * nVertex];

		float d = 1.0f / mSplit;
		float dth = (float) (2 * Math.PI / mSplit);
		float dpi = (float) (Math.PI / mSplit);

		for (int i = 0; i < nVertex; i++) {
			for (int j = 0; j < nVertex; j++) {
				vertexArray[stride * (i * nVertex + j) + 0] = (float) (mRadius * Math.cos(dpi * i - Math.PI / 2) * Math.sin(dth * j - Math.PI));
				vertexArray[stride * (i * nVertex + j) + 1] = (float) (mRadius * Math.sin(dpi * i - Math.PI / 2));
				vertexArray[stride * (i * nVertex + j) + 2] = (float) (mRadius * Math.cos(dpi * i - Math.PI / 2) * Math.cos(dth * j - Math.PI));
	
				vertexArray[stride * (i * nVertex + j) + 3] = d * j;
				vertexArray[stride * (i * nVertex + j) + 4] = d * i;
			}
		}
		
		setVertexArray(vertexArray, nVertex * nVertex);
		
		short indexArray[] = new short[6 * mSplit * mSplit];

		for (int i = 0; i < mSplit; i++) {
			for (int j = 0; j < mSplit; j++) {
				short v0 = (short) (i * (mSplit + 1) + j);
				short v1 = (short) (i * (mSplit + 1) + (j + 1));
				short v2 = (short) ((i + 1) * (mSplit + 1) + j);
				short v3 = (short) ((i + 1) * (mSplit + 1) + (j + 1));
				
				indexArray[6 * (i* mSplit + j) + 0] = v0;
				indexArray[6 * (i* mSplit + j) + 1] = v1;
				indexArray[6 * (i* mSplit + j) + 2] = v3;

				indexArray[6 * (i* mSplit + j) + 3] = v0;
				indexArray[6 * (i* mSplit + j) + 4] = v3;
				indexArray[6 * (i* mSplit + j) + 5] = v2;
			}
		}
		
		setIndexArray(indexArray, 6  * mSplit * mSplit);
	}
}

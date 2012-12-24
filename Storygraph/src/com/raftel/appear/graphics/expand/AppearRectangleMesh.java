package com.raftel.appear.graphics.expand;

import com.raftel.appear.graphics.AppearMesh;

public class AppearRectangleMesh extends AppearMesh {

	private float mWidth;
	private float mHeight;
	private int mSplit;

	public AppearRectangleMesh() {

	}

	public AppearRectangleMesh(float width, float height, int split) {
		mWidth = width;
		mHeight = height;
		mSplit = split;
		
		int stride = getVertexStride();
		int nVertex = mSplit + 1;
		float vertexArray[] = new float[stride * nVertex * nVertex];

		float d = 1.0f / mSplit;
		float splitWidth = mWidth / split;
		float splitHeight = mHeight / split;
		
		for (int i = 0; i < nVertex; i++) {
			for (int j = 0; j < nVertex; j++) {
				vertexArray[stride * (i * nVertex + j) + 0] = j * splitWidth;
				vertexArray[stride * (i * nVertex + j) + 1] = i * splitHeight;
				vertexArray[stride * (i * nVertex + j) + 2] = 0.0f;
	
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

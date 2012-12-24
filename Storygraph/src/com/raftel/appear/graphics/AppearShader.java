package com.raftel.appear.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class AppearShader {
	private int mProgram;
	private int mVertexShader;
	private int mFragemenShader;

	// Vertex attribute
	private int mColorHandle;
	private int mPositionHandle;
	private int mTextureHandle;

	// Matrix
	private int mMVPMatrixHandle;

	public AppearShader(String vertexShaderCode, String fragmentShaderCode) {
		loadShader(vertexShaderCode, fragmentShaderCode);
	}

	public void loadShader(String vShaderCode, String fShaderCode) {
		mProgram = GLES20.glCreateProgram();
		mVertexShader = AppearGraphicsUtil.loadShader(GLES20.GL_VERTEX_SHADER, vShaderCode);
		mFragemenShader = AppearGraphicsUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, fShaderCode);

		GLES20.glAttachShader(mProgram, mVertexShader);
		GLES20.glAttachShader(mProgram, mFragemenShader);
		GLES20.glLinkProgram(mProgram);

		IntBuffer linkStatus = IntBuffer.allocate(1);
		GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus);

		if (linkStatus.get() == GLES20.GL_FALSE) {
			GLES20.glDeleteProgram(mProgram);
			AppearGraphicsUtil.checkError("AppearShader", "loadShader", "linkStatus is not true");
			return;
		}

		// attributes
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		mTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");

		// uniforms
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		AppearGraphicsUtil.checkError("AppearShader", "loadShader", "");
	}

	public void useProgram() {
		GLES20.glUseProgram(mProgram);

		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mTextureHandle);

		AppearGraphicsUtil.checkError("AppearShader", "useProgram", "");
	}
	
	public void unuseProgram() {
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureHandle);		
		
		AppearGraphicsUtil.checkError("AppearShader", "unuseProgram", "");
	}
	
	public void updateMesh(AppearMesh mesh) {
		FloatBuffer buffer = mesh.getVertexBuffer();

		buffer.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, mesh.getVertexStride() * 4, buffer);

		buffer.position(3);
		GLES20.glVertexAttribPointer(mTextureHandle, 2, GLES20.GL_FLOAT, false, mesh.getVertexStride() * 4, buffer);
		
		AppearGraphicsUtil.checkError("AppearShader", "updateMesh", "");
	}
	
	public void updateMaterial(AppearMaterial material) {
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, material.getTexture());
		
		// headnum : Handling color
		float r = (float) Color.red(material.getColor()) / 255f;
		float g = (float) Color.green(material.getColor()) / 255f;
		float b = (float) Color.blue(material.getColor()) / 255f;
		float a = (float) Color.alpha(material.getColor()) / 255f;

		GLES20.glUniform4f(mColorHandle, r, g, b, a);
		
		AppearGraphicsUtil.checkError("AppearShader", "updateMaterial", "");
	}
	
	public void updateMatrix(float[] mMatrix, float[] vMatrix, float[] pMatrix) {
		float[] mvMatrix = new float[16];
		float[] mvpMatrix = new float[16];
		
		Matrix.multiplyMM(mvMatrix, 0, vMatrix, 0, mMatrix, 0);
		Matrix.multiplyMM(mvpMatrix, 0, pMatrix, 0, mvMatrix, 0);

		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		
		AppearGraphicsUtil.checkError("AppearShader", "updateMatrix", "");
	}
	
	public void updateMatrix(float[] mMatrix, float[] vpMatrix) {
		float[] mvpMatrix = new float[16];
		Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, mMatrix, 0);

		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		
		AppearGraphicsUtil.checkError("AppearShader", "updateMatrix", "");
	}
}

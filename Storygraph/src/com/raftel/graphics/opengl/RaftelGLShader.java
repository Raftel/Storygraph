package com.raftel.graphics.opengl;

import java.nio.IntBuffer;

import android.opengl.GLES20;

public class RaftelGLShader {
	private int mProgram;
	private int mVertexShader;
	private int mFragemenShader;

	// Vertex attribute
	private int mColorHandle;
	private int mPositionHandle;
	private int mTextureHandle;

	// Matrix
	private int mMVPMatrixHandle;

	public RaftelGLShader(String vertexShaderCode, String fragmentShaderCode) {
		loadShader(vertexShaderCode, fragmentShaderCode);
	}

	public void loadShader(String vShaderCode, String fShaderCode) {
		mProgram = GLES20.glCreateProgram();
		mVertexShader = RaftelGLUtil.loadShader(GLES20.GL_VERTEX_SHADER,
				vShaderCode);
		mFragemenShader = RaftelGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,
				fShaderCode);

		GLES20.glAttachShader(mProgram, mVertexShader);
		GLES20.glAttachShader(mProgram, mFragemenShader);
		GLES20.glLinkProgram(mProgram);

		IntBuffer linkStatus = IntBuffer.allocate(1);
		GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus);

		if (linkStatus.get() == GLES20.GL_FALSE) {
			GLES20.glDeleteProgram(mProgram);
			RaftelGLUtil.checkError("LplusShader", "loadShader",
					"linkStatus is not true");
			return;
		}

		// attributes
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		mTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");

		// uniforms
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		RaftelGLUtil.checkError("LplusShader", "loadShader", "");
	}

	public void useProgram() {
		GLES20.glUseProgram(mProgram);

		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mTextureHandle);

		RaftelGLUtil.checkError("LplusShader", "useProgram", "");
	}
	
	public void unuseProgram() {
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureHandle);		
		
		RaftelGLUtil.checkError("LplusShader", "unuseProgram", "");
	}
}

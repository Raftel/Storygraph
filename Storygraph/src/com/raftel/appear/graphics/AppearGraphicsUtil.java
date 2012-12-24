package com.raftel.appear.graphics;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class AppearGraphicsUtil {

	public static int loadShader(int type, String shaderCode) {

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}
	
	public static int loadTexture(Bitmap bitmap) {
		int[] textureId = new int[1];

		if (GLES20.glGetError() != GLES20.GL_NO_ERROR)
			Log.e("RaftelGLUtil", "loadTexture error - " + GLUtils.getEGLErrorString(GLES20.glGetError()));

		GLES20.glGenTextures(1, textureId, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		// bitmap.recycle();

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		if (GLES20.glGetError() != GLES20.GL_NO_ERROR)
			Log.e("RaftelGLUtil", "loadTexture error - " + GLUtils.getEGLErrorString(GLES20.glGetError()));

		return textureId[0];
	}

	public static void loadTexture(int textureId, Bitmap bitmap) {
		int[] textureArray = new int[1];
		textureArray[0] = textureId;

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureArray[0]);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
	}

	public static void unloadTexture(int textureId) {
		if (GLES20.glGetError() != GLES20.GL_NO_ERROR)
			Log.e("RaftelGLUtil", "unloadTexture error - " + GLUtils.getEGLErrorString(GLES20.glGetError()));

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		int idArr[] = new int[textureId];
		GLES20.glDeleteTextures(1, idArr, 0);

		if (GLES20.glGetError() != GLES20.GL_NO_ERROR)
			Log.e("RaftelGLUtil", "unloadTexture error - " + GLUtils.getEGLErrorString(GLES20.glGetError()));
	}
	
	public static void checkError(String className, String funcName, String msg) {
		int error = GLES20.glGetError();
		if (error != GLES20.GL_NO_ERROR)
			Log.e("Appear", className + ":" + funcName + "(" + msg + ")" + "gl error - " + GLUtils.getEGLErrorString(error));
	}
}

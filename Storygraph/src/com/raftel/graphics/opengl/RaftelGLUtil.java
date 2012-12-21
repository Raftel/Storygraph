package com.raftel.graphics.opengl;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class RaftelGLUtil {

	public static int loadShader(int type, String shaderCode) {

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}
	
	public static void checkError(String className, String funcName, String msg) {
		int error = GLES20.glGetError();
		if (error != GLES20.GL_NO_ERROR)
			Log.e("Lplus", className + ":" + funcName + "(" + msg + ")" + "gl error - " + GLUtils.getEGLErrorString(error));
	}
}

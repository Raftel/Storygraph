package com.raftel.appear.system;

import android.content.Context;
import android.app.Activity;

public interface AppearApplication {
	public boolean onInitialize(int width, int height);
	public boolean onSurfaceCreated(int width, int height);
	public boolean onSurfaceChanged(int width, int height);
	public Activity getCurrentActivity();
	public Context getCurrentContext();
}

package com.raftel.appear.controls;

import com.raftel.appear.common.AppearObject;

public interface AppearDnDSource {
	public boolean onDraggingStarted(AppearObject targetObject, float x, float y);
	public boolean onDragging(AppearObject targetObject, float x, float y);
	public boolean onDnDTargetChanged(AppearObject targetObject, float x, float y);
	public boolean onDraggingEnd(AppearObject targetObject, float x, float y);
	public boolean onDraggingCanceled(AppearObject targetObject, float x, float y);
	public AppearObject getSourceObject();
}

package com.raftel.appear.controls;

import com.raftel.appear.common.AppearObject;

public interface AppearDnDTarget {
	public boolean onDraggingEntered(AppearObject sourceObject, float x, float y);
	public boolean onDraggingOver(AppearObject sourceObject, float x, float y);
	public boolean onDraggingLeaved(AppearObject sourceObject, float x, float y);
	public boolean onDnDSourceDropped(AppearObject sourceObject, float x, float y);
	public boolean onDraggingCanceled(AppearObject sourceObject, float x, float y);
	public AppearObject getTargetObject();
}

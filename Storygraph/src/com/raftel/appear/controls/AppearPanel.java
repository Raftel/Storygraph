package com.raftel.appear.controls;

import com.raftel.appear.controls.AppearSimpleControl;
import com.raftel.appear.touch.AppearTouchInfo;

public class AppearPanel extends AppearSimpleControl {

	public AppearPanel() {
		
	}
	
	public void addItem() {
		
	}
	
	public void deleteItem() {
		
	}

	public boolean onTouchDown (AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchMove (AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchUp (AppearTouchInfo touchInfo) {
		return false;
	}

	public boolean onTouchCancel (AppearTouchInfo touchInfo) {
		return false;
	}

	public void onChildControlAdded (AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
	}

	public void onChildControlRemoved (AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
	}
}

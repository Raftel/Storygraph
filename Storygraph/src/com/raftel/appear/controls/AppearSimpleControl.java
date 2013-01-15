package com.raftel.appear.controls;

import android.graphics.Bitmap;

import com.raftel.appear.common.AppearBounds;

import com.raftel.appear.controls.AppearControl;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.mesh.AppearRectangleMesh;
import com.raftel.appear.graphics.expand.AppearRenderModel;

public class AppearSimpleControl extends AppearControl {
	public class SimpleControlDelegator implements AppearControlDelegator {
		public boolean onChildControlAdded(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
			return onChildControlAdded(child, syncToTouchGraph, syncToRenderGraph);
		}
		
		public boolean onChildControlRemoved(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
			return onChildControlRemoved(child, syncToTouchGraph, syncToRenderGraph);
		}
	}

	public class SimpleControlTouchHandler extends AppearTouchHandler {
		public boolean onTouchDown(AppearTouchInfo touchInfo) {
			return onTouchDown(touchInfo);
		}

		public boolean onTouchMove(AppearTouchInfo touchInfo) {
			return onTouchMove(touchInfo);
		}

		public boolean onTouchUp(AppearTouchInfo touchInfo) {
			return onTouchUp(touchInfo);
		}

		public boolean onTouchCancel(AppearTouchInfo touchInfo) {
			return onTouchCancel(touchInfo);
		}
	}

	public class SimpleControlBG extends AppearRenderModel {
		Bitmap mBGBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		private static final int SIMPLE_CONTROL_BG_SPLIT = 50;

		public SimpleControlBG() {
			mMaterial = new AppearMaterial();
			mBounds = new AppearBounds(0, 0, 0, 0);

			mMaterial.setColor(0xff00ffff);
			setMaterial(mMaterial);
			setPickable(true);
		}

		public void setBGBitmap(Bitmap bitmap) {
			mBGBitmap = bitmap;
			mMaterial.setTexture(mBGBitmap, false);
		}

		public Bitmap getBGBitmap() {
			return mBGBitmap;
		}

		public void setBounds(AppearBounds bounds) {
			mBounds = bounds;
			AppearRectangleMesh mesh = new AppearRectangleMesh(mBounds.getWidth(), mBounds.getHeight(), SIMPLE_CONTROL_BG_SPLIT);
			setMesh(mesh);
			setTranslation(mBounds.getX(), mBounds.getY(), 0);

			onBoundsChanged(mBounds);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}

	}

	private SimpleControlDelegator mSimpleControlDelegator = null;
	private SimpleControlTouchHandler mSimpleControlTouchHandler = null;
	private SimpleControlBG mSimpleControlBG = null;

	public AppearSimpleControl() {
		mSimpleControlDelegator = new SimpleControlDelegator();
		mSimpleControlTouchHandler = new SimpleControlTouchHandler();
		mSimpleControlBG = new SimpleControlBG();
		setControlDelegator(mSimpleControlDelegator);
		setTouchHandler(mSimpleControlTouchHandler);
		getRenderModel().addChild(mSimpleControlBG);
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

	public boolean onChildControlAdded (AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		return true;
	}

	public boolean onChildControlRemoved (AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		return true;
	}

	public boolean onBoundsChanged (AppearBounds bounds) {
		return true;
	}

}

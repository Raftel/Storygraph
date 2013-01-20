package com.raftel.appear.controls;

import com.raftel.appear.controls.AppearControl;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.graphics.AppearMesh;

public abstract class AppearSimpleControl extends AppearControl implements AppearControl.NotificationListener {
	public class TouchHandler extends AppearTouchHandler {
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

	private TouchHandler mTouchHandler = new TouchHandler();

	public AppearSimpleControl() {
		addNotificationListener(this);
		setTouchHandler(mTouchHandler);
	}

	@Override
	public void setTouchHandler(AppearTouchHandler touchHandler) {
		if (touchHandler == null) {
			setTouchHandler(mTouchHandler);
		}
		else {
			setTouchHandler(touchHandler);
		}
	}

	// For default touch handling
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

	// For default listener.
	public void onParentControlChanged(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph) {
	}
	
	public void onChildControlAdded(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
	}
	
	public void onChildControlRemoved(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
	}
	
	public void onTranslationChanged(float x, float y, float z) {
	}
	
	public void onRotationChanged(float x, float y, float z) {
	}
	
	public void onScaleChanged(float x, float y, float z) {
	}
	
	public void onMeshChanged(AppearMesh mesh) {
	}

/*
	public void setSize(float width, float height) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setMesh(new AppearRectangleMesh(width, height, 50));
			if (mControlEventHandler != null) {
				mControlEventHandler.onSizeChanged(width, height);
			}
		}
	}

	public void setSizeWidth(float width) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			float height = 0;
			AppearRectangleMesh mesh = renderTarget.getMesh();
			if (mesh != null) {
				height = mesh.getHeight();
			}
			renderTarget.setMesh(new AppearRectangleMesh(width, height, 50));
			if (mControlEventHandler != null) {
				mControlEventHandler.onSizeChanged(width, height);
			}
		}
	}

	public void setSizeHeight(float height) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			float width = 0;
			AppearRectangleMesh mesh = renderTarget.getMesh();
			if (mesh != null) {
				width = mesh.getWidth();
			}
			renderTarget.setMesh(new AppearRectangleMesh(width, height, 50));
			if (mControlEventHandler != null) {
				mControlEventHandler.onSizeChanged(width, height);
			}
		}
	}

	public float[] getSize() {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		float[] size = 0;
		if (renderTarget != null) {
			AppearRectangleMesh mesh = renderTarget.getMesh();
			if (mesh != null) {
				size[0] = mesh.getWidth();
				size[1] = mesh.height();
			}
		}

		return size;
	}

	public void addSize(float width, float height) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			AppearRectangleMesh mesh = renderTarget.getMesh();
			if (mesh != null) {
				width += mesh.getWidth();
				height += mesh.height();
			}
			renderTarget.setMesh(new AppearRectangleMesh(width, height, 50));
			if (mControlEventHandler != null) {
				mControlEventHandler.onSizeChanged(width, height);
			}
		}
	}
*/
}

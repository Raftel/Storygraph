package com.raftel.appear.controls;

import java.util.ArrayList;

import com.raftel.appear.touch.AppearTouchTarget;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchGraph;
import com.raftel.appear.graphics.expand.AppearRenderTarget;
import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearMesh;

public class AppearControl {
	public interface Delegator {
	}

	public interface NotificationListener {
		public void onParentControlChanged(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph);
		public void onChildControlAdded(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph);
		public void onChildControlRemoved(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph);
		public void onTranslationChanged(float x, float y, float z);
		public void onRotationChanged(float x, float y, float z);
		public void onScaleChanged(float x, float y, float z);
		public void onMeshChanged(AppearMesh mesh);
	}

	public class Notifier implements NotificationListener {
		private ArrayList<NotificationListener> mListenerList = null;
		
		public Notifier() {
			mListenerList = new ArrayList<NotificationListener>();
		}

		public boolean addListener(NotificationListener listener) {
			if (listener != null) {
				mListenerList.add(listener);
				return true;
			}
			return false;
		}

		public boolean removeListener(NotificationListener listener) {
			if (listener != null) {
				mListenerList.remove(listener);
				return true;
			}
			return false;
		}

		public void removeAllListener() {
			mListenerList.clear();
		}

		public void onParentControlChanged(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onParentControlChanged(parent, syncToTouchGraph, syncToRenderGraph);
				}
			}
		}
		
		public void onChildControlAdded(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onChildControlAdded(child, syncToTouchGraph, syncToRenderGraph);
				}
			}
		}
		
		public void onChildControlRemoved(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onChildControlRemoved(child, syncToTouchGraph, syncToRenderGraph);
				}
			}
		}
		
		public void onTranslationChanged(float x, float y, float z) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onTranslationChanged(x, y, z);
				}
			}
		}
		
		public void onRotationChanged(float x, float y, float z) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onRotationChanged(x, y, z);
				}
			}
		}
		
		public void onScaleChanged(float x, float y, float z) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onScaleChanged(x, y, z);
				}
			}
		}
		
		public void onMeshChanged(AppearMesh mesh) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onMeshChanged(mesh);
				}
			}
		}
	}

	private AppearControl mParent = null;
	private ArrayList<AppearControl> mChildControlList = null;
	private AppearTouchHandler mTouchHandler = null;
	private AppearRenderModel mRenderModel = null;
	private AppearTouchGraph mTouchGraph = null;
	private AppearRenderGraph mRenderGraph = null;
	private Notifier mNotifier = null;

	public AppearControl() {
		mChildControlList = new ArrayList<AppearControl>();
		mRenderModel = new AppearRenderModel();
		mNotifier = new Notifier();
	}

	public AppearControl getParentControl() {
		return mParent;
	}

	public void setParentControl(AppearControl parent, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (mParent != parent) {
			mParent = parent;
		}

		if ((syncToTouchGraph == true) && (mTouchHandler != null)) {
			if (mParent != null) {
				AppearTouchTarget parentTarget = getParentTouchTarget();
				if (parentTarget != null) {
					AppearTouchGraph parentGraph = parentTarget.getGraph();
					if (parentGraph != null) {
						parentGraph.newTouchTarget(parentTarget, mTouchHandler);
						mTouchGraph = parentGraph;
					}
				}
			}
			else {
				mTouchGraph.deleteTouchTarget(mTouchHandler);
				mTouchGraph = null;
			}
		}
		
		if ((syncToRenderGraph == true) && (mRenderModel != null)) {
			if (mParent != null) {
				AppearRenderTarget parentTarget = getParentRenderTarget();
				if (parentTarget != null) {
					AppearRenderGraph parentGraph = parentTarget.getGraph();
					if (parentGraph != null) {
						parentGraph.newRenderTarget(parentTarget, mRenderModel);
						mRenderGraph = parentGraph;
					}
				}
			}
			else {
				mRenderGraph.deleteRenderTarget(mRenderModel);
				mRenderGraph = null;
			}
		}

		mNotifier.onParentControlChanged(parent, syncToTouchGraph, syncToRenderGraph);
	}

	public void setParentControl(AppearControl parent) {
		setParentControl(parent, true, true);		
	}

	public ArrayList<AppearControl> getChildControlList() {
		return mChildControlList;
	}

	public boolean addChildControl(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null) {
			return false;
		}

		AppearControl prevParent = child.getParentControl();
		if (prevParent == this) {
			return true;
		}

		if (prevParent != null) {
			prevParent.removeChildControl(child, syncToTouchGraph, syncToRenderGraph);
		}

		mChildControlList.add(child);
		child.setParentControl(this, syncToTouchGraph, syncToRenderGraph);

		mNotifier.onChildControlAdded(child, syncToTouchGraph, syncToRenderGraph);

		return true;
	}

	public boolean addChildControl(AppearControl child) {
		return addChildControl(child, true, true);		
	}

	public boolean removeChildControl(AppearControl child, boolean syncToTouchGraph, boolean syncToRenderGraph) {
		if (child == null)
			return false;

		if (child.getParentControl() != this)
			return false;

		mChildControlList.remove(child);
		child.setParentControl(null, syncToTouchGraph, syncToRenderGraph);

		mNotifier.onChildControlRemoved(child, syncToTouchGraph, syncToRenderGraph);

		return true;
	}

	public boolean removeChildControl(AppearControl child) {
		return removeChildControl(child, true, true);
	}

	public void removeAllChildControl(boolean syncToTouchGraph, boolean syncToRenderGraph) {
		for (int i = 0; i < mChildControlList.size(); i++) {
			mChildControlList.get(i).setParentControl(null, syncToTouchGraph, syncToRenderGraph);
			mNotifier.onChildControlRemoved(mChildControlList.get(i), syncToTouchGraph, syncToRenderGraph);
		}
		mChildControlList.clear();
	}

	public void removeAllChildControl() {
		removeAllChildControl(true, true);
	}
	
	public void setTouchHandler(AppearTouchHandler touchHandler) {
		mTouchHandler = touchHandler;
	}

	public AppearTouchHandler getTouchHandler() {
		return mTouchHandler;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}

	public void setTouchGraph(AppearTouchGraph touchGraph) {
		mTouchGraph = touchGraph;
	}

	public AppearTouchGraph getTouchGraph() {
		return mTouchGraph;
	}

	public void setRenderGraph(AppearRenderGraph renderGraph) {
		mRenderGraph = renderGraph;
	}

	public AppearRenderGraph getRenderGraph() {
		return mRenderGraph;
	}

	public void addNotificationListener(NotificationListener listener) {
		mNotifier.addListener(listener);
	}

	public void removeNotificationListener(NotificationListener listener) {
		mNotifier.removeListener(listener);
	}

	public void setTranslation(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setTranslation(x, y, z);
			mNotifier.onTranslationChanged(x, y, z);
		}
	}

	public void setTranslationX(float x) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setTranslationX(x);
			float[] translation = renderTarget.getTranslation();
			mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
		}
	}

	public void setTranslationY(float y) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setTranslationY(y);
			float[] translation = renderTarget.getTranslation();
			mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
		}
	}

	public void setTranslationZ(float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setTranslationZ(z);
			float[] translation = renderTarget.getTranslation();
			mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
		}
	}

	public float[] getTranslation() {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			return renderTarget.getTranslation();
		}

		return null;
	}

	public void addTranslation(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.addTranslation(x, y, z);
			float[] translation = renderTarget.getTranslation();
			mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
		}
	}

	public void setRotation(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setRotation(x, y, z);
			mNotifier.onRotationChanged(x, y, z);
		}
	}

	public void setRotationX(float x) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setRotationX(x);
			float[] rotation = renderTarget.getRotation();
			mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
		}
	}

	public void setRotationY(float y) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setRotationY(y);
			float[] rotation = renderTarget.getRotation();
			mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
		}
	}

	public void setRotationZ(float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setRotationZ(z);
			float[] rotation = renderTarget.getRotation();
			mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
		}
	}

	public float[] getRotation() {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			return renderTarget.getRotation();
		}

		return null;
	}

	public void addRotation(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.addRotation(x, y, z);
			float[] rotation = renderTarget.getRotation();
			mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
		}
	}

	public void setScale(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setScale(x, y, z);
			mNotifier.onScaleChanged(x, y, z);
		}
	}

	public void setScaleX(float x) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setScaleX(x);
			float[] scale = renderTarget.getScale();
			mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
		}
	}

	public void setScaleY(float y) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setScaleY(y);
			float[] scale = renderTarget.getScale();
			mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
		}
	}

	public void setScaleZ(float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setScaleZ(z);
			float[] scale = renderTarget.getScale();
			mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
		}
	}

	public float[] getScale() {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			return renderTarget.getScale();
		}

		return null;
	}

	public void addScale(float x, float y, float z) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.addScale(x, y, z);
			float[] scale = renderTarget.getScale();
			mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
		}
	}

	public void setMesh(AppearMesh mesh) {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			renderTarget.setMesh(mesh);
		}
	}

	public AppearMesh getMesh() {
		AppearRenderTarget renderTarget = getAssociatedRenderTarget();
		if (renderTarget != null) {
			return renderTarget.getMesh();
		}

		return null;
	}

	private AppearTouchTarget getAssociatedTouchTarget() {
		if ((mTouchGraph != null) && (mTouchHandler != null)) {
			return mTouchHandler.getTargetOnGraph(mTouchGraph);
		}
		
		return null;
	}

	private AppearRenderTarget getAssociatedRenderTarget() {
		if ((mRenderGraph != null) && (mRenderModel != null)) {
			return mRenderModel.getTargetOnGraph(mRenderGraph);
		}
	
		return null;
	}

	private AppearTouchTarget getParentTouchTarget() {
		AppearTouchGraph parentTouchGraph = null;
		AppearTouchHandler parentTouchHandler = null;
		AppearControl parent = mParent;
		while (parent != null) {
			parentTouchGraph = parent.getTouchGraph();
			parentTouchHandler = parent.getTouchHandler();
			if ((parentTouchGraph != null) && (parentTouchHandler != null)) {
				return parentTouchHandler.getTargetOnGraph(parentTouchGraph);
			}

			parent = parent.getParentControl();
		}
		
		return null;
	}
	
	private AppearRenderTarget getParentRenderTarget() {
		AppearRenderGraph parentRenderGraph = null;
		AppearRenderModel parentRenderModel = null;
		AppearControl parent = mParent;
		while (parent != null) {
			parentRenderGraph = parent.getRenderGraph();
			parentRenderModel = parent.getRenderModel();
			if ((parentRenderGraph != null) && (parentRenderModel != null)) {
				return parentRenderModel.getTargetOnGraph(parentRenderGraph);
			}

			parent = parent.getParentControl();
		}
		
		return null;
	}
}

package com.raftel.appear.graphics.expand;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.expand.AppearRenderGraph;
import com.raftel.appear.graphics.AppearNode;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.AppearMesh;

public class AppearRenderTarget extends AppearModel {
	
	public interface Delegator {
	}

	public interface NotificationListener {
		public void onParentTargetChanged(AppearRenderTarget parent);
		public void onChildTargetAdded(AppearRenderTarget child);
		public void onChildTargetRemoved(AppearRenderTarget child);
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

		public void onParentTargetChanged(AppearRenderTarget parent) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onParentTargetChanged(parent);
				}
			}
		}
		
		public void onChildTargetAdded(AppearRenderTarget child) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onChildTargetAdded(child);
				}
			}
		}
		
		public void onChildTargetRemoved(AppearRenderTarget child) {
			for (int i = 0; i < mListenerList.size(); i++) {
				NotificationListener listener = mListenerList.get(i);
				if (listener != null ) {
					listener.onChildTargetRemoved(child);
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

	public interface LookupFilter {
		public boolean filter(AppearRenderTarget target);
	}

	public class LookupTargetAtPoint implements LookupFilter {
		private float mX;
		private float mY;
		private float mZ;
	
		public LookupTargetAtPoint(float x, float y, float z) {
			mX = x;
			mY = y;
			mZ = z;
		}
	
		public boolean filter(AppearRenderTarget target) {
			return true;
		}
	}
	
	public class LookupTouchableTarget implements LookupFilter {
		public LookupTouchableTarget() {
		}
	
		public boolean filter(AppearRenderTarget target) {
			return true;
		}
	}
	
	public class LookupDropTarget implements LookupFilter {
		public LookupDropTarget() {
		}
	
		public boolean filter(AppearRenderTarget target) {
			return true;
		}
	}

	public class LookupVisibleTarget implements LookupFilter {
		public LookupVisibleTarget() {
		}
	
		public boolean filter(AppearRenderTarget target) {
			return true;
		}
	}

	private AppearRenderGraph mGraph = null;
	private AppearRenderTarget mParentTarget = null;
	private ArrayList<AppearRenderTarget> mChildTargetList = null;
	private AppearRenderModel mRenderModel = null;
	private Notifier mNotifier = null;

	public AppearRenderTarget() {
		mChildTargetList = new ArrayList<AppearRenderTarget>();
		mNotifier = new Notifier();
	}

	public void setGraph(AppearRenderGraph graph) {
		mGraph = graph;
	}

	public AppearRenderGraph getGraph() {
		return mGraph;
	}

	public AppearRenderTarget getParentTarget() {
		return mParentTarget;
	}

	public void setParentTarget(AppearRenderTarget parent) {
		if (mParentTarget != parent) {
			mParentTarget = parent;
			setGraph(mParentTarget.getGraph());
			mNotifier.onParentTargetChanged(parent);
		}
	}

	public ArrayList<AppearRenderTarget> getChildTargetList() {
		return mChildTargetList;
	}

	public boolean addChildTarget(AppearRenderTarget child) {
		if (child == null)
			return false;

		AppearRenderTarget prevParent = child.getParentTarget();
		if (prevParent == this)
			return true;

		if (prevParent != null) {
			prevParent.removeChildTarget(child);
		}
	
		child.setParentTarget(this);
		mChildTargetList.add(child);

		// For Graphics
		super.addChild(child);

		mNotifier.onChildTargetAdded(child);

		return true;
	}

	public boolean removeChildTarget(AppearRenderTarget child) {
		if (child == null)
			return false;

		if (child.getParentTarget() != this)
			return false;
		
		child.setParentTarget(null);
		mChildTargetList.remove(child);

		// For Graphics
		super.removeChild(child);

		mNotifier.onChildTargetRemoved(child);

		return true;
	}

	public void removeAllChildTarget() {
		for (int i = 0; i < mChildTargetList.size(); i++) {
			mChildTargetList.get(i).setParentTarget(null);
			mNotifier.onChildTargetRemoved(mChildTargetList.get(i));
		}

		mChildTargetList.clear();
		
		// For Graphics
		super.removeAllChild();
	}

	public void setRenderModel(AppearRenderModel renderModel) {
		if (mRenderModel == renderModel) {
			return;
		}

		if (mRenderModel != null) {
			mRenderModel.removeTarget(this);
			super.removeChild(mRenderModel);
		}

		if (renderModel != null) {
			renderModel.addTarget(this);
			ArrayList<AppearNode> childList = super.getChildList();
			childList.add(0, renderModel);
		}

		mRenderModel = renderModel;
	}

	public AppearRenderModel getRenderModel() {
		return mRenderModel;
	}

	public void addNotificationListener(NotificationListener listener) {
		mNotifier.addListener(listener);
	}

	public void removeNotificationListener(NotificationListener listener) {
		mNotifier.removeListener(listener);
	}

	public AppearRenderTarget lookupTarget(LookupFilter[] filters) {
		for (int i = 0; i < filters.length; i++) {
			if (filters[i].filter(this) == false) {
				return null;
			}
		}

		AppearRenderTarget hitTarget = null;
		for (int i = 0; i < mChildTargetList.size(); i++) {
			hitTarget = mChildTargetList.get(i).lookupTarget(filters);
			if (hitTarget != null) {
				return hitTarget;
			}
		}

		return this;
	}

	// AppearModel
	@Override
	public void setMesh(AppearMesh mesh) {
		if (mRenderModel != null) {
			mRenderModel.setMesh(mesh);
		}
		else {
			super.setMesh(mesh);
		}

		mNotifier.onMeshChanged(mesh);
 	}

	@Override
	public AppearMesh getMesh() {
		if (mRenderModel != null) {
			return mRenderModel.getMesh();
		}
		else {
			return super.getMesh();
		}
	}

	@Override
	public void setMaterial(AppearMaterial material) {
		if (mRenderModel != null) {
			mRenderModel.setMaterial(material);
		}
		else {
			super.setMaterial(material);
		}
	}

	@Override
	public AppearMaterial getMaterial() {
		if (mRenderModel != null) {
			return mRenderModel.getMaterial();
		}
		else {
			return super.getMaterial();
		}
	}

	@Override
	public void setTexture(Bitmap bitmap) {
		if (mRenderModel != null) {
			mRenderModel.setTexture(bitmap);
		}
		else {
			super.setTexture(bitmap);
		}
	}

	@Override
	public void setTexture(Bitmap bitmap, boolean doRecycle) {
		if (mRenderModel != null) {
			mRenderModel.setTexture(bitmap, doRecycle);
		}
		else {
			super.setTexture(bitmap, doRecycle);
		}
	}

	@Override
	public boolean isTextureLoaded() {
		if (mRenderModel != null) {
			return mRenderModel.isTextureLoaded();
		}
		else {
			return super.isTextureLoaded();
		}
	}

	@Override
	protected float isPicked(float[] matVP, float[] inNear, float[] inFar) {
		return super.isPicked(matVP, inNear, inFar);
	}

	// AppearNode
	@Override
	public void setTranslation(float x, float y, float z) {
		super.setTranslation(x, y, z);
		mNotifier.onTranslationChanged(x, y, z);
	}

	@Override
	public void setTranslationX(float x) {
		super.setTranslationX(x);
		float[] translation = getTranslation();
		mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
	}

	@Override
	public void setTranslationY(float y) {
		super.setTranslationY(y);
		float[] translation = getTranslation();
		mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
	}

	@Override
	public void setTranslationZ(float z) {
		super.setTranslationZ(z);
		float[] translation = getTranslation();
		mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
	}

	@Override
	public void addTranslation(float x, float y, float z) {
		super.addTranslation(x, y, z);
		float[] translation = getTranslation();
		mNotifier.onTranslationChanged(translation[0], translation[1], translation[2]);
	}

	@Override
	public void setRotation(float x, float y, float z) {
		super.setRotation(x, y, z);
		mNotifier.onRotationChanged(x, y, z);
	}

	@Override
	public void setRotationX(float x) {
		super.setRotationX(x);
		float[] rotation = getRotation();
		mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
	}

	@Override
	public void setRotationY(float y) {
		super.setRotationY(y);
		float[] rotation = getRotation();
		mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
	}

	@Override
	public void setRotationZ(float z) {
		super.setRotationZ(z);
		float[] rotation = getRotation();
		mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
	}

	@Override
	public void addRotation(float x, float y, float z) {
		super.addRotation(x, y, z);
		float[] rotation = getRotation();
		mNotifier.onRotationChanged(rotation[0], rotation[1], rotation[2]);
	}

	@Override
	public void setScale(float x, float y, float z) {
		super.setScale(x, y, z);
		mNotifier.onScaleChanged(x, y, z);
	}

	@Override
	public void setScaleX(float x) {
		super.setScaleX(x);
		float[] scale = getScale();
		mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
	}

	@Override
	public void setScaleY(float y) {
		super.setScaleY(y);
		float[] scale = getScale();
		mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
	}

	@Override
	public void setScaleZ(float z) {
		super.setScaleZ(z);
		float[] scale = getScale();
		mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
	}

	@Override
	public void addScale(float x, float y, float z) {
		super.addScale(x, y, z);
		float[] scale = getScale();
		mNotifier.onScaleChanged(scale[0], scale[1], scale[2]);
	}

	@Override
	@Deprecated
	public void setParent(AppearNode parent) {
	}

	@Override
	@Deprecated
	public boolean addChild(AppearNode node) {
		return false;
	}

	@Override
	@Deprecated
	public boolean removeChild(AppearNode node) {
		return false;
	}

	@Override
	@Deprecated
	public boolean removeAllChild() {
		return false;
	}
}

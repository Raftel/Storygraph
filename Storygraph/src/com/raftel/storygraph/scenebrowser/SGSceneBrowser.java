package com.raftel.storygraph.scenebrowser;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.raftel.appear.common.AppearBounds;
import com.raftel.appear.controls.AppearControl;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.expand.AppearRenderModel;
import com.raftel.appear.graphics.mesh.AppearRectangleMesh;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;

public class SGSceneBrowser extends AppearControl {
	public static class __HorizontalSceneScrolling extends AppearTouchHandler {
		public boolean onTouchDown(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchMove(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchUp(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchCancel(AppearTouchInfo touchInfo) {
			return true;
		}
	}

	public static class __SceneZooming extends AppearTouchHandler {
		public boolean onTouchDown(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchMove(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchUp(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchCancel(AppearTouchInfo touchInfo) {
			return true;
		}
	}

	public static class __SceneContainer extends AppearRenderModel {
		Bitmap mBGBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		private static final int SG_SCENE_SPLIT = 50;

		public __SceneContainer() {
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
			AppearRectangleMesh mesh = new AppearRectangleMesh(mBounds.getWidth(), mBounds.getHeight(), SG_SCENE_SPLIT);
			setMesh(mesh);
			setTranslation(mBounds.getX(), mBounds.getY(), 0);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}

	}

	public static class __Scene extends AppearRenderModel {
		Bitmap mBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		private static final int SG_SCENE_SPLIT = 50;

		public __Scene() {
			mMaterial = new AppearMaterial();
			mBounds = new AppearBounds(0, 0, 0, 0);

			mMaterial.setColor(0xff00ffff);
			setMaterial(mMaterial);
			setPickable(true);
		}

		public void setBitmap(Bitmap bitmap) {
			mBitmap = bitmap;
			mMaterial.setTexture(mBitmap, false);
		}

		public Bitmap getBitmap() {
			return mBitmap;
		}

		public void setBounds(AppearBounds bounds) {
			mBounds = bounds;
			AppearRectangleMesh mesh = new AppearRectangleMesh(mBounds.getWidth(), mBounds.getHeight(), SG_SCENE_SPLIT);
			setMesh(mesh);
			setTranslation(mBounds.getX(), mBounds.getY(), 0);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}
	}

	private __HorizontalSceneScrolling mHorizontalSceneScrolling = null;
	private __SceneZooming mSceneZooming = null;
	private __SceneContainer mSceneContainer = null;
	private ArrayList<__Scene> mSceneList = null;
	private static final float SG_BROWSER_SCENE_MARGIN = 10;

	public SGSceneBrowser() {
		mHorizontalSceneScrolling = new __HorizontalSceneScrolling();
		mSceneZooming = new __SceneZooming();
		mSceneContainer = new __SceneContainer();
		mSceneList = new ArrayList<__Scene>();

		setTouchHandler(mHorizontalSceneScrolling);
		getRenderModel().addChild(mSceneContainer);
	}

	public void setBounds(AppearBounds bounds) {
		mSceneContainer.setBounds(bounds);
	}

	public AppearBounds getBounds() {
		return mSceneContainer.getBounds();
	}

	public int addScene(int sceneNum, Bitmap bitmap) {
		__Scene scene = new __Scene();
		AppearBounds sceneBounds = new AppearBounds((
				getBounds().getWidth() * sceneNum) + SG_BROWSER_SCENE_MARGIN, 
				SG_BROWSER_SCENE_MARGIN, 
				getBounds().getWidth() - SG_BROWSER_SCENE_MARGIN * 2,
				getBounds().getHeight() - SG_BROWSER_SCENE_MARGIN * 2);
		scene.setBitmap(bitmap);
		scene.setBounds(sceneBounds);

		mSceneContainer.addChild(scene);
		mSceneList.add(sceneNum, scene);
		return mSceneList.size() - 1;
	}

	public int addScene(Bitmap bitmap) {
		__Scene scene = new __Scene();
		AppearBounds sceneBounds = new AppearBounds((
				getBounds().getWidth() * mSceneList.size()) + SG_BROWSER_SCENE_MARGIN, 
				SG_BROWSER_SCENE_MARGIN, 
				getBounds().getWidth() - SG_BROWSER_SCENE_MARGIN * 2, 
				getBounds().getHeight() - SG_BROWSER_SCENE_MARGIN * 2);
		scene.setBitmap(bitmap);
		scene.setBounds(sceneBounds);

		mSceneContainer.addChild(scene);
		mSceneList.add(scene);
		return mSceneList.size() - 1;
	}

	public void removeScene(int sceneNum) {
		mSceneContainer.removeChild(mSceneList.get(sceneNum));

		mSceneList.remove(sceneNum);
	}
}

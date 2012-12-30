package com.raftel.storygraph.scenebrowser;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.raftel.appear.common.AppearBounds;
import com.raftel.appear.controls.AppearControl;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.expand.AppearRectangleMesh;
import com.raftel.appear.graphics.expand.AppearRenderModel;

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
		AppearModel mModel = null;
		private static final int SG_SCENE_SPLIT = 50;

		public __SceneContainer() {
			mMaterial = new AppearMaterial();
			mBounds = new AppearBounds(0, 0, 0, 0);
			mModel = new AppearModel();

			mMaterial.setColor(0xff00ffff);
			mModel.setMaterial(mMaterial);
			mModel.setPickable(true);
			setModel(mModel);
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
			mModel.setMesh(mesh);
			mModel.setTranslation(mBounds.getX(), mBounds.getY(), 0);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}

	}

	public static class __Scene extends AppearRenderModel {
		Bitmap mBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		AppearModel mModel = null;
		private static final int SG_SCENE_SPLIT = 50;

		public __Scene() {
			mMaterial = new AppearMaterial();
			mBounds = new AppearBounds(0, 0, 0, 0);
			mModel = new AppearModel();

			mMaterial.setColor(0xff00ffff);
			mModel.setMaterial(mMaterial);
			mModel.setPickable(true);
			setModel(mModel);
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
			mModel.setMesh(mesh);
			mModel.setTranslation(mBounds.getX(), mBounds.getY(), 0);
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

		setRenderModel(mSceneContainer);
		setTouchHandler(mHorizontalSceneScrolling);
	}

	public void setBounds(AppearBounds bounds) {
		mSceneContainer.setBounds(bounds);
	}

	public AppearBounds getBounds() {
		return mSceneContainer.getBounds();
	}

	public int addScene(int sceneNum, Bitmap bitmap) {
		__Scene scene = new __Scene();
		AppearBounds sceneBounds = new AppearBounds((getBounds().getWidth()*sceneNum)+SG_BROWSER_SCENE_MARGIN, 
								SG_BROWSER_SCENE_MARGIN, 
								getBounds().getWidth()-SG_BROWSER_SCENE_MARGIN*2, 
								getBounds().getHeight()-SG_BROWSER_SCENE_MARGIN*2);
		scene.setBitmap(bitmap);
		scene.setBounds(sceneBounds);

		mSceneContainer.getModel().addChild(scene.getModel());
		mSceneList.add(sceneNum, scene);
		return mSceneList.size() - 1;
	}

	public int addScene(Bitmap bitmap) {
		__Scene scene = new __Scene();
		AppearBounds sceneBounds = new AppearBounds((getBounds().getWidth()*mSceneList.size())+SG_BROWSER_SCENE_MARGIN, 
								SG_BROWSER_SCENE_MARGIN, 
								getBounds().getWidth()-SG_BROWSER_SCENE_MARGIN*2, 
								getBounds().getHeight()-SG_BROWSER_SCENE_MARGIN*2);
		scene.setBitmap(bitmap);
		scene.setBounds(sceneBounds);

		mSceneContainer.getModel().addChild(scene.getModel());
		mSceneList.add(scene);
		return mSceneList.size() - 1;
	}

	public void removeScene(int sceneNum) {
		mSceneContainer.getModel().removeChild(mSceneList.get(sceneNum).getModel());

		mSceneList.remove(sceneNum);
	}
}

/*
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.AppearNode;
import com.raftel.appear.graphics.AppearSurface;
import com.raftel.appear.graphics.expand.AppearTouchableScene;
import com.raftel.appear.graphics.expand.AppearRectangleMesh;

import com.raftel.storygraph.R;

public class SGSceneBrowser extends AppearTouchableScene {

	Context mContext;
	
	public SGSceneBrowser(Context context, AppearSurface surface) {
		super(surface);
		
		mContext = context;
		
		Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_earth);
		Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_book);
		
		AppearRectangleMesh rectangleMesh = new AppearRectangleMesh(300, 200, 50);
		
		AppearMaterial material1 = new AppearMaterial();
		material1.setColor(0xff00ffff);
		material1.setTexture(bitmap1, false);
		
		AppearMaterial material2 = new AppearMaterial();
		material2.setColor(0xff00ffff);
		material2.setTexture(bitmap2, false);
		
		AppearModel model1 = new AppearModel();
		model1.setMesh(rectangleMesh);
		model1.setMaterial(material1);
		model1.setTranslation(200, 200, 0);
		model1.setPickable(true);
		
		AppearModel model2 = new AppearModel();
		model2.setMesh(rectangleMesh);
		model2.setMaterial(material2);
		model2.setTranslation(20, 600, 0);
		//model2.setScale(1.5f, 1.5f, 0);
		model2.setPickable(true);
				
		AppearNode node = new AppearNode();
		node.addChild(model1);
		node.addChild(model2);
		addRenderNode(node);
		
		//AppearAnimation anim = AppearAnimationManager.getInstance().createAnimation(1000, 10000, AppearAnimation.FUNC_EASE_OUT);
		//anim.addProperty(new AppearAnimationProp(AppearAnimationProp.PROP_ROTATE_X, model, 0, 360));
		//anim.start();
	}
}
*/

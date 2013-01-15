package com.raftel.appear.controls;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.raftel.appear.common.AppearBounds;
import com.raftel.appear.controls.AppearControl;
import com.raftel.appear.touch.AppearTouchHandler;
import com.raftel.appear.touch.AppearTouchInfo;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.mesh.AppearRectangleMesh;
import com.raftel.appear.graphics.expand.AppearRenderModel;

public class AppearImageBrowser extends AppearControl {
	public class HorizontalImageScrolling extends AppearTouchHandler {
		private float prevX = 0;
		public boolean onTouchDown(AppearTouchInfo touchInfo) {
			prevX = touchInfo.getTouchPoint().getX();
			return true;
		}

		public boolean onTouchMove(AppearTouchInfo touchInfo) {
			float currX = touchInfo.getTouchPoint().getX();
			AppearBounds bounds = mImageFrameContainer.getBounds();
			bounds.setX(bounds.getX() + (currX - prevX));
			mImageFrameContainer.setBounds(bounds);
			prevX = currX;
			return true;
		}

		public boolean onTouchUp(AppearTouchInfo touchInfo) {
			return true;
		}

		public boolean onTouchCancel(AppearTouchInfo touchInfo) {
			return true;
		}
	}

	public class ImageZooming extends AppearTouchHandler {
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

	public class ImageFrameContainer extends AppearRenderModel {
		Bitmap mBGBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		private static final int IMAGE_BROWSER_SPLIT = 50;

		public ImageFrameContainer() {
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
			AppearRectangleMesh mesh = new AppearRectangleMesh(mBounds.getWidth(), mBounds.getHeight(), IMAGE_BROWSER_SPLIT);
			setMesh(mesh);
			setTranslation(mBounds.getX(), mBounds.getY(), 0);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}

	}

	public class ImageFrame extends AppearRenderModel {
		Bitmap mBitmap = null;
		AppearMaterial mMaterial = null;
		AppearBounds mBounds = null;
		private static final int IMAGE_BROWSER_SPLIT = 50;

		public ImageFrame() {
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
			AppearRectangleMesh mesh = new AppearRectangleMesh(mBounds.getWidth(), mBounds.getHeight(), IMAGE_BROWSER_SPLIT);
			setMesh(mesh);
			setTranslation(mBounds.getX(), mBounds.getY(), 0);
		}

		public AppearBounds getBounds() {
			return mBounds;
		}
	}

	private HorizontalImageScrolling mHorizontalImageScrolling = null;
	private ImageZooming mImageZooming = null;
	private ImageFrameContainer mImageFrameContainer = null;
	private ArrayList<ImageFrame> mImageFrameList = null;
	private static final float IMAGE_BROWSER_FRAME_MARGIN = 10;

	public AppearImageBrowser() {
		mHorizontalImageScrolling = new HorizontalImageScrolling();
		mImageZooming = new ImageZooming();
		mImageFrameContainer = new ImageFrameContainer();
		mImageFrameList = new ArrayList<ImageFrame>();

		setTouchHandler(mHorizontalImageScrolling);
		getRenderModel().addChild(mImageFrameContainer);
	}

	public void setBounds(AppearBounds bounds) {
		mImageFrameContainer.setBounds(bounds);
	}

	public AppearBounds getBounds() {
		return mImageFrameContainer.getBounds();
	}

	public int addImage(int imageFrameNum, Bitmap bitmap) {
		ImageFrame imageFrame = new ImageFrame();
		AppearBounds imageFrameBounds = new AppearBounds((
				getBounds().getWidth() * imageFrameNum) + IMAGE_BROWSER_FRAME_MARGIN, 
				IMAGE_BROWSER_FRAME_MARGIN, 
				getBounds().getWidth() - IMAGE_BROWSER_FRAME_MARGIN * 2,
				getBounds().getHeight() - IMAGE_BROWSER_FRAME_MARGIN * 2);
		imageFrame.setBitmap(bitmap);
		imageFrame.setBounds(imageFrameBounds);

		mImageFrameContainer.addChild(imageFrame);
		mImageFrameList.add(imageFrameNum, imageFrame);
		return mImageFrameList.size() - 1;
	}

	public int addImage(Bitmap bitmap) {
		ImageFrame imageFrame = new ImageFrame();
		AppearBounds imageFrameBounds = new AppearBounds((
				getBounds().getWidth() * mImageFrameList.size()) + IMAGE_BROWSER_FRAME_MARGIN, 
				IMAGE_BROWSER_FRAME_MARGIN, 
				getBounds().getWidth() - IMAGE_BROWSER_FRAME_MARGIN * 2, 
				getBounds().getHeight() - IMAGE_BROWSER_FRAME_MARGIN * 2);
		imageFrame.setBitmap(bitmap);
		imageFrame.setBounds(imageFrameBounds);

		mImageFrameContainer.addChild(imageFrame);
		mImageFrameList.add(imageFrame);
		return mImageFrameList.size() - 1;
	}

	public void removeImage(int imageFrameNum) {
		mImageFrameContainer.removeChild(mImageFrameList.get(imageFrameNum));

		mImageFrameList.remove(imageFrameNum);
	}
}

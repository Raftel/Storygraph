package com.raftel.appear.graphics;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class AppearMaterial {

	public static final int INVALID_TEXTURE_ID = -1;
	public static final int INITIAL_COLOR = 0xFFFFFFFF;
	private static ArrayList<AppearMaterial> sReservedTexLoadingList = new ArrayList<AppearMaterial>();
	
	private int mTexture;
	private Bitmap mBitmap;
	private int mColor;
	private boolean mTexLoadingReserved;
	private boolean mDoRecycle = false;
	
	private static void addTexLoading(AppearMaterial material) {
		sReservedTexLoadingList.add(material);
	}

	/* Must be called in render thread */
	public static void doReservedTexLoading() {
		if (sReservedTexLoadingList.size() == 0)
			return;

		synchronized (sReservedTexLoadingList) {

			// divide texture loading
			int count = 5;
			if (sReservedTexLoadingList.size() < count)
				count = sReservedTexLoadingList.size();

			while (count != 0) {
				AppearMaterial material = sReservedTexLoadingList.get(0);
				if (material != null) {
					material.loadTexture();
					sReservedTexLoadingList.remove(material);
				}
				count--;
			}
		}
	}
	
	public AppearMaterial() {
		mTexture = INVALID_TEXTURE_ID;
		mBitmap = null;
		mTexLoadingReserved = false;
		mColor = INITIAL_COLOR;
	}
	
	public int getTexture() {
		return mTexture;
	}
	
	public void setTexture(Bitmap bitmap, boolean doRecycle) {
		mBitmap = bitmap;
		mDoRecycle = doRecycle;
		mTexLoadingReserved = true;
		addTexLoading(this);
	}
	
	public int getColor() {
		return mColor;
	}
	
	public void setColor(int color) {
		mColor = color;
	}

	public void setColor(int alpha, int red, int green, int blue) {
		// range = 0 ~ 255
		mColor = Color.argb(alpha, red, green, blue);
	}

	public void setColor(float alpha, float red, float green, float blue) {
		// range = 0.0f ~ 1.0f
		mColor = Color.argb((int) (alpha * 255f), (int) (255f * red), (int) (255f * green), (int) (255f * blue));
	}
	
	/* Must be called in render thread */
	public void loadTexture() {
		if (mTexLoadingReserved) {
			if (mTexture != INVALID_TEXTURE_ID) {
				if (mBitmap != null) {
					if (mBitmap.isRecycled() == false)
						AppearGraphicsUtil.loadTexture(mTexture, mBitmap);
					else
						Log.w("RaftelGLMaterial", "loadTexture - bitmap is recylcled");

					if (mDoRecycle)
						mBitmap.recycle();
					mBitmap = null;
					mDoRecycle = false;
				} else {
					AppearGraphicsUtil.unloadTexture(mTexture);
					mTexture = INVALID_TEXTURE_ID;
					mDoRecycle = false;
				}
			} else {
				if (mBitmap != null) {
					if (mBitmap.isRecycled() == false)
						mTexture = AppearGraphicsUtil.loadTexture(mBitmap);
					else
						Log.w("RaftelGLMaterial", "loadTexture - bitmap is recylcled");

					if (mDoRecycle)
						mBitmap.recycle();
					mBitmap = null;
					mDoRecycle = false;
				}
			}

			mTexLoadingReserved = false;
		}
	}
}

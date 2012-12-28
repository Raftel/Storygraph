package com.raftel.appear.bitmap;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

public class AppearBitmapData {

	public static final int BITMAP_TYPE_NONE = -1;
	public static final int BITMAP_TYPE_FILE = 0;
	public static final int BITMAP_TYPE_MEDIA_STORE = 1;
	public static final int BITMAP_TYPE_RES_ID = 2;

	private Context mContext;
	private int mType;
	private String mLocation;
	private int mResourceId;
	private long mMediaId;
	
	public AppearBitmapData(Context context) {
		mContext = context;
		mType = BITMAP_TYPE_NONE;
		mResourceId = -1;
		mMediaId = -1;
		mLocation = null;
	}
	
	public ContentResolver getContentResolver() {
		return mContext.getContentResolver();
	}
	
	public Resources getResources() {
		return mContext.getResources();
	}
	
	public void setType(int type) {
		mType = type;
	}
	
	public int getType() {
		return mType;
	}
	
	public long getMediaId() {
		return mMediaId;
	}

	public void setMediaId(long id) {
		mMediaId = id;
	}	

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		mLocation = location;
	}

	public int getResourceId() {
		return mResourceId;
	}

	public void setResourceId(int id) {
		mResourceId = id;
	}
}

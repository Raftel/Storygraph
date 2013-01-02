package com.raftel.appear.resource;

import java.io.File;
import java.util.ArrayList;

import com.raftel.appear.bitmap.AppearBitmapData;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AppearResourceManager {
	private static AppearResourceManager mManager;
	private Context mContext;

	public static void createResourceManager(Context context) {
		if (mManager == null)
			mManager = new AppearResourceManager(context);
	}

	public static AppearResourceManager getInstance() {
		return mManager;
	}

	private AppearResourceManager(Context context) {
		mContext = context;
	}

	// FIXME
	public ArrayList<AppearBitmapData> createDataFromFileSystem(String path) {
		ArrayList<AppearBitmapData> bitmapDataList = null;

		File dir = new File(path);
		String[] files = dir.list();

		if ((files != null) && (files.length > 0)) {
			for (int j = 0; j < files.length; j++) {
				if (dir.listFiles()[j].isDirectory() == false) {

					if (bitmapDataList == null)
						bitmapDataList = new ArrayList<AppearBitmapData>();

					AppearBitmapData bitmapData = new AppearBitmapData(mContext);
					bitmapData.setType(AppearBitmapData.BITMAP_TYPE_FILE);
					bitmapData.setLocation(path + files[j]);
					bitmapDataList.add(bitmapData);
				}
			}
		}

		return bitmapDataList;
	}

	// FIXME
	public ArrayList<AppearBitmapData> createAllDataFromMediaStore() {
		ArrayList<AppearBitmapData> bitmapDataList = new ArrayList<AppearBitmapData>();

		Cursor cur = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		int colData = cur.getColumnIndex(MediaStore.MediaColumns.DATA);
		int colID = cur.getColumnIndex(MediaStore.Images.Media._ID);

		if (cur.moveToFirst()) {
			do {
				File data = new File(cur.getString(colData));

				AppearBitmapData bitmapData = new AppearBitmapData(mContext);
				bitmapData.setType(AppearBitmapData.BITMAP_TYPE_MEDIA_STORE);
				bitmapData.setLocation(data.getPath());
				bitmapData.setMediaId(cur.getLong(colID));
				bitmapDataList.add(bitmapData);
			} while (cur.moveToNext());

			cur.close();
		}

		return bitmapDataList;
	}

	// FIXME
	public ArrayList<AppearBitmapData> createDataFromMediaStore(String bucketName) {
		ArrayList<AppearBitmapData> bitmapDataList = new ArrayList<AppearBitmapData>();

		Cursor cur = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		int colData = cur.getColumnIndex(MediaStore.MediaColumns.DATA);
		int colBucket = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
		int colID = cur.getColumnIndex(MediaStore.Images.Media._ID);

		if (cur.moveToFirst()) {
			do {
				File data = new File(cur.getString(colData));
				String strBucket = cur.getString(colBucket);

				if (bucketName.equals(strBucket)) {
					AppearBitmapData bitmapData = new AppearBitmapData(mContext);
					bitmapData.setType(AppearBitmapData.BITMAP_TYPE_MEDIA_STORE);
					bitmapData.setLocation(data.getPath());
					bitmapData.setMediaId(cur.getLong(colID));
					bitmapDataList.add(bitmapData);
				}
			} while (cur.moveToNext());

			cur.close();
		}

		return bitmapDataList;
	}
}

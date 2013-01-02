package com.raftel.appear.resource;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AppearResourceManager {

	Context mContext;
	
	public AppearResourceManager(Context context) {
		mContext = context;
	}
	
//    public static final int RESOURCE_TYOE_MEDIA_STORE = 0;
//    public static final int RESOURCE_TYOE_FILE_SYSTEM = 1;
//
//    private static ResourceManager mManager;
//    private Context mContext;
//    private CBGalleryBitmapLoader mBitmaploader;
//
//    public static void createCBGalleryResourceManager(Context context) {
//	if (mManager == null)
//	    mManager = new ResourceManager(context);
//    }
//
//    public static ResourceManager getResourceManager() {
//	return mManager;
//    }
//
//    private ResourceManager(Context context) {
//	mContext = context;
//	mBitmaploader = new CBGalleryBitmapLoader(mContext, this);
//    }
//
//    public ArrayList<CBGalleryAlbumData> createAlbumsDataFromFileSystem(String path) {
//
//	ArrayList<CBGalleryAlbumData> albums = new ArrayList<CBGalleryAlbumData>();
//	File dir = new File(path);
//	String[] files = dir.list();
//
//	if ((files != null) && (files.length > 0)) {
//	    ArrayList<CBGalleryPhotoData> photos = null;
//	    for (int j = 0; j < files.length; j++) {
//		if (dir.listFiles()[j].isDirectory() == false) {
//
//		    if (photos == null)
//			photos = new ArrayList<CBGalleryPhotoData>();
//
//		    CBGalleryPhotoData pd = new CBGalleryPhotoData(j);
//		    pd.setResourceType(CBGalleryPhotoData.CB_GALLERY_PHOTO_RESOURCE_TYPE_FILE);
//		    pd.setResourcePath(path + files[j]);
//		    photos.add(pd);
//		}
//	    }
//
//	    if (photos != null) {
//		CBGalleryAlbumData album = new CBGalleryAlbumData(0, dir.getName(), photos);
//		albums.add(album);
//	    }
//	}
//
//	return albums;
//    }
//
//    public ArrayList<CBGalleryAlbumData> createAlbumsDataFromMediaStore() {
//	ArrayList<CBGalleryAlbumData> albums = new ArrayList<CBGalleryAlbumData>();
//
//	Cursor cur = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//	int colData = cur.getColumnIndex(MediaStore.MediaColumns.DATA);
//	int colBucket = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//	int colID = cur.getColumnIndex(MediaStore.Images.Media._ID);
//
//	CBGalleryAlbumData album = null;
//	ArrayList<CBGalleryPhotoData> photos = null;
//	boolean bNewBucket = true;
//
//	if (cur.moveToFirst()) {
//	    do {
//
//		bNewBucket = true;
//		File data = new File(cur.getString(colData));
//		String strBucket = cur.getString(colBucket);
//
//		for (int i = 0; i < albums.size(); i++) {
//		    if (albums.get(i).getAlbumName().equals(strBucket)) {
//			bNewBucket = false;
//			album = albums.get(i);
//			photos = album.getPhotoList();
//			break;
//		    }
//		}
//
//		if (bNewBucket == true) {
//		    photos = new ArrayList<CBGalleryPhotoData>();
//
//		    album = new CBGalleryAlbumData(albums.size(), strBucket, photos);
//		    albums.add(album);
//
//		    CBGalleryPhotoData pd = new CBGalleryPhotoData(photos.size());
//		    pd.setResourceType(CBGalleryPhotoData.CB_GALLERY_PHOTO_RESOURCE_TYPE_DB);
//		    pd.setResourcePath(data.getPath());
//		    pd.setMediaId(cur.getLong(colID));
//		    photos.add(pd);
//
//		    bNewBucket = false;
//		} else {
//		    CBGalleryPhotoData pd = new CBGalleryPhotoData(photos.size());
//		    pd.setResourceType(CBGalleryPhotoData.CB_GALLERY_PHOTO_RESOURCE_TYPE_DB);
//		    pd.setResourcePath(data.getPath());
//		    pd.setMediaId(cur.getLong(colID));
//		    photos.add(pd);
//		}
//	    } while (cur.moveToNext());
//
//	    cur.close();
//	}
//
//	return albums;
//    }
//
//    public void asyncLoadCenterCropedBitmapWithCache(CBGalleryPhotoData data, SCGModel model, int reqW, int reqH) {
//	mBitmaploader.asyncLoadCenterCropedBitmapWithCache(data, model, reqW, reqH);
//    }
//
//    public void ayncloadCenterCropedBitmap(CBGalleryPhotoData data, SCGModel model, int reqW, int reqH) {
//	mBitmaploader.ayncloadCenterCropedBitmap(data, model, reqW, reqH);
//    }
//
//    public void asyncLoadBitmapWithCache(CBGalleryPhotoData data, SCGModel model, int reqW, int reqH) {
//	mBitmaploader.asyncLoadBitmapWithCache(data, model, reqW, reqH);
//    }
//
//    public void asyncLoadBitmap(CBGalleryPhotoData data, SCGModel model, int reqW, int reqH) {
//	mBitmaploader.asyncLoadBitmap(data, model, reqW, reqH);
//    }
}

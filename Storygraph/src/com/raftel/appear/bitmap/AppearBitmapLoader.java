package com.raftel.appear.bitmap;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

public class AppearBitmapLoader {
	private Context mContext;
	private LruCache<String, Bitmap> mMemoryCache;

	public interface Callback {
		public void setTextureLoadTask(TextureLoadTask task);
		public TextureLoadTask getTextureLoadTask();
		public void onBitmapLoaded(Bitmap bitmap);
	}
	
	public AppearBitmapLoader(Context context) {
		mContext = context;
		// Get memory class of this device, exceeding this amount will throw an OutOfMemory exception.
		final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = 1024 * 1024 * memClass / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in bytes rather than number of items.
				return bitmap.getByteCount();
			}
		};
	}

	public String getCacheKey(AppearBitmapData data) {
		String key = null;

		switch (data.getType()) {
		case AppearBitmapData.BITMAP_TYPE_RES_ID:
			key = String.valueOf(data.getResourceId());
			break;
		case AppearBitmapData.BITMAP_TYPE_MEDIA_STORE:
			key = String.valueOf(data.getMediaId());
			break;
		case AppearBitmapData.BITMAP_TYPE_FILE:
			key = String.valueOf(data.getLocation());
			break;
		default:
			break;
		}

		return key;
	}

	public Bitmap getBitmapFromMemCache(String key) {
		if (key == null)
			return null;

		return (Bitmap) mMemoryCache.get(key);
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public void loadBitmap(final AppearBitmapData data, final AppearBitmapLoader.Callback callback, final int reqW, final int reqH, final boolean doCache) {

		final String imageKey = getCacheKey(data);
		Bitmap bitmap = getBitmapFromMemCache(imageKey);

		if (bitmap != null) {
			callback.onBitmapLoaded(bitmap);
		} else {
			Activity av = (Activity) mContext;
			av.runOnUiThread(new Runnable() {
				public void run() {
					if (cancelPotentialWork(data, callback)) {
						final TextureLoadTask task = new TextureLoadTask(callback, reqW, reqH, doCache);
						callback.setTextureLoadTask(task);
						task.execute(data);
					}
				}
			});
		}
	}

	public static boolean cancelPotentialWork(AppearBitmapData data, AppearBitmapLoader.Callback callback) {
		final TextureLoadTask task = (TextureLoadTask)callback.getTextureLoadTask();

		if (task != null && task instanceof TextureLoadTask) {
			final AppearBitmapData prevData = task.data;
			if (prevData != data) {
				// Cancel previous task
				task.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}

		// No task associated with the ImageView, or an existing task was cancelled
		return true;
	}

	public class TextureLoadTask extends AsyncTask<AppearBitmapData, Void, Bitmap> {
		private final WeakReference<AppearBitmapLoader.Callback> mCallback;
		private AppearBitmapData data = null;
		private int mReqWidth;
		private int mReqHeight;
		private boolean mDoCache;

		public TextureLoadTask(AppearBitmapLoader.Callback callback, int reqWidth, int reqHeight, boolean doCache) {
			// Use a WeakReference to ensure the ImageView can be garbage collected
			mCallback = new WeakReference<AppearBitmapLoader.Callback>(callback);
			mReqWidth = reqWidth;
			mReqHeight = reqHeight;
			mDoCache = doCache;
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(AppearBitmapData... params) {
			Bitmap bitmap = null;
			data = params[0];

			synchronized (data) {

				if (mDoCache == true) {
					bitmap = getBitmapFromMemCache((getCacheKey(data)));
				}

				if (bitmap == null) {
					switch (data.getType()) {
					case AppearBitmapData.BITMAP_TYPE_FILE:
						bitmap = AppearBitmapUtil.decodeSampledBitmapFromFile(data.getLocation(), mReqWidth, mReqHeight);
						break;
					case AppearBitmapData.BITMAP_TYPE_MEDIA_STORE:
						Bitmap msBitmap = AppearBitmapUtil.loadBitmapFromMediaStore(data.getContentResolver(), data.getMediaId());
						bitmap = Bitmap.createScaledBitmap(msBitmap, mReqWidth, mReqHeight, true);
						break;
					case AppearBitmapData.BITMAP_TYPE_RES_ID:
						bitmap = AppearBitmapUtil.decodeSampledBitmapFromResource(data.getResources(), data.getResourceId(), mReqWidth, mReqHeight);
						break;
					default:
						break;
					}

					if (mDoCache == true) {
						if (bitmap != null) {
							addBitmapToMemoryCache((getCacheKey(data)), bitmap);
						}

					}
				}
			}
			return bitmap;
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (mCallback != null && bitmap != null) {
				final AppearBitmapLoader.Callback callback = mCallback.get();
				if (callback != null) {
					callback.onBitmapLoaded(bitmap);
					callback.setTextureLoadTask(null);
				}
			}
		}
	}
}

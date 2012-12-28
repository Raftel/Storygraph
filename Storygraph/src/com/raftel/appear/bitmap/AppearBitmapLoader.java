package com.raftel.appear.bitmap;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.raftel.appear.graphics.AppearModel;

public class AppearBitmapLoader {
	private Context mContext;
	private LruCache<String, Bitmap> mMemoryCache;

	public AppearBitmapLoader(Context context) {
		mContext = context;
		// Get memory class of this device, exceeding this amount will throw an
		// OutOfMemory exception.
		final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = 1024 * 1024 * memClass / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in bytes rather than number
				// of items.
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

	public void loadBitmap(final AppearBitmapData data, final AppearModel model, final int reqW, final int reqH, final boolean doCache) {

		final String imageKey = getCacheKey(data);

		Bitmap bitmap = getBitmapFromMemCache(imageKey);

		if (bitmap != null) {
			model.setTexture(bitmap);
		} else {
			Activity av = (Activity) mContext;
			av.runOnUiThread(new Runnable() {
				public void run() {
					if (cancelPotentialWork(data, model)) {
						final TextureLoadTask task = new TextureLoadTask(model, reqW, reqH, doCache);

						// TODO : Check by gooson
						// if (model instanceof AppearModel) {
						// ((AppearModel)model).setTexLoaderTask(task);
						// }

						task.execute(data);
					}
				}
			});
		}
	}

	public static boolean cancelPotentialWork(AppearBitmapData data, AppearModel model) {
		final TextureLoadTask task = null;

		// TODO : Check by gooson
		// if (model instanceof AppearModel) {
		// task = ((AppearModel) model).getTexLoaderTask();
		// }

		if (task != null) {
			final AppearBitmapData prevData = task.data;
			if (prevData != data) {
				// Cancel previous task
				task.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}

		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	public class TextureLoadTask extends AsyncTask<AppearBitmapData, Void, Bitmap> {
		private final WeakReference<AppearModel> mModel;
		private AppearBitmapData data = null;
		private int mReqWidth;
		private int mReqHeight;
		private boolean mDoCache;

		public TextureLoadTask(AppearModel model, int reqWidth, int reqHeight, boolean doCache) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			mModel = new WeakReference<AppearModel>(model);
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

			if (mModel != null && bitmap != null) {
				final AppearModel model = mModel.get();
				if (model != null) {

					if (mDoCache == true)
						model.setTexture(bitmap);
					else
						model.setTexture(bitmap, true);

					// TODO : Check by gooson
					// if (model instanceof CBGallery3DModel) {
					// 		((CBGallery3DModel) model).setTexLoaderTask(null);
					// }
				}
			}
		}
	}
}

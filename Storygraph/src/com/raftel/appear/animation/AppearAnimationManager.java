package com.raftel.appear.animation;

import java.util.Vector;

public class AppearAnimationManager {

	private static AppearAnimationManager mManager;
	private Vector<AppearAnimation> mAnims = new Vector<AppearAnimation>();

	public static AppearAnimationManager getInstance() {
		if (mManager == null)
			mManager = new AppearAnimationManager();

		return mManager;
	}

	private AppearAnimationManager() {
	}

	public AppearAnimation createAnimation(long delay, long duration, int interpolationType) {
		AppearAnimation newAnimation = new AppearAnimation(delay, duration, interpolationType);
		mAnims.add(newAnimation);

		return newAnimation;
	}

	public void doAnimations() {
		if (!hasAnimation()) {
			return;
		}

		long now = System.currentTimeMillis();

		for (int i = 0; i < mAnims.size();) {
			if (mAnims.get(i).isRunning())
				mAnims.get(i).doAnimation(now);

			if (mAnims.get(i).isFinished()) {
				mAnims.remove(i);
			} else {
				i++;
			}
		}
	}

	public boolean hasAnimation() {
		return mAnims.size() > 0 ? true : false;
	}
}

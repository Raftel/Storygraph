package com.raftel.appear.animation.expand;

import com.raftel.appear.animation.AppearAnimation;
import com.raftel.appear.animation.AppearAnimationManager;
import com.raftel.appear.animation.AppearAnimationProp;

import android.view.View;

public class AppearAnimationSet {

	public static void spin(View view) {
		AppearAnimation anim = AppearAnimationManager.getInstance().createAnimation(0, 500, AppearAnimation.FUNC_EASE_OUT);
        anim.addProperty(new AppearAnimationProp(AppearAnimationProp.PROP_ROTATE_Y, view, 0.0f, 360.0f));
        anim.start();
	}
	
	public static void swing(View view) {
		AppearAnimation animRight = AppearAnimationManager.getInstance().createAnimation(0, 1000, AppearAnimation.FUNC_EASE_OUT);
		animRight.addProperty(new AppearAnimationProp(AppearAnimationProp.PROP_TRANSLATE_X, view, view.getTranslationX(), view.getRootView().getWidth()));
        
		AppearAnimation animLeft = AppearAnimationManager.getInstance().createAnimation(500, 500, AppearAnimation.FUNC_EASE_OUT);
        animLeft.addProperty(new AppearAnimationProp(AppearAnimationProp.PROP_TRANSLATE_X, view, view.getRootView().getWidth(), -view.getRootView().getWidth()));
        
        animRight.addNextAnimation(animLeft);
        animRight.start();
	}
}

package com.raftel.storygraph.scenebrowser;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.raftel.appear.animation.AppearAnimation;
import com.raftel.appear.animation.AppearAnimationManager;
import com.raftel.appear.animation.AppearAnimationProp;
import com.raftel.appear.graphics.AppearMaterial;
import com.raftel.appear.graphics.AppearModel;
import com.raftel.appear.graphics.AppearNode;
import com.raftel.appear.graphics.AppearSurface;
import com.raftel.appear.graphics.expand.AppearRectangleMesh;
import com.raftel.appear.graphics.expand.AppearSphereMesh;
import com.raftel.appear.graphics.expand.AppearTouchableScene;
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

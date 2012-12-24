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
import com.raftel.appear.graphics.AppearScene;
import com.raftel.appear.graphics.expand.AppearRectangleMesh;
import com.raftel.appear.graphics.expand.AppearSphereMesh;
import com.raftel.storygraph.R;

public class SGSceneBrowser extends AppearScene {

	Context mContext;
	
	public SGSceneBrowser(Context context) {
		mContext = context;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_earth);
		
		AppearSphereMesh sphereMesh = new AppearSphereMesh(50, 20);
		AppearRectangleMesh rectangleMesh = new AppearRectangleMesh(400, 200, 50);
		
		AppearMaterial material = new AppearMaterial();
		material.setColor(0xff00ffff);
		material.setTexture(bitmap, false);
		
		AppearModel model = new AppearModel();
		//model.setMesh(rectMesh);
		//model.setMesh(sphereMesh);
		model.setMesh(rectangleMesh);
		model.setMaterial(material);
		model.setTranslation(200, 200, 0);
				
		AppearNode node = new AppearNode();
		node.addChild(model);
		addRenderNode(node);
		
		AppearAnimation anim = AppearAnimationManager.getInstance().createAnimation(1000, 10000, AppearAnimation.FUNC_EASE_OUT);
		anim.addProperty(new AppearAnimationProp(AppearAnimationProp.PROP_ROTATE_X, model, 0, 360));
		anim.start();
	}
}

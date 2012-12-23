package com.raftel.storygraph.scenebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.raftel.appear.AppearMaterial;
import com.raftel.appear.AppearModel;
import com.raftel.appear.AppearScene;
import com.raftel.appear.expand.AppearSphereMesh;
import com.raftel.storygraph.R;

public class SGSceneBrowser extends AppearScene {

	Context mContext;
	
	public SGSceneBrowser(Context context) {
		mContext = context;
		
		AppearSphereMesh mesh = new AppearSphereMesh(50, 20);
		AppearMaterial material = new AppearMaterial();
		material.setColor(0xff00ffff);
		
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_earth);
		material.setTexture(bitmap, true);
		
		
		AppearModel model = new AppearModel();
		model.setMesh(mesh);
		model.setMaterial(material);
		model.setTranslation(100, 100, 0);
		
		addRenderNode(model);
	}
}

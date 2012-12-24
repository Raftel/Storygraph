package com.raftel.storygraph.scenebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.raftel.appear.AppearMaterial;
import com.raftel.appear.AppearModel;
import com.raftel.appear.AppearNode;
import com.raftel.appear.AppearScene;
import com.raftel.appear.expand.AppearRectMesh;
import com.raftel.appear.expand.AppearSphereMesh;
import com.raftel.storygraph.R;

public class SGSceneBrowser extends AppearScene {

	Context mContext;
	
	public SGSceneBrowser(Context context) {
		mContext = context;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_earth);
		
		AppearRectMesh rectMesh = new AppearRectMesh(500, 100);
		AppearSphereMesh sphereMesh = new AppearSphereMesh(50, 20);
		
		AppearMaterial material = new AppearMaterial();
		material.setColor(0xff00ffff);
		material.setTexture(bitmap, false);
		
		AppearModel model = new AppearModel();
		//model.setMesh(rectMesh);
		model.setMesh(sphereMesh);
		model.setMaterial(material);
		model.setTranslation(200, 200, 0);
				
		AppearNode node = new AppearNode();
		node.addChild(model);
		addRenderNode(node);
	}
}

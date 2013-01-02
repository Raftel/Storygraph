package com.raftel.appear.resource;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

public class FolderListView extends FrameLayout implements OnItemClickListener {

	public FolderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

//    private LinearLayout mLiearLayout;
//    private ListView mListView;
//    private TextView myPath;
//    private TextView mListTitle;
//    
//    
//    private String mCurrentPath;
//
//    private List<String> item = null;
//    private List<String> path = null;
//
//    private String root = "/";
//
//    public FolderListView(Context context, String title, String path) {
//	super(context);
//	this.setBackgroundColor(0x00000000);
//
//	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	mLiearLayout = (LinearLayout) inflater.inflate(R.layout.cb_gallery_folder_list, null);
//	this.addView(mLiearLayout);
//
//	mListView = (ListView) findViewById(R.id.list);
//	mListTitle = (TextView) findViewById(R.id.list_title);
//	myPath = (TextView) findViewById(R.id.path);
//
//	mListView.setOnItemClickListener(this);
//	mListTitle.setText(title);
//
//	getDir(path);
//    }
//
//    private void getDir(String dirPath) {
//	mCurrentPath = dirPath;
//	myPath.setText("Location: " + dirPath);
//
//	item = new ArrayList<String>();
//	path = new ArrayList<String>();
//
//	File f = new File(dirPath);
//	File[] files = f.listFiles();
//
//	/* item, path Ãß°¡ */
//	if (!dirPath.equals(root)) {
//	    item.add("../");
//	    path.add(f.getParent());
//	}
//
//	for (int i = 0; i < files.length; i++) {
//	    File file = files[i];
//	    
//	    if (file.isDirectory() && file.getPath().contains(".") == false) {
//		item.add(file.getName() + "/");
//		path.add(file.getPath());
//	    }
//	}
//
//	ArrayAdapter<String> fileList = new ArrayAdapter<String>(this.getContext(), R.layout.cb_gallery_folder_list_row, item);
//	mListView.setAdapter(fileList);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//	File file = new File(path.get(position));
//
//	if (file.isDirectory()) {
//	    if (file.canRead())
//		getDir(path.get(position));
//	    else {
//		new AlertDialog.Builder(this.getContext()).setTitle("[" + file.getName() + "] folder can't be read!")
//			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			    public void onClick(DialogInterface dialog, int which) {
//			    }
//			}).show();
//	    }
//	}
//    }
//    
//    public String getCurrentPath() {
//	return mCurrentPath;
//    }
}
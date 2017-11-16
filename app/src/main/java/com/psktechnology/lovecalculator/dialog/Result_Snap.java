package com.psktechnology.lovecalculator.dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.psktechnology.lovecalculator.R;
import com.psktechnology.lovecalculator.global.Utils;

public class Result_Snap extends Dialog {
	
	TextView tvu, tvurp, tvpercentage;
	TextView tvloves;
	
	String u, urp, percentage;
	Activity activity;

	public Result_Snap(Activity activity, String u, String urp, String percentage) {
		super(activity);
		this.activity = activity;
		this.u = u;
		this.urp = urp;
		this.percentage = percentage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result_snap);

		init();
	}

	private void init() {
		tvu = (TextView)findViewById(R.id.tvu);
		tvurp = (TextView)findViewById(R.id.tvurp);
		tvpercentage = (TextView)findViewById(R.id.tvlovepercentage);
		tvloves = (TextView)findViewById(R.id.tvloves);
		
		tvloves.setTypeface(Utils.setFont(activity, Utils.FONT_AWESOME));
		
		tvu.setText(u.trim());
		tvurp.setText(urp.trim());
		tvpercentage.setText(percentage+"%");
		
		getBitmap();
	}

	private void getBitmap() {
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		
		Bitmap bmap = view.getDrawingCache();
		
//		int contentViewTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop(); /* skip status bar in screenshot */
//		Storage.shareBitmapInfo = Bitmap.createBitmap(bmap, 0, contentViewTop, bmap.getWidth(), bmap.getHeight() - contentViewTop, null, true);
		
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");    
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete ();
		try {
		       FileOutputStream out = new FileOutputStream(file);
		       bmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();

		} catch (Exception e) {
		       e.printStackTrace();
		}
		
		view.setDrawingCacheEnabled(false);

//		new Result_Dialog(activity, u, urp, percentage, bmap).show();
		
	}

}
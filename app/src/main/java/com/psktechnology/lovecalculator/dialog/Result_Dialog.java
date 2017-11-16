package com.psktechnology.lovecalculator.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.psktechnology.lovecalculator.R;
import com.psktechnology.lovecalculator.global.Utils;

public class Result_Dialog extends Dialog implements OnClickListener{
	
	TextView tvu, tvurp, tvpercentage;
	Button btnclose;
	
	TextView tvmail, tvshare, tvsms, tvloves;
	
	String u, urp, percentage;
	Activity activity;
	
	ClearData clearData;
	//private InterstitialAd interstitial;
	
	public interface ClearData {
		void clearData();
	}

	public Result_Dialog(Activity activity, String u, String urp, String percentage) {
		super(activity);
		this.activity = activity;
		this.clearData = (ClearData) activity;
		this.u = u;
		this.urp = urp;
		this.percentage = percentage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result_dialog);
		
		init();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnclose:
			onBackPressed();
			break;
			
		case R.id.tvmail:
			String msg = "Love Calculator\n" + u + " Loves " + urp + " = " + percentage + "%" + "\n\n" + "Test your love\n" + Utils.PLAY_STORE_LINK;
			Utils.sendEmail(activity, msg);
			break;
			
		case R.id.tvshare:
			msg = "Love Calculator\n" + u + " Loves " + urp + " = " + percentage + "%" + "\n\n" + "Test your love\n" + Utils.PLAY_STORE_LINK;
			Utils.share(activity, msg);
			break;
			
		case R.id.tvsms:
			msg = "Love Calculator\n" + u + " Loves " + urp + " = " + percentage + "%" + "\n\n" + "Test your love\n" + Utils.PLAY_STORE_LINK;
			Utils.sendSMS(activity, msg);
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		dismiss();
		clearData.clearData();
		//displayInterstitial();
	}

/*	private void displayInterstitial() {
		if (interstitial.isLoaded()) {
            interstitial.show();
        }
	}*/

	private void init() {
		tvu = (TextView)findViewById(R.id.tvu);
		tvurp = (TextView)findViewById(R.id.tvurp);
		tvpercentage = (TextView)findViewById(R.id.tvlovepercentage);
		
		tvmail = (TextView)findViewById(R.id.tvmail);
		tvshare = (TextView)findViewById(R.id.tvshare);
		tvsms = (TextView)findViewById(R.id.tvsms);
		tvloves = (TextView)findViewById(R.id.tvloves);
		
		tvmail.setTypeface(Utils.setFont(activity, Utils.FONT_AWESOME));
		tvshare.setTypeface(Utils.setFont(activity, Utils.FONT_AWESOME));
		tvsms.setTypeface(Utils.setFont(activity, Utils.FONT_AWESOME));
		tvloves.setTypeface(Utils.setFont(activity, Utils.FONT_AWESOME));
		
		btnclose = (Button)findViewById(R.id.btnclose);
		
		btnclose.setOnClickListener(this);
		tvmail.setOnClickListener(this);
		tvshare.setOnClickListener(this);
		tvsms.setOnClickListener(this);
		
		tvu.setText(u.trim());
		tvurp.setText(urp.trim());
		tvpercentage.setText(percentage+"%");
		
/*		interstitial = new InterstitialAd(activity);
        interstitial.setAdUnitId(Utils.INTERSTITIAL_ON_RETRY);

		AdRequest adRequest = new AdRequest.Builder()
				.build();

		interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
        });*/
	}

}
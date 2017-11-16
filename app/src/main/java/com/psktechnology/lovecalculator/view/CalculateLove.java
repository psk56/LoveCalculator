package com.psktechnology.lovecalculator.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.EStickeezPosition;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.psktechnology.lovecalculator.R;
import com.psktechnology.lovecalculator.global.Utils;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class CalculateLove extends AppCompatActivity implements OnClickListener {

	Button btnname, btnbday, btnage, btnzodiac;

	private AdView adView;
	private InterstitialAd interstitial;

	private StartAppAd startAppAd = new StartAppAd(CalculateLove.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.psktechnology.lovecalculator.R.layout.calculate_love);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		init();

		StartAppSDK.init(CalculateLove.this, Utils.STARTAPP_AD_DEVELOPER_ID, Utils.STARTAPP_AD_APP_ID, true); //TODO: Replace with your IDs
		StartAppAd.showSlider(CalculateLove.this);

		MobileCore.init(CalculateLove.this, Utils.MOBILECARE_DEV_HASH_KEY, LOG_TYPE.DEBUG, AD_UNITS.ALL_UNITS);
		if (MobileCore.isStickeeReady()) {
			MobileCore.showStickee(CalculateLove.this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.btnname:
				calculateByName();
				break;

			case R.id.btnbday:
				calculateByBday();
				break;

			case R.id.btnage:
				calculateByAge();
				break;

			case R.id.btnzodiac:
				calculateByZodiac();
				break;

			default:
				break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		startAppAd.onResume();
		requestRandomStickeePosition();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		startAppAd.onPause();
		if (adView != null) {
			adView.pause();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		startAppAd.onBackPressed();
		displayInterstitial();
	}

	private void init() {
		btnname = (Button) findViewById(R.id.btnname);
		btnbday = (Button) findViewById(R.id.btnbday);
		btnage = (Button) findViewById(R.id.btnage);
		btnzodiac = (Button) findViewById(R.id.btnzodiac);

		btnname.setOnClickListener(this);
		btnbday.setOnClickListener(this);
		btnage.setOnClickListener(this);
		btnzodiac.setOnClickListener(this);

		adView = new AdView(CalculateLove.this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(Utils.BANNER_TOP_ON_CALCULATE_LOVE);

		LinearLayout layout = (LinearLayout) findViewById(R.id.adlayout);
		layout.addView(adView);

		interstitial = new InterstitialAd(CalculateLove.this);
		interstitial.setAdUnitId(Utils.INTERSTITIAL_ON_APP_EXIT);

		AdRequest adRequest = new AdRequest.Builder()
				.build();


		// Start loading the ad in the background.
		adView.loadAd(adRequest);
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
                displayInterstitial();
			}
		});
	}

	private void calculateByName() {
		startActivity(new Intent(CalculateLove.this, Calculation.class)
				.putExtra(Utils.CALCULATION_METHOD, 1));
	}

	private void calculateByBday() {
		startActivity(new Intent(CalculateLove.this, Calculation.class)
				.putExtra(Utils.CALCULATION_METHOD, 2));
	}

	private void calculateByAge() {
		startActivity(new Intent(CalculateLove.this, Calculation.class)
				.putExtra(Utils.CALCULATION_METHOD, 3));
	}

	private void calculateByZodiac() {
		startActivity(new Intent(CalculateLove.this, Calculation.class)
				.putExtra(Utils.CALCULATION_METHOD, 4));
	}

	private void displayInterstitial() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

	private void requestRandomStickeePosition() {

		if (MobileCore.isStickeeReady()) {
			MobileCore.showStickee(CalculateLove.this);
		}

		final List<EStickeezPosition> stickeePositions = Collections.unmodifiableList(Arrays.asList(EStickeezPosition.values()));
		final Random randomObj = new Random();
		int numOfOptions = stickeePositions.size() + 1; // Preset positions or anchoring at example anchor.

		if (Math.random() * numOfOptions < 1) { // gives anchoring stickee a fair chance to appear.
			MobileCore.setStickeezPositionBelowView(CalculateLove.this, R.id.btnzodiac);
		} else {
			MobileCore.setStickeezPosition(stickeePositions.get(randomObj.nextInt(stickeePositions.size())));
		}
	}

}
package com.psktechnology.lovecalculator.global;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Utils {
	
	public static final String CALCULATION_METHOD = "CALCULATION_METHOD";
	
	public static final String PLZ_ENTER_UR_NAME = "Please enter Your Name";
	public static final String PLZ_ENTER_UR_PARTNERS_NAME = "Please enter Your Partner's Name";
	
	public static final String PLZ_SET_UR_BDATE = "Please set Your Birthdate";
	public static final String PLZ_SET_UR_PARTNERS_BDATE = "Please set Your Partner's Birthdate";
	
	public static final String PLZ_ENTER_UR_AGE = "Please enter Your Age";
	public static final String PLZ_ENTER_UR_VALID_AGE = "Please enter Your Valid Age";
	public static final String PLZ_ENTER_UR_PARTNERS_AGE = "Please enter Your Partner's Age";
	public static final String PLZ_ENTER_UR_PARTNERS_VALID_AGE = "Please enter Your Partner's Valid Age";
	
	public static final String FONT_AWESOME = "fontawesome_webfont.ttf";
	
	public static final String STARTAPP_AD_DEVELOPER_ID = "111467081";
	public static final String STARTAPP_AD_APP_ID = "211260077";
	
	public static final String MOBILECARE_DEV_HASH_KEY = "KGDSMVAOOAY3SE6MDZGILZ1UIAX7";
	public static final String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.psktechnology.lovecalculator";
	
	
	public static final String BANNER_TOP_ON_CALCULATE_LOVE = "ca-app-pub-8653645062546222/7781838998";
	public static final String BANNER_BOTTOM_ON_CALCULATION = "ca-app-pub-8653645062546222/9258572192";
	public static final String INTERSTITIAL_ON_RETRY = "ca-app-pub-8653645062546222/1735305394";
	public static final String INTERSTITIAL_ON_APP_EXIT = "ca-app-pub-8653645062546222/3212038598";
	
	public static void toast(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static Typeface setFont(Activity activity, String fontname) {
		Typeface font = Typeface.createFromAsset(activity.getAssets(), fontname);
		return font;
	}
	
	public static void sendSMS(Activity activity, String msg) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("sms_body", msg);
		activity.startActivity(smsIntent);
	}

	public static void sendEmail(Activity activity, String msg) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Love Calculator");
		emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
		emailIntent.setType("message/rfc822");
		activity.startActivity(Intent.createChooser(emailIntent, "Email:"));
	}
	
	public static void share(Activity activity, String msg) {
		List<Intent> targetedShareIntents = new ArrayList<Intent>();

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, msg);

		List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

		for (ResolveInfo resolveInfo : resInfo) {
			String packageName = resolveInfo.activityInfo.packageName;

			Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
			targetedShareIntent.setType("text/plain");
			targetedShareIntent.putExtra(Intent.EXTRA_TEXT, msg);
			targetedShareIntent.setPackage(packageName);
			
			if (packageName.contains("com.facebook.katana")) {
				targetedShareIntents.add(targetedShareIntent);
			} else if (packageName.contains("com.google.android.apps.plus")) {
				targetedShareIntents.add(targetedShareIntent);
			} else if (packageName.contains("com.whatsapp")) {
				targetedShareIntents.add(targetedShareIntent);
			} else if (packageName.contains("com.twitter.android")) {
				targetedShareIntents.add(targetedShareIntent);
			}

		}

		// Add my own activity in the share Intent chooser
		// Intent i = new Intent(this, NextActivity.class);
		// targetedShareIntents.add(i);

		Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share:");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
		activity.startActivity(chooserIntent);
	}
	
	public static String getDeviceId(Activity activity) {
		TelephonyManager tm =(TelephonyManager)activity.getSystemService(activity.TELEPHONY_SERVICE);
	    return tm.getDeviceId();
	}

}
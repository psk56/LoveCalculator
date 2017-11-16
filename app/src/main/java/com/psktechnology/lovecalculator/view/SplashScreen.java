package com.psktechnology.lovecalculator.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.psktechnology.lovecalculator.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SplashScreen extends AppCompatActivity {
	
	static final Integer SPLASH_DISPLAY_LENGTH = 2000;
	private AtomicBoolean mRequestPermissionsInProcess = new AtomicBoolean();
	private static final int REQUEST_PERMISSION = 3;
	private static final String PARAM_REQUEST_IN_PROCESS = "Permission";
	private static final String PREFERENCE_PERMISSION_DENIED = "PREFERENCE_PERMISSION_DENIED";
	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_screen);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		if (savedInstanceState != null) {
			boolean tmp = savedInstanceState.getBoolean(PARAM_REQUEST_IN_PROCESS, false);
			mRequestPermissionsInProcess.set(tmp);
		}

		if (Build.VERSION.SDK_INT >= 23){
			if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				init();
			} else {
				checkPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE});
				init();
			}
		}else{
			init();
		}


	}

	private void init() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(SplashScreen.this, CalculateLove.class));
				finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}

	private void checkPermissions(String[] permissions) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			checkPermissionInternal(permissions);
		}else {
			init();
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	private boolean checkPermissionInternal(String[] permissions) {
		ArrayList<String> requestPerms = new ArrayList<String>();
		for (String permission : permissions) {
			if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED && !userDeniedPermissionAfterRationale(permission)) {
				requestPerms.add(permission);
			}
		}
		if (requestPerms.size() > 0 && !mRequestPermissionsInProcess.getAndSet(true)) {
			//  We do not have this essential permission, ask for it
			requestPermissions(requestPerms.toArray(new String[requestPerms.size()]), REQUEST_PERMISSION);
			return true;
		}

		return false;
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == REQUEST_PERMISSION) {
			for (int i = 0, len = permissions.length; i < len; i++) {
				String permission = permissions[i];
				if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
					if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
						showRationale(permission, R.string.permission_denied_storage);
					} else if (android.Manifest.permission.READ_PHONE_STATE.equals(permission)) {
						showRationale(permission, R.string.permission_denied_storage);
					}
				}else {
					init();
				}
			}
		}
	}


	@TargetApi(Build.VERSION_CODES.M)
	private void showRationale(final String permission, int promptResId) {
		if (shouldShowRequestPermissionRationale(permission) && !userDeniedPermissionAfterRationale(permission)) {

			//  Notify the user of the reduction in functionality and possibly exit (app dependent)
			new AlertDialog.Builder(this)
					.setTitle(R.string.permission_denied)
					.setMessage(promptResId)
					.setPositiveButton(R.string.permission_deny, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								dialog.dismiss();
							} catch (Exception ignore) {
							}
							setUserDeniedPermissionAfterRationale(permission);
							mRequestPermissionsInProcess.set(false);
						}
					})
					.setNegativeButton(R.string.permission_retry, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								dialog.dismiss();
							} catch (Exception ignore) {
							}
							mRequestPermissionsInProcess.set(false);
							checkPermissions(new String[]{permission});
						}
					})
					.show();
		} else {
			mRequestPermissionsInProcess.set(false);
		}
	}

	private boolean userDeniedPermissionAfterRationale(String permission) {
		SharedPreferences sharedPrefs = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
		return sharedPrefs.getBoolean(PREFERENCE_PERMISSION_DENIED + permission, false);
	}

	private void setUserDeniedPermissionAfterRationale(String permission) {
		SharedPreferences.Editor editor = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE).edit();
		editor.putBoolean(PREFERENCE_PERMISSION_DENIED + permission, true).commit();
	}

}
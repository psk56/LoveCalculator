package com.psktechnology.lovecalculator.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.EStickeezPosition;
import com.psktechnology.lovecalculator.global.AgeCalculation;
import com.psktechnology.lovecalculator.global.Love;
import com.psktechnology.lovecalculator.R;
import com.psktechnology.lovecalculator.global.Utils;
import com.psktechnology.lovecalculator.dialog.Result_Dialog;
import com.psktechnology.lovecalculator.dialog.Result_Dialog.ClearData;
import com.startapp.android.publish.StartAppAd;

public class Calculation extends AppCompatActivity implements OnClickListener, ClearData {
	
	EditText etyou, etyourpartner;
	Spinner spyou, spyourpartner;
	Button btncalculate;
	
	Bundle bundle;
	static Integer calculateMethod;
	
	String u, urp, urBdate, urpBdate;
	String percentage;
	
	static Integer dateFor;
	
	Calendar cal;
	Integer day;
	Integer month;
	Integer year;

	Integer ur_day, ur_p_day;
	Integer ur_month, ur_p_month;
	Integer ur_year, ur_p_year;

	ArrayList<String> zodiac;
	ArrayAdapter<String> adptZodic;
	
	private AdView adView;
	private StartAppAd startAppAd = new StartAppAd(Calculation.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.calculation);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		init();
		getBundleData();

		StartAppAd.showSlider(Calculation.this);


		if (MobileCore.isStickeeReady()) {
			MobileCore.showStickee(Calculation.this);
		}

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btncalculate:
			calculateLove(calculateMethod);
			break;
			
		case R.id.etyou:
			youDatePicker(0).show();
			dateFor = 1;
			break;
			
		case R.id.etyourpartner:
			youDatePicker(0).show();
			dateFor = 2;
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
	}

	private void init() {
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		  
		etyou = (EditText)findViewById(R.id.etyou);
		etyourpartner = (EditText)findViewById(R.id.etyourpartner);
		
		spyou = (Spinner)findViewById(R.id.spyou);
		spyourpartner = (Spinner)findViewById(R.id.spyourpartner);
		btncalculate = (Button)findViewById(R.id.btncalculate);
		
		btncalculate.setOnClickListener(this);
		
		adView = new AdView(Calculation.this);
	    adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
	    adView.setAdUnitId(Utils.BANNER_BOTTOM_ON_CALCULATION);

	    LinearLayout layout = (LinearLayout) findViewById(R.id.adlayout);
	    layout.addView(adView);
	    
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice(Utils.getDeviceId(Calculation.this))
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
	}
	
	private void getBundleData() {
		
		bundle = getIntent().getExtras();
		if (bundle != null) {
			calculateMethod = bundle.getInt(Utils.CALCULATION_METHOD, 0);
			
			switch (calculateMethod) {
			case 1:
				setViewsByName();
				break;
				
			case 2:
				setViewsByBdate();
				break;
				
			case 3:
				setViewsByAge();
				break;
				
			case 4:
				setViewsByZodiac();
				break;

			default:
				break;
			}
		}
		
	}
	
	private void setViewsByName() {
		spyou.setVisibility(View.GONE);
		spyourpartner.setVisibility(View.GONE);
		
		etyou.setHint("Your Name");
		etyourpartner.setHint("Your Partner's Name");
		
		etyou.setFilters(new InputFilter[] {
				new InputFilter() {
			        public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
			            if(src.equals("")){ // for backspace
			                return src;
			            }
			            if(src.toString().matches("[a-zA-Z]+")){
			                return src;
			            }
			            return "";
			        }
			    }
			});
		
		etyourpartner.setFilters(new InputFilter[] {
				new InputFilter() {
			        public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
			            if(src.equals("")){ // for backspace
			                return src;
			            }
			            if(src.toString().matches("[a-zA-Z]+")){
			                return src;
			            }
			            return "";
			        }
			    }
			});
		
		
	}
	
	private void setViewsByBdate() {
		spyou.setVisibility(View.GONE);
		spyourpartner.setVisibility(View.GONE);
		
		etyou.setHint("Click here to set Your Birthdate");
		etyourpartner.setHint("Click here to set Your Partner's Birthdate");
		
		etyou.setFocusable(false);
		etyourpartner.setFocusable(false);
		
		etyou.setOnClickListener(this);
		etyourpartner.setOnClickListener(this);
	}

	private void setViewsByAge() {
		spyou.setVisibility(View.GONE);
		spyourpartner.setVisibility(View.GONE);
		
		etyou.setHint("Your Age");
		etyourpartner.setHint("Your Partner's Age");
		
		etyou.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
		etyourpartner.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
		
		etyou.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
		etyourpartner.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
		
		// EditText must not start with Zero
		etyou.addTextChangedListener(new TextWatcher(){
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            if (etyou.getText().toString().matches("^0") )
	            	etyou.setText("");
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){	}
	        public void afterTextChanged(Editable s){	}
	    });
		
		etyourpartner.addTextChangedListener(new TextWatcher(){
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            if (etyourpartner.getText().toString().matches("^0") )
	            	etyourpartner.setText("");
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){	}
	        public void afterTextChanged(Editable s){	}
	    });
		
	}
	
	private void setViewsByZodiac() {
		etyou.setVisibility(View.GONE);
		etyourpartner.setVisibility(View.GONE);
		
		zodiac = new ArrayList<String>();
		adptZodic = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.zodiac));
		spyou.setAdapter(adptZodic);
		spyourpartner.setAdapter(adptZodic);
	}

	private void calculateLove(Integer calculateMethod) {
		switch (calculateMethod) {
		case 1:
			if(validate()) {
				calculateByName();
			}
			break;
			
		case 2:
			if(validate()) {
				calculateByBdate();
			}
			break;
			
		case 3:
			if(validate()) {
				calculateByAge();
			}
			break;
	
		case 4:
			calculateByZodiac();
			break;

		default:
			break;
		}
		
	}
	
	private boolean validate() {
		u = etyou.getText().toString().trim();
		urp = etyourpartner.getText().toString().trim();
		
		switch (calculateMethod) {
		case 1:
			if(u != null && u.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_NAME);
				etyou.requestFocus();
				etyou.selectAll();
				return false;
			} else if(urp != null && urp.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_PARTNERS_NAME);
				etyourpartner.requestFocus();
				etyourpartner.selectAll();
				return false;
			} else
				return true;
			
		case 2:
			if(u != null && u.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_SET_UR_BDATE);
				etyou.requestFocus();
				etyou.selectAll();
				return false;
			} else if(urp != null && urp.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_SET_UR_PARTNERS_BDATE);
				etyourpartner.requestFocus();
				etyourpartner.selectAll();
				return false;
			} else
				return true;
			
		case 3:
			if(u != null && u.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_AGE);
				etyou.requestFocus();
				etyou.selectAll();
				return false;
			} else if(u.equalsIgnoreCase("0") || u.equalsIgnoreCase("00") || u.equalsIgnoreCase("000")) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_VALID_AGE);
				etyou.requestFocus();
				etyou.selectAll();
				return false;
			} else if(urp != null && urp.length() == 0) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_PARTNERS_AGE);
				etyourpartner.requestFocus();
				etyourpartner.selectAll();
				return false;
			} else if(urp.equalsIgnoreCase("0") || urp.equalsIgnoreCase("00") || urp.equalsIgnoreCase("000")) {
				Utils.toast(Calculation.this, Utils.PLZ_ENTER_UR_PARTNERS_VALID_AGE);
				etyourpartner.requestFocus();
				etyourpartner.selectAll();
				return false;
			} else
				return true;
			
			default:
				return false;

		}
	}

	private void calculateByName() {
		u = etyou.getText().toString();
		urp = etyourpartner.getText().toString();
		
		Love love = new Love();
		love.setNames(u, urp);
		percentage = String.valueOf(love);
		
		resultDialog();
	}

	private void calculateByBdate() {
		
		try {
			urBdate = ur_day + "/" + ur_month + "/" + ur_year;
			urpBdate = ur_p_day + "/" + ur_p_month + "/" + ur_p_year;
			
			u = AgeCalculation.age(ur_year, ur_month, ur_day);
			urp = AgeCalculation.age(ur_p_year, ur_p_month, ur_p_day);
			
			Love love = new Love();
			love.setNames(u, urp);
			percentage = String.valueOf(love);

			resultDialog();
		} catch (Exception e) {
			
			urBdate = ur_day + "/" + ur_month + "/" + ur_year;
			urpBdate = ur_p_day + "/" + ur_p_month + "/" + ur_p_year;
			
			u = "30";
			urp = "28";
			
			Love love = new Love();
			love.setNames(u, urp);
			percentage = String.valueOf(love);

			resultDialog();
		}

	}

	private void calculateByAge() {
		u = etyou.getText().toString();
		urp = etyourpartner.getText().toString();
		
		Love love = new Love();
		love.setNames(u, urp);
		percentage = String.valueOf(love);
		
		resultDialog();
	}

	private void calculateByZodiac() {
		u = spyou.getSelectedItem().toString();
		urp = spyourpartner.getSelectedItem().toString();
		
		Love love = new Love();
		love.setNames(u, urp);
		percentage = String.valueOf(love);
		
		resultDialog();
	}
	
	private void resultDialog() {
		if(calculateMethod == 2){
			new Result_Dialog(Calculation.this, urBdate, urpBdate, percentage).show();
		} else
			new Result_Dialog(Calculation.this, u, urp, percentage).show();
	}
	
	protected Dialog youDatePicker(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		  public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			  
			  switch (dateFor) {
			case 1:
				etyou.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
				ur_day = selectedDay;
				ur_month = (selectedMonth + 1);
				ur_year = selectedYear;
				break;
				
			case 2:
				etyourpartner.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
				ur_p_day = selectedDay;
				ur_p_month = (selectedMonth + 1);
				ur_p_year = selectedYear;
				break;

			default:
				break;
			}
		  }
	};

	@Override
	public void clearData() {
		etyou.setText("");
		etyourpartner.setText("");
		
		etyou.requestFocus();
	}
	
	private void requestRandomStickeePosition() {
		
		if (MobileCore.isStickeeReady()) {
			MobileCore.showStickee(Calculation.this);
		}
		
		final List<EStickeezPosition> stickeePositions = Collections.unmodifiableList(Arrays.asList(EStickeezPosition.values()));
		final Random randomObj = new Random();
		int numOfOptions = stickeePositions.size() + 1; // Preset positions or anchoring at example anchor.

		if (Math.random() * numOfOptions < 1) { // gives anchoring stickee a fair chance to appear.
			MobileCore.setStickeezPositionBelowView(Calculation.this, R.id.btncalculate);
		} else {
			MobileCore.setStickeezPosition(stickeePositions.get(randomObj.nextInt(stickeePositions.size())));
		}
	}

}
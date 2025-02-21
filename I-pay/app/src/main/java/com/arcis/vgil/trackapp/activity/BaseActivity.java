package com.arcis.vgil.trackapp.activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;


import com.arcis.vgil.R;
import com.arcis.vgil.activity.LoginActivity;
import com.arcis.vgil.errorNotification.GMailSender;

import java.util.LinkedHashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author jai
 * 
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener, OnCheckedChangeListener,OnItemClickListener,
		CompoundButton.OnCheckedChangeListener {

	/**
	 * Context of currrent screen
	 */
	public Context currentContext;

	/**
	 * Data Map for send request to webService
	 */
	private static LinkedHashMap<String, Object> requestDataMap = null;

	/**
	 * @return : Current Context
	 */
	public Context getCurrentContext() {
		return currentContext;
	}

	/**
	 * Set Current context
	 * 
	 * @param currentContext
	 *            : Current context
	 */
	public void setCurrentContext(Context currentContext) {
		this.currentContext = currentContext;
		int flag;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
			flag = PendingIntent.FLAG_MUTABLE;
		else
			flag =PendingIntent.FLAG_UPDATE_CURRENT;
		intent = PendingIntent.getActivity(this.currentContext ,0,new Intent(this.currentContext, LoginActivity.class), flag);
	}

	public static LinkedHashMap<String, Object> getRequestDataMap() {
		if (requestDataMap == null) {
			requestDataMap = new LinkedHashMap<String, Object>();
			return requestDataMap;
		} else {
			return requestDataMap;
		}
	}

	public static void setRequestDataMap(LinkedHashMap<String, Object> requestDataMap) {
		BaseActivity.requestDataMap = requestDataMap;
	}

	PendingIntent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//			@Override
//			public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
//				try {
//					displayErrorMessageToast();
//					Thread.sleep(2500);
//				} catch (Exception e1) {
//					Log.e("Error: ", e1.getMessage());
//				}
//				
//				Writer result = new StringWriter();
//				PrintWriter printWriter = new PrintWriter(result);
//				paramThrowable.printStackTrace(printWriter);
//				sendMail(result.toString());
//				AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//				mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, intent);
//				System.exit(2);
//			}
//		});
	
		inti();
		setDataOnScreen();
	}

	private void displayErrorMessageToast() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				String errorMsg = "Application Restarting."; // ideally this string would be in your Resources
				AlertDialog.Builder dialog=new AlertDialog.Builder(getCurrentContext());
				dialog.setMessage(errorMsg);
				dialog.show();
				Looper.loop();
			}
		}.start();
	}
	
	/**
	 * Used to initialize various elements of screens
	 */
	public void inti() {
	}

	@Override
	public void onClick(View v) {

	}



	/**
	 * Used for Various screen validations
	 */
	public boolean validation() {
		return true;
	}

	/**
	 * To check Internet connection status
	 * 
	 * @return true : if Internet connection is available
	 */

	public boolean isOnline() {
		boolean flag = false;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null) {
			flag = cm.getActiveNetworkInfo().isConnectedOrConnecting();
		}
		return flag;
	}

	/**
	 * Set Data on screen
	 */
	public void setDataOnScreen() {

	}

	/**
	 * Get data Entered by user , just before next key
	 */
	public void getScreenData() {

	}

	public void startNextScreen() {

	}
	/**
	 * @param StringCode
	 * @return : string value of id from resource
	 */
	public String getStringFromResource(int StringCode) {
		return getResources().getString(StringCode);
	}










	/**
	 * To check weather data is input is present or not (Only valid for textBox
	 * :))
	 * 
	 * @param resourceId
	 * @return : True if data is not null
	 */
	public boolean notNullCheck(int resourceId) {
		boolean flag = true;
		View view = findViewById(resourceId);
		if (view instanceof EditText) {
			EditText editText = (EditText) view;
			if (editText.length() == 0) {
				//editText.setError(getStringFromResource(R.string.error2));
				editText.setError(Html.fromHtml("<font color='#0097a7'>Complete Missing Field</font>"));
				flag = false;
			}
		}

		if (view instanceof RadioGroup) {
			RadioGroup group = (RadioGroup) view;
			if (group.getCheckedRadioButtonId() == -1) {
				flag = false;
			}
		}

		if (view instanceof Spinner) {
			Spinner spinner = (Spinner) view;
			if (spinner.getSelectedItemPosition() == 0 || spinner.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
				flag = false;
			}
		}

		return flag;
	}

	/*
	 * (non-Javadoc)Used for taking values of Spinner/DropDown
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	/*
	 * (non-Javadoc) Used for setting default value of spinner
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	/*
	 * (non-Javadoc) RadioGroup Input Reader
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

	/*
	 * (non-Javadoc) CheckBox Change Input Listener
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	}

	public void dataCleanUp(){
		getRequestDataMap().clear();
		getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	public void sendMail(String body) {
		try {
			GMailSender sender = new GMailSender("error.silverskills@gmail.com", "Emrtech@1234");
			sender.sendMail(getStringFromResource(R.string.app_name), body, "error.silverskills@gmail.com", "jai.mishra@arcisindia.com");
			
		} catch (Exception e) {
			Log.e("SendMail", e.getMessage(), e);
		}
	}
	public static String Encrypt(String text, String key)
			throws Exception {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes= new byte[16];
			byte[] b= key.getBytes("UTF-8");
			int len= b.length;
			if (len > keyBytes.length) len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
			cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);

			byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
			Base64Coder encoder = new Base64Coder();
			return encoder.encodeLines(results);
			}
	
	
	
	
   public  OnTouchListener listTouchListener = new OnTouchListener() {
		

		@Override
	 	 public boolean onTouch(View v, MotionEvent event) {
			 int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
		}
	};


@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	
}}

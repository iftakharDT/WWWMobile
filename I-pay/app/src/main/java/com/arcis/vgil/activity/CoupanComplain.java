package com.arcis.vgil.activity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;

public class CoupanComplain  extends BaseActivity{
	
	private RadioGroup rg_searchType;
	private RadioButton rb_self	,rb_other;
	String ContactTypeAM;
	private static final int CAPTURE_IMAGE_CAMERA = 1;
	private static final int CAPTURE_IMAGE_GALLARY = 2;
	private Bitmap mbitmap;
	private ImageView mDocImage;
	File file  = new File(Environment.getExternalStorageDirectory()+"/"+"doc.jpeg");
    EditText mobileNumber,couponCode,uid,amountOnCoupon,amountRedeemed;
	AlertDialog.Builder dialog;
	ImageView tempImage;
	String IsSelf=" ";
	private Button takephoto ,takephotofromgallary,submit;
	SharedPreferences spref;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.customer_redemption_history);
		setCurrentContext(this);
        spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
		rb_self= (RadioButton)findViewById(R.id.rb_self);
		rb_other= (RadioButton)findViewById(R.id.rb_other);
		rg_searchType = (RadioGroup)findViewById(R.id.rg_searchtype);
		rg_searchType.setOnCheckedChangeListener(this);
		mobileNumber=(EditText)findViewById(R.id.mobilenumber);
		couponCode=(EditText)findViewById(R.id.coupon_code);
		uid=(EditText)findViewById(R.id.uid);
		amountOnCoupon=(EditText)findViewById(R.id.couponamount);
		amountRedeemed=(EditText)findViewById(R.id.amountredeemed);
		takephoto= (Button)findViewById(R.id.takephoto);
		takephotofromgallary=(Button)findViewById(R.id.takephotofromgallary);
		takephoto.setOnClickListener(this);
		takephotofromgallary.setOnClickListener(this);
		mDocImage=(ImageView) findViewById(R.id.img_attachment);
		mDocImage.setOnClickListener(this);
		submit=(Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		
	}
        @Override
        public void setDataOnScreen() {
        	// TODO Auto-generated method stub
        	if (ContactTypeAM.equals("16")) {
    			rb_self.setChecked(true);
    			rb_other.setVisibility(View.INVISIBLE);
    		}
        	else {
        		rb_other.setVisibility(View.VISIBLE);
        		rb_self.setVisibility(View.VISIBLE);
        	
			}
        }
        
        @Override
        public void getScreenData() {
        	// TODO Auto-generated method stub
        	super.getScreenData();
    		getRequestDataMap().clear();
 
    		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
    		getRequestDataMap().put("ComplaintNo", "0");
    		getRequestDataMap().put("MobileNo", mobileNumber.getText().toString());
    		getRequestDataMap().put("CouponCode", couponCode.getText().toString());
    		getRequestDataMap().put("IsSelf", IsSelf);
    		if (uid.length()!=0) {
    			getRequestDataMap().put("UID", uid.getText().toString());
			}
    		else {
    			getRequestDataMap().put("UID", "0");	
			}
    	
    		getRequestDataMap().put("AmountPrinted", amountOnCoupon.getText().toString());
    		getRequestDataMap().put("AmountRedemmed", amountRedeemed.getText().toString());
    		
    		if (mbitmap!=null) {
    			/*Base64.encodeToString(b, Base64.DEFAULT)*/
    			getRequestDataMap().put("COUPONImage",getByteArray(mbitmap)/*"Sanjeev sir"*/);
    		}
    		else {
    			getRequestDataMap().put("COUPONImage", null);
    		}
    		
    		getRequestDataMap().put("CreatedBy", passworddetails.getString(Constants.USERID,""));
    		getRequestDataMap().put("CreatedByContactType", passworddetails.getString(Constants.CONTACTTYPEID,""));
    		getRequestDataMap().put("ImageUploadedBy", passworddetails.getString(Constants.USERID,""));
    		
    		getRequestDataMap().put("ImageByContactType",passworddetails.getString(Constants.CONTACTTYPEID,""));
    		
    		try {
    			getRequestDataMap().put(Constants.username, passworddetails.getString("username",""));
    			getRequestDataMap().put(Constants.password,passworddetails.getString("password",""));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		super.onCheckedChanged(group, checkedId);
		
		switch (group.getId()) {
		
			
		case R.id.rg_searchtype:
			
			switch (checkedId) {
			case R.id.rb_self:
				
				mobileNumber.setText(spref.getString(Constants.MOBILE_NUMBER, ""));
			
				IsSelf="0";
				break;
				
			case R.id.rb_other:
				IsSelf="1";
				mobileNumber.setText("");
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent= null;
		switch (v.getId()) {
		
		case R.id.takephoto:
			// capture image from camera.
			intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, CAPTURE_IMAGE_CAMERA);
			break;
		case R.id.takephotofromgallary:
			// Take photo from gallary
			
			 try
			    {
			        intent = new Intent( Intent.ACTION_GET_CONTENT );
			        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			        intent.setType( "image/*" );
			        startActivityForResult( intent, CAPTURE_IMAGE_GALLARY);
			    }
			    catch ( ActivityNotFoundException e )
			    {
			        e.printStackTrace();
			    }
			break;
			
		case R.id.img_attachment:
			dialog=new AlertDialog.Builder(getCurrentContext());
			tempImage=new ImageView(getCurrentContext());
			tempImage.setImageBitmap(mbitmap);
			dialog.setView(tempImage);
			dialog.show();
			break;
			
		case R.id.submit:
			
			if(validation()){
				getScreenData();
				InsertComplaint();
			}
			break;
			
		default:
			break;
			
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		if(resultCode == RESULT_OK){
			
			if(requestCode == CAPTURE_IMAGE_CAMERA){
				mbitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
				mDocImage.setImageBitmap(mbitmap);
			}else if(requestCode== CAPTURE_IMAGE_GALLARY){
				
				 try {
		                // We need to recyle unused bitmaps
		                if (mbitmap != null) {
		                	mbitmap.recycle();
		                }
		                InputStream stream = getContentResolver().openInputStream(
		                        data.getData());
		                mbitmap = BitmapFactory.decodeStream(stream);
		                stream.close();
		                mDocImage.setImageBitmap(mbitmap);
		            } catch (FileNotFoundException e) {
		                e.printStackTrace();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
			}
			
			
			
		}
	}
	
	@Override
	public boolean validation() {
		boolean flag = true;
		String errorMsg = "Invalid";
		
		int checkedid = rg_searchType.getCheckedRadioButtonId();
		if(checkedid==-1){
			flag = false; 
			errorMsg = errorMsg+" Please select either Self or Other ";
		}
		if (mobileNumber.length()!=10) {
			
			mobileNumber.setError(Html.fromHtml("<font color='#0097a7'>Please Fill mobile number</font>"));
			flag = false; 
		}
		if (couponCode.length()!=11) {
			couponCode.setError(Html.fromHtml("<font color='#0097a7'>Please Fill couponCode number</font>"));
			flag = false; 
		}
		
		if (amountOnCoupon.length()==0) {
			amountOnCoupon.setError(Html.fromHtml("<font color='#0097a7'>Please Fill coupon Amount</font>"));
			flag = false; 
		}
		if (amountRedeemed.length()==0) {
			amountRedeemed.setError(Html.fromHtml("<font color='#0097a7'>Please Fill Redeemed Amount</font>"));
			flag = false; 
		}
		
		if(!flag)
			Util.showToast(getCurrentContext(), errorMsg, Toast.LENGTH_LONG).show();
			
		return flag;
		
	}
	
	public byte[] getByteArray(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
		byte[] byteArray = stream.toByteArray();
		try {
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if(mDocImage!=null){
			tempImage.setImageBitmap(mbitmap);
		}
		
	}
	
	private void InsertComplaint() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(CoupanComplain.this, ProgressDialog.STYLE_SPINNER,"Uploading data Please wait..", new GetDataCallBack() {
			@Override
			public void processResponse(Object responseObject) {
				SoapObject response = null;
				try {
					response = (SoapObject) responseObject;
				}  catch (Exception e) {
					e.printStackTrace();
				} finally {
					AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext());
					if (response == null) {
						alert.setTitle("Alert !");
						if (responseObject != null) {
							alert.setMessage(responseObject.toString());
						} else {
							alert.setMessage(getStringFromResource(R.string.error4));
						}

					} else {  
						
						
					}

					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							
						}
					});
					alert.setCancelable(false);
					alert.show();
				}
			}
		});


		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName = "InsertComplaint";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(getRequestDataMap());
		dataFromNetwork.execute();
	}
}

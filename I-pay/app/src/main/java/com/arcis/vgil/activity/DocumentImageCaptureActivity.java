package com.arcis.vgil.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;

import org.ksoap2.serialization.SoapObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class DocumentImageCaptureActivity extends Activity implements OnClickListener {


	private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7;
	private Button btn1,btn2,btn3,btn4;
	private Button btn_save;

	public static final int REQUEST_CODE_CAPTURE_IDPROOFE = 23456;
	public static final int REQUEST_CODE_CAPTURE_ADDRESS_PROOFE = 23457;
	public static final int REQUEST_CODE_CAPTURE_BANK_PROOOFE = 23458;
	public static final int REQUEST_CODE_CAPTURE_PHOTO_PROOFE = 23459;

	private ImageView img_proofe1,img_proofe2,img_proofe3,img_proofe4;
	Bitmap bt_idproofe,bt_addrproofe,bt_bankproofe,bt_photoproofe;

	File fileidproofe;
	File fileaddressProofe;
	File filebankproofe;
	File filephotoproofe;

	String mCustomerId;
	private Context mContext;
	File docDir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.document_proof_layout);

		mContext = this;
		txt1 = (TextView)findViewById(R.id.txt1);
		txt2 = (TextView)findViewById(R.id.txt2);
		txt3 = (TextView)findViewById(R.id.txt3);
		txt4 = (TextView)findViewById(R.id.txt4);
		txt5 = (TextView)findViewById(R.id.txt5);
		txt6 = (TextView)findViewById(R.id.txt6);
		txt7 = (TextView)findViewById(R.id.txt7);

		docDir = new File(Environment.getExternalStorageDirectory()+"/"+"DocPics");
		docDir.mkdirs();

		fileidproofe 		= new File(docDir,"idproofe.png");
		fileaddressProofe 	= new File(docDir,"Addressproofe.png");
		filebankproofe  	= new File(docDir,"bankproofe.png");
		filephotoproofe 	= new File(docDir,"photoprooofe.png");

		btn1 = (Button)findViewById(R.id.btn_idproofe);
		btn1.setOnClickListener(this);

		btn2 = (Button)findViewById(R.id.btn_addproofe);
		btn2.setOnClickListener(this);

		btn3 = (Button)findViewById(R.id.btn_bankproofe);
		btn3.setOnClickListener(this);


		btn4 = (Button)findViewById(R.id.btn_photoproofe);
		btn4.setOnClickListener(this);

		btn_save = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);

		img_proofe1 = (ImageView)findViewById(R.id.img_idproofe);
		img_proofe2 = (ImageView)findViewById(R.id.img_addproofe);
		img_proofe3 = (ImageView)findViewById(R.id.img_bankproofe);
		img_proofe4 = (ImageView)findViewById(R.id.img_photoproofe);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			mCustomerId = bundle.getString(Constants.ID);
			txt1.setText(bundle.getString(Constants.NAME));
			txt2.setText(bundle.getString(Constants.MOBILE_NUMBER));
			txt3.setText(bundle.getString(Constants.address));
			txt4.setText(bundle.getString(Constants.contacttype));
			txt5.setText(bundle.getString(Constants.AccountNumber));
			txt6.setText(bundle.getString(Constants.BankName));
			txt7.setText(bundle.getString(Constants.BranchName));
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		final Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Please Select");
		
		switch (v.getId()) {

		case R.id.btn_idproofe:


			builder.setItems(R.array.docchooser, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					if(which==0){
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileidproofe));
						startActivityForResult(intent, REQUEST_CODE_CAPTURE_IDPROOFE);
					}else{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_PICK);
						startActivityForResult(Intent.createChooser(intent,
								"Select Picture"), REQUEST_CODE_CAPTURE_IDPROOFE);
					}
				}
			});
			builder.create().show();

			break;
		case R.id.btn_addproofe:

			builder.setItems(R.array.docchooser, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					if(which==0){
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileaddressProofe));
						startActivityForResult(intent, REQUEST_CODE_CAPTURE_ADDRESS_PROOFE);
					}else{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent,
								"Select Picture"), REQUEST_CODE_CAPTURE_ADDRESS_PROOFE);
					}
				}
			});
			builder.create().show();

			break;
		case R.id.btn_bankproofe:

			builder.setItems(R.array.docchooser, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					if(which==0){
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filebankproofe));
						startActivityForResult(intent, REQUEST_CODE_CAPTURE_BANK_PROOOFE);
					}else{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filebankproofe));
						startActivityForResult(Intent.createChooser(intent,
								"Select Picture"), REQUEST_CODE_CAPTURE_BANK_PROOOFE);
					}
				}
			});
			builder.create().show();


			break;
		case R.id.btn_photoproofe:

			builder.setItems(R.array.docchooser, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					if(which==0){
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filephotoproofe));
						startActivityForResult(intent, REQUEST_CODE_CAPTURE_PHOTO_PROOFE);
					}else{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent,
								"Select Picture"), REQUEST_CODE_CAPTURE_PHOTO_PROOFE);
					}
				}
			});
			builder.create().show();

			break;

		case R.id.save:
			UploadDocProofe();
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

		if(resultCode ==RESULT_OK){

			if(requestCode == REQUEST_CODE_CAPTURE_IDPROOFE){
				
				bt_idproofe = BitmapFactory.decodeFile(fileidproofe.getAbsolutePath(), options);
				if(bt_idproofe==null){
					InputStream ist;
					try {
						ist = getContentResolver().openInputStream(data.getData());
						bt_idproofe = BitmapFactory.decodeStream(ist, null, options);
						ist.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				img_proofe1.setImageBitmap(bt_idproofe);
				
			}else if(requestCode == REQUEST_CODE_CAPTURE_ADDRESS_PROOFE){
				bt_addrproofe = BitmapFactory.decodeFile(fileaddressProofe.getAbsolutePath(), options);
				if(bt_addrproofe==null){
					InputStream ist;
					try {
						ist = getContentResolver().openInputStream(data.getData());
						bt_addrproofe = BitmapFactory.decodeStream(ist, null, options);
						ist.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				img_proofe2.setImageBitmap(bt_addrproofe);
				
			}else if(requestCode ==REQUEST_CODE_CAPTURE_BANK_PROOOFE){
				
				bt_bankproofe = BitmapFactory.decodeFile(filebankproofe.getAbsolutePath(), options);
				if(bt_bankproofe==null){
					InputStream ist;
					try {
						ist = getContentResolver().openInputStream(data.getData());
						bt_bankproofe = BitmapFactory.decodeStream(ist, null, options);
						ist.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				img_proofe3.setImageBitmap(bt_bankproofe);
				
			}else if(requestCode== REQUEST_CODE_CAPTURE_PHOTO_PROOFE){
				
				bt_photoproofe = BitmapFactory.decodeFile(filephotoproofe.getAbsolutePath(), options);
				if(bt_photoproofe==null){
					InputStream ist;
					try {
						ist = getContentResolver().openInputStream(data.getData());
						bt_photoproofe = BitmapFactory.decodeStream(ist, null, options);
						ist.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				img_proofe4.setImageBitmap(bt_photoproofe);
			}

		}
	}

	public byte[] getByteArray(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	private void UploadDocProofe(){

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(DocumentImageCaptureActivity.this, ProgressDialog.STYLE_SPINNER,"updating data ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object responseObject) {
				
				
				SoapObject response = null;
				try {
					response = (SoapObject) responseObject;
				}  catch (Exception e) {
					e.printStackTrace();
				} finally {
					AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
					if (response == null) {
						alert.setTitle(getResources().getString(R.string.error6));
						if (responseObject != null) {
							alert.setMessage(responseObject.toString());
						} else {
							alert.setMessage(getResources().getString(R.string.error4));
						}

					} else {

						alert.setMessage(getResources().getString(R.string.message5));
					}
					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

							arg0.dismiss();
							String[] children = docDir.list();
							for (int i = 0; i < children.length; i++) {
								new File(docDir, children[i]).delete();
							}
							finish();

						}
					});
					alert.create().show();

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(DocumentImageCaptureActivity.this, Constants.PREF_NAME);

		request.put("CustomerID", mCustomerId);
		try {

			if(bt_idproofe==null){
				request.put("IDProofDocumentID", "0");
			}else{
				request.put("IDProofDocumentID", "1");
				request.put("IDProofImage", getByteArray(bt_idproofe));
			}
			if(bt_addrproofe==null){
				request.put("AddressDocumentID", "0");
			}else{
				request.put("AddressDocumentID", "2");
				request.put("AddresProofImage", getByteArray(bt_addrproofe));
			}

			if(bt_bankproofe==null){
				request.put("BankProofDocumentID", "0");
			}else{
				request.put("BankProofDocumentID", "3");
				request.put("BankProofImage", getByteArray(bt_bankproofe));
			}
			if(bt_photoproofe==null){
				request.put("PhotoDocumentID", "0");
			}else{
				request.put("PhotoDocumentID", "4");
				request.put("PhotoImage", getByteArray(bt_photoproofe));
			}
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SaveUnveriedContactDocument";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();

	}
}

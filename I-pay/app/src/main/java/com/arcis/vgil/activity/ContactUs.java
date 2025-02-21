package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ContactUs extends BaseActivity {
	
	TextView am_name,am_cotanct,am_email;
	TextView si_name,si_cotanct,si_email;
	TextView rm_name,rm_cotanct,rm_email;
	TextView mm_name,mm_cotanct,mm_email;
	String contactNumber;
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.about_us);
		setCurrentContext(this);
		iniViews();
		CustomerUsefulCOntacts();
		iniListner();
		
	}





	private void iniViews() {
		// TODO Auto-generated method stub
		
	    am_name=(TextView) findViewById(R.id.tv_am_name);
	    am_cotanct=(TextView) findViewById(R.id.tv_am_contact);
	    am_email=(TextView) findViewById(R.id.tv_am_email);
	    
	    
		si_name=(TextView) findViewById(R.id.si_name);
		si_cotanct=(TextView) findViewById(R.id.si_phone);
		si_email=(TextView) findViewById(R.id.si_email);
		
		rm_name=(TextView) findViewById(R.id.rm_name);
		rm_cotanct=(TextView) findViewById(R.id.rm_phone);
		rm_email=(TextView) findViewById(R.id.rm_email);
		 
		mm_name =(TextView) findViewById(R.id.mm_name);
		mm_cotanct=(TextView) findViewById(R.id.mm_contact);
		mm_email=(TextView) findViewById(R.id.mm_email);
		
		
		
	}
	
	private void iniListner() {
		// TODO Auto-generated method stub
		 am_cotanct.setOnClickListener(this);
		 si_cotanct.setOnClickListener(this);
		 rm_cotanct.setOnClickListener(this);
		 mm_cotanct.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.tv_am_contact:
			
			if (am_cotanct.getText().toString().equalsIgnoreCase("Not Available")||am_cotanct.getText().toString().length()==0) {
				Toast.makeText(getCurrentContext(), "Contact Not Available", Toast.LENGTH_LONG).show();
			}
			else {
				  contactNumber=am_cotanct.getText().toString();
				  AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
			        builder.setTitle("Alert !");
			        builder.setMessage("Charge applicable as per your operator")
			               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   try {

			                           // set the data

			                          /* String uri = "tel:" +contactNumber;
			                           Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
			                           startActivity(callIntent);*/

			                       } catch (Exception e) {

			                           Toast.makeText(getCurrentContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
			                           e.printStackTrace();

			                       }
			                   }
			               })
			               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       // User cancelled the dialog
			                   }
			               });
			        // Create the AlertDialog object and return it
			        AlertDialog alert = builder.create();
		            alert.show();
			    
				
			}
			
			break;
			
        case R.id.si_phone:
			
        	if (si_cotanct.getText().toString().equalsIgnoreCase("Not Available")||si_cotanct.getText().toString().length()==0) {
				Toast.makeText(getCurrentContext(), "Contact Not Available", Toast.LENGTH_LONG).show();
			}
			else {
				 AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
			        builder.setTitle("Alert !");
			        builder.setMessage("Charge applicable as per your operator")
			               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   try {

			                           // set the data

			                         /*  String uri = "tel:" +si_cotanct.getText().toString();
			                           Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
			                           startActivity(callIntent);*/

			                       } catch (Exception e) {

			                           Toast.makeText(getCurrentContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
			                           e.printStackTrace();

			                       }
			                   }
			               })
			               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       // User cancelled the dialog
			                   }
			               });
			        // Create the AlertDialog object and return it
			        AlertDialog alert = builder.create();
		            alert.show();
				
			}
			
			break;
			
       case R.id.rm_phone:
	
    		if (rm_cotanct.getText().toString().equalsIgnoreCase("Not Available")||rm_cotanct.getText().toString().length()==0) {
				Toast.makeText(getCurrentContext(), "Contact Not Available", Toast.LENGTH_LONG).show();
			}
			else {
				 AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
			        builder.setTitle("Alert !");
			        builder.setMessage("Charge applicable as per your operator")
			               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   try {

			                           // set the data

			                          /* String uri = "tel:" +rm_cotanct.getText().toString();
			                           Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
			                           startActivity(callIntent);*/

			                       } catch (Exception e) {

			                           Toast.makeText(getCurrentContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
			                           e.printStackTrace();

			                       }
			                   }
			               })
			               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       // User cancelled the dialog
			                   }
			               });
			        // Create the AlertDialog object and return it
			        AlertDialog alert = builder.create();
		            alert.show();
				
			}
			
	         break;
	
       case R.id.mm_contact:
    	   
    		if (mm_cotanct.getText().toString().equalsIgnoreCase("Not Available")||mm_cotanct.getText().toString().length()==0) {
				Toast.makeText(getCurrentContext(), "Contact Not Avaible", Toast.LENGTH_LONG).show();
			}
			else {
				 AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
			        builder.setTitle("Alert !");
			        builder.setMessage("Charge applicable as per your operator")
			               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       // FIRE ZE MISSILES!
			                	   
			                	   try {

			                           // set the data

			                          /* String uri = "tel:" +mm_cotanct.getText().toString();
			                           Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
			                           startActivity(callIntent);*/

			                       } catch (Exception e) {

			                           Toast.makeText(getCurrentContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
			                           e.printStackTrace();

			                       }
			                   }
			               })
			               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       // User cancelled the dialog
			                   }
			               });
			        // Create the AlertDialog object and return it
			        AlertDialog alert = builder.create();
		            alert.show();
				
			}
			
	
	break;

		default:
			break;
		}
	}

	
	private void CustomerUsefulCOntacts() {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getCntactUS(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							am_name.setText(itemQuantityValueList.get(0).get(Constants.AM_NAME));
							am_cotanct.setText(itemQuantityValueList.get(0).get(Constants.Am_Cotnact));
							am_email.setText(itemQuantityValueList.get(0).get(Constants.Am_EMAIL));
								
							    
								si_name.setText(itemQuantityValueList.get(0).get(Constants.SI_NAME));
								si_cotanct.setText(itemQuantityValueList.get(0).get(Constants.SI_CONTACT));
								si_email.setText(itemQuantityValueList.get(0).get(Constants.SI_EMAIL));
								
								
								rm_name.setText(itemQuantityValueList.get(0).get(Constants.RM_NAME));
								rm_cotanct.setText(itemQuantityValueList.get(0).get(Constants.RM_CONTACT));
								rm_email.setText(itemQuantityValueList.get(0).get(Constants.RM_EMAIL));
								 
								mm_name.setText(itemQuantityValueList.get(0).get(Constants.MM_NAME));
								mm_cotanct.setText(itemQuantityValueList.get(0).get(Constants.MM_CONTACT));
								mm_email.setText(itemQuantityValueList.get(0).get(Constants.MM_EMAIL));
							
						}
						

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
try {       
	 
	        
	      
			request.put("ContactID", passworddetails.getString(Constants.USERID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CustomerUsefulCOntacts";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		
	}

}

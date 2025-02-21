package com.arcis.vgil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;

public class EditGoodsReceivedActivity  extends BaseActivity{


	EditText edttxt2,edttxt3,edttxt4,edttxt5;
	private Bundle bundle = new Bundle();
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		setContentView(R.layout.edit_goods_received_layout);
	}

	@Override
	public void getScreenData() {
		// TODO Auto-generated method stub

		bundle.putString(Constants.damageQty, edttxt2.getText().toString());
		bundle.putString(Constants.salereturn, edttxt3.getText().toString());
		if(edttxt4.getText().toString().length()>0){
			bundle.putString(Constants.descOfDamage, edttxt4.getText().toString());
		}else{
			bundle.putString(Constants.descOfDamage,"");
		}
		bundle.putString(Constants.shortQty, edttxt5.getText().toString());
	}

	@Override
	public void setDataOnScreen() {
		// TODO Auto-generated method stub

		
		edttxt2 = (EditText)findViewById(R.id.editText2);
		edttxt3 = (EditText)findViewById(R.id.editText3);
		edttxt4 = (EditText)findViewById(R.id.editText4);
		edttxt5 = (EditText)findViewById(R.id.editText5);
		setCurrentContext(this);
		Button btn_save = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);

		Intent intent  =getIntent();
		if(intent!=null && intent.getExtras()!=null){

			Bundle bundle = intent.getExtras();

			edttxt2.setText(bundle.getString(Constants.damageQty));
			edttxt3.setText(bundle.getString(Constants.salereturn));
			edttxt4.setText(bundle.getString(Constants.descOfDamage));
			edttxt5.setText(bundle.getString(Constants.shortQty));
		}

	}

	@Override
 	public boolean validation() {
		// TODO Auto-generated method stub
		String errMsg = getStringFromResource(R.string.error3);
		boolean flag = true;
		

		if(edttxt2.getText().toString().length()==0){

			flag = false;
			edttxt2.setError(getStringFromResource(R.string.damageqty));
		}
		if(edttxt3.getText().toString().length()==0){

			flag = false;
			edttxt3.setError(getStringFromResource(R.string.salereturn));
		}
		if(edttxt5.getText().toString().length()==0){

			flag = false;
			edttxt5.setError(getStringFromResource(R.string.shortqty));
		}
		
		if(!flag){
			Util.showToast(getCurrentContext(), errMsg, Toast.LENGTH_SHORT).show();
		}
		return flag;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(validation()){
			getScreenData();
			Intent intent = new Intent();
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}

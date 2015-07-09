package com.example.projectmaingraduate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordDialogActivity extends Activity{
	
	private EditText edt_oldPassword, edt_newPassword;
	private Button btnClose, btnVerify;
	private TextView tvInvalid;
	private String textInvalid="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_update_account_admin);
		
		edt_oldPassword = (EditText) findViewById(R.id.dialog_update_account_admin_edt_old_pass);
		edt_newPassword = (EditText) findViewById(R.id.dialog_update_account_admin_edt_new_pass);
		btnClose = (Button) findViewById(R.id.dialog_update_account_admin_btn_close);
		btnVerify = (Button) findViewById(R.id.dialog_update_account_admin_btn_verify);
		tvInvalid = (TextView) findViewById(R.id.dialog_update_account_admin_tv_invalid);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("AccontPasswordUpdate");
		final String pass = bundle.getString("password");
		final String account = bundle.getString("account");
		
		btnClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btnVerify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String oldPass = edt_oldPassword.getText().toString();
				String newPass = edt_newPassword.getText().toString();
				if(!oldPass.equalsIgnoreCase(pass)) {
					tvInvalid.setText("Mật khẩu cũ nhập vào sai");
					tvInvalid.setVisibility(TextView.VISIBLE);
				} else {
					if(checkStandardPassword(newPass)) {
						tvInvalid.setVisibility(TextView.INVISIBLE);
						boolean ok = ParseApplication.UpdatePasswordAdmin(account, newPass);
						if (ok) {
							LoginAdminActivity.updatePasswordUpdate(newPass);
							Toast.makeText(getApplicationContext(),
									"Cập nhật mật khẩu thành công", Toast.LENGTH_LONG).show();
							finish();
						} else {
							Toast.makeText(PasswordDialogActivity.this,
									"Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
						}
					} else {
						tvInvalid.setText(textInvalid);
						tvInvalid.setVisibility(TextView.VISIBLE);
					}
				}
			}
		});
		
	}

	protected boolean checkStandardPassword(String newPass) {
		// TODO Auto-generated method stub
		String textPass = newPass;
		int size = textPass.length();
		if(size < 4) {
			textInvalid = "Độ dài mật khẩu phải lớn hơn 4 ký tự";
			return false;
		} 
		return true;
	}
	
}











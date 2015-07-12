package com.example.projectmaingraduate;

import java.util.ArrayList;
 
import com.example.object.AccountUser;

import android.app.Activity; 
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DialogSigninUser extends Activity {

	private LinearLayout layoutSignin, layoutLogin;
	private EditText edtAccount, edtPassword, edtFullname, edtAddress,
			edtPhone, edtEmail, edtLoginAccount, edtLoginPassword;
	private ImageButton btnRefresh;
	private ImageView btnClose;
	private Button btnSignin, btnCreateSignin, btnCreateLogin, btnLogin;
	private TextView tvNoticeError, tvInvalidAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_login_user);
		
		layoutSignin = (LinearLayout) findViewById(R.id.dialog_login_user_layout_sign_in);
		layoutLogin = (LinearLayout) findViewById(R.id.dialog_login_user_layout_login);
		edtLoginAccount = (EditText) findViewById(R.id.dialog_login_user_edt_account_login);
		edtLoginPassword = (EditText) findViewById(R.id.dialog_login_user_edt_password_login);
		edtAccount = (EditText) findViewById(R.id.dialog_login_user_edt_account);
		edtPassword = (EditText) findViewById(R.id.dialog_login_user_edt_password);
		edtFullname = (EditText) findViewById(R.id.dialog_login_user_edt_fullName);
		edtAddress = (EditText) findViewById(R.id.dialog_login_user_edt_address);
		edtPhone = (EditText) findViewById(R.id.dialog_login_user_edt_phone);
		edtEmail = (EditText) findViewById(R.id.dialog_login_user_edt_email);
		btnCreateSignin = (Button) findViewById(R.id.dialog_login_user_btn_create_sign_in);
		btnCreateLogin = (Button) findViewById(R.id.dialog_login_user_btn_create_login);
		btnClose = (ImageView) findViewById(R.id.dialog_login_user_btn_close);
		btnRefresh = (ImageButton) findViewById(R.id.dialog_login_user_btn_refresh);
		btnSignin = (Button) findViewById(R.id.dialog_login_user_btn_sign_in);
		btnLogin = (Button) findViewById(R.id.dialog_login_user_btn_login);
		tvNoticeError = (TextView) findViewById(R.id.dialog_login_user_tv_notice_error);
		tvInvalidAccount = (TextView) findViewById(R.id.dialog_login_user_tv_invalidAccount_login);

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btnCreateLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutLogin.setVisibility(LinearLayout.VISIBLE);
				layoutSignin.setVisibility(LinearLayout.INVISIBLE);
				edtLoginAccount.setText("");
				edtLoginPassword.setText("");
			}
		});
		
		btnCreateSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutLogin.setVisibility(LinearLayout.INVISIBLE);
				layoutSignin.setVisibility(LinearLayout.VISIBLE);
				refreshData();
			}
		});

		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refreshData();
			}
		});
		
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = edtLoginPassword.getText().toString();
				String account = edtLoginAccount.getText().toString();
				boolean ok = checkAccountUser(account, password);
				if(ok) {
					tvInvalidAccount.setVisibility(TextView.INVISIBLE);
					Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
					// tạo đối tượng getSharedPreference
					SharedPreferences pre = getSharedPreferences("LOGIN", MODE_PRIVATE);
					// tạo đối tượng Editor để lưu thay đổi
					SharedPreferences.Editor editor = pre.edit();
					editor.clear();
					editor.commit();
					editor.putString("user", account);
					editor.putString("pass", password);
					editor.commit();
					finish();
				} else {
					tvInvalidAccount.setVisibility(TextView.VISIBLE);
				}
			}
		});

		btnSignin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String account, email, fullName, address, phone, password;
				String Error = "";
				account = edtAccount.getText().toString().trim();
				email = edtEmail.getText().toString().trim();
				fullName = edtFullname.getText().toString().trim();
				address = edtAddress.getText().toString().trim();
				phone = edtPhone.getText().toString().trim();
				password = edtPassword.getText().toString().trim();
				// kiem tra cu phap
				if (!checkKeySpace(account) || account.equalsIgnoreCase("")) {
					Error = "Tên tài khoản không được trống và có kí tự cách";
					tvNoticeError.setText(Error);
					tvNoticeError.setVisibility(TextView.VISIBLE);
				} else {
					if (email.equalsIgnoreCase("") || password.equalsIgnoreCase("")
							|| fullName.equalsIgnoreCase("") || phone.equalsIgnoreCase("")) {
						Error = "Nhập chưa đầy đủ thông tin";
						tvNoticeError.setText(Error);
						tvNoticeError.setVisibility(TextView.VISIBLE);
					} else if(password.length() < 5) {
						Error = "Mật khẩu phải lớn hơn 4 kí tự";
						tvNoticeError.setText(Error);
						tvNoticeError.setVisibility(TextView.VISIBLE);
					} else {
						tvNoticeError.setText("");
						tvNoticeError.setVisibility(TextView.GONE);
						boolean ok = ParseApplication.SigninAccountUser(
								account, password, address, phone, fullName, email);
						if (ok) {
							// tạo đối tượng getSharedPreference
							SharedPreferences pre = getSharedPreferences("LOGIN", MODE_PRIVATE);
							// tạo đối tượng Editor để lưu thay đổi
							SharedPreferences.Editor editor = pre.edit();
							editor.clear();
							editor.commit();
							editor.putString("user", account);
							editor.putString("pass", password);
							editor.commit();
							finish();
							Toast.makeText(getApplicationContext(),
									"Đăng kí  tài khoản thành công", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Đăng kí  tài khoản thất bại", Toast.LENGTH_LONG).show();
						}
					}
				}
			}
		});

	}

	protected void refreshData() {
		edtAccount.setText("");
		edtAddress.setText("");
		edtEmail.setText("");
		edtFullname.setText("");
		edtPassword.setText("");
		edtPhone.setText("");
	}

	protected boolean checkKeySpace(String account) {
		// TODO Auto-generated method stub
		String text = account;
		char c[] = text.toCharArray();
		for (int i = 0; i < c.length; i++) {
			String t = c[i] + "";
			if (t.equalsIgnoreCase(" ")) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean checkAccountUser(String accontUser, String password) {
		ArrayList<AccountUser> listUser = ParseApplication.getInforAccountUser();
		for (int i = 0; i < listUser.size(); i++) {
			if (accontUser.equalsIgnoreCase(listUser.get(i).getAccount())
					&& password.equalsIgnoreCase(listUser.get(i).getPassword())) {
				return true;
			}
		}
		return false;
	}


}

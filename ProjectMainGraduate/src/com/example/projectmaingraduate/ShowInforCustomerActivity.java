package com.example.projectmaingraduate;

import com.example.fragment.AdminShowMessageReceived;
import com.example.object.AccountUser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShowInforCustomerActivity extends Activity {

	private ImageButton imgbtnEdit, imgbtnClose;
	private TextView tvNoticeError;
	private Button btnSave;
	private EditText edtFullName, edtAddress, edtEmail, edtPhone, edtAccount,
			edtPassword;
	private String account, pass, email, address, phone, fullname;
	private String oldAccount;
	private AccountUser user;
	private int position;
	private boolean admin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_show_infor_customer);

		imgbtnEdit = (ImageButton) findViewById(R.id.dialog_show_infor_customer_btn_edit);
		imgbtnClose = (ImageButton) findViewById(R.id.dialog_show_infor_customer_btn_close);
		btnSave = (Button) findViewById(R.id.dialog_show_infor_customer_btn_save);
		edtAccount = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_account);
		edtPassword = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_password);
		edtFullName = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_fullName);
		edtAddress = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_address);
		edtEmail = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_email);
		edtPhone = (EditText) findViewById(R.id.dialog_show_infor_customer_edt_phone);
		tvNoticeError = (TextView) findViewById(R.id.dialog_show_infor_customer_tv_notice_error);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("Customer");
		admin = bundle.getBoolean("admin");
		position = bundle.getInt("index");
		user = (AccountUser) bundle.getSerializable("AccountUser");
		
		Log.d("Admin",String.valueOf(admin));
		
		if(position == -2) {
			imgbtnEdit.setVisibility(ImageButton.GONE);
		}
		
		oldAccount = user.getAccount();
		account = user.getAccount();
		pass = user.getPassword();
		email = user.getEmail();
		phone = user.getPhone();
		fullname = user.getFullName();
		address = user.getAddress();
		
		edtAccount.setText(user.getAccount());
		edtAddress.setText(user.getAddress());
		edtEmail.setText(user.getEmail());
		edtFullName.setText(user.getFullName());
		edtPassword.setText(user.getPassword());
		edtPhone.setText(user.getPhone());
		
		imgbtnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		imgbtnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnSave.getVisibility() == Button.GONE) {
					btnSave.setVisibility(Button.VISIBLE);
					edtAccount.setEnabled(true);
					edtAddress.setEnabled(true);
					edtEmail.setEnabled(true);
					edtFullName.setEnabled(true);
					edtPhone.setEnabled(true);
					edtPassword.setEnabled(true);
				} else {
					btnSave.setVisibility(Button.GONE);
					edtAccount.setEnabled(false);
					edtAddress.setEnabled(false);
					edtEmail.setEnabled(false);
					edtFullName.setEnabled(false);
					edtPhone.setEnabled(false);
					edtPassword.setEnabled(false);
					tvNoticeError.setVisibility(TextView.GONE);
				}

			}
		});
		
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				account = edtAccount.getText().toString();
				pass = edtPassword.getText().toString();
				email = edtEmail.getText().toString();
				phone = edtPhone.getText().toString();
				fullname = edtFullName.getText().toString();
				address = edtAddress.getText().toString();
				if (!checkKeySpace(account) || account.equalsIgnoreCase("")) {
					String Error = "Tên tài khoản không được trống và có kí tự cách";
					tvNoticeError.setText(Error);
					tvNoticeError.setVisibility(TextView.VISIBLE);
				}  else if(pass.length() < 5) {
					tvNoticeError.setText( "Mật khẩu phải lớn hơn 4 kí tự");
					tvNoticeError.setVisibility(TextView.VISIBLE);
				} else {
					if (email.equalsIgnoreCase("") || pass.equalsIgnoreCase("") || address.equalsIgnoreCase("")
							|| fullname.equalsIgnoreCase("") || phone.equalsIgnoreCase("")) {
						String Error = "Dữ liệu cập nhật bị trống";
						tvNoticeError.setText(Error);
						tvNoticeError.setVisibility(TextView.VISIBLE);
					} else {
						tvNoticeError.setText("");
						tvNoticeError.setVisibility(TextView.GONE);
						boolean ok = ParseApplication.UpdateAccountUser(oldAccount, account, pass, 
								address, phone, fullname, email);
						if (ok) {
//							user.setAccount(account);
//							user.setPassword(pass);
//							user.setAddress(address);
//							user.setEmail(email);
//							user.setPhone(phone);
//							user.setFullName(fullname);
//							oldAccount = account;
							if(admin) {
								CustomerManagementActivity.setUpdateAccountUser(position, 
										account, pass, address, phone, email, fullname);
							} else {
								if(position == -3) {
									AdminShowMessageReceived.setUpdateAccountUser(
											account, pass, address, phone, email, fullname);
								} else {
									// tạo đối tượng getSharedPreference
									SharedPreferences pre = getSharedPreferences("LOGIN", MODE_PRIVATE);
									// tạo đối tượng Editor để lưu thay đổi
									SharedPreferences.Editor editor = pre.edit();
									editor.clear();
									editor.commit();
									editor.putString("user", account);
									editor.putString("pass", pass);
									editor.commit();
									ComunicationActivity.setUpdateAccountUser(
											account, pass, address, phone, email, fullname);
								}
							}
							finish();
							Toast.makeText(getApplicationContext(),
									"Cập nhật thành công", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Cập nhật thất bại", Toast.LENGTH_LONG).show();
						}
					}
				}
			}
		});
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

}

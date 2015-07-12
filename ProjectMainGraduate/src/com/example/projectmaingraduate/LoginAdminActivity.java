package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.object.AccountAdmin;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginAdminActivity extends Activity {

	private Button btnLogin, btnUpdate, btnDoneLogin, btnDoneUpdate;
	private EditText edtAccountLogin, edtPasswordLogin, edtAccountUpdate;
	private static EditText edtPasswordUpdate;
	private TextView tvInvalidAccounLogin, tvInvalidAccounUpdate;
	private LinearLayout layoutUpdate, layoutLogin;
	private ActionBar actionBar;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_admin);
		getActionBar().setTitle("ADMINSTRATOR");
		// #655656
		initiation();
		actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutLogin.setVisibility(LinearLayout.VISIBLE);
				layoutUpdate.setVisibility(LinearLayout.INVISIBLE);
				edtAccountLogin.setText("");
				edtPasswordLogin.setText("");
			}
		});

		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginAdminActivity.this);
				builder.setTitle("Câu hỏi");
				builder.setIcon(R.drawable.icon_question_red);
				builder.setMessage("Admin có chắc chắn muốn cập nhật thông tin tài khoản?");
				builder.setNegativeButton("Đúng vậy",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								layoutUpdate.setVisibility(LinearLayout.VISIBLE);
								layoutLogin.setVisibility(LinearLayout.INVISIBLE);
								edtAccountUpdate.setText("");
								edtPasswordUpdate.setText("");
							}
						});
				builder.setPositiveButton("Không phải", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				builder.create().show();
			}
		});
		
		btnDoneUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = edtPasswordUpdate.getText().toString();
				String account = edtAccountUpdate.getText().toString();
				boolean ok = checkAccountAdmin(account, pass);
				if(ok) {
					tvInvalidAccounUpdate.setVisibility(TextView.INVISIBLE);
					Intent intent = new Intent(LoginAdminActivity.this, PasswordDialogActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account", account);
					bundle.putString("password", pass);
					intent.putExtra("AccontPasswordUpdate", bundle);
					startActivity(intent);
				} else {
					tvInvalidAccounUpdate.setVisibility(TextView.VISIBLE);
				}
			}
		});
		
		
		btnDoneLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tk = edtAccountLogin.getText().toString();
				String mk = edtPasswordLogin.getText().toString();
				AsyncTask<String, Void, Boolean> task = new AsynTaskCheckAccount(tk, mk);
				task.execute();
			}
		});
	}

	protected boolean checkAccountAdmin(String accontAdmin, String password) {
		ArrayList<AccountAdmin> listAdmin = ParseApplication.getInforAccountAdmin();
		for (int i = 0; i < listAdmin.size(); i++) {
			if (accontAdmin.equalsIgnoreCase(listAdmin.get(i).getUser())
					&& password.equalsIgnoreCase(listAdmin.get(i).getPass())) {
				return true;
			}
		}
		return false;
	}

	private void initiation() {
		layoutUpdate = (LinearLayout) findViewById(R.id.activity_start_admin_layout_update);
		layoutLogin = (LinearLayout) findViewById(R.id.activity_start_admin_layout_login);
		btnLogin = (Button) findViewById(R.id.activity_start_admin_btn_login);
		btnUpdate = (Button) findViewById(R.id.activity_start_admin_btn_update);
		btnDoneLogin = (Button) findViewById(R.id.activity_start_admin_btn_done_login);
		btnDoneUpdate = (Button) findViewById(R.id.activity_start_admin_btn_done_update);
		edtAccountLogin = (EditText) findViewById(R.id.activity_start_admin_edt_account_login);
		edtAccountUpdate = (EditText) findViewById(R.id.activity_start_admin_edt_account_update);
		edtPasswordLogin = (EditText) findViewById(R.id.activity_start_admin_edt_password_login);
		edtPasswordUpdate = (EditText) findViewById(R.id.activity_start_admin_edt_password_update);
		tvInvalidAccounLogin = (TextView) findViewById(R.id.activity_start_admin_tv_invalidAccount_login);
		tvInvalidAccounUpdate = (TextView) findViewById(R.id.activity_start_admin_tv_invalidAccount_update);
	}
	
	public static void updatePasswordUpdate(String pass) {
		edtPasswordUpdate.setText(pass);
	}
	
	
	private class AsynTaskCheckAccount extends AsyncTask<String, Void, Boolean> implements
							DialogInterface.OnClickListener{
		String Account, Password;
		ProgressDialog dialog;
		
		public AsynTaskCheckAccount(String account, String password) {
			this.Account = account;
			this.Password = password;
		}
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(LoginAdminActivity.this, "", "Đang xác thực tài khoản");
		}
		

		@Override
		protected Boolean doInBackground(String... params) {
			boolean ok = checkAccountAdmin(Account, Password);
			return ok;
		}

		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.dismiss();
			boolean ok = result;
			if(ok) {
				tvInvalidAccounLogin.setVisibility(TextView.INVISIBLE);
				Intent intent = new Intent(LoginAdminActivity.this, AdminstratorActivity.class);
				startActivity(intent);
			} else {
				tvInvalidAccounLogin.setVisibility(TextView.VISIBLE);
			}
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		
	}

}






package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.object.AccountUser;

import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ComunicationActivity extends Activity {

	private EditText edtTextsend;
	private LinearLayout layoutChat;
	private ScrollView scrollView;
	private static TextView tvAccountName;
	private TextView tvDetaiAccount;
	private Button btnSend;
	private Boolean resume = false;
	private Boolean ok = true, login = true;
	private String UserAccount = "";
	private static AccountUser accountUser;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_writing_message);

		edtTextsend = (EditText) findViewById(R.id.activity_writing_message_edt_text_input);
		layoutChat = (LinearLayout) findViewById(R.id.activity_writing_message_Linearlayout_chat);
		scrollView = (ScrollView) findViewById(R.id.activity_writing_message_scrollView_chat);
		btnSend = (Button) findViewById(R.id.activity_writing_message_btn_send);
		tvAccountName = (TextView)findViewById(R.id.activity_writing_message_tv_accountName);
		tvDetaiAccount = (TextView) findViewById(R.id.activity_writing_message_tv_detail_Account);
		
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendData();
			}
		});
		
		edtTextsend.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					sendData();
					return true;
				}
				return false;
			}
		});
		
		tvDetaiAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resume = true;
				Intent intent = new Intent(ComunicationActivity.this, ShowInforCustomerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("AccountUser", accountUser);
				bundle.putInt("index", 0);
				bundle.putBoolean("admin", false);
				intent.putExtra("Customer", bundle);
				startActivityForResult(intent, 0);
			}
		});
		
	}
	  
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences preferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
		String accountname = preferences.getString("user", "");
		UserAccount = accountname;
		String password = preferences.getString("pass", "");
		if (!accountname.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && login) {
			tvDetaiAccount.setVisibility(TextView.VISIBLE);
			new StartLoadListData(accountname).execute();
			login = false;
		}
		if(!accountname.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && resume) {
			tvDetaiAccount.setVisibility(TextView.VISIBLE);
		}
		setTextNameAccount(accountname);
	}
	
	
	protected void sendData() {
		SharedPreferences preferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
		final String user = preferences.getString("user", "");
		String pass = preferences.getString("pass", "");
		if (!user.equalsIgnoreCase("") && !pass.equalsIgnoreCase(""))
			ok = true;
		else
			ok = false;
		if (ok) {
			Log.d("TAI KHOAN CO DANG NHAP", user + "  " + pass);
			final String text = edtTextsend.getText().toString().trim() + "";
			if (!text.equalsIgnoreCase("")) {
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ParseApplication.sendMessage("DataMessage", "countMessage", user, text);
					}
				});
				thread.start();
				edtTextsend.setText("");
				DisplayText(text);
			}
		} else {
			Log.d("CHUA DANG NHAP KHONG THE CHAT", "NULL");
			startActivity(new Intent(ComunicationActivity.this, DialogSigninUser.class));
			Toast.makeText(getApplicationContext(),
					"Đăng nhập để gởi tin nhắn", Toast.LENGTH_SHORT).show();
		}
	}

	protected void DisplayText(String text) {
		TextView textDisplay = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 20, 10, 20);
		params.gravity = Gravity.RIGHT;
		textDisplay.setLayoutParams(params);
		textDisplay.setBackgroundResource(R.drawable.selector_tv_textdisplay);
		textDisplay.setText(text);
		textDisplay.setTextSize(20);
		textDisplay.setPadding(15, 15, 15, 15);
		layoutChat.addView(textDisplay);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}
	
	protected void DisplayTextReceived(String text) {
		TextView textDisplay = new TextView(ComunicationActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 20, 10, 20);
		params.gravity = Gravity.LEFT;
		textDisplay.setLayoutParams(params);
		textDisplay
				.setBackgroundResource(R.drawable.selector_tv_mesage_received);
		textDisplay.setText(text);
		textDisplay.setTextSize(20);
		textDisplay.setPadding(15, 15, 15, 15);
		layoutChat.addView(textDisplay);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}

	protected void DisplayTextDayReceived(String text) {
		TextView textDisplay = new TextView(ComunicationActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(20, 20, 20, 20);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		textDisplay.setLayoutParams(params);
		textDisplay.setTextColor(Color.RED);
		textDisplay.setText(text);
		textDisplay.setTextSize(24);
		layoutChat.addView(textDisplay);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}
	
	public static void setTextNameAccount(String account) {
		tvAccountName.setText("Tài khoản: " + account);
	}
	
	public static void setUpdateAccountUser(String account, String pass,
			String address, String phone, String email, String fullname) {
		accountUser.setAccount(account);
		accountUser.setPassword(pass);
		accountUser.setAddress(address);
		accountUser.setEmail(email);
		accountUser.setFullName(fullname);
		accountUser.setPhone(phone);
	}
	
	
	// Handler Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_user, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_log_out:
			SharedPreferences preferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	// Create AsynTask thread to loading message
	
	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<String>> implements
		DialogInterface.OnClickListener {

		ProgressDialog dialog;
		String account;
		int countAdmin;
		
		public StartLoadListData(String accountname) {
			this.account = accountname;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ComunicationActivity.this, "", "Loading, please waiting...");
		}
		
		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			try {
				accountUser = ParseApplication.getAllInforOfAUser(account);
				countAdmin = accountUser.getCountMessageAdmin();
				ParseApplication.NoticeReadMessage("countMessageAdminSent", UserAccount);
				return ParseApplication.getAllMessageOfAccount(
						"DataMessageReceived", accountUser.getAccount());
			} catch (Exception e) {
				Log.d("AdminShowMessageReceived", "Error loading getAllMessageOfAccount");
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<String> result) {
			dialog.dismiss();
			if (result.size() > 0) {
				int i = 0;
				for (String mesage : result) {
					if (countAdmin > 0 && (i == (result.size() - countAdmin))) {
						DisplayTextDayReceived("Bạn có tin nhắn mới từ Admin");
					}
					DisplayTextReceived(mesage);
					i++;
				}
			}
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		
	}
}




 



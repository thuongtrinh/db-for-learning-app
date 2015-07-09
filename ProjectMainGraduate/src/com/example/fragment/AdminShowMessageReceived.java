package com.example.fragment;

import java.util.ArrayList;

import com.example.object.AccountUser;
import com.example.projectmaingraduate.AdminstratorActivity; 
import com.example.projectmaingraduate.FeedbackManagementActivity;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.R;
import com.example.projectmaingraduate.ShowInforCustomerActivity;

import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AdminShowMessageReceived extends Fragment {

	private EditText edtTextsend;
	private LinearLayout layoutChat;
	private static TextView tvAccountName, tvDetailAccount;
	private Button btnSend;
	private static AccountUser accountUser;
	private int index;

	public AdminShowMessageReceived(AccountUser user, int position) {
		this.accountUser = user;
		this.index = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_writing_message, container, false);

		edtTextsend = (EditText) view
				.findViewById(R.id.activity_writing_message_edt_text_input);
		layoutChat = (LinearLayout) view
				.findViewById(R.id.activity_writing_message_Linearlayout_chat);
		btnSend = (Button) view
				.findViewById(R.id.activity_writing_message_btn_send);
		tvAccountName = (TextView) view
				.findViewById(R.id.activity_writing_message_tv_accountName);
		tvDetailAccount = (TextView) view
				.findViewById(R.id.activity_writing_message_tv_detail_Account);

		tvDetailAccount.setVisibility(TextView.VISIBLE);
		tvAccountName.setText("Tài khoản: " + accountUser.getAccount());

		// load message received
		int c = accountUser.getCountMessage();
		if(c != -1) {
			new StartLoadListData(c).execute();
		}
		
		edtTextsend.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					sendData();
					return true;
				}
				return false;
			}
		});

		layoutChat.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(accountUser.getCountMessage() > 0) {
					FeedbackManagementActivity.list.get(index).setCountMessage(0);
					FeedbackManagementActivity.adapter.notifyDataSetChanged();
					ParseApplication.NoticeReadMessage("countMessage", accountUser.getAccount());
					AdminstratorActivity.CancelNotificationMessage();
				}
				return true;
			}
		});
		
		
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendData();
			}
		});

		tvDetailAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShowInforCustomerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("AccountUser", accountUser);
				bundle.putInt("index", -3);
				bundle.putBoolean("admin", false);
				intent.putExtra("Customer", bundle);
				startActivityForResult(intent, 0);
			}
		});

		return view;
	}

	protected void sendData() {
		final String text = edtTextsend.getText().toString().trim() + "";
		if (!text.equalsIgnoreCase("")) {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					ParseApplication.sendMessage("DataMessageReceived", "countMessageAdminSent",
							accountUser.getAccount(), text);
				}
			});
			thread.start();
			edtTextsend.setText("");
			DisplayText(text);
		}
	}

	protected void DisplayText(String text) {
		TextView textDisplay = new TextView(getActivity());
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
	}

	protected void DisplayTextReceived(String text) {
		TextView textDisplay = new TextView(getActivity());
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
	}

	protected void DisplayTextDayReceived(String text) {
		TextView textDisplay = new TextView(getActivity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(20, 20, 20, 20);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		textDisplay.setLayoutParams(params);
		textDisplay.setTextColor(Color.RED);
		textDisplay.setText(text);
		textDisplay.setTextSize(24);
		layoutChat.addView(textDisplay);
	}
	
	public static void setUpdateAccountUser(String account, String pass,
			String address, String phone, String email, String fullname) {
		accountUser.setAccount(account);
		accountUser.setAddress(address);
		accountUser.setEmail(email);
		accountUser.setPhone(phone);
		accountUser.setPassword(pass);
		accountUser.setFullName(fullname);
		tvAccountName.setText("Tài khoản: " + accountUser.getAccount());
	}
	

	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<String>> implements
			DialogInterface.OnClickListener {

		ProgressDialog dialog;
		int count;

		public StartLoadListData(int c) {
			this.count = c;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "",
					"Loading, please waiting...");
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			try {
				return ParseApplication.getAllMessageOfAccount("DataMessage", accountUser.getAccount());
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
				Log.d("Count", String.valueOf(count));
				for (String mesage : result) {
					if (count > 0 && (i == (result.size() - count))) {
						DisplayTextDayReceived("Tin nhắn mới từ khách hàng");
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

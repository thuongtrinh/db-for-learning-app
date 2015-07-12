package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.CustomerManagerAdapter;
import com.example.object.AccountUser;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class CustomerManagementActivity extends Activity {

	private ListView lvCustomer;
	private static CustomerManagerAdapter adapter;
	private static ArrayList<AccountUser> list = new ArrayList<AccountUser>();
	private ActionBar actionBar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_manager);

		lvCustomer = (ListView) findViewById(R.id.activity_customer_manager_lv_customer);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		list = ParseApplication.getAllInforUser();
		
		if(list.size() > 0) {
			adapter = new CustomerManagerAdapter(this, list);
			lvCustomer.setAdapter(adapter);
		}

		lvCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Intent intent = new Intent(CustomerManagementActivity.this, ShowInforCustomerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("AccountUser", list.get(position));
				bundle.putInt("index", position);
				bundle.putBoolean("admin", true);
				intent.putExtra("Customer", bundle);
				startActivityForResult(intent, 0);
			}
		});
		
//		lvCustomer.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					final int position, long id) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(CustomerManagementActivity.this);
//				builder.setIcon(R.drawable.icon_question_red);
//				builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản của khách hàng này không?");
//				builder.setTitle("Câu hỏi");
//				builder.setPositiveButton("Không phải",
//					new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				});
//				builder.setNegativeButton("Đúng vậy",
//					new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						boolean ok = ParseApplication.DeleteAccountOfCustomer(list.get(position).getAccount());
//						list.remove(position);
//						adapter.notifyDataSetChanged();
//					}
//				});
//				builder.show();
//				
//				return false;
//			}
//		});

	}

	public static void setUpdateAccountUser(int position, String account, String pass,
			String address, String phone, String email, String fullname) {
		list.get(position).setPassword(pass);
		list.get(position).setAccount(account);
		list.get(position).setPassword(pass);
		list.get(position).setAddress(address);
		list.get(position).setPhone(phone);
		list.get(position).setEmail(email);
		list.get(position).setFullName(fullname);
		adapter.notifyDataSetChanged();
	}

}

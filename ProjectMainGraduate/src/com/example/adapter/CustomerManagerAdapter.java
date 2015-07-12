package com.example.adapter;
///////// TTTXXXTTT
import java.util.ArrayList;

import com.example.object.AccountUser;
import com.example.projectmaingraduate.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomerManagerAdapter extends ArrayAdapter<AccountUser> {
	
	private Activity activity;
	private ArrayList<AccountUser> arrayList;

	public CustomerManagerAdapter(Activity context, ArrayList<AccountUser> list) {
		super(context, 0, list);
		this.arrayList = list;
		this.activity = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_item_customer, null);
		}
		
		TextView tvSTT = (TextView) convertView.findViewById(R.id.adapter_item_customer_tv_stt);
		TextView tvAccount = (TextView) convertView.findViewById(R.id.adapter_item_customer_tv_account);
		TextView tvPassword = (TextView) convertView.findViewById(R.id.adapter_item_customer_tv_password);
		
		tvSTT.setText(String.valueOf(position));
		tvAccount.setText(arrayList.get(position).getAccount());
		tvPassword.setText(arrayList.get(position).getPassword());

		return convertView;
	}
	
}













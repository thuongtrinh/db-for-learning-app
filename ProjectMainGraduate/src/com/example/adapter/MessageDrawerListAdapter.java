package com.example.adapter;

import java.util.ArrayList;

import com.example.object.AccountUser;
import com.example.projectmaingraduate.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; 
import android.widget.ImageView;
import android.widget.TextView;

public class MessageDrawerListAdapter extends ArrayAdapter<AccountUser> {

	private ArrayList<AccountUser> arrayList;
	private Activity activity;

	public MessageDrawerListAdapter(Activity context, ArrayList<AccountUser> list) {
		super(context, 0, list);
		this.activity = context;
		this.arrayList = list;
	}


	@Override
	public AccountUser getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_message_user, null);
		}
		TextView tvCountMessage = (TextView) convertView
				.findViewById(R.id.adapter_message_user_tv_count_message);
		TextView tvAccount = (TextView) convertView
				.findViewById(R.id.adapter_message_user_tv_account_user);
		ImageView imgEnvelope = (ImageView) convertView
				.findViewById(R.id.adapter_message_user_img_envelope);
		ImageView imgOpenEnvelope = (ImageView) convertView
				.findViewById(R.id.adapter_message_user_img_open_envelope);
		
		tvAccount.setText(arrayList.get(position).getAccount());
		if (arrayList.get(position).getCountMessage() > 0) {
			tvCountMessage.setVisibility(TextView.VISIBLE);
			imgEnvelope.setVisibility(ImageView.VISIBLE);
			imgOpenEnvelope.setVisibility(ImageView.INVISIBLE);
			tvCountMessage.setText(String.valueOf(arrayList.get(position).getCountMessage()));
		} else if(arrayList.get(position).getCountMessage() == 0) {
			imgOpenEnvelope.setVisibility(ImageView.VISIBLE);
			imgEnvelope.setVisibility(ImageView.INVISIBLE);
			tvCountMessage.setVisibility(TextView.INVISIBLE);
		} else {
			tvCountMessage.setVisibility(TextView.INVISIBLE);
			imgEnvelope.setVisibility(ImageView.INVISIBLE);
			imgOpenEnvelope.setVisibility(ImageView.INVISIBLE);
		}
		
		return convertView;
	}

}






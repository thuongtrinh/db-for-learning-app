package com.example.adapter;

import java.util.ArrayList;

import com.example.projectmaingraduate.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class IncomeManagerAdapter extends ArrayAdapter<Integer>{
	
	private Activity activity;
	private ArrayList<Integer> arrayList;
	private CallBack callBack;
	
	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	public interface CallBack {
		public void onclickCallBackDetailIncome(int year);
	}
	

	public IncomeManagerAdapter(Activity context, ArrayList<Integer> list) {
		super(context, 0, list);
		this.activity = context;
		this.arrayList = list;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_income_manager, null);
		}
		
		TextView tvYearIncome = (TextView) convertView.findViewById(R.id.adapter_income_manager_tv_income_year);
		ImageButton imgbtnShow = (ImageButton) convertView.findViewById(R.id.adapter_income_manager_imgbtn_detail);
		tvYearIncome.setText("Doanh thu năm " + String.valueOf(arrayList.get(position)));
		
		imgbtnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(callBack != null) {
					callBack.onclickCallBackDetailIncome(arrayList.get(position));
				}
			}
		});
		
		return convertView;
	}
}


















package com.example.adapter;

import java.util.ArrayList;

import com.example.object.Income;
import com.example.projectmaingraduate.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DetailIncomeManagerAdapter extends ArrayAdapter<Income> {
	
	private Activity activity;
	private ArrayList<Income> arrayList;

	public DetailIncomeManagerAdapter(Activity context, ArrayList<Income> objects) {
		super(context, 0, objects);
		this.activity = context;
		this.arrayList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_detail_income_manager, null);
		}
		
		TextView tvSTT = (TextView) convertView.findViewById(R.id.adapter_detail_income_manager_tv_stt);
		TextView tvDay = (TextView) convertView.findViewById(R.id.adapter_detail_income_manager_tv_DayMonthYear);
		TextView tvNameProduct = (TextView) convertView.findViewById(R.id.adapter_detail_income_manager_tv_name_product);
		TextView tvSoluong = (TextView) convertView.findViewById(R.id.adapter_detail_income_manager_tv_soluong);
		TextView tvSumMoney = (TextView) convertView.findViewById(R.id.adapter_detail_income_manager_tv_SumMoney);
		
		tvSTT.setText(String.valueOf(position));
		tvDay.setText(arrayList.get(position).getTime());
		tvSoluong.setText(arrayList.get(position).getSoluong() + "");
		tvNameProduct.setText(arrayList.get(position).getProductLabel());
		tvSumMoney.setText(arrayList.get(position).getSumMoney());
		
		return convertView;
	}
	
}







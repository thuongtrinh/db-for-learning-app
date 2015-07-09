package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.IncomeManagerAdapter;
import com.example.adapter.IncomeManagerAdapter.CallBack;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class IncomeManagementActivity extends Activity {
	
	private ListView lvIncome;
	private IncomeManagerAdapter adapter;
	private static ArrayList<Integer> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income_management);
		lvIncome =  (ListView) findViewById(R.id.activity_income_management_listView);
		
		list = ParseApplication.checkYearNumberIncome();
		if(list.size() > 0) {
			sort(list);
			adapter = new IncomeManagerAdapter(IncomeManagementActivity.this, list);
			adapter.setCallBack(new CallBack() {
				
				@Override
				public void onclickCallBackDetailIncome(int year) {
					Intent intent = new Intent(IncomeManagementActivity.this, DetailIncomeManager.class);
					Bundle bundle = new Bundle();
					bundle.putInt("Year", year);
					intent.putExtra("Income", bundle);
					startActivityForResult(intent, 0);
				}
			});
			
			lvIncome.setAdapter(adapter);
		}
		
	}

	private void sort(ArrayList<Integer> integers) {
		int tam, n = integers.size();
		for(int i = 0; i < n -1; i ++) {
			for(int j = i+1; j < n; j++) {
				if(integers.get(i) < integers.get(j) ) {
					tam = integers.get(i);
					integers.set(i, integers.get(j));
					integers.set(j, tam);
				}
			}
		}
	}
}







//private void sort(ArrayList<Integer> integers) {
//	int tam, n = integers.size();
//	for(int i = n - 1; i > 0; i--) {
//		for(int j = i - 1; j >= 0; j--) {
//			if(integers.get(i) > integers.get(j) ) {
//				tam = integers.get(i);
//				integers.set(i, integers.get(j));
//				integers.set(j, tam);
//			}
//		}
//	}
//	
//	for(int i = 0; i<n; i++) {
//		Log.d("vi tri " + i, "" +  integers.get(i));
//	}
//}







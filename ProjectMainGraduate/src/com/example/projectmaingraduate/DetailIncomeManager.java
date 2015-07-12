package com.example.projectmaingraduate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.example.adapter.DetailIncomeManagerAdapter;
import com.example.object.Income;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailIncomeManager extends Activity {
	
	private ArrayList<Income> list;
	private Spinner spinnerProduct, spinnerMonth;
	private TextView tvSumIncome;
	private ListView lvDetailIncome;
	private ProgressBar progressBar;
	private DetailIncomeManagerAdapter adapter;
	private String month [] = {"Tất cả", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
			"Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
	private String product [] = {"Tất cả", "Điện thoại", "Máy tính bảng", "Phụ kiện"};
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_income_manager);
		
		spinnerProduct = (Spinner) findViewById(R.id.activity_detail_income_manager_spinnerProduct);
		spinnerMonth = (Spinner) findViewById(R.id.activity_detail_income_manager_spinnerMonth);
		tvSumIncome = (TextView) findViewById(R.id.activity_detail_income_manager_tv_SumIncome);
		lvDetailIncome = (ListView) findViewById(R.id.activity_detail_income_manager_lv_detai_income);
		progressBar = (ProgressBar) findViewById(R.id.activity_detail_income_manager_ProgressBar);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("Income");
		final int year = bundle.getInt("Year");
		
		ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, month);
		ArrayAdapter<String> adapterProduct = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, product);
		adapterMonth.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		adapterProduct.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinnerMonth.setAdapter(adapterMonth);
		spinnerProduct.setAdapter(adapterProduct);
		
		new LoadDataAsynTask(true, 0).execute(year);
		
		spinnerMonth.setPressed(true);
		spinnerProduct.setPressed(true);
		
		spinnerProduct.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(!spinnerProduct.isPressed()) {
					list.clear();
					tvSumIncome.setText("");
					adapter.notifyDataSetChanged();
					// lay du lieu label
					new LoadDataAsynTask(false, position).execute(year);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		
		spinnerMonth.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(!spinnerMonth.isPressed()) {
					list.clear();
					tvSumIncome.setText("");
					adapter.notifyDataSetChanged();
					new LoadDataAsynTask(true, position).execute(year);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	

	private class LoadDataAsynTask extends AsyncTask<Integer, Void, ArrayList<Income>> {
		
		int index;
		boolean check;
		
		public LoadDataAsynTask(boolean b, int position) {
			this.index = position;
			this.check = b;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<Income> doInBackground(Integer... params) {
			int year = params[0];
			if(check) {
				Log.d("Log no else", "no else");
				return ParseApplication.getInforIncomeOfStore(year, index);
			} else {
				Log.d("Log else", "else");
				return ParseApplication.getInforIncomeOfStoreSortLabel(year, index);
			}
		}
		
		@Override
		protected void onPostExecute(final ArrayList<Income> result) {
			list = result;
			super.onPostExecute(result);
			progressBar.setVisibility(View.GONE);
			if(result.size() > 0) {
				adapter = new DetailIncomeManagerAdapter(DetailIncomeManager.this, list);
				lvDetailIncome.setAdapter(adapter);
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						long money = 0;
						for(Income income : result) {
							String price = income.getSumMoney();
							String cost = price.replace(".", "");
							long gia = Long.parseLong(cost);
							money = money + gia;
						}
						final long tien = money;
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								String card = stylePrice(tien);
								tvSumIncome.setText("Tổng doanh thu: " + card + " VNĐ");
							}

							String stylePrice(long price) {
								long newPrice = price;
								// establish dot "."
								DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
								// decimalFormatSymbols.setDecimalSeparator('.');
								decimalFormatSymbols.setGroupingSeparator('.');
								DecimalFormat decimalFormat = new DecimalFormat("#,##0", decimalFormatSymbols);
								return decimalFormat.format(newPrice);
							}
						});
					}
				});
				thread.start();

			}
		}
		
	}

	
}









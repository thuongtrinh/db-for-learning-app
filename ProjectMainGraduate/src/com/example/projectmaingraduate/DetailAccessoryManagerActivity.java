package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.DetailAccessoryManagerAdapter;
import com.example.adapter.DetailAccessoryManagerAdapter.CallBack;
import com.example.object.DetailAccessory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DetailAccessoryManagerActivity extends Activity {
	
	public static DetailAccessoryManagerAdapter adapter = null;
	public static ArrayList<DetailAccessory> list;
	private ListView lvDetailAccessory;
	public String idType;
	private ProgressBar progressBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_product_manager);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("AccessoryType");
		idType = bundle.getString("IdTypeAccessory");

		lvDetailAccessory = (ListView) findViewById(R.id.activity_detail_product_manager_lv_product);
		progressBar = (ProgressBar) findViewById(R.id.activity_detail_product_manager_ProgressBar);
		
		new StartLoadListData().execute();
		
	}

	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<DetailAccessory>> implements
			DialogInterface.OnClickListener {

		@Override
		protected void onPreExecute() {
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<DetailAccessory> doInBackground(Void... params) {
			try {
				return ParseApplication.getDetailAccessoryManager(idType);
			} catch (Exception e) {
				Log.d("DetailAccessoryManagerActivity", "Error loading");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<DetailAccessory> result) {
			list = result;
			progressBar.setVisibility(View.GONE);

			if (result.size() > 0) {
				adapter = new DetailAccessoryManagerAdapter(DetailAccessoryManagerActivity.this, result);
				adapter.setCallBack(new CallBack() {

					@Override
					public void onclickCallBackEdit(int index) {
						Intent intent = new Intent(DetailAccessoryManagerActivity.this, 
								UpdateDetailAccessoryManagerActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdUpdateAccessory", list.get(index).getId());
						bundle.putInt("position", index);
						intent.putExtra("UpdateAccessory", bundle);
						startActivityForResult(intent, 0);
					}

					@Override
					public void onclickCallBackDelete(final int index) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								DetailAccessoryManagerActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Câu hỏi");
						builder.setMessage("Bạn có chắc chăn muốn xóa thông tin này khỏi CSDL không?");
						builder.setNegativeButton("Đúng vậy", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									boolean ok = ParseApplication
											.RemoveObjectProductAccessory(list.get(index).getId());
									list.remove(index);
									adapter.notifyDataSetChanged();
									if (ok) {
										Toast.makeText(getApplicationContext(), "Xóa thành công",
												Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(getApplicationContext(), "Xóa thất bại",
												Toast.LENGTH_LONG).show();
									}
								} catch (Exception e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(), "Xóa thất bại", 
												Toast.LENGTH_LONG).show();
								}
							}
						});

						builder.setPositiveButton("Không phải", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// khong lam gi ca
							}
						});
						builder.show();
					}
				});
				
				lvDetailAccessory.setAdapter(adapter);
			}

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			progressBar.setVisibility(View.GONE);
		}
	}
	
}

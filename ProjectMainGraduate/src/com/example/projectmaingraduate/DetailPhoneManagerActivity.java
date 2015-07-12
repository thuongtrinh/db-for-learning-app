package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.DetailPhoneManagerAdapter;
import com.example.adapter.DetailPhoneManagerAdapter.CallBack;
import com.example.object.DetailPhone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DetailPhoneManagerActivity extends Activity {

	public static DetailPhoneManagerAdapter adapter = null;
	public static ArrayList<DetailPhone> list;
	private ListView lvDetailPhone;
	public String idType;
	private ProgressBar progressBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_product_manager);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("PhoneType");
		idType = bundle.getString("IdTypePhone");

		lvDetailPhone = (ListView) findViewById(R.id.activity_detail_product_manager_lv_product);
		progressBar = (ProgressBar) findViewById(R.id.activity_detail_product_manager_ProgressBar);
		
		new StartLoadListData().execute();
		
	}

	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<DetailPhone>> implements
			DialogInterface.OnClickListener {

//		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
//			dialog = ProgressDialog.show(DetailPhoneManagerActivity.this, "", "");
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<DetailPhone> doInBackground(Void... params) {
			try {
				return ParseApplication.getDetailPhoneManager(idType);
			} catch (Exception e) {
				Log.d("DetailPhoneManagerActivity", "Error loading");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<DetailPhone> result) {
//			dialog.dismiss();
			list = result;
			progressBar.setVisibility(View.GONE);

			if (result.size() > 0) {
				adapter = new DetailPhoneManagerAdapter(DetailPhoneManagerActivity.this, result);
				adapter.setCallBack(new CallBack() {

					@Override
					public void onclickCallBackShowDetail(int index) {
						Intent intent = new Intent(
								DetailPhoneManagerActivity.this, DetailColorPhoneManagerDiaglogActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdColorPhone", list.get(index).getId());
						bundle.putInt("position_pre", index);
						intent.putExtra("ColorPhone", bundle);
						startActivityForResult(intent, 0);
					}

					@Override
					public void onclickCallBackEdit(int index) {
						Intent intent = new Intent(
								DetailPhoneManagerActivity.this, UpdateDetailPhoneManagerActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdUpdatePhone", list.get(index).getId());
						intent.putExtra("UpdatePhone", bundle);
						startActivityForResult(intent, 0);

					}

					@Override
					public void onclickCallBackDelete(final int index) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								DetailPhoneManagerActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Câu hỏi");
						builder.setMessage("Bạn có chắc chăn muốn xóa thông tin này khỏi CSDL không?");
						builder.setNegativeButton("Đúng vậy", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								try {
									boolean ok = ParseApplication
										.RemoveObjectProduct("Phone", "PhoneNumber", list.get(index).getId());
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
				
				lvDetailPhone.setAdapter(adapter);
			}

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
//			dialog.cancel();
			progressBar.setVisibility(View.GONE);
		}
	}

}

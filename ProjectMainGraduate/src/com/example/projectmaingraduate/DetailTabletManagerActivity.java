package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.DetailTabletManagerAdapter;
import com.example.adapter.DetailTabletManagerAdapter.CallBack;
import com.example.object.DetailTablet;

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

public class DetailTabletManagerActivity extends Activity {
	
	public static DetailTabletManagerAdapter adapter = null;
	public static ArrayList<DetailTablet> list;
	private ListView lvDetailTablet;
	public String idType;
	private ProgressBar progressBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_product_manager);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("TabletType");
		idType = bundle.getString("IdTypeTablet");

		lvDetailTablet = (ListView) findViewById(R.id.activity_detail_product_manager_lv_product);
		progressBar = (ProgressBar) findViewById(R.id.activity_detail_product_manager_ProgressBar);
		
		new StartLoadListData().execute();
		
	}

	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<DetailTablet>> implements
			DialogInterface.OnClickListener {

		@Override
		protected void onPreExecute() {
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<DetailTablet> doInBackground(Void... params) {
			try {
				return ParseApplication.getDetailTabletManager(idType);
			} catch (Exception e) {
				Log.d("DetailTabletManagerActivity", "Error loading");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<DetailTablet> result) {
			list = result;
			progressBar.setVisibility(View.GONE);

			if (result.size() > 0) {
				adapter = new DetailTabletManagerAdapter(DetailTabletManagerActivity.this, result);
				adapter.setCallBack(new CallBack() {

					@Override
					public void onclickCallBackShowDetail(int index) {
						Intent intent = new Intent(
								DetailTabletManagerActivity.this, DetailColorTabletManagerDiaglogActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdColorTablet", list.get(index).getId());
						bundle.putInt("position_pre", index);
						intent.putExtra("ColorTablet", bundle);
						startActivityForResult(intent, 0);
					}

					@Override
					public void onclickCallBackEdit(int index) {
						Intent intent = new Intent(
								DetailTabletManagerActivity.this, UpdateDetailTabletManagerActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdUpdateTablet", list.get(index).getId());
						intent.putExtra("UpdateTablet", bundle);
						startActivityForResult(intent, 0);

					}

					@Override
					public void onclickCallBackDelete(final int index) {
						AlertDialog.Builder builder = new AlertDialog.Builder(DetailTabletManagerActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Câu hỏi");
						builder.setMessage("Bạn có chắc chăn muốn xóa thông tin này khỏi CSDL không?");
						builder.setNegativeButton("Đúng vậy", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									boolean ok = ParseApplication
											.RemoveObjectProduct("Tablet", "TabletNumber", list.get(index).getId());
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
				
				lvDetailTablet.setAdapter(adapter);
			}

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			progressBar.setVisibility(View.GONE);
		}
	}
	
}

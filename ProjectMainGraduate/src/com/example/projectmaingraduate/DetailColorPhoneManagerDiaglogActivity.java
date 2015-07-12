package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.DetailColorPhoneManagerAdapter;
import com.example.adapter.DetailColorPhoneManagerAdapter.CallBack;
import com.example.object.PhoneNumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DetailColorPhoneManagerDiaglogActivity extends Activity {

	public static DetailColorPhoneManagerAdapter adapter = null;
	private ListView lvDetailColorPhone;
	private ProgressBar progressBar;
	private String idColorType;
	private int position_pre;
	public static ArrayList<PhoneNumber> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_detail_color_product_manager);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("ColorPhone");
		position_pre = bundle.getInt("position_pre"); 
		idColorType = bundle.getString("IdColorPhone");

		lvDetailColorPhone = (ListView) findViewById(R.id.dialog_detail_color_product_manager_lv_product);
		progressBar = (ProgressBar) findViewById(R.id.dialog_detail_color_product_manager_ProgressBar);
		
		new StartLoadListData().execute();
		
	}

	private class StartLoadListData extends
			AsyncTask<Void, Void, ArrayList<PhoneNumber>> implements
			DialogInterface.OnClickListener {

		@Override
		protected void onPreExecute() {
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<PhoneNumber> doInBackground(Void... params) {
			try {
				return ParseApplication.getDetailColorPhoneManager(idColorType);
			} catch (Exception e) {
				Log.d("DetailColorPhoneManagerDiaglogActivity", "Error loading");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<PhoneNumber> result) {
			
			list = result;
			progressBar.setVisibility(ProgressBar.GONE);

			if (result.size() > 0) {
				adapter = new DetailColorPhoneManagerAdapter(
						DetailColorPhoneManagerDiaglogActivity.this, result);
				
				adapter.setCallBack(new CallBack() {

					@Override
					public void onclickCallColorBackEdit(int index) {
						 Intent intent = new Intent(DetailColorPhoneManagerDiaglogActivity.this,
						 UpdateDetailColorPhoneManagerDialogActivity.class);
						 Bundle bundle = new Bundle();
						 bundle.putString("objectId", list.get(index).getIdDetails());
						 bundle.putInt("numberSold", list.get(index).getNumberSold());
						 bundle.putInt("numberProduct", list.get(index).getNumber());
						 bundle.putInt("position", index); 
						 bundle.putInt("position_pre", position_pre);
						 intent.putExtra("UpdateColorPhone", bundle);
						 startActivityForResult(intent, 0);
					}

					@Override
					public void onclickCallColorBackDelete(final int index) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								DetailColorPhoneManagerDiaglogActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Câu hỏi");
						builder.setMessage("Bạn có chắc chắn muốn xóa thông tin này khỏi CSDL không?");
						builder.setNegativeButton("Đúng vậy", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								try {
									boolean ok = ParseApplication.RemoveObjectSubProduct("PhoneNumber", 
											list.get(index).getIdDetails());
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
							public void onClick(DialogInterface dialog, int which) {
								// khong lam gi ca
							}
						});
						builder.show();
					}
				});

				lvDetailColorPhone.setAdapter(adapter);
			}
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}

}

package com.example.projectmaingraduate;

import java.util.ArrayList;

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

import com.example.adapter.DetailColorTabletManagerAdapter;
import com.example.adapter.DetailColorTabletManagerAdapter.CallBack;
import com.example.object.TabletNumber;

public class DetailColorTabletManagerDiaglogActivity extends Activity{
	
	
	public static DetailColorTabletManagerAdapter adapter = null;
	private ListView lvDetailColorTablet;
	private ProgressBar progressBar;
	private String idColorType;
	private int position_pre;
	public static ArrayList<TabletNumber> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_detail_color_product_manager);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("ColorTablet");
		position_pre = bundle.getInt("position_pre"); 
		idColorType = bundle.getString("IdColorTablet");

		lvDetailColorTablet = (ListView) findViewById(R.id.dialog_detail_color_product_manager_lv_product);
		progressBar = (ProgressBar) findViewById(R.id.dialog_detail_color_product_manager_ProgressBar);
		
		new StartLoadListData().execute();
		
	}

	private class StartLoadListData extends
			AsyncTask<Void, Void, ArrayList<TabletNumber>> implements
			DialogInterface.OnClickListener {

		@Override
		protected void onPreExecute() {
			progressBar.showContextMenu();
		}

		@Override
		protected ArrayList<TabletNumber> doInBackground(Void... params) {
			try {
				return ParseApplication.getDetailColorTabletManager(idColorType);
			} catch (Exception e) {
				Log.d("DetailColorTabletManagerDiaglogActivity", "Error loading");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<TabletNumber> result) {
			
			list = result;
			progressBar.setVisibility(ProgressBar.GONE);

			if (result.size() > 0) {
				adapter = new DetailColorTabletManagerAdapter(
						DetailColorTabletManagerDiaglogActivity.this, result);
				
				adapter.setCallBack(new CallBack() {

					@Override
					public void onclickCallColorBackEdit(int index) {
						 Intent intent = new Intent(DetailColorTabletManagerDiaglogActivity.this,
						 UpdateDetailColorTabletManagerDialogActivity.class);
						 Bundle bundle = new Bundle();
						 bundle.putString("objectId", list.get(index).getIdDetails());
						 bundle.putInt("numberSold", list.get(index).getNumberSold());
						 bundle.putInt("numberProduct", list.get(index).getNumber());
						 bundle.putInt("position", index); 
						 bundle.putInt("position_pre", position_pre);
						 intent.putExtra("UpdateColorTablet", bundle);
						 startActivityForResult(intent, 0);
					}

					@Override
					public void onclickCallColorBackDelete(final int index) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								DetailColorTabletManagerDiaglogActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Câu hỏi");
						builder.setMessage("Bạn có chắc chắn muốn xóa thông tin này khỏi CSDL không?");
						builder.setNegativeButton("Đúng vậy", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								try {
									boolean ok = ParseApplication.RemoveObjectSubProduct("TabletNumber", 
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

				lvDetailColorTablet.setAdapter(adapter);
			}
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}
	
}

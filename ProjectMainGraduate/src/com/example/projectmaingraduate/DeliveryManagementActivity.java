package com.example.projectmaingraduate;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.DeliveryManagerAdapter;
import com.example.adapter.DeliveryManagerAdapter.CallBack;
import com.example.object.AccountUser;
import com.example.object.Delivery;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryManagementActivity extends Activity {
	
	private ListView listview;
	private DeliveryManagerAdapter adapter;
	private boolean selected = false;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_manager);
		listview = (ListView) findViewById(R.id.activity_delivery_manager_lv_giaohang);
		new DataAsyncTask().execute();
	}
	
	
	private class DataAsyncTask extends AsyncTask<Void, Void, ArrayList<Delivery>> implements
				DialogInterface.OnClickListener {

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(DeliveryManagementActivity.this, "", "Loading...");
		}
		
		@Override
		protected ArrayList<Delivery> doInBackground(Void... params) {
			return ParseApplication.getInforDelivery();
		}
		
		@Override
		protected void onPostExecute(final ArrayList<Delivery> result) {
			dialog.dismiss();
			
			if(result.size() > 0) {
				adapter = new DeliveryManagerAdapter(DeliveryManagementActivity.this, result);
				adapter.setCallBack(new CallBack() {
					
					@Override
					public void onClickCallback(LinearLayout lnLayout, int position) {
						
						if(selected) {
							selected = false;
							lnLayout.setVisibility(LinearLayout.GONE);
						} else {
							selected = true;
							lnLayout.setVisibility(LinearLayout.VISIBLE);
						}
					}

					@Override
					public void onClickCallbackCustomer(final int position) {
						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								AccountUser accountUser = result.get(position).getUser();
								Intent intent = new Intent(DeliveryManagementActivity.this, ShowInforCustomerActivity.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("AccountUser", accountUser);
								bundle.putInt("index", -2);
								bundle.putBoolean("admin", false); // don't important
								intent.putExtra("Customer", bundle);
								startActivityForResult(intent, 0);
								return null;
							}
						}.execute();
					}

					@Override
					public void onClickCallbackProduct(int position, String price) {
						Intent intent = new Intent(getApplicationContext(), ShowInforProductActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("idDelivery", result.get(position).getId());
						bundle.putString("Type", result.get(position).getType());
						bundle.putString("Price", price);
						intent.putExtra("pakage", bundle);
						startActivityForResult(intent, 0);
					}

					@Override
					public void onClickCallbackDelete(final int position) {
						AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryManagementActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu này không?");
						builder.setTitle("Câu hỏi");
						builder.setPositiveButton("Không phải",
							new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						builder.setNegativeButton("Đúng vậy",
							new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Thread thread = new Thread(new Runnable() {
									
									@Override
									public void run() {
										final boolean ok = ParseApplication.RemoveDataDelivery(result.get(position).getId());
										
										handler.post(new Runnable() {
											
											@Override
											public void run() {
												if (ok) {
													result.remove(position);
													adapter.notifyDataSetChanged();
													Toast.makeText(DeliveryManagementActivity.this,
															"Đã xóa thành công", Toast.LENGTH_SHORT).show();
												} else {
													Toast.makeText(DeliveryManagementActivity.this,
															"Xóa thất bại", Toast.LENGTH_SHORT).show();
												}
											}
										});
									}
								});
								thread.start();
							}
						});
						builder.show();
					}

					@Override
					public void onClickCallbackConfirmDelivery(final int position, 
							final boolean b, final TextView tvStateDelivery, final Spinner spinner) {
						AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryManagementActivity.this);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setTitle("Thông báo");
						if(b) {
							builder.setMessage("Bạn chưa giao hàng?");
						} else {
							builder.setMessage("Bạn đã giao hàng?");
						}
						builder.setPositiveButton("Không phải",
								new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								adapter.notifyDataSetChanged();
							}
						});
						
						builder.setNegativeButton("Đúng vậy",
								new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Thread thread = new Thread(new Runnable() {
									
									@Override
									public void run() {
										final boolean state = !b;
										Log.d("!b", String.valueOf(state));
										ParseQuery<ParseObject> query = ParseQuery.getQuery("TableDelivery");
										String id = result.get(position).getId();
										query.whereEqualTo("objectId", id);
										try {
											List<ParseObject> list = query.find();
											if(list.size() > 0) {
												list.get(0).put("StateDelivery", state);
												list.get(0).saveInBackground();
												handler.post(new Runnable() {
													
													@Override
													public void run() {
														result.get(position).setStateDelivery(state);
														adapter.notifyDataSetChanged();
													}
												});
											}
										} catch (ParseException e) {
											e.printStackTrace();
											Toast.makeText(DeliveryManagementActivity.this, 
													"Cập nhật chưa thành công", Toast.LENGTH_LONG).show();
										}
									}
								});
								thread.start();
							}
						});
						builder.show();
					}
				});
				
				listview.setAdapter(adapter);
			}
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		
	}
	
}
















package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.GridAccessoryAdapter;
import com.example.object.DetailAccessory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

public class ProductAccessoryActivity extends Activity {

	private GridAccessoryAdapter adapter = null;
	private GridView gridView;
	private TextView tvNameproduct;
	public static final int DETAIL_ACCESSORY = 0;
	private ArrayList<DetailAccessory> list = new ArrayList<DetailAccessory>();
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container_product);
		gridView = (GridView) findViewById(R.id.activity_container_product_gridview);
		tvNameproduct = (TextView) findViewById(R.id.activity_container_product_nameproduct);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("TypeAccessory");
		final String id = bundle.getString("idTypeAccessory");
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// get name type accessory to display on head
				final String nameproduct = ParseApplication.getNameTypeProduct("AccessoriesType", id);
				list = ParseApplication.getRepresentAccessory("IdTypeAccessories",id);
				
				// in ra kiem tra xem list.size()
				if(list.size() == 0) {
					Log.d("ID Co so phan tu", "000");
				} else {
					Log.d("ID Co so phan tu", Integer.toString(list.size()));
					for(DetailAccessory accessory : list) {
						Log.d("objectId", accessory.getId());
						Log.d("Label", accessory.getName());
					}
				}
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						tvNameproduct.setText("PHỤ KIỆN " + nameproduct.toUpperCase());

						if (list != null && list.size() > 0) {
							adapter = new GridAccessoryAdapter(ProductAccessoryActivity.this, list);
							adapter.setCallBack(new GridAccessoryAdapter.CallBack() {

								@Override
								public void onclickGridAccessory(int index, String idDetail, String priceOff) {
									Intent intent = new Intent(ProductAccessoryActivity.this, DetailProductAccessoryActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("PriceOff", priceOff);
									bundle.putString("IdOfDetailAccessory", idDetail);
									intent.putExtra("DetailAccessory", bundle);
									startActivityForResult(intent, DETAIL_ACCESSORY);
								}
							});
							gridView.setAdapter(adapter);
						}
					}
				});
			}
		});
		
		thread.start();
		
	}

}

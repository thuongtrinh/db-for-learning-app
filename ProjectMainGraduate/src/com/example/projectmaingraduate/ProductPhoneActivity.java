package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.GridPhoneAdapter;
import com.example.object.DetailPhone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.TextView;

public class ProductPhoneActivity extends Activity {

	private GridPhoneAdapter adapter = null;
	private GridView gridView;
	private TextView tvNameproduct;
	public static final int DETAIL_PHONE = 0;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container_product);
		gridView = (GridView) findViewById(R.id.activity_container_product_gridview);
		tvNameproduct = (TextView) findViewById(R.id.activity_container_product_nameproduct);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("TypePhone");
		final String id = bundle.getString("idTypePhone");
		String nameproduct = ParseApplication.getNameTypeProduct("PhoneType", id);
		tvNameproduct.setText("ĐIỆN THOẠI " + nameproduct.toUpperCase());
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				final ArrayList<DetailPhone> list = ParseApplication.getRepresentPhone("IdTypePhone", id);
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						if (list != null && list.size() > 0) {
							adapter = new GridPhoneAdapter(ProductPhoneActivity.this, list);
							adapter.setCallBack(new GridPhoneAdapter.GridPhoneOnClickAdapter() {

								@Override
								public void onclickGridPhone(int index, String idDetail, String priceOff) {
									Intent intent = new Intent(ProductPhoneActivity.this, DetailProductPhoneActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("PriceOff", priceOff);
									bundle.putString("IdOfDetailPhone", idDetail);
									intent.putExtra("DetailPhone", bundle);
									startActivityForResult(intent, DETAIL_PHONE);
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

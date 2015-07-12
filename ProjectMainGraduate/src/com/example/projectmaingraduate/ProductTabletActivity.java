package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.GridTabletAdapeter;
import com.example.object.DetailTablet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.TextView;

public class ProductTabletActivity extends Activity {

	private GridTabletAdapeter adapter = null;
	private GridView gridView;
	private TextView tvNameproduct;
	public static final int DETAIL_TABLET = 0;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container_product);
		gridView = (GridView) findViewById(R.id.activity_container_product_gridview);
		tvNameproduct = (TextView) findViewById(R.id.activity_container_product_nameproduct);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("TypeTablet");
		final String id = bundle.getString("idTypeTablet");
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				final ArrayList<DetailTablet> list = ParseApplication.getRepresentTablet("IdTypeTablet", id);
				final String nameproduct = ParseApplication.getNameTypeProduct("TabletType", id);
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						tvNameproduct.setText("MÁY TÍNH BẢNG " + nameproduct.toUpperCase());
						if (list != null && list.size() > 0) {
							adapter = new GridTabletAdapeter(ProductTabletActivity.this, list);
							adapter.setCallBack(new GridTabletAdapeter.GridTabletAdapter() {

								@Override
								public void onclickGridTablet(int index, String idDetail, String priceOff) {
									Intent intent = new Intent(ProductTabletActivity.this, DetailProductTabletActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("PriceOff", priceOff);
									bundle.putString("IdOfDetailTablet", idDetail);
									intent.putExtra("DetailTablet", bundle);
									startActivityForResult(intent, DETAIL_TABLET);
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

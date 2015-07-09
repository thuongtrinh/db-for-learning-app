package com.example.projectmaingraduate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductManagementActivity extends Activity {
	
	private ListView lvTypeProduct;
	private ArrayAdapter<String> adapter;
	private String Products [] = {"Điện thoại","Máy tính bảng","Các phụ kiện"}; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_management);
		
		lvTypeProduct = (ListView) findViewById(R.id.activity_product_management_lv_type_products);
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, Products);
		lvTypeProduct.setAdapter(adapter);
		
		lvTypeProduct.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					Intent intent = new Intent(getApplicationContext(), PhoneManagerActivity.class);
					startActivity(intent);
					break;
				case 1:
					Intent intent1 = new Intent(getApplicationContext(), TabletManagerActivity.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(getApplicationContext(), AccessoryManagerActivity.class);
					startActivity(intent2);
					break;

				default:
					break;
				}
				
			}
		});
		
	}
	
}

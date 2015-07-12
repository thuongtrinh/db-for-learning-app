package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.TypeAccessoryManagerAdapter;
import com.example.object.TypeAccessory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AccessoryManagerActivity extends Activity {

	private TypeAccessoryManagerAdapter adapter;
	private ArrayList<TypeAccessory> typeAccessories;
	private ListView lvTypeAccessory;
	private TextView tvHead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_all_product_manager);
		lvTypeAccessory = (ListView) findViewById(R.id.activity_product_manager_lv_product);
		tvHead = (TextView) findViewById(R.id.activity_product_manager_tv_head);
		
		tvHead.setText("Danh mục phụ kiện");
		
		typeAccessories = ParseApplication.getTypeAccessoryAdmin();

		if (typeAccessories.size() > 0) {
			adapter = new TypeAccessoryManagerAdapter(AccessoryManagerActivity.this, typeAccessories);
			lvTypeAccessory.setAdapter(adapter);
		}

		lvTypeAccessory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AccessoryManagerActivity.this, DetailAccessoryManagerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("IdTypeAccessory", typeAccessories.get(position).getIdLeft());
				intent.putExtra("AccessoryType", bundle);
				startActivityForResult(intent, 0);
			}
		});

	}

}

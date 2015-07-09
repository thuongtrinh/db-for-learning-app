package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.TypePhoneManagerAdapter;
import com.example.object.TypePhone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PhoneManagerActivity extends Activity{
	
	private TypePhoneManagerAdapter adapter;
	private ArrayList<TypePhone> typePhones;
	private ListView lvTypePhone;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_product_manager);
		
		lvTypePhone = (ListView) findViewById(R.id.activity_product_manager_lv_product);
		typePhones = ParseApplication.getTypePhone();
		
		if(typePhones.size() > 0) {
			adapter = new TypePhoneManagerAdapter(PhoneManagerActivity.this, typePhones);
			lvTypePhone.setAdapter(adapter);
		}
		
		
		lvTypePhone.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PhoneManagerActivity.this, DetailPhoneManagerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("IdTypePhone", typePhones.get(position).getId());
				intent.putExtra("PhoneType", bundle);
				startActivityForResult(intent, 0);
			}
		});
		
	}
	
	
}












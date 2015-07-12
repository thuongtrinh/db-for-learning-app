package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.adapter.TypeTabletManagerAdapter;
import com.example.object.TypeTablet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TabletManagerActivity extends Activity{

	private TypeTabletManagerAdapter adapter;
	private ArrayList<TypeTablet> typeTablets;
	private ListView lvTypeTablet;
	private TextView tvHead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_product_manager);
		
		lvTypeTablet = (ListView) findViewById(R.id.activity_product_manager_lv_product);
		tvHead = (TextView) findViewById(R.id.activity_product_manager_tv_head);
		
		tvHead.setText("Danh mục máy tính bảng");
		
		typeTablets = ParseApplication.getTypeTablet();
		
		if(typeTablets.size() > 0) {
			adapter = new TypeTabletManagerAdapter(TabletManagerActivity.this, typeTablets);
			lvTypeTablet.setAdapter(adapter);
		}
		
		
		lvTypeTablet.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TabletManagerActivity.this, DetailTabletManagerActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("IdTypeTablet", typeTablets.get(position).getId());
				intent.putExtra("TabletType", bundle);
				startActivityForResult(intent, 0);
			}
		});
		
	}
	
	
}

package com.example.projectmaingraduate;

import com.example.adapter.PromotionsPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class GiftContentActivity extends FragmentActivity{
	
	public static ViewPager viewPager;
	private PromotionsPagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gift_product_content);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("Gift");
		String idDetail = bundle.getString("IdGift");
		
		mAdapter = new PromotionsPagerAdapter(getSupportFragmentManager(), idDetail);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}













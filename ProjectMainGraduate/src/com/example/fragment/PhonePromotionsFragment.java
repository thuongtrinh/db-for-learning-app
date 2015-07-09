package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.GridPhoneAdapter;
import com.example.adapter.GridPhoneAdapter.GridPhoneOnClickAdapter;
import com.example.object.DetailPhone;
import com.example.projectmaingraduate.DetailProductPhoneActivity;
import com.example.projectmaingraduate.GiftContentActivity;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.R;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhonePromotionsFragment extends Fragment {
	
	private GridView gridViewPromtions;
	private GridPhoneAdapter adapter;
	private TextView tvHead;
	private ImageView imgBack, imgNext;
	public static final int DETAIL_PROMOTIONS_PHONE = 0;
	private String idGift;
	private final String TAG = "PhonePromotionsFragment";

	public PhonePromotionsFragment(String id) {
		this.idGift = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_promotions, container, false);
		
		tvHead = (TextView) view.findViewById(R.id.fragment_promotions_tv_headPromotions);
		imgBack = (ImageView) view.findViewById(R.id.fragment_promotions_img_back);
		imgNext = (ImageView) view.findViewById(R.id.fragment_promotions_img_next);
		gridViewPromtions = (GridView) view.findViewById(R.id.fragment_promotions_gridview);
		
		tvHead.setText("Khuyến mãi các loại điện thoại");
		imgNext.setVisibility(LinearLayout.INVISIBLE);
		
//		ArrayList<DetailPhone> listDetailPhones = ParseApplication
//				.getInforRepresentPhonePromotions(idGift);
//
//		if (listDetailPhones != null) {
//			adapter = new GridPhoneAdapter(getActivity(), listDetailPhones);
//			adapter.setCallBack(new GridPhoneOnClickAdapter() {
//				
//				@Override
//				public void onclickGridPhone(int index, String idDetail, String priceOff) {
//					Intent intent = new Intent(getActivity(), DetailProductPhoneActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("PriceOff", priceOff);
//					bundle.putString("IdOfDetailPhone", idDetail);
//					intent.putExtra("DetailPhone", bundle);
//					startActivityForResult(intent, DETAIL_PROMOTIONS_PHONE);
//				}
//			});
//			
//			gridViewPromtions.setAdapter(adapter);
//		}
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(2);
			}
		});
		
		
		new StartLoadListData().execute();
		
		return view;
	}
	
	private class StartLoadListData extends
			AsyncTask<Void, Void, ArrayList<DetailPhone>> implements DialogInterface.OnClickListener {

		ProgressDialog dialog;
	
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "", "Loading, please waiting...");
		}

		@Override
		protected ArrayList<DetailPhone> doInBackground(Void... params) {
			try {
				return ParseApplication.getInforRepresentPhonePromotions(idGift);
			} catch (Exception e) {
				Log.d(TAG, "Error loading getInforRepresentAccesoryPromotions" );
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(ArrayList<DetailPhone> result) {
			dialog.dismiss();
			if (result != null) {
				adapter = new GridPhoneAdapter(getActivity(), result);
				adapter.setCallBack(new GridPhoneOnClickAdapter() {
					
					@Override
					public void onclickGridPhone(int index, String idDetail, String priceOff) {
						Intent intent = new Intent(getActivity(), DetailProductPhoneActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("PriceOff", priceOff);
						bundle.putString("IdOfDetailPhone", idDetail);
						intent.putExtra("DetailPhone", bundle);
						startActivityForResult(intent, DETAIL_PROMOTIONS_PHONE);
					}
				});
				
				gridViewPromtions.setAdapter(adapter);
			}
		}
		
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		
	}
	
	
}



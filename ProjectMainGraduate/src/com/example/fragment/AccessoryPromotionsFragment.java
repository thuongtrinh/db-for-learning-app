package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.GridAccessoryAdapter;
import com.example.object.DetailAccessory;
import com.example.object.Gift;
import com.example.projectmaingraduate.DetailProductAccessoryActivity;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class AccessoryPromotionsFragment extends Fragment {
	
	private GridView gridViewPromtions;
	private GridAccessoryAdapter adapter;
	private TextView tvHead;
	private ImageView imgBack, imgNext;
	public static final int DETAIL_PROMOTIONS_ACCESSORY = 0;
	private String idGift;
	private final String TAG = "AccessoryPromotionsFragment";
	
	public AccessoryPromotionsFragment(String id) {
		// TODO Auto-generated constructor stub
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
		
		tvHead.setText("Khuyến mãi các phụ kiện");
		
//		ArrayList<DetailAccessory> listDetailAccessories = ParseApplication
//				.getInforRepresentAccesoryPromotions(idGift);
//
//		if (listDetailAccessories != null) {
//			adapter = new GridAccessoryAdapter(getActivity(), listDetailAccessories);
//			adapter.setCallBack(new GridAccessoryAdapter.CallBack() {
//
//				@Override
//				public void onclickGridAccessory(int index, String idDetail, String priceOff) {
//					Intent intent = new Intent(getActivity(), DetailProductAccessoryActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("PriceOff", priceOff);
//					bundle.putString("IdOfDetailAccessory", idDetail);
//					intent.putExtra("DetailAccessory", bundle);
//					startActivityForResult(intent, DETAIL_PROMOTIONS_ACCESSORY);
//				}
//			});
//			gridViewPromtions.setAdapter(adapter);
//		}
		
		imgNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(2);
			}
		});
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(0);
			}
		});
		
		new StartLoadListData().execute();
		
		return view;
	}
	
	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<DetailAccessory>> implements DialogInterface.OnClickListener {
		
		ProgressDialog dialog;
		
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "", "Loading, please waiting...");
		}

		@Override
		protected ArrayList<DetailAccessory> doInBackground(Void... params) {
//			ArrayList<DetailAccessory> listDetailAccessories = ParseApplication
//					.getInforRepresentAccesoryPromotions(idGift);
			
			try {
				return ParseApplication.getInforRepresentAccesoryPromotions(idGift);
			} catch (Exception e) {
				Log.d(TAG, "Error loading getInforRepresentAccesoryPromotions" );
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(ArrayList<DetailAccessory> result) {
			dialog.dismiss();
			if (result != null) {
				adapter = new GridAccessoryAdapter(getActivity(), result);
				adapter.setCallBack(new GridAccessoryAdapter.CallBack() {

					@Override
					public void onclickGridAccessory(int index, String idDetail, String priceOff) {
						Intent intent = new Intent(getActivity(), DetailProductAccessoryActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("PriceOff", priceOff);
						bundle.putString("IdOfDetailAccessory", idDetail);
						intent.putExtra("DetailAccessory", bundle);
						startActivityForResult(intent, DETAIL_PROMOTIONS_ACCESSORY);
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















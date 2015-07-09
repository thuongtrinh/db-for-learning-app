package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.GridTabletAdapeter;
import com.example.adapter.GridTabletAdapeter.GridTabletAdapter;
import com.example.object.DetailTablet;
import com.example.projectmaingraduate.DetailProductTabletActivity;
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
import android.widget.TextView;

public class TabletPromotionsFragment extends Fragment {
	
	private GridView gridViewPromtions;
	private GridTabletAdapeter adapter;
	private TextView tvHead;
	private ImageView imgBack, imgNext;
	public static final int DETAIL_PROMOTIONS_TABLET = 0;
	private String idGift;
	private final String TAG = "TabletPromotionsFragment";

	public TabletPromotionsFragment(String id) {
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
		
		tvHead.setText("Khuyến mãi các loại máy tính bảng");
		
//		ArrayList<DetailTablet> listDetailTablet = ParseApplication
//				.getInforRepresentTabletPromotions(idGift);
//
//		if (listDetailTablet != null) {
//			adapter = new GridTabletAdapeter(getActivity(), listDetailTablet);
//			adapter.setCallBack(new GridTabletAdapter() {
//				
//				@Override
//				public void onclickGridTablet(int index, String idDetail, String priceOff) {
//					Intent intent = new Intent(getActivity(), DetailProductTabletActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("PriceOff", priceOff);
//					bundle.putString("IdOfDetailTablet", idDetail);
//					intent.putExtra("DetailTablet", bundle);
//					startActivityForResult(intent, DETAIL_PROMOTIONS_TABLET);
//				}
//			});
//			
//			gridViewPromtions.setAdapter(adapter);
//		}
		
		
		imgNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(3);
			}
		});
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(1);
			}
		});
		
		new StartLoadListData().execute();
		
		return view;
	}
	
	
	private class StartLoadListData extends
			AsyncTask<Void, Void, ArrayList<DetailTablet>> implements DialogInterface.OnClickListener {

		ProgressDialog dialog;
		
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "", "Loading, please waiting...");
		}

		@Override
		protected ArrayList<DetailTablet> doInBackground(Void... params) {
			
			try {
				return ParseApplication.getInforRepresentTabletPromotions(idGift);
			} catch (Exception e) {
				Log.d(TAG, "Error loading getInforRepresentAccesoryPromotions" );
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(ArrayList<DetailTablet> result) {
			dialog.dismiss();

			if (result != null) {
				adapter = new GridTabletAdapeter(getActivity(), result);
				adapter.setCallBack(new GridTabletAdapter() {
					
					@Override
					public void onclickGridTablet(int index, String idDetail, String priceOff) {
						Intent intent = new Intent(getActivity(), DetailProductTabletActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("PriceOff", priceOff);
						bundle.putString("IdOfDetailTablet", idDetail);
						intent.putExtra("DetailTablet", bundle);
						startActivityForResult(intent, DETAIL_PROMOTIONS_TABLET);
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

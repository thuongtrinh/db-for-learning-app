package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.ListAccessoryTypeAdapter;
import com.example.object.TypeAccessory;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.ProductAccessoryActivity;
import com.example.projectmaingraduate.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class AccessoriesFragment extends Fragment{
	
	private ListView lvAccessoryType;
	private ListAccessoryTypeAdapter adapterAccessoryType = null;
	public static final int TYPE_ACCESSORY = 0;
	private ScaleAnimation scaleAnimation;
	private ArrayList<TypeAccessory> list2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_accessory, container, false);
		
		// set display of accessory
		scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.animation_scale);
		lvAccessoryType = (ListView) rootView.findViewById(R.id.fragment_accessories_lv_accessory);
		list2 = ParseApplication.getTypeAccessory();
		
		if (list2 != null) {
			adapterAccessoryType = new ListAccessoryTypeAdapter(getActivity(), list2);
			adapterAccessoryType.setCallBack(new ListAccessoryTypeAdapter.CallBack() {
				
				@Override
				public void onclick_Accessory(int index, String id, View image) {
					image.startAnimation(scaleAnimation);
					Intent intent = new Intent(getActivity(), ProductAccessoryActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("idTypeAccessory", id);
					intent.putExtra("TypeAccessory", bundle);
					startActivityForResult(intent, TYPE_ACCESSORY);
				}

				@Override
				public void onClickRatingLeft(final int index, RatingBar ratingBar,
						float rating, boolean action) {
					if(action) {
//						ratingBar.setRating(rating);
						final float rate = (rating + Float.parseFloat(list2.get(index).getRatingLeft()));
						final int num = list2.get(index).getRatingNumberLeft() + 1;
						// update value of list
						list2.get(index).setRatingNumberLeft(num);
						list2.get(index).setRatingLeft(Float.toString(rate));
						
						Thread thread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								ParseApplication.UpdateRating("AccessoriesType", list2.get(index).getIdLeft(), rate, num);
							}
						});
						thread.start();
					}
					adapterAccessoryType.notifyDataSetChanged();
				}

				@Override
				public void onClickRatingRight(final int index, RatingBar ratingBar,
						float rating, boolean action) {
					if(action) {
//						ratingBar.setRating(rating);
						final float rate = (rating + Float.parseFloat(list2.get(index).getRatingRight()));
						final int num = list2.get(index).getRatingNumberRight() + 1;
						// update value of list
						list2.get(index).setRatingNumberRight(num);
						list2.get(index).setRatingRight(Float.toString(rate));
						
						Thread thread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								ParseApplication.UpdateRating("AccessoriesType", list2.get(index).getIdRight(), rate, num);
							}
						});
						thread.start();
					}
					adapterAccessoryType.notifyDataSetChanged();
				}
			});
			
			lvAccessoryType.setAdapter(adapterAccessoryType);
		} else {
			Toast.makeText(getActivity(), "result accessory type is null",
					Toast.LENGTH_LONG).show();
		}
		
		
		return rootView;
	}
	
	
//	@Override
//	protected void onCreate(Bundle arg0) {
//		// TODO Auto-generated method stub
//		super.onCreate(arg0);
//		setContentView(R.layout.activity_start_main);
//	}
	

}

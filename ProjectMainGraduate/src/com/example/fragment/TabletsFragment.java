package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.ListTabletTypeAdapter;
import com.example.object.TypeTablet;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.ProductTabletActivity;
import com.example.projectmaingraduate.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class TabletsFragment extends Fragment {
	
	private ListView lvTabletType;
	private ListTabletTypeAdapter adapterTabletType = null;
	public static final int TYPE_TABLET = 0;
	private ScaleAnimation scaleAnimation;
	private ArrayList<TypeTablet> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tablet, container, false);
		
		lvTabletType = (ListView) rootView.findViewById(R.id.fragment_tablet_lv_tablet_type);
		
		scaleAnimation =  (ScaleAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.animation_scale);
		
		list = ParseApplication.getTypeTablet();
		
		if (list.size() > 0) {
			adapterTabletType = new ListTabletTypeAdapter(getActivity(), list);
			adapterTabletType.setCallback(new ListTabletTypeAdapter.Callback() {

				@Override
				public void onClickTablet(int index, String id, View circleImage) {
					circleImage.startAnimation(scaleAnimation);
					Intent intent = new Intent(getActivity(), ProductTabletActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("idTypeTablet", id);
					intent.putExtra("TypeTablet", bundle);
					startActivityForResult(intent, TYPE_TABLET);
				}

				@Override
				public void onclickRating(final int index, RatingBar ratingBar, float rating, 
						boolean action) {
					if(action) {
//						ratingBar.setRating(rating);
						final float rate = (rating + Float.parseFloat(list.get(index).getRating()));
						final int num = list.get(index).getRatingNumber() + 1;
						// update value of list
						list.get(index).setRatingNumber(num);
						list.get(index).setRating(Float.toString(rate));
						
						Thread thread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								ParseApplication.UpdateRating("TabletType", list.get(index).getId(), rate, num);
							}
						});
						thread.start();
					}
					adapterTabletType.notifyDataSetChanged();
				}
			});
			lvTabletType.setAdapter(adapterTabletType);
		} else {
			Toast.makeText(getActivity(), "result tablet type is null", Toast.LENGTH_LONG).show();
		}
		return rootView;
	}
	
}

package com.example.fragment;

import java.util.ArrayList;

import com.example.object.Gift;
import com.example.projectmaingraduate.GiftContentActivity;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class GiftsFragment extends Fragment {

	private TextView tvHeadGift;
	private LinearLayout flowLayout;
	private ArrayList<Gift> listGift;
	public static final int DETAIL_GIFT = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_gift, container, false);

		flowLayout = (LinearLayout) rootView.findViewById(R.id.fragment_gift_flowlayout);
		tvHeadGift = (TextView) rootView.findViewById(R.id.fragment_gift_tv_head_gift);
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/VN3D.TTF");  //VHPRES.TTF
		tvHeadGift.setTypeface(typeface);
		
		// set display of gift
		listGift = ParseApplication.getGiftInventory();
		if (listGift != null) {
			for (int i = 0; i < listGift.size(); i++) {
				// initiation id promotions
				final int t = i;
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ParseApplication.initValueIdColumnPromotions(listGift.get(t).getId(), "Accessories", "AccessoriesId");
						ParseApplication.initValueIdColumnPromotions(listGift.get(t).getId(), "Phone", "PhonesId");
						ParseApplication.initValueIdColumnPromotions(listGift.get(t).getId(), "Tablet", "TabletsId");
					}
				});
				thread.start();
				// display information content item promotion 
				ImageView image = new ImageView(getActivity());
				image.setPadding(10, 20, 10, 20);
				image.setScaleType(ScaleType.FIT_XY);
				flowLayout.addView(image, LayoutParams.MATCH_PARENT, 450);
				diplayImage(listGift.get(i).getImage(), image);
				final int k = i;
				image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), GiftContentActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("IdGift", listGift.get(k).getId());
						intent.putExtra("Gift", bundle);
						startActivityForResult(intent, DETAIL_GIFT);
					}
				} );
			}
		}
		
		return rootView;
	}
	
	
	private void diplayImage(ParseFile image, final ImageView imageGif) {
		if (image != null) {
			image.getDataInBackground(new GetDataCallback() {

				@Override
				public void done(byte[] data, ParseException e) {
					if (e == null) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						if (bitmap != null) {
							imageGif.setImageBitmap(bitmap);
						}
					} else {
						Log.e("Notify", "Loading image later");
					}
				}
			});
		}
	}

}

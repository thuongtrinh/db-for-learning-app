package com.example.fragment;

import com.example.object.Gift;
import com.example.projectmaingraduate.GiftContentActivity;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class InforGiftProductFragment extends Fragment {
	
	private TextView tvLabel, tvContent;
	private ImageView imgContent, imgNext;
	private String IdGift;
	
	public InforGiftProductFragment(String id) {
		// TODO Auto-generated constructor stub
		this.IdGift = id;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gift_content, container, false);
		
		tvLabel = (TextView) view.findViewById(R.id.fragment_gift_content_tv_label);
		tvContent = (TextView) view.findViewById(R.id.fragment_gift_content_tv_content);
		imgContent = (ImageView) view.findViewById(R.id.fragment_gift_content_img_slogan);
		imgNext = (ImageView) view.findViewById(R.id.fragment_gift_content_img_next);
		
		Gift gift = ParseApplication.getInformationOfGift(IdGift);
		
		if(gift != null) {
			tvLabel.setText(gift.getLabel());
			tvContent.setText(gift.getContent());
			displayImage(gift.getImage(), imgContent);
		}
		
		imgNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GiftContentActivity.viewPager.setCurrentItem(1);
			}
		});
		
		return view;
	}
	
	
	private void displayImage(ParseFile image, final ImageView img) {
		if(image != null) {
			image.getDataInBackground(new GetDataCallback() {
				
				@Override
				public void done(byte[] data, ParseException e) {
					if(e == null) {
						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						if (bmp != null) {
							img.setImageBitmap(bmp);
						}
					} else {
						Log.e("paser after downloaded", " null");
					}
				}
			});
			
		}
	}
	
}

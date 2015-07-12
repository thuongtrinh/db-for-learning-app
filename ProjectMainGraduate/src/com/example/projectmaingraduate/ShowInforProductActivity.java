package com.example.projectmaingraduate;

import java.util.ArrayList;

import com.example.object.Delivery;
import com.example.object.DetailAccessory;
import com.example.object.DetailPhone;
import com.example.object.DetailTablet;
import com.example.object.PhoneNumber;
import com.example.object.TabletNumber;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowInforProductActivity extends Activity {

	private ImageButton imgbtnClose;
	private ImageView imgAvatar;
	private TextView tvPromotion, tvGuarantee, tvState, tvLabel, tvColor, tvPrice;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_show_infor_product);

		imgbtnClose = (ImageButton) findViewById(R.id.dialog_show_infor_product_btn_close);
		imgAvatar = (ImageView) findViewById(R.id.dialog_show_infor_product_img_representation);
		tvState = (TextView) findViewById(R.id.dialog_show_infor_product_tv_trangthai);
		tvGuarantee = (TextView) findViewById(R.id.dialog_show_infor_product_tv_baohanh);
		tvPromotion = (TextView) findViewById(R.id.dialog_show_infor_product_tv_khuyenmai);
		tvColor = (TextView) findViewById(R.id.dialog_show_infor_product_tv_color);
		tvLabel = (TextView) findViewById(R.id.dialog_show_infor_product_tv_label);
		tvPrice = (TextView) findViewById(R.id.dialog_show_infor_product_tv_price);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("pakage");
		String price = bundle.getString("Price");
		final String type = bundle.getString("Type");
		final String idDelivery = bundle.getString("idDelivery");
		
		tvPrice.setText(price + " VNĐ");
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				final Delivery delivery = ParseApplication.getInforDetaiProductDelivery(idDelivery, type);
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
//						if(!type.equalsIgnoreCase("tablet")) {
							tvColor.setText(delivery.getColor());
//						}
						switch (type) {
						case "phone":
							DetailPhone phone = delivery.getPhone();
							tvLabel.setText(styleLabel(phone.getName()));
							ArrayList<PhoneNumber> numberPhone = phone.getPhoneNumbers();
							if(numberPhone.size() > 0) {
								ParseFile file = numberPhone.get(0).getImage();
								displayImage(file, imgAvatar);
							}
							tvGuarantee.setText(phone.getGuarantee().toString());
							tvState.setText(phone.getState());
							if(phone.isSaleOff()) {
								int percent = phone.getPercentPromotion();
								tvPromotion.setText(String.valueOf(percent) + "%");
							} else {
								tvPromotion.setText("0%");
							}
							break;
						case "tablet":
							DetailTablet tablet = delivery.getTablet();
							tvLabel.setText(styleLabel(tablet.getName()));
							ArrayList<TabletNumber> numberTablet = tablet.getTabletNumbers();
							if(numberTablet.size() > 0) {
								ParseFile file = numberTablet.get(0).getImage();
								displayImage(file, imgAvatar);
							}
							tvGuarantee.setText(tablet.getGuarantee().toString());
							tvState.setText(tablet.getState());
							if(tablet.isSaleOff()) {
								int percent = tablet.getPercentPromotion();
								tvPromotion.setText(String.valueOf(percent) + "%");
							} else {
								tvPromotion.setText("0%");
							}
							break;
						case "accessory":
							DetailAccessory accessory = delivery.getAccessory();
							tvLabel.setText(styleLabel(accessory.getName()));
							ParseFile file = accessory.getAvatar();
							displayImage(file, imgAvatar);
							tvGuarantee.setText(accessory.getGuarantee().toString());
							tvState.setText(accessory.getState());
							if(accessory.isSaleOff()) {
								int percent = accessory.getPercentPromotion();
								tvPromotion.setText(String.valueOf(percent) + "%");
							} else {
								tvPromotion.setText("0%");
							}
							break;
						default:
							break;
						}
					}
				});
			}
		});
		thread.start();
		
		imgbtnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	public String styleLabel(String _label) {
		final String nameLabel;
		int n = _label.indexOf("(");
		int m = _label.indexOf(")");
		if (n != -1) {
			nameLabel = _label.substring(0, n);
		} else {
			nameLabel = _label;
		}
		return nameLabel;
	}
	
	private void displayImage(ParseFile thumbnail, final ImageView img) {

		if (thumbnail != null) {
			thumbnail.getDataInBackground(new GetDataCallback() {

				@Override
				public void done(byte[] data, ParseException e) {
					if (e == null) {
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









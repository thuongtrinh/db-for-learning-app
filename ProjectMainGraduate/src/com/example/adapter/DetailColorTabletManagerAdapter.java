package com.example.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.object.TabletNumber;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

public class DetailColorTabletManagerAdapter extends ArrayAdapter<TabletNumber>{

	
	private ArrayList<TabletNumber> arrayList;
	private Activity activity;
	
	private CallBack callBack;

	public interface CallBack {
		public void onclickCallColorBackDelete(int index); 
		public void onclickCallColorBackEdit(int index);
	}
	
	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	public DetailColorTabletManagerAdapter(Activity context, ArrayList<TabletNumber> objects) {
		super(context, 0, objects);
		this.activity = context;
		this.arrayList = objects;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.adapter_detail_color_product_manager, null);
		ImageView avatar = (ImageView) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_img_avatar);
		TextView tvLabelcolor = (TextView) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_tv_labelcolor_product);
		TextView tvNumber = (TextView) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_tv_number_product);
		TextView tvNumberSold = (TextView) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_tv_number_sold);
		ImageButton btnDelete = (ImageButton) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_btn_delete);
		ImageButton btnEdit = (ImageButton) convertView
				.findViewById(R.id.adapter_detail_color_product_manager_btn_edit);
		
		
		displayImage(arrayList.get(position).getImage(), avatar);
		tvLabelcolor.setText(arrayList.get(position).getColor());
		tvNumber.setText(String.valueOf(arrayList.get(position).getNumber()));
		tvNumberSold.setText(String.valueOf(arrayList.get(position).getNumberSold()));
		
		btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(callBack != null) {
					callBack.onclickCallColorBackDelete(position);
				}
			}
		});
		
		btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(callBack != null) {
					callBack.onclickCallColorBackEdit(position);
				}
			}
		});
		
		return convertView;
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
						Log.e("paser after downloade", " null");
					}
				}
			});
		}
	}

}

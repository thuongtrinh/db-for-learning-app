package com.example.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.object.TypeAccessory;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

public class TypeAccessoryManagerAdapter extends ArrayAdapter<TypeAccessory> {
	
	private Activity activity;
	private ArrayList<TypeAccessory> arrayList;

	public TypeAccessoryManagerAdapter(Activity context, ArrayList<TypeAccessory> list) {
		super(context, 0, list);
		this.activity = context;
		this.arrayList = list;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_all_product_manager, null);
		}
		
		ImageView avatar = (ImageView) convertView.findViewById(R.id.adapter_all_product_manager_img_avatar);
		TextView tvNameProduct = (TextView) convertView.findViewById(R.id.adapter_all_product_manager_tv_name_product);
		
		displayImage(arrayList.get(position).getImageLeft(), avatar);
		tvNameProduct.setText(arrayList.get(position).getNameLeft());
		
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
						Log.e("paser after downloaded", " null");
					}
				}
			});
		}
	}

}

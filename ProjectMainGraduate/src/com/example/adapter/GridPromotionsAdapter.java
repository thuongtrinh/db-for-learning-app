//package com.example.adapter;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.object.DetailAccessory;
//import com.example.object.DetailPhone;
//import com.example.projectmaingraduate.R;
//import com.parse.GetDataCallback;
//import com.parse.ParseException;
//import com.parse.ParseFile;
//
//public class GridPromotionsAdapter extends ArrayAdapter<DetailAccessory>{
//	private Activity activity;
//	private ArrayList<DetailAccessory> arrayList;
//	
//	public interface GridPromotionsOnClickAdapter {
//		public void onclickPromotions(int index, String id);
//	}
//	
//	private GridPromotionsOnClickAdapter gridClickAdapter;
//	
//	public void setCallBack(GridPromotionsOnClickAdapter gridClickAdapter) {
//		this.gridClickAdapter = gridClickAdapter;
//	}
//
//	public GridPromotionsAdapter(Activity context, ArrayList<DetailAccessory> objects) {
//		super(context, 0, objects);
//		this.activity = context;
//		this.arrayList = objects;
//	}
//	
//	
//	public class ViewHolder {
//		ImageView avatar;
//		TextView tvPrice, tvName, tvState;
//	}
//	
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ViewHolder holder = new ViewHolder();
//		View view = convertView;
//		if(view == null) {
//			LayoutInflater inflater = activity.getLayoutInflater();
//			view = inflater.inflate(R.layout.adapter_gridview_phone, null);
//			holder.avatar = (ImageView) view.findViewById(R.id.adapter_gridview_phone_image);
//			holder.tvState = (TextView) view.findViewById(R.id.adapter_gridview_phone_state);
//			holder.tvName = (TextView) view.findViewById(R.id.adapter_gridview_phone_nametablet);
//			holder.tvPrice = (TextView) view.findViewById(R.id.adapter_gridview_phone_price);
//			view.setTag(holder);
//		} else {
//			holder = (ViewHolder) view.getTag();
//		}
//		holder.tvName.setText(arrayList.get(position).getName());
//		holder.tvPrice.setText(arrayList.get(position).getPrice());
////		holder.tvState.setText(arrayList.get(position).getCondition());
//		displayImage(arrayList.get(position).getAvatar(), holder.avatar);
//		
//		holder.avatar.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (gridClickAdapter != null) {
//					gridClickAdapter.onclickPromotions(position, arrayList.get(position).getId());
//				}
//			}
//		});
//		
//		return view;
//	}
//	
//	
//	private void displayImage(ParseFile thumbnail, final ImageView img) {
//
//		if (thumbnail != null) {
//			thumbnail.getDataInBackground(new GetDataCallback() {
//
//				@Override
//				public void done(byte[] data, ParseException e) {
//					if (e == null) {
//						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//						if (bmp != null) {
//							img.setImageBitmap(bmp);
//						}
//					} else {
//						Log.e("paser after downloaded", " null");
//					}
//				}
//			});
//		}
//	}
//
//
//}

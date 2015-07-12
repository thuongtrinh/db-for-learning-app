package com.example.adapter;

import java.util.ArrayList;

import com.example.graphic.CircleImage;
import com.example.object.TypeTablet;
import com.example.projectmaingraduate.MuahangDialogActivity;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ListTabletTypeAdapter extends ArrayAdapter<TypeTablet> {

	public interface Callback {
		public void onClickTablet(int index, String Id, View v);
		public void onclickRating(int index, RatingBar ratingBar, float rating, boolean b);
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private Activity activity;
	private ArrayList<TypeTablet> arrayList;
	private Callback callback;
	private ViewHolder holder;
	private ArrayList<Integer> integers = new ArrayList<Integer>();

	public ListTabletTypeAdapter(Activity context, ArrayList<TypeTablet> list) {
		super(context, 0, list);
		this.activity = context;
		this.arrayList = list;
		for(int i=0;i<arrayList.size();i++){
			this.integers.add(i, 0);
		}
	}

	public class ViewHolder {
		TextView tvTablettype;
		CircleImage image;
		RatingBar ratingBar;
		TextView tvRatingNum;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.adapter_tablet_type, null);
			holder.tvTablettype = (TextView) view.findViewById(R.id.adapter_tablet_type_tv_tablet);
			holder.image = (CircleImage) view.findViewById(R.id.adapter_tablet_type_image);
			holder.ratingBar = (RatingBar) view.findViewById(R.id.adapter_tablet_type_ratingBar);
			holder.tvRatingNum = (TextView) view.findViewById(R.id.adapter_tablet_type_tv_ratingNumber);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvTablettype.setText(arrayList.get(position).getName());
		displayImage(arrayList.get(position).getImage(), holder.image);
		
		int numberRating = arrayList.get(position).getRatingNumber();
		String SumRating = arrayList.get(position).getRating();

		holder.tvRatingNum.setText(Integer.toString(numberRating) + " rating");
		final float startRating = Float.parseFloat(SumRating)/(1.0f * numberRating);
		holder.ratingBar.setRating(startRating);

		holder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (callback != null) {
					callback.onClickTablet(position, arrayList.get(position).getId(), v);
				}
			}
		});

		holder.ratingBar
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(final RatingBar ratingBar, final float rating,
							boolean fromUser) {
						if (callback != null && fromUser == true) {
							final int n = integers.get(position);
							final int num = arrayList.get(position).getRatingNumber() + 1;
							if(n < 2) {
								AlertDialog.Builder builder = new AlertDialog.Builder(activity);
								builder.setIcon(R.drawable.icon_question_red);
								builder.setMessage("Bạn chắc chắc chọn đánh giá này?");
								builder.setTitle("Question?");
								builder.setPositiveButton("Không phải",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog, int which) {
												ratingBar.setRating(startRating);
											}
										});
								builder.setNegativeButton("Đúng vậy", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										holder.tvRatingNum.setText(Integer.toString(num) + " rating");
										callback.onclickRating(position, ratingBar, rating, true);
										int solan = n + 1;
										integers.set(position, solan);
									}
								});
								builder.show();
							} else {
								// update listView again 
								callback.onclickRating(position, ratingBar, rating, false);
								ratingBar.setIsIndicator(true);
								Toast.makeText(getContext(),"Bạn chỉ được đánh giá 2 lần",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
		
		return view;
	}

	private void displayImage(ParseFile thumbnail, final CircleImage img) {

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

package com.example.adapter;

import java.util.ArrayList;

import com.example.object.TypeAccessory;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class ListAccessoryTypeAdapter extends ArrayAdapter<TypeAccessory> {
	
	public interface CallBack {
		public void onclick_Accessory(int index, String id, View v);
		public void onClickRatingLeft(int index, RatingBar ratingBar, float rating, boolean b);
		public void onClickRatingRight(int index, RatingBar ratingBar, float rating, boolean b);
	}
	
	private Activity activity;
	private ArrayList<TypeAccessory> arrayList;
	private CallBack callBack;
	private ViewHolder holder;
	private ArrayList<Integer> integersLeft = new ArrayList<Integer>();
	private ArrayList<Integer> integersRight = new ArrayList<Integer>();
	
	
	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}
	
	public ListAccessoryTypeAdapter(Activity context, ArrayList<TypeAccessory> list2) {
		super(context, 0 , list2);
		this.activity = context;
		this.arrayList =  list2;
		for (int i = 0; i < arrayList.size(); i++) {
			this.integersLeft.add(i, 0);
			this.integersRight.add(i, 0);
		}
	}

	private class ViewHolder {
		TextView tv_stt_left, tv_stt_right, tv_name_left, tv_name_right,
				tv_numberRatingLeft, tv_numberRatingRight;
		ImageView img_TypeAccessory_Left, img_TypeAccessory_Right;
		RatingBar ratingBarLeft, ratingBarRight;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		View view = convertView;
		final int num1;
		final int num2;
		int i = position +1;
		if(view == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.adapter_accessory_type, null);
			holder.tv_stt_left = (TextView) view.findViewById(R.id.adapter_accessory_type_tv_stt_left);
			holder.tv_stt_right = (TextView) view.findViewById(R.id.adapter_accessory_type_tv_stt_right);
			holder.tv_name_left = (TextView) view.findViewById(R.id.adapter_accessory_type_tv_name_left);
			holder.tv_name_right = (TextView) view.findViewById(R.id.adapter_accessory_type_tv_name_right);
			holder.img_TypeAccessory_Left = (ImageView) view.findViewById(R.id.adapter_accessory_type_img_left); 
			holder.img_TypeAccessory_Right = (ImageView) view.findViewById(R.id.adapter_accessory_type_img_right);
			holder.ratingBarLeft = (RatingBar) view.findViewById(R.id.adapter_accessory_type_ratingbar_left);
			holder.ratingBarRight = (RatingBar) view.findViewById(R.id.adapter_accessory_type_ratingbar_right);
			holder.tv_numberRatingLeft = (TextView) view.findViewById(R.id.adapter_accessory_type_numberRating_left);
			holder.tv_numberRatingRight = (TextView) view.findViewById(R.id.adapter_accessory_type_numberRating_right);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		num1 = position + i;
		num2 = position + i + 1;
		holder.tv_stt_left.setText(Integer.toString(num1) + ".");
		holder.tv_name_left.setText(arrayList.get(position).getNameLeft());
		displayImage(arrayList.get(position).getImageLeft(), holder.img_TypeAccessory_Left);
		if(arrayList.get(position).getNameRight() != null){
			holder.tv_stt_right.setText(Integer.toString(num2) + ".");
			holder.tv_name_right.setText(arrayList.get(position).getNameRight());
			displayImage(arrayList.get(position).getImageRight(), holder.img_TypeAccessory_Right);
		} else {
			LinearLayout layoutRight = (LinearLayout) view.findViewById(R.id.id_layoutRight);
			layoutRight.setVisibility(LinearLayout.INVISIBLE);
			layoutRight.setEnabled(false);
		}
		
		int numberRatingLeft = arrayList.get(position).getRatingNumberLeft();
		String SumRatingLeft = arrayList.get(position).getRatingLeft();

		holder.tv_numberRatingLeft.setText(Integer.toString(numberRatingLeft) + " rating");
		final float startRatingLeft = Float.parseFloat(SumRatingLeft)/(1.0f * numberRatingLeft);
		holder.ratingBarLeft.setRating(startRatingLeft);
		
		int numberRatingRight = arrayList.get(position).getRatingNumberRight();
		String SumRatingRight = arrayList.get(position).getRatingRight();

		holder.tv_numberRatingRight.setText(Integer.toString(numberRatingRight) + " rating");
		final float startRatingRight = Float.parseFloat(SumRatingRight)/(1.0f * numberRatingRight);
		holder.ratingBarRight.setRating(startRatingRight);
		
		// handler clickListener give image left and image right
		holder.img_TypeAccessory_Left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(callBack != null){
					callBack.onclick_Accessory(num1, arrayList.get(position).getIdLeft(), v);
					Log.d("objectID left", arrayList.get(position).getIdLeft());
				}
			}
		});
		
		holder.img_TypeAccessory_Right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (callBack != null) {
					callBack.onclick_Accessory(num2, arrayList.get(position).getIdRight(), v);
					Log.d("objectID right", arrayList.get(position).getIdRight());
				}
			}
		});
		
		// handler handler click give rating
		
		holder.ratingBarLeft.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(final RatingBar ratingBar, final float rating,
					boolean fromUser) {
				if(callBack != null && fromUser==true) {
					final int n = integersLeft.get(position);
					final int num = arrayList.get(position).getRatingNumberLeft() + 1;
					if(n < 2) {
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setMessage("Bạn chắc chắc chọn đánh giá này?");
						builder.setTitle("Question?");
						builder.setPositiveButton("Không phải",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										ratingBar.setRating(startRatingLeft);
									}
								});
						builder.setNegativeButton("Đúng vậy", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								holder.tv_numberRatingLeft.setText(Integer.toString(num) + " rating");
								callBack.onClickRatingLeft(position, ratingBar, rating, true);
								int solan = n + 1;
								integersLeft.set(position, solan);
							}
						});
						builder.show();
					} else {
						// update listView again 
						callBack.onClickRatingLeft(position, ratingBar, rating, false);
						ratingBar.setIsIndicator(true);
						Toast.makeText(getContext(),"You are only rated 2 times",
								Toast.LENGTH_SHORT).show();
					}
					
				}
				
			}
		});
		
		holder.ratingBarRight.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(final RatingBar ratingBar, final float rating,
					boolean fromUser) {
				if(callBack != null && fromUser == true) {
					final int n = integersRight.get(position);
					final int num = arrayList.get(position).getRatingNumberRight() + 1;
					if(n < 2) {
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						builder.setIcon(R.drawable.icon_question_red);
						builder.setMessage("Bạn chắc chắc chọn đánh giá này?");
						builder.setTitle("Question?");
						builder.setPositiveButton("Không phải",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										ratingBar.setRating(startRatingRight);
									}
								});
						builder.setNegativeButton("Đúng vậy", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								holder.tv_numberRatingRight.setText(Integer.toString(num) + " rating");
								callBack.onClickRatingRight(position, ratingBar, rating, true);
								int solan = n + 1;
								integersRight.set(position, solan);
							}
						});
						builder.show();
					} else {
						// update listView again 
						callBack.onClickRatingRight(position, ratingBar, rating, false);
						ratingBar.setIsIndicator(true);
						Toast.makeText(getContext(),"Bạn chỉ được đánh giá 2 lần",
								Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		return view;
	}
	
	private void displayImage(ParseFile thumbnail, final ImageView img) {

		if (thumbnail != null) {
			thumbnail.getDataInBackground(new GetDataCallback() {

				@Override
				public void done(byte[] data, ParseException e) {
					if (e == null) {
						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
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





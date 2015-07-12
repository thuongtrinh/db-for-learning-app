package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.GridAccessoryAdapter;
import com.example.object.DetailAccessory;
import com.example.object.TypePhone;
import com.example.projectmaingraduate.DetailProductAccessoryActivity;
import com.example.projectmaingraduate.ParseApplication;
import com.example.projectmaingraduate.ProductPhoneActivity;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class PhonesFragment extends Fragment {

	private ImageView imageLeft, imageRightTop, imageRightButtom;
	private LinearLayout linearLayout;
	public static final int TYPE_PHONE = 0;
//	private ArrayList<TypePhone> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_phone, container, false);

		imageLeft = (ImageView) view.findViewById(R.id.fragment_phone_imageViewLeft);
		imageRightTop = (ImageView) view.findViewById(R.id.fragment_phone_imageViewLRightTop);
		imageRightButtom = (ImageView) view.findViewById(R.id.fragment_phone_imageViewLRightButtom);
		linearLayout = (LinearLayout) view.findViewById(R.id.fragment_phone_flowlayout);

//		final ArrayList<TypePhone> list = ParseApplication.getTypePhone();
//		if (list != null) {
//			int j = 0, d = list.size();
//			while (j < d) {
//				switch (j) {
//				case 0:
//					diplayImage(list.get(j).getAvatar(), imageLeft);
//					imageLeft.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("idTypePhone", list.get(0).getId());
//							intent.putExtra("TypePhone", bundle);
//							startActivityForResult(intent, TYPE_PHONE);
//						}
//					});
//					break;
//				case 1:
//					diplayImage(list.get(j).getAvatar(), imageRightTop);
//					imageRightTop.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("idTypePhone", list.get(1).getId());
//							intent.putExtra("TypePhone", bundle);
//							startActivityForResult(intent, TYPE_PHONE);
//						}
//					});
//					break;
//				case 2:
//					diplayImage(list.get(j).getAvatar(), imageRightButtom);
//					imageRightButtom.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("idTypePhone", list.get(2).getId());
//							intent.putExtra("TypePhone", bundle);
//							startActivityForResult(intent, TYPE_PHONE);
//						}
//					});
//					break;
//				default:
//					break;
//				}
//				j++;
//				if (j == 3)
//					break;
//			}
//			if (d > 3) {
//				int length, size = (list.size() - 3);
//				if (size % 2 == 0) {
//					length = size / 2;
//				} else {
//					length = size / 2 + 1;
//				}
//				for (int i = 0; i < length; i++) {
//					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//							LayoutParams.MATCH_PARENT, 400);
//					LinearLayout layout = new LinearLayout(getActivity());
//					layout.setLayoutParams(layoutParams);
//					layout.setOrientation(LinearLayout.HORIZONTAL);
//					layout.setPadding(0, 6, 0, 6);
//					layout.setWeightSum(2);
//
//					LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
//							0, LayoutParams.MATCH_PARENT, 1);
//					ImageView image1 = new ImageView(getActivity());
//					image1.setLayoutParams(layoutParams1);
//					image1.setPadding(0, 0, 6, 0);
//					image1.setScaleType(ImageView.ScaleType.FIT_XY);
//
//					LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
//							0, LayoutParams.MATCH_PARENT, 1);
//					ImageView image2 = new ImageView(getActivity());
//					image2.setLayoutParams(layoutParams2);
//					image2.setPadding(6, 0, 0, 0);
//					image2.setScaleType(ImageView.ScaleType.FIT_XY);
//
//					layout.addView(image1);
//					layout.addView(image2);
//
//					linearLayout.addView(layout);
//					final int n1 = 2 * i + 3;
//					final int n2 = 2 * i + 4;
//					diplayImage(list.get(n1).getAvatar(), image1);
//					if (n2 < list.size()) {
//						diplayImage(list.get(n2).getAvatar(), image2);
//					} else {
//						image2.setVisibility(ImageView.INVISIBLE);
//					}
//
//					image1.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("idTypePhone", list.get(n1)
//									.getId());
//							intent.putExtra("TypePhone", bundle);
//							startActivityForResult(intent, TYPE_PHONE);
//						}
//					});
//
//					image2.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("idTypePhone", list.get(n2)
//									.getId());
//							intent.putExtra("TypePhone", bundle);
//							startActivityForResult(intent, TYPE_PHONE);
//						}
//					});
//				}
//			}
//
//		}
		new StartLoadListData().execute();
		
		return view;
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
	
	
	
	private class StartLoadListData extends AsyncTask<Void, Void, ArrayList<TypePhone>> 
							implements DialogInterface.OnClickListener {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "", "Loading, please waiting...");
		}

		@Override
		protected ArrayList<TypePhone> doInBackground(Void... params) {
			try {
				return ParseApplication.getTypePhone();
			} catch (Exception e) {
				Log.d("PhonesFragment", "Error loading getTypePhone" );
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(final ArrayList<TypePhone> result) {
			dialog.dismiss();
			if (result != null) {
				int j = 0, d = result.size();
				while (j < d) {
					switch (j) {
					case 0:
						diplayImage(result.get(j).getAvatar(), imageLeft);
						imageLeft.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("idTypePhone", result.get(0).getId());
								intent.putExtra("TypePhone", bundle);
								startActivityForResult(intent, TYPE_PHONE);
							}
						});
						break;
					case 1:
						diplayImage(result.get(j).getAvatar(), imageRightTop);
						imageRightTop.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("idTypePhone", result.get(1).getId());
								intent.putExtra("TypePhone", bundle);
								startActivityForResult(intent, TYPE_PHONE);
							}
						});
						break;
					case 2:
						diplayImage(result.get(j).getAvatar(), imageRightButtom);
						imageRightButtom.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("idTypePhone", result.get(2).getId());
								intent.putExtra("TypePhone", bundle);
								startActivityForResult(intent, TYPE_PHONE);
							}
						});
						break;
					default:
						break;
					}
					j++;
					if (j == 3)
						break;
				}
				if (d > 3) {
					int length, size = (result.size() - 3);
					if (size % 2 == 0) {
						length = size / 2;
					} else {
						length = size / 2 + 1;
					}
					for (int i = 0; i < length; i++) {
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, 400);
						LinearLayout layout = new LinearLayout(getActivity());
						layout.setLayoutParams(layoutParams);
						layout.setOrientation(LinearLayout.HORIZONTAL);
						layout.setPadding(0, 6, 0, 6);
						layout.setWeightSum(2);

						LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
								0, LayoutParams.MATCH_PARENT, 1);
						ImageView image1 = new ImageView(getActivity());
						image1.setLayoutParams(layoutParams1);
						image1.setPadding(0, 0, 6, 0);
						image1.setScaleType(ImageView.ScaleType.FIT_XY);

						LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
								0, LayoutParams.MATCH_PARENT, 1);
						ImageView image2 = new ImageView(getActivity());
						image2.setLayoutParams(layoutParams2);
						image2.setPadding(6, 0, 0, 0);
						image2.setScaleType(ImageView.ScaleType.FIT_XY);

						layout.addView(image1);
						layout.addView(image2);

						linearLayout.addView(layout);
						final int n1 = 2 * i + 3;
						final int n2 = 2 * i + 4;
						diplayImage(result.get(n1).getAvatar(), image1);
						if (n2 < result.size()) {
							diplayImage(result.get(n2).getAvatar(), image2);
						} else {
							image2.setVisibility(ImageView.INVISIBLE);
						}

						image1.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("idTypePhone", result.get(n1)
										.getId());
								intent.putExtra("TypePhone", bundle);
								startActivityForResult(intent, TYPE_PHONE);
							}
						});

						image2.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductPhoneActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("idTypePhone", result.get(n2)
										.getId());
								intent.putExtra("TypePhone", bundle);
								startActivityForResult(intent, TYPE_PHONE);
							}
						});
					}
				}
			}
		}
		
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
		
	}
	
	

}

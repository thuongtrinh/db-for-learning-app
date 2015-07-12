package com.example.projectmaingraduate;

import com.example.object.DetailAccessory;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class DetailProductAccessoryActivity extends Activity {

	private TextView tvLabel, tvPrice, tvCondition, tvGuarantee, tvState, tvInforDetail;
	private ImageView imgRepresent;
	private Button btnMuahang;
	private LinearLayout linearLayout;
	private boolean login = false, sale;
	private String idDetail;
	private String account, password, cost;
	private int number;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_product_accessory);
		initiation();
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("DetailAccessory");
		idDetail = bundle.getString("IdOfDetailAccessory");
		final String priceOff = bundle.getString("PriceOff");
		
		Thread thread = new Thread(new Runnable() {
			
			DetailAccessory accessory = ParseApplication.getInforDetailAccessory(idDetail);
			
			@Override
			public void run() {
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						if(accessory != null){
							final String nameLabel;
							number = accessory.getSumAccessory();
							if(number > 0)	sale = true;
							else 	sale = false;
							nameLabel = accessory.getName();
							tvLabel.setText(accessory.getName());
							tvGuarantee.setText(accessory.getGuarantee());
							if(accessory.isSaleOff()) {
								cost = priceOff;
								tvPrice.setText(priceOff + " vnđ");
							} else {
								cost = accessory.getPrice();
								tvPrice.setText(accessory.getPrice() + " vnđ");
							}
							tvState.setText(accessory.getState());
							tvCondition.setText(accessory.getConditon());
							tvInforDetail.setText(accessory.getInforDetail());
							ParseFile image = accessory.getImageRepresent();
							displayImage(image, imgRepresent);
							
							for(int i = 0; i < 5 ; i++) {
								ImageView imageView = new ImageView(DetailProductAccessoryActivity.this);
								imageView.setPadding(10, 20, 10, 20);
								imageView.setScaleType(ScaleType.FIT_XY);
								linearLayout.addView(imageView, LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
								switch (i) {
									case 0:
										if(accessory.getImage1() != null)
										displayImage(accessory.getImage1(), imageView);
										break;
									case 1:
										if(accessory.getImage2() != null)
										displayImage(accessory.getImage2(), imageView);
										break;
									case 2:
										if(accessory.getImage3() != null)
										displayImage(accessory.getImage3(), imageView);
										break;
									case 3:
										if(accessory.getImage4() != null)
										displayImage(accessory.getImage4(), imageView);
										break;
									case 4:
										if(accessory.getImage5() != null)
										displayImage(accessory.getImage5(), imageView);
										break;
									default:
										break;
								}
							}
							
							btnMuahang.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
								  if (login) {
										if(sale) {
											Intent intent = new Intent(DetailProductAccessoryActivity.this,
															MuahangDialogActivity.class);
											Bundle bundle = new Bundle();
											bundle.putString("TypeProduct", "ACCESSORY");
											bundle.putString("Account", account);
											bundle.putString("Lable", nameLabel);
											bundle.putString("Price", cost);
											bundle.putString("Color", "");
											bundle.putString("idProduct", idDetail);
											intent.putExtra("InforProduct", bundle);
											startActivityForResult(intent, 0);
										} else {
											AlertDialog.Builder builder = new AlertDialog.Builder(
															DetailProductAccessoryActivity.this);
											builder.setIcon(R.drawable.icon_error);
											builder.setMessage("Sản phẩm bạn chọn đã hết hàng");
											builder.setTitle("Thông báo");
													builder.setPositiveButton("OK",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog, int which) {
																}
															});
											builder.show();
										}
									} else {
										startActivity(new Intent(
												DetailProductAccessoryActivity.this, DialogSigninUser.class));
										Toast.makeText(getApplicationContext(),
												"Đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
									}
								}
							});
						}
					}
				});
			}
		});
		
		thread.start();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences preferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
		account = preferences.getString("user", "");
		password = preferences.getString("pass", "");
		if (!account.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
			login = true;
		} else {
			login = false;
		}
	}
	

	private void displayImage(ParseFile file, final ImageView image) {
		if(file != null) {
			file.getDataInBackground(new GetDataCallback() {
				
				@Override
				public void done(byte[] data, ParseException e) {
					// TODO Auto-generated method stub
					if(e == null) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
						if(bitmap != null) {
							image.setImageBitmap(bitmap);
						}
					} else {
						Log.e("paser after downloaded", " null");
					}
				}
			});
		}
	}
	
	
	private void initiation() {
		tvLabel = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_label);
		tvPrice = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_price);
		tvCondition = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_conditon);
		tvGuarantee = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_guarantee);
		tvState = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_state);
		imgRepresent = (ImageView) findViewById(R.id.activity_detail_product_accessory_img_represent);
		linearLayout = (LinearLayout) findViewById(R.id.activity_detail_product_accessory_linearLayout_listImage);
		tvInforDetail = (TextView) findViewById(R.id.activity_detail_product_accessory_tv_inforDetail);
		btnMuahang = (Button) findViewById(R.id.activity_detail_product_accessory_btn_muahang);
	}

}











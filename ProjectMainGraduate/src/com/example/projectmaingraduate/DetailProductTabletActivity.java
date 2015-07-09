package com.example.projectmaingraduate;

import com.example.object.DetailTablet;
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

public class DetailProductTabletActivity extends Activity {

	private TextView tvLabel, tvTabletColor, tvPrice, tvCondition, tvGuarantee,
			tvState;
	private TextView tvInfor3G, tvInfor4G, tvSim, tvSize, tvWeight,
			tvMonitorType, tvMonitorSize, tvMemory, tvMemoryCardSlot, tvWLAN,
			tvBluetooth, tvUSB, tvNFC, tvGPS, tvOperatorSystem, tvChipset,
			tvCPU, tvGPU, tvSensors, tvMainCamera, tvSubCamera, tvVideo, tvPin,
			tvConversationTime, tvWaitTime, tvPlayMusicTime;
	private ImageView imgTablet;
	private LinearLayout layoutRadiobtn, layout_linear;
	private DetailTablet tablet;
	private Button btnMuahang;
	private boolean login = false, selectColor = false, sale;
	private String idDetail;
	private String account, password, cost, color_selected = "no";
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_product_phone_and_tablet);
		initiation();
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("DetailTablet");
		idDetail = bundle.getString("IdOfDetailTablet");
		final String priceOff = bundle.getString("PriceOff");
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				tablet = ParseApplication.getInforDetailTablet(idDetail);
				
				handler.post(new Runnable() {
					public void run() {
						if (tablet != null) {
							// set title, state and infor guarantee...
							String color_tablet = "";
							final String nameLabel;
							String label = tablet.getName();
							int n = label.indexOf("(");
							int m = label.indexOf(")");

							if (n != -1) {
								color_tablet = label.substring(n + 1, m);
								nameLabel = label.substring(0, n);
							} else {
								nameLabel = label;
							}
							tvLabel.setText(nameLabel);
							tvTabletColor.setText(color_tablet);
							if(tablet.isSaleOff()) {
								cost = priceOff;
								tvPrice.setText(priceOff + "vnđ");
							} else {
								cost = tablet.getPrice();
								tvPrice.setText(tablet.getPrice() + " vnđ");
							}
							tvCondition.setText(tablet.getCondition());
							
							// set image start represent
							if (tablet.getTabletNumbers().get(0).getImage() != null) {
								displayImage(tablet.getTabletNumbers().get(0).getImage(), imgTablet);
							}
							
							// handler set select color of tablet and listener event
							int i = 0, type = tablet.getTabletNumbers().size();
							if (type > 1) {
								selectColor = true;
								layout_linear.setVisibility(LinearLayout.VISIBLE);
								while (i < type) {
									final int k = i;
									String color = tablet.getTabletNumbers().get(i).getColor();
									LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(50, 50);
									Button buttonCircle = new Button(DetailProductTabletActivity.this);
									buttonCircle.setLayoutParams(layoutParams1);
									layoutRadiobtn.addView(buttonCircle);
									if (i > 0)  layoutParams1.setMargins(15, 0, 0, 0);
									switch (color) {
									case "black":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_black);
										break;
									case "white":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_white);
										break;
									case "gray":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_gray);
										break;
									case "silver":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_silver);
										break;
									case "gold":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_gold);
										break;
									case "bronze":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_bronze);
										break;
									case "blue":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_blue);
										break;
									case "yellow":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_yellow);
										break;
									case "red":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_red);
										break;
									case "green":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_green);
										break;
									case "orange":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_orange);
										break;
									case "purple":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_purple);
										break;
									case "pink":
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_pink);
										break;
									default:
										buttonCircle.setBackgroundResource(R.drawable.selector_circle_button_white);
										break;
									}

									buttonCircle.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											ParseFile file = tablet.getTabletNumbers().get(k).getImage();
											displayImage(file, imgTablet);
											color_selected = tablet.getTabletNumbers().get(k).getColor();
											if (tablet.getTabletNumbers().get(k).getNumber() > 0) {
												tvCondition.setText("Còn hàng");
												sale = true;
											} else {
												tvCondition.setText("Hết hàng");
												sale = false;
											}
										}
									});
									i++;
								}
							}
							
							btnMuahang.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									if (login) {
										if(selectColor) {
											if(color_selected.equalsIgnoreCase("no")) {
												AlertDialog.Builder builder = new AlertDialog.Builder(
														DetailProductTabletActivity.this);
												builder.setIcon(R.drawable.icon_error);
												builder.setMessage("Bạn chưa chọn màu sản phẩm!");
												builder.setTitle("Thông báo");
												builder.setPositiveButton(
														"OK",
														new DialogInterface.OnClickListener() {

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
															}
														});
												builder.show();
											} else {
												if(sale) {
														Intent intent = new Intent(DetailProductTabletActivity.this, 
																MuahangDialogActivity.class);
														Bundle bundle = new Bundle();
														bundle.putString("TypeProduct", "TABLET");
														bundle.putString("Account", account);
														bundle.putString("Lable", nameLabel);
														bundle.putString("Price", cost);
														bundle.putString("Color", color_selected);
														bundle.putString("idProduct", idDetail);
														intent.putExtra("InforProduct", bundle);
														startActivityForResult(intent, 0);
													} else {
														AlertDialog.Builder builder = new AlertDialog.Builder(
																DetailProductTabletActivity.this);
														builder.setIcon(R.drawable.icon_error);
														builder.setMessage("Sản phẩm bạn chọn đã hết hàng");
														builder.setTitle("Thông báo");
														builder.setPositiveButton("OK",
																new DialogInterface.OnClickListener() {

																	@Override
																	public void onClick(DialogInterface dialog,
																			int which) {
																	}
																});
														builder.show();
													}
											}
										} else {
											int num = tablet.getTabletNumbers().get(0).getNumber();
											if(num > 0) {
												color_selected = tablet.getTabletNumbers().get(0).getColor();
												Intent intent = new Intent(DetailProductTabletActivity.this,
																MuahangDialogActivity.class);
												Bundle bundle = new Bundle();
												bundle.putString("TypeProduct", "TABLET");
												bundle.putString("Account", account);
												bundle.putString("Lable", nameLabel);
												bundle.putString("Price", cost);
												bundle.putString("Color", color_selected);
												bundle.putString("idProduct", idDetail);
												intent.putExtra("InforProduct", bundle);
												startActivityForResult(intent, 0);
											} else {
												AlertDialog.Builder builder = new AlertDialog.Builder(
																DetailProductTabletActivity.this);
												builder.setIcon(R.drawable.icon_error);
												builder.setMessage("Sản phẩm bạn chọn đã hết hàng");
												builder.setTitle("Thông báo");
												builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
													
													@Override
													public void onClick(DialogInterface dialog, int which) {
													}
												});
												builder.show();
											}
											
										}
									} else {
										startActivity(new Intent(
												DetailProductTabletActivity.this, DialogSigninUser.class));
										Toast.makeText(getApplicationContext(),
												"Đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
									}
								}
							});
							
							
							// setText detail information, index of Tablet
							tvGuarantee.setText(tablet.getGuarantee());
							tvState.setText(tablet.getState());
							tvInfor3G.setText(tablet.getInfor3G());
							tvInfor4G.setText(tablet.getInfor4G());
							tvSim.setText(tablet.getSim());
							tvMonitorSize.setText(tablet.getMonitorSize());
							tvSize.setText(tablet.getSize());
							tvWeight.setText(tablet.getWeight());
							tvMonitorType.setText(tablet.getTypeMonitor());
							tvMemory.setText(tablet.getMemory());
							tvMemoryCardSlot.setText(tablet.getMemoryCardSlot());
							tvWLAN.setText(tablet.getWlan());
							tvBluetooth.setText(tablet.getBluetooth());
							tvUSB.setText(tablet.getUsb());
							tvNFC.setText(tablet.getNfc());
							tvGPS.setText(tablet.getGps());
							tvOperatorSystem.setText(tablet.getOperatorSystem());
							tvChipset.setText(tablet.getChipset());
							tvCPU.setText(tablet.getCpu());
							tvGPU.setText(tablet.getGpu());
							tvSensors.setText(tablet.getSensors());
							tvMainCamera.setText(tablet.getMainCamera());
							tvSubCamera.setText(tablet.getSubCamera());
							tvVideo.setText(tablet.getVideo());
							tvPin.setText(tablet.getPin());
							tvConversationTime.setText(tablet.getConversationTime());
							tvWaitTime.setText(tablet.getWaitTime());
							tvPlayMusicTime.setText(tablet.getPlayMusicTime());
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

	public void initiation() {
		layoutRadiobtn = (LinearLayout) findViewById(R.id.activity_detail_product_phone_and_tablet_linearLayout_radio);
		layout_linear = (LinearLayout) findViewById(R.id.activity_detail_product_phone_and_tablet_layout_linear);
		imgTablet = (ImageView) findViewById(R.id.activity_detail_product_phone_and_tablet_img_tablet);
		// //
		btnMuahang = (Button) findViewById(R.id.activity_detail_product_phone_and_tablet_btn_muahang);
		tvLabel = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_label);
		tvPrice = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_price);
		tvTabletColor = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_tablet_color);
		tvCondition = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_conditon);
		tvGuarantee = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_guarantee);
		tvState = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_state);
		imgTablet = (ImageView) findViewById(R.id.activity_detail_product_phone_and_tablet_img_tablet);
		tvInfor3G = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_3g);
		tvInfor4G = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_4g);
		tvSim = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_sim);
		tvSize = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_size);
		tvWeight = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_weight);
		tvMonitorType = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_monitor);
		tvMonitorSize = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_monitorSize);
		tvMemory = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_memory);
		tvMemoryCardSlot = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_memoryCardSlot);
		tvWLAN = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_wlan);
		tvBluetooth = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_bluetooth);
		tvUSB = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_usb);
		tvNFC = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_nfc);
		tvGPS = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_gps);
		tvOperatorSystem = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_operatorSystem);
		tvChipset = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_chipset);
		tvCPU = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_cpu);
		tvGPU = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_gpu);
		tvSensors = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_sensors);
		tvMainCamera = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_mainCamera);
		tvSubCamera = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_subCamera);
		tvVideo = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_video);
		tvPin = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_pin);
		tvConversationTime = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_conversationTime);
		tvWaitTime = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_waitTime);
		tvPlayMusicTime = (TextView) findViewById(R.id.activity_detail_product_phone_and_tablet_tv_playMusicTime);
	}
	
}

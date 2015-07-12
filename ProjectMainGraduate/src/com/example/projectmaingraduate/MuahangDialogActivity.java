package com.example.projectmaingraduate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import com.example.object.AccountUser;
import com.example.object.DetailAccessory;
import com.example.object.PhoneNumber;
import com.example.object.TabletNumber;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.vtc.pay.common.VTCPayInfoPayment;
import com.vtc.pay.lib.IVTC;
import com.vtc.pay.lib.VTCPay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MuahangDialogActivity extends Activity {
	
	public static final int PHONE = 0;
	public static final int TABLET = 1;
	public static final int ACCESSORY = 2;
	private EditText edtSoluong, edtHoten, edtPhone, edtEmail, edtAdressDelivery, edtAddress;
	private Button btnDathang;
	private ImageButton imgClose;
	private ImageView imageRepresenet;
	private TextView tvNameProduct, tvPrice, tvColor, tv_MessageError;
	private String fullname, phone, address, email, description;
	private Handler handler = new Handler();
	private AccountUser user;
	private VTCPay vtcid;
	private VTCPayCallback callback;
	public Thread threadUpdate;
	private int NumberProduct, numberSelected;
	String colorSelected, typeSelected, objectIdAccount, objectIdProduct, addressDelivery;
	String LabelIncome, PriceIncome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_mua_hang);
		
		imageRepresenet = (ImageView) findViewById(R.id.dialog_muahang_img_representation);
		tvNameProduct = (TextView) findViewById(R.id.dialog_muahang_tv_label);
		tvPrice = (TextView) findViewById(R.id.dialog_muahang_tv_price);
		tvColor = (TextView) findViewById(R.id.dialog_muahang_tv_color);
		tv_MessageError = (TextView) findViewById(R.id.dialog_mua_hang_tv_MessageError);
		edtSoluong = (EditText) findViewById(R.id.dialog_mua_hang_edt_soluong);
		edtHoten = (EditText) findViewById(R.id.dialog_mua_hang_edt_hoten);
		edtPhone = (EditText) findViewById(R.id.dialog_mua_hang_edt_phoneNumber);
		edtEmail = (EditText) findViewById(R.id.dialog_mua_hang_edt_email);
		edtAdressDelivery = (EditText) findViewById(R.id.dialog_mua_hang_edt_address_delivery);
		edtAddress = (EditText) findViewById(R.id.dialog_mua_hang_edt_address);
		btnDathang = (Button) findViewById(R.id.dialog_mua_hang_btn_datmuahang);
		imgClose = (ImageButton) findViewById(R.id.dialog_muahang_imgbtn_close);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("InforProduct");
		final String idProduct = bundle.getString("idProduct");
		final String color = bundle.getString("Color");
		final String price = bundle.getString("Price");
		final String label = bundle.getString("Lable");
		final String account = bundle.getString("Account");
		final String type = bundle.getString("TypeProduct");
		
		colorSelected = color;
		typeSelected = type;
		objectIdProduct = idProduct;
		PriceIncome = price;
		LabelIncome = label;
		
		tvNameProduct.setText(label);
		tvPrice.setText(price + " VNĐ");
		if(color.equalsIgnoreCase("")) {
			tvColor.setVisibility(TextView.GONE);
		} else {
			tvColor.setText(color);
		}
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				user = ParseApplication.getAllInforOfAUser(account);
				objectIdAccount = user.getId();
				
				handler.post(new Runnable() {
					public void run() {
						if(user != null) {
							fullname = user.getFullName().toString();
							phone = user.getPhone().toString();
							email = user.getEmail().toString();
							address = user.getAddress().toString();
							
							edtAddress.setText(address);
							edtEmail.setText(email);
							edtHoten.setText(fullname);
							edtPhone.setText(phone);
						}
					}
				});
				
				if(type.equalsIgnoreCase("PHONE")) {
					final PhoneNumber phoneNumber = ParseApplication.getInforPhoneNumberOfColor(idProduct, color);
					NumberProduct = phoneNumber.getNumber();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							description = "Phone";
							if(phoneNumber != null) {
								displayImage(phoneNumber.getImage(), imageRepresenet);
							}
						}
					});
				} else if(type.equalsIgnoreCase("TABLET")) {
					final TabletNumber tabletNumber = ParseApplication.getInforTabletNumberOfColor(idProduct, color);
					NumberProduct = tabletNumber.getNumber();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							description = "Tablet";
							if(tabletNumber != null) {
								displayImage(tabletNumber.getImage(), imageRepresenet);
							}
							
						}
					});
				} else if(type.equalsIgnoreCase("ACCESSORY")) {
					final DetailAccessory detailAccessory = ParseApplication.getInforDetailAccessory(idProduct);
					NumberProduct = detailAccessory.getSumAccessory();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							description = "Accessory";
							if(detailAccessory != null) {
								displayImage(detailAccessory.getAvatar(), imageRepresenet);
							}
							
						}
					});
				}
				
			}
		});
		thread.start();
		
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnDathang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String number = edtSoluong.getText().toString().trim();
				addressDelivery = edtAdressDelivery.getText().toString().trim();
				if(!number.equalsIgnoreCase("") && !addressDelivery.equalsIgnoreCase("")) {
					int num = Integer.parseInt(number);
					numberSelected = num;
					if(num > NumberProduct) {
						AlertDialog.Builder builder = new AlertDialog.Builder(MuahangDialogActivity.this);
						builder.setIcon(R.drawable.icon_error);
						builder.setMessage("Số lượng sản phẩm chỉ còn "+ String.valueOf(NumberProduct));
						builder.setTitle("Thông báo");
						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
						builder.show();
					} else {
						VTCPay.initWithAppID(Integer.parseInt("500000284"), "0973718347", md5("Thuong92@"));
						vtcid = VTCPay.getInstance();
						callback = new VTCPayCallback();
						String dongia = formatDigits(price);

						VTCPayInfoPayment infoPay = new VTCPayInfoPayment();
						infoPay.setDescription(description);
						infoPay.setPrice(Long.parseLong(dongia));
						infoPay.setQuantity(Integer.parseInt(number));

						final Intent intent = vtcid.home(callback, MuahangDialogActivity.this, infoPay);
						
						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								startActivity(intent);
								return null;
							}
						}.execute();
					}
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(MuahangDialogActivity.this);
					builder.setIcon(R.drawable.icon_error);
					builder.setMessage("Nhập đủ thông tin trước khi giao dịch!");
					builder.setTitle("Thông báo");
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							});
					builder.show();
				}
				
				
			}
		});
		
	}
	
	public static String formatDigits(String value) {
		String digits = value.toString().replaceAll("\\D", "");
		return digits;
	}
	

	class VTCPayCallback implements IVTC {

		@Override
		/**
		 * Hàm callback trả thông tin về cho app người dùng ra khỏi lib
		 * type: CACEL_DIALOG người dùng ra khỏi lib
		 */
		public void requestResult(int resultCode, String result) {
			switch (resultCode) {
			case VTCPay.PAY_SUCCESS:
				try {
					JSONObject json = new JSONObject(result);
					int Status = json.getInt("Status");
					String OrderCode = json.getString("OrderCode");
					long Amount = json.getLong("Amount");
					String ProductName = json.getString("ProductName");
					String MethodName = json.getString("MethodName");
					String AccountName = json.getString("AccountName");
					int appID = json.getInt("AppID");
					tv_MessageError.setText(Html
									.fromHtml("Thanh toán thành công với mã đơn hàng là: " + OrderCode));
					
					// Them khach hang vao danh sach giao hang, doanh thu va giam so luong hang trong kho 
					threadUpdate = new Thread(new Runnable() {
						
						@Override
						public void run() {
							String type = typeSelected;
							switch (type) {
							case "PHONE":
								ParseApplication.AddDataDelivery(PHONE, objectIdAccount, objectIdProduct, 
										numberSelected, colorSelected, addressDelivery, LabelIncome, PriceIncome);
								break;
							case "TABLET":
								ParseApplication.AddDataDelivery(TABLET, objectIdAccount, objectIdProduct, 
										numberSelected, colorSelected, addressDelivery, LabelIncome, PriceIncome);
								break;
							case "ACCESSORY":
								ParseApplication.AddDataDelivery(ACCESSORY, objectIdAccount, objectIdProduct, 
										numberSelected, colorSelected, addressDelivery, LabelIncome, PriceIncome);
								break;
							default:
								break;
							}
						}
					});
					threadUpdate.start();
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;

			case VTCPay.PAY_CANCEL:
				tv_MessageError.setText(result);
				break;
			}
		}
		
	}
	
	
	
	
	
	
	public static String md5(String s) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(s.getBytes());

			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);

			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
//	public static String md5(String msg) {
//  try {
//      MessageDigest md = MessageDigest.getInstance("MD5");
//      md.update(msg.getBytes());
//      byte byteData[] = md.digest();
//      //convert the byte to hex format method 1
//      StringBuffer sb = new StringBuffer();
//      for (int i = 0; i < byteData.length; i++) {
//          sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//      }
//      Log.d("MD5", sb.toString());
//      return  sb.toString();
//  } catch (Exception ex) {
//      return "";
//  }
//}
	
	public void displayImage(ParseFile thumbnail, final ImageView image) {
		if(thumbnail != null) {
			thumbnail.getDataInBackground(new GetDataCallback() {
				
				@Override
				public void done(byte[] data, ParseException e) {
					if(e == null) {
						Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
						if (bmp != null) {
							image.setImageBitmap(bmp);
						}
					} else {
						Log.e("paser after downloaded", " null");
					}
				}
			});
		}
		
		
	}
	
	
}




//String t = "https://www.nganluong.vn/mobile_checkout_api_post.php?func=sendOrder&version=1.0&" +
//		"merchant_id=412566&merchant_account=trinhviet1928@gmail.com&order_code=37888&" +
//		"total_amount=20000&currency=vnd&language=vi&return_url=http://vnexpress.net&" +
//		"cancel_url=https://www.nganluong.vn&notify_url=http://tgvn.com.vn&" +
//		"buyer_fullname=Trịnh Xuân Thương&buyer_email=trinhxuan92@gmail.com&" +
//		"buyer_mobile=0973718347&buyer_address=Đà Nẵng&";



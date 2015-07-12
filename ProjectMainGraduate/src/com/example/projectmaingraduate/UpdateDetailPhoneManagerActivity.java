package com.example.projectmaingraduate;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.object.DetailPhone;
import com.vtc.pay.common.VTCPayInfoPayment;
import com.vtc.pay.lib.VTCPay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDetailPhoneManagerActivity extends Activity {

	private EditText edtLabel, edtPrice, edtGuarantee, edtState,
			edtPercentPromption, edtSaleOff;
	private Button btnVerify, btnClose;
	private ImageButton imgbtnRefresh;
	private DetailPhone detailPhone = new DetailPhone();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);//
		setContentView(R.layout.activity_update_product_manager);
		initiation();
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("UpdatePhone");
		final String idUpdate = bundle.getString("IdUpdatePhone");
		
		detailPhone = ParseApplication.getInforUpdateProductsPhone(idUpdate);
		refreshData();
		
		init();
		
		imgbtnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshData();
			}
		});
		
		
		btnVerify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDetailPhoneManagerActivity.this);
				builder.setIcon(R.drawable.icon_question_red);
				builder.setTitle("Câu hỏi");
				builder.setMessage("Bạn có sẵn sàng cập nhật lại thông tin không?");
				builder.setNegativeButton("Đúng vậy", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						boolean ok;
						String price, cost, saleOff, state, guarantee, label;
						int percent = 0;
						price = edtPrice.getText().toString().trim();
						cost = price.replace(".", "");
						saleOff = edtSaleOff.getText().toString().trim();
						try {
							Integer.parseInt(cost);
							percent = Integer.parseInt(edtPercentPromption.getText().toString().trim());
							ok = true;
						} catch (NumberFormatException e) {
							ok = false;
							e.printStackTrace();
						}
						if (ok && (saleOff.equalsIgnoreCase("true") || saleOff.equalsIgnoreCase("false"))) {
							label = edtLabel.getText().toString();
							state = edtState.getText().toString();
							guarantee = edtGuarantee.getText().toString();
							boolean b = Boolean.parseBoolean(saleOff);
							detailPhone.setName(label);
							detailPhone.setSaleOff(b);
							detailPhone.setPrice(price);
							detailPhone.setGuarantee(guarantee);
							detailPhone.setState(state);
							detailPhone.setPercentPromotion(percent);
							boolean verify = ParseApplication.VerifyDataUpdateProduct("Phone", idUpdate,
									label, price, percent, b, guarantee, state);
							if(verify) {
								Toast.makeText(getApplicationContext(), "Cập nhật thành công",
													Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(getApplicationContext(), "Cập nhật thất bại",
													Toast.LENGTH_LONG).show();
							}
						} else {
							showWarning();
						}
					}
				});
				
				builder.setPositiveButton("Không phải", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				builder.show();
			}
		});
		
		
		btnClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
	}

	protected void showWarning() {
		AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDetailPhoneManagerActivity.this);
		builder.setIcon(R.drawable.icon_error);
		builder.setTitle("Cảnh báo");
		builder.setMessage("Cập nhật thông tin không đúng cú pháp, xin kiểm tra và nhập lại!");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 
			}
		});
		builder.show();
	}

	protected void refreshData() {
		if(detailPhone != null) {
			edtLabel.setText(detailPhone.getName());
			edtPrice.setText(detailPhone.getPrice());
			edtGuarantee.setText(detailPhone.getGuarantee());
			edtState.setText(detailPhone.getState());
			edtPercentPromption.setText(String.valueOf(detailPhone.getPercentPromotion()));
			edtSaleOff.setText(String.valueOf(detailPhone.isSaleOff()));
		}
	}

	private void initiation() {
		// TODO Auto-generated method stub
		edtLabel = (EditText) findViewById(R.id.activity_update_product_manager_edt_label);
		edtPrice = (EditText) findViewById(R.id.activity_update_product_manager_edt_price);
		edtGuarantee = (EditText) findViewById(R.id.activity_update_product_manager_edt_guarantee);
		edtState = (EditText) findViewById(R.id.activity_update_product_manager_edt_state);
		edtSaleOff = (EditText) findViewById(R.id.activity_update_product_manager_edt_saleoff);
		edtPercentPromption = (EditText) findViewById(R.id.activity_update_product_manager_edt_percentpromotion);
		btnVerify = (Button) findViewById(R.id.activity_update_product_manager_btn_verify);
		btnClose = (Button) findViewById(R.id.activity_update_product_manager_edt_close);
		imgbtnRefresh = (ImageButton) findViewById(R.id.activity_update_product_manager_imgbtn_refresh);
	}
	
	
	/**
	 * Hàm chuyển đổi thành format tiền, tiêu chuẩn việt nam
	 * 
	 * @param editText
	 * @param btn_xoa
	 * @param tv_money
	 * @param type
	 */
	public static class CurrencyTextWatcher implements TextWatcher {

		boolean mEditing;
		private EditText editText;
		private int nType = 0;
//		private TextView tv_money = null;

		public CurrencyTextWatcher(final EditText editText, int type) {
			mEditing = false;
			this.editText = editText;
			this.nType = type;
		}

		public CurrencyTextWatcher(final EditText editText, final TextView tv_money, int type) {
			mEditing = false;
			this.editText = editText;
			this.nType = type;
//			this.tv_money = tv_money;
		}

		public synchronized void afterTextChanged(Editable s) {
			if (!mEditing) {
				mEditing = true;

				if (this.nType == 1) {
					String digits = s.toString().replaceAll("\\D", "");
					// editText.setInputType(InputType.TYPE_CLASS_TEXT);
					try {
						String formatted = formatMoney(Long.parseLong(digits));
						s.replace(0, s.length(), formatted);
						// editText.setInputType(InputType.TYPE_CLASS_NUMBER);
					} catch (NumberFormatException nfe) {
						s.clear();
					}
				}

				mEditing = false;
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}

	/**
	 * Format tiền thành chuẩn việt nam
	 * 
	 * @param editText : truyền kiểu số
	 */
	public static void setOnChangeEdittextFormatMoney(final EditText editText) {

		editText.addTextChangedListener(new CurrencyTextWatcher(editText, 1));
	}

	public static void setOnChangeEdittext(final EditText editText) {
		editText.addTextChangedListener(new CurrencyTextWatcher(editText, 0));
	}

	public static String formatMoney(long s) {
		NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.US);
		String money = usFormat.format(s);
		money = money.replace(",", ".");
		return money;
	}


	private void init() {
		setOnChangeEdittextFormatMoney(edtPrice);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}
	
}






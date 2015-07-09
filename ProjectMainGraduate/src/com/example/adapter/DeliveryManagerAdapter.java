package com.example.adapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.example.object.Delivery;
import com.example.object.DetailAccessory;
import com.example.object.DetailPhone;
import com.example.object.DetailTablet;
import com.example.projectmaingraduate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DeliveryManagerAdapter extends ArrayAdapter<Delivery> {

	private Activity activity;
	private ArrayList<Delivery> arrayList;
	private CallBack callBack;
	private String arr[] = { "Chưa giao hàng", "Đã giao hàng" };
	private boolean giaohang;

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	public interface CallBack {
		public void onClickCallback(LinearLayout lnLayout, int position);

		public void onClickCallbackCustomer(int position);

		public void onClickCallbackProduct(int position, String price);

		public void onClickCallbackDelete(int position);

		public void onClickCallbackConfirmDelivery(int index, boolean b, 
				TextView tvStateDelivery, Spinner spinner);
	}

	public DeliveryManagerAdapter(Activity context, ArrayList<Delivery> objects) {
		super(context, 0, objects);
		this.activity = context;
		this.arrayList = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.adapter_delivery_giaohang, null);
		}

		TextView tvCustomerName = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_Tenkhachhang);
		ImageButton imgbtnDelete = (ImageButton) convertView
				.findViewById(R.id.adapter_delivery_giaohang_imgbtn_delete);
		final TextView tvStateDelivery = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_state_giaohang);
		final TextView tvAddressDelivery = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_address_delivery);
		final TextView tvProductName = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_Tensanpham);
		final TextView tvPrice = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_price);
		final TextView tvSoluong = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_Soluong);
		final TextView tvTongtien = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_tongtien);
		final TextView tvDate = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_Ngaygiaodich);
		final TextView tvCustomer = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_infor_khachhang);
		final TextView tvProduct = (TextView) convertView
				.findViewById(R.id.adpater_delivery_giaohang_tv_infor_sanpham);
		
		final Spinner spinner = (Spinner) convertView
				.findViewById(R.id.adpater_delivery_giaohang_spinner_giaohang);
		final LinearLayout lnLayout = (LinearLayout) convertView
				.findViewById(R.id.adapter_delivery_giaohang_lnlayout);
		LinearLayout lnLayoutMain = (LinearLayout) convertView
				.findViewById(R.id.adpater_delivery_giaohang_lnlayout_main);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_spinner_item, arr);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);

		tvCustomerName.setText(arrayList.get(position).getUser().getFullName()
				.toString());

		String card = "";
		String type = arrayList.get(position).getType();
		switch (type) {
		case "phone":
			DetailPhone phone = arrayList.get(position).getPhone();
			tvProductName.setText(styleLabel(phone.getName()));
			String gia = phone.getPrice();
			if (phone.isSaleOff()) {
				int pro = phone.getPercentPromotion();
				String money = stylePrice(gia, pro, true);
				card = money;
				tvPrice.setText(money + " VNĐ");
			} else {
				card = gia;
				tvPrice.setText(gia + " VNĐ");
			}
			break;
		case "tablet":
			DetailTablet tablet = arrayList.get(position).getTablet();
			tvProductName.setText(styleLabel(tablet.getName()));
			String gia1 = tablet.getPrice();
			if (tablet.isSaleOff()) {
				int pro = tablet.getPercentPromotion();
				String money = stylePrice(gia1, pro, true);
				card = money;
				tvPrice.setText(money + " VNĐ");
			} else {
				card = gia1;
				tvPrice.setText(gia1 + " VNĐ");
			}
			break;
		case "accessory":
			DetailAccessory accessory = arrayList.get(position).getAccessory();
			tvProductName.setText(styleLabel(accessory.getName()));
			String gia2 = accessory.getPrice();
			if (accessory.isSaleOff()) {
				int pro = accessory.getPercentPromotion();
				String money = stylePrice(gia2, pro, true);
				card = money;
				tvPrice.setText(money + " VNĐ");
			} else {
				card = gia2;
				tvPrice.setText(gia2 + " VNĐ");
			}

			break;
		default:
			break;
		}

		final String Price = card;
		int num = arrayList.get(position).getSoluong();
		tvSoluong.setText(String.valueOf(num));
		if (num > 1) {
			String Tong = stylePrice(card, num, false);
			tvTongtien.setText(Tong + " VNĐ");
		} else {
			tvTongtien.setText(card + " VNĐ");
		}

		tvAddressDelivery.setText(arrayList.get(position).getAddressDelivery());
		tvDate.setText(arrayList.get(position).getTime() + "");
		
		giaohang = arrayList.get(position).isStateDelivery();
		if (giaohang) {
			spinner.setSelection(1);
		} else {
			spinner.setSelection(0);
		}
		
		spinner.setPressed(true);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, final int index, long id) {
				
				if(spinner.isPressed()) {
					tvStateDelivery.setText(arr[index]);
					if(!arrayList.get(position).isStateDelivery()) {
						tvStateDelivery.setTextColor(Color.RED);
					} else {
						tvStateDelivery.setTextColor(Color.BLACK);
					}
//					Log.d("", ">>>000 roi??  String.valueOf(pressed)");
				} else {
					if(callBack != null) {
						callBack.onClickCallbackConfirmDelivery(position, 
								arrayList.get(position).isStateDelivery(), tvStateDelivery, spinner);
					}
//					Log.d("", ">>>000 roi??  String.valueOf(setPressed(false);)");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		lnLayoutMain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (callBack != null) {
					callBack.onClickCallback(lnLayout, position);
				}
			}
		});

		tvCustomer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (callBack != null) {
					callBack.onClickCallbackCustomer(position);
				}
			}
		});
		
		tvProduct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (callBack != null) {
					callBack.onClickCallbackProduct(position, Price);
				}
			}
		});

		imgbtnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (callBack != null) {
					callBack.onClickCallbackDelete(position);
				}
			}
		});
		
		return convertView;
	}

	private String stylePrice(String gia, int pro, boolean sale) {
		String money = "";
		if (sale) {
			String cost = gia.replace(".", "");
			long tam = Long.parseLong(cost), newPrice;
			newPrice = tam - (long) (tam * ((pro) / 100f));
			// establish dot "."
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
			// decimalFormatSymbols.setDecimalSeparator('.');
			decimalFormatSymbols.setGroupingSeparator('.');
			DecimalFormat decimalFormat = new DecimalFormat("#,##0",
					decimalFormatSymbols);
			money = decimalFormat.format(newPrice);
		} else {
			String cost = gia.replace(".", "");
			long tam = Long.parseLong(cost), newPrice;
			newPrice = tam * pro;
			// establish dot "."
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
			// decimalFormatSymbols.setDecimalSeparator('.');
			decimalFormatSymbols.setGroupingSeparator('.');
			DecimalFormat decimalFormat = new DecimalFormat("#,##0",
					decimalFormatSymbols);
			money = decimalFormat.format(newPrice);
		}
		return money;
	}
	
	public String styleLabel(String _label) {
		final String nameLabel;
		int n = _label.indexOf("(");
		if (n != -1) {
			nameLabel = _label.substring(0, n);
		} else {
			nameLabel = _label;
		}
		return nameLabel;
	}

}

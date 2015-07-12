package com.example.adapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.example.object.DetailTablet;
import com.example.projectmaingraduate.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridTabletAdapeter extends ArrayAdapter<DetailTablet> {

	private Activity activity;
	private ArrayList<DetailTablet> arrayList;
	private ImageView avatar, imgSaleoff;
	private TextView tvPrice, tvName, tvState, tvPriceOff;
	private String money = "";

	public interface GridTabletAdapter {
		public void onclickGridTablet(int index, String id, String money);
	}

	private GridTabletAdapter gridTabletAdapter;

	public void setCallBack(GridTabletAdapter gridTabletAdapter) {
		this.gridTabletAdapter = gridTabletAdapter;
	}

	public GridTabletAdapeter(Activity context, ArrayList<DetailTablet> objects) {
		super(context, 0, objects);
		this.activity = context;
		this.arrayList = objects;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		LayoutInflater inflater = activity.getLayoutInflater();
		view = inflater.inflate(R.layout.adapter_gridview_products, null);
		avatar = (ImageView) view.findViewById(R.id.adapter_gridview_products_image);
		tvState = (TextView) view.findViewById(R.id.adapter_gridview_products_tv_state);
		tvName = (TextView) view.findViewById(R.id.adapter_gridview_products_tv_nametablet);
		tvPrice = (TextView) view.findViewById(R.id.adapter_gridview_products_tv_price);
		tvPriceOff = (TextView) view.findViewById(R.id.adapter_gridview_products_tv_price_saleoff);
		imgSaleoff = (ImageView) view.findViewById(R.id.adapter_gridview_products_img_saleoff);

		tvName.setText(arrayList.get(position).getName());
		String uPrice = arrayList.get(position).getPrice().trim();
		tvPrice.setText(uPrice + " vnđ");

		tvState.setText(arrayList.get(position).getCondition());
		displayImage(arrayList.get(position).getAvatar(), avatar);
		Boolean ok = arrayList.get(position).isSaleOff();
		if (ok) {
			try {
				imgSaleoff.setVisibility(LinearLayout.VISIBLE);
				tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//				tvPrice.setTextSize(16);
				tvPrice.setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
				tvPrice.setTextColor(Color.RED);
				tvPrice.setTypeface(null, Typeface.BOLD);
				int percent = arrayList.get(position).getPercentPromotion();
				String cost = uPrice.replace(".", "");
				int tam = Integer.parseInt(cost), newPrice;
				newPrice = tam - (int) (tam * ((percent) / 100f));
				// establish dot "."
				DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
				// decimalFormatSymbols.setDecimalSeparator('.');
				decimalFormatSymbols.setGroupingSeparator('.');
				DecimalFormat decimalFormat = new DecimalFormat("#,##0", decimalFormatSymbols);
				money = decimalFormat.format(newPrice);

				tvPriceOff.setText(money + " vnđ");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		final String card = money;
		avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (gridTabletAdapter != null) {
					gridTabletAdapter.onclickGridTablet(position, arrayList.get(position).getId(), card);
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

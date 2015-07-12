package com.example.projectmaingraduate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDetailColorPhoneManagerDialogActivity extends Activity {

	private Button btnVerify, btnClose;
	private EditText edtNumberSold, edtNumberProduct;
	private int numberSold, numberProduct;
	private int numlaterSold, numlaterProduct;
	private String idDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_update_color_number_product);
		overridePendingTransition(R.anim.in_translate, R.anim.out_translate);

		btnClose = (Button) findViewById(R.id.dialog_update_color_number_product_btn_close);
		btnVerify = (Button) findViewById(R.id.dialog_update_color_number_product_btn_verify);
		edtNumberSold = (EditText) findViewById(R.id.dialog_update_color_number_product_edt_number_sold);
		edtNumberProduct = (EditText) findViewById(R.id.dialog_update_color_number_product_edt_number_product);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("UpdateColorPhone");
		final int index_pre = bundle.getInt("position_pre");
		final int index = bundle.getInt("position");
		numberProduct = bundle.getInt("numberProduct");
		numberSold = bundle.getInt("numberSold");
		idDetail = bundle.getString("objectId");
		
		// dua gia tri len EditText
		edtNumberSold.setText(String.valueOf(numberSold));
		edtNumberProduct.setText(String.valueOf(numberProduct));

		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnVerify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String numSold = edtNumberSold.getText().toString().trim();
					String numProduct = edtNumberProduct.getText().toString().trim();
					numlaterSold = Integer.parseInt(numSold);
					numlaterProduct = Integer.parseInt(numProduct);
					boolean ok = ParseApplication.UpdateNumberColorProduct(
							"PhoneNumber", idDetail, numlaterProduct, numlaterSold);
					if (ok) {
						Toast.makeText(getApplicationContext(),"Cập nhật thành công", Toast.LENGTH_LONG).show();
						DetailColorPhoneManagerDiaglogActivity.list.get(index).setNumber(numlaterProduct);
						DetailColorPhoneManagerDiaglogActivity.list.get(index).setNumberSold(numlaterSold);
						DetailColorPhoneManagerDiaglogActivity.adapter.notifyDataSetChanged();
						//
						int sumProduct = DetailPhoneManagerActivity.list.get(
								index_pre).getSumPhone() - numberProduct + numlaterProduct;
						int sumSold = DetailPhoneManagerActivity.list.get(
								index_pre).getSumPhoneSold() - numberSold + numlaterSold;
						DetailPhoneManagerActivity.list.get(index_pre).setSumPhone(sumProduct);
						DetailPhoneManagerActivity.list.get(index_pre).setSumPhoneSold(sumSold);
						DetailPhoneManagerActivity.adapter.notifyDataSetChanged();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
					}
				} catch (NumberFormatException e) {
					showWarning();
					e.printStackTrace();
				}
			}
		});

	}

	protected void showWarning() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				UpdateDetailColorPhoneManagerDialogActivity.this);
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


}

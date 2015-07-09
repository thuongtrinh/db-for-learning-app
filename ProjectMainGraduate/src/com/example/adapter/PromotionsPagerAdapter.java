package com.example.adapter;

import com.example.fragment.InforGiftProductFragment;
import com.example.fragment.AccessoryPromotionsFragment;
import com.example.fragment.PhonePromotionsFragment;
import com.example.fragment.TabletPromotionsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PromotionsPagerAdapter extends FragmentPagerAdapter {
	
	private final String id;

	public PromotionsPagerAdapter(FragmentManager fm, String idDetail) {
		super(fm);
		this.id = idDetail;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Phones fragment activity
			return new InforGiftProductFragment(id);
		case 1:
			// Tablets fragment activity
			return new AccessoryPromotionsFragment(id);
		case 2:
			// Tablets fragment activity
			return new TabletPromotionsFragment(id);
		case 3:
			// Tablets fragment activity
			return new PhonePromotionsFragment(id);
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}


















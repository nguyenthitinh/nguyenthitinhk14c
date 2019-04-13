package com.example.quanlythuchicanhan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.quanlythuchicanhan.Fragment.FragmentThongKeHomNay;
import com.example.quanlythuchicanhan.Fragment.FragmentThongKeNgay;
import com.example.quanlythuchicanhan.Fragment.FragmentThongKeThang;

public class PagerAdapter extends FragmentStatePagerAdapter {


    public static final int NUMBER_PAGERS = 3;

    public String tabTitle[] = new String[] {"Hôm Nay", "Tháng Này", "Ngày Cụ Thể"};
    public PagerAdapter(FragmentManager fm)
    {
        super(fm);
    }



    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new FragmentThongKeHomNay();
        }
        else if (i==1){
            return new FragmentThongKeThang();
        }
        else if (i==2){
            return new FragmentThongKeNgay();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_PAGERS;
    }

    public CharSequence getPageTitle(int position) {
        return tabTitle[position];

    }

}

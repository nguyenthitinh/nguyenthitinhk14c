package com.example.quanlythuchicanhan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.quanlythuchicanhan.Fragment.FragmentThongKeChiHomNay;
import com.example.quanlythuchicanhan.Fragment.FragmentThongKeChiNgay;
import com.example.quanlythuchicanhan.Fragment.FragmentThongKeChiThang;

public class PagerChiAdapter extends FragmentStatePagerAdapter{
    public static final int NUMBER_PAGERS = 3;

    public String tabTitle[] = new String[] {"Hôm Nay", "Tháng Này", "Ngày Cụ Thể"};

    public PagerChiAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new FragmentThongKeChiHomNay();
        }
        else if (i==1){
            return new FragmentThongKeChiThang();
        }
        else if (i==2){
            return new FragmentThongKeChiNgay();
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

package com.moneygiver.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import com.moneygiver.activities.R;

public class FragmentPageAdapter extends FragmentPagerAdapter {


    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return new LeftFragment();
            case 1:
                return new CenterFragment();
            case 2:
                return new RightFragment();
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }


    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
}

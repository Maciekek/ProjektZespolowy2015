package com.moneygiver.views.loggedIn.SwipeLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moneygiver.views.loggedIn.fragments.CenterFragment;
import com.moneygiver.views.loggedIn.fragments.LeftFragment;
import com.moneygiver.views.loggedIn.fragments.RightFragment;

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

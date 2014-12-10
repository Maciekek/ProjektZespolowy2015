package com.moneygiver.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;

/**
 * Created by Szymon on 2014-12-09.
 */
public class MainTabs extends FragmentActivity implements ActionBar.TabListener{
    private LoginChecker loginChecker;
    private LoginExecutor loginExecutor;
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;

    ActionBar actionbar;
    ViewPager viewpager;
    FragmentPageAdapter ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();

        setContentView(R.layout.activity_tabs_main);

        viewpager = (ViewPager) findViewById(R.id.pager);
        ft = new FragmentPageAdapter(getSupportFragmentManager());
        actionbar = getActionBar();
        viewpager.setAdapter(ft);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("Left").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Center").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Right").setTabListener(this));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                actionbar.setSelectedNavigationItem(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewpager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    public void isLoggedIn(View v) {
        if(loginChecker.isLoggedIn()) {
            Message.message(this, "Zalogowany");
        }else {
            Message.message(this, "Niezalogowany");
        }
    }

    public void logout(View v) {
        loginExecutor.LogUserOut();
    }

}

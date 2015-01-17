package com.moneygiver.views.loggedIn.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.moneygiver.DBObjects.UserData;
import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;
import com.moneygiver.database.DatabaseAdapter;
import com.moneygiver.views.R;
import com.moneygiver.views.loggedIn.SwipeLayout.FragmentPageAdapter;

/**
 * Created by Szymon on 2014-12-09.
 */
public class MainTabs extends FragmentActivity implements ActionBar.TabListener{

    private static long back_pressed;
    private LoginChecker loginChecker;
    private LoginExecutor loginExecutor;
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;


    private UserData userData;

    DatabaseAdapter db;
    ActionBar actionBar;
    ViewPager viewpager;
    FragmentPageAdapter ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);
        db = new DatabaseAdapter(this);
        userData = db.getUserData();

        prepareTabs();

        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);

    }

    public UserData getUserData() {
        return userData;
    }

    private void prepareTabs() {
        viewpager = (ViewPager) findViewById(R.id.pager);
        ft = new FragmentPageAdapter(getSupportFragmentManager());
        actionBar = getActionBar();
        viewpager.setAdapter(ft);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Finanse").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Środek").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Prawo").setTabListener(this));

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                actionBar.setSelectedNavigationItem(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Message.message(this, getString(R.string.press_back_again));
        back_pressed = System.currentTimeMillis();
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

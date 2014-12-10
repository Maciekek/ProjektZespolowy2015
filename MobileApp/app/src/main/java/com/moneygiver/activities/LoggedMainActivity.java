package com.moneygiver.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;


/**
 * Created by Szymon on 2014-12-07.
 */
public class LoggedMainActivity extends Activity implements OnRefreshListener {
    private LoginChecker loginChecker;
    private LoginExecutor loginExecutor;
    private Context context = this;
    private SwipeRefreshLayout swipeLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_main);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_center_container);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);
    }

    public void logout(View v) {
        loginExecutor.LogUserOut();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                Message.message(context, "Refreshed :)");
            }
        }, 2000);
    }
    public void isLoggedIn(View v) {
        if(loginChecker.isLoggedIn()) {
            Message.message(this, "Zalogowany");
        }else {
            Message.message(this, "Niezalogowany");
        }
    }
}

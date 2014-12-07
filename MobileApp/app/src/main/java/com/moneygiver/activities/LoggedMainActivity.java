package com.moneygiver.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;

/**
 * Created by Szymon on 2014-12-07.
 */
public class LoggedMainActivity extends Activity {
    private LoginExecutor loginExecutor;
    private LoginChecker loginChecker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_main);

        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);
    }

    public void logout(View v) {
        loginExecutor.LogUserOut();
    }

    public void isLoggedIn(View v) {
        if(loginChecker.isLoggedIn()) {
            Message.message(this, "Zalogowany");
        }else {
            Message.message(this, "Niezalogowany");
        }
    }
}

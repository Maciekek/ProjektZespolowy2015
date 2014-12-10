package com.moneygiver.views.loggedOut.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;
import com.moneygiver.views.R;
import com.moneygiver.views.loggedIn.activities.MainTabs;


public class LoginActivity extends Activity {

    private static long back_pressed;
    private LoginExecutor loginExecutor;
    private LoginChecker loginChecker;

    private Intent j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_home);



        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);
        j = new Intent(this, MainTabs.class);
        if(loginChecker.isLoggedIn()) {
//            startActivity(i);
            startActivity(j);
            finish();
        }
    }

    public void signIn(View v) { loginExecutor.signIn(); }

    public void isLoggedIn(View v) {
        if(loginChecker.isLoggedIn()) {
            Message.message(this, "Zalogowany");
        }else {
            Message.message(this, "Niezalogowany");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the  menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Message.message(this, getString(R.string.press_back_again));
        back_pressed = System.currentTimeMillis();
    }
}

package com.moneygiver.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;


public class HomeActivity extends Activity {

    private LoginExecutor loginExecutor;
    private LoginChecker loginChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginExecutor = new LoginExecutor(findViewById(R.id.responseTextView), findViewById(R.id.logintw), findViewById(R.id.passwordet), this);
        loginChecker = new LoginChecker(this);
    }

    public void signIn(View v) {
        loginExecutor.signIn();
    }

    public void isLoggedIn(View v) {
        loginChecker.isLoggedIn();
    }

    public void logout(View v) {
        loginExecutor.LogUserOut();
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
}

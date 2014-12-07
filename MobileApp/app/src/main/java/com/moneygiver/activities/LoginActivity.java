package com.moneygiver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;


public class LoginActivity extends Activity {

    private LoginExecutor loginExecutor;
    private LoginChecker loginChecker;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginExecutor = new LoginExecutor(this);
        loginChecker = new LoginChecker(this);
        i = new Intent(this, LoggedMainActivity.class);
        if(loginChecker.isLoggedIn()) {
            startActivity(i);
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
}

package com.moneygiver.moneygiver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moneygiver.communication.PostExecutor;


public class HomeActivity extends Activity {

    private Button testButton;
    private TextView tw;
    private AutoCompleteTextView loginTW;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
PostExecutor pe;
    public void buttonOnClick(View v) {
        tw = (TextView) findViewById(R.id.responseTextView);
        loginTW = (AutoCompleteTextView) findViewById(R.id.logintw);
        passwordET = (EditText) findViewById(R.id.passwordet);

        pe = new PostExecutor("http://178.62.111.179/userCredentials", loginTW.getText().toString(),
                passwordET.getText().toString());
        Thread t = new Thread(pe);
        t.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tw.setText(pe.result);
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

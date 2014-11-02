package com.moneygiver.moneygiver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.moneygiver.communication.PostExecutor;

import java.io.IOException;


public class HomeActivity extends Activity {

    private Button testButton;
    private TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
PostExecutor pe;
    public void buttonOnClick(View v) {
        tw = (TextView) findViewById(R.id.textView2);
        pe = new PostExecutor();
        Thread t = new Thread(pe);
        t.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tw.setText("Serwer zwrócił:\n" + pe.result);
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

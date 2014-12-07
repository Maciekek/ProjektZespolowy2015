package com.moneygiver.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.moneygiver.activities.LoggedMainActivity;
import com.moneygiver.activities.LoginActivity;
import com.moneygiver.activities.R;
import com.moneygiver.communication.HttpRequest;
import com.moneygiver.database.DatabaseAdapter;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Szymon on 2014-11-02.
 */
public class LoginExecutor {

    private AutoCompleteTextView loginTW;
    private EditText passwordET;
    private DatabaseAdapter dbAdapter;
    private Activity activity;
    private Intent i;

    private HttpRequest HttpRequest;
    private static final String LOGIN_URL = "http://178.62.111.179/userCredentials";

    public LoginExecutor(Context context) {
        activity = (Activity) context;
        loginTW = (AutoCompleteTextView) activity.findViewById(R.id.logintw);
        passwordET = (EditText) activity.findViewById(R.id.passwordet);

        dbAdapter = new DatabaseAdapter(context);
    }

    public void signIn() {
        String login = loginTW.getText().toString();
        String pass = passwordET.getText().toString();

        String json = makeJSON(login, pass);
        HttpRequest = new HttpRequest(LOGIN_URL, json, activity);
        String credentials = makeCredentials(login, pass);
        HttpRequest.Post(this, credentials);
    }

    private String makeJSON(String login, String password) {
        return "{\"userCredentials\": {\"login\": \"" +login +
                "\", \"password\":\"" + password +"\"}}";
    }

    private String makeCredentials(String login, String pass) {
        return login+":"+pass;
    }

    public void LogUserIn() {
        dbAdapter.deleteAllData();
        dbAdapter.insertData(1);
        i = new Intent(activity, LoggedMainActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public void LogUserOut() {
        dbAdapter.deleteAllData();
        dbAdapter.insertData(0);
        i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
        activity.finish();
    }
}

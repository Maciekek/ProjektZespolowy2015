package com.moneygiver.actions;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.moneygiver.communication.HttpRequest;
import com.moneygiver.database.DatabaseAdapter;

/**
 * Created by Szymon on 2014-11-02.
 */
public class LoginExecutor {

    private TextView responseTW;
    private AutoCompleteTextView loginTW;
    private EditText passwordET;
    private DatabaseAdapter dbAdapter;
    private Context context;

    private HttpRequest HttpRequest;

    public LoginExecutor(Object response, Object login, Object password, Context context) {
        responseTW = (TextView) response;
        loginTW = (AutoCompleteTextView) login;
        passwordET = (EditText) password;
        dbAdapter = new DatabaseAdapter(context);
    }

    public void signIn() {
        // METODA DO ZROBIENIA
        String json = "{\"userCredentials\": {\"login\": \"" +loginTW.getText().toString() +
                "\", \"password\":\"" + passwordET.getText().toString() +"\"}}";

        HttpRequest = new HttpRequest("http://178.62.111.179/userCredentials", json, responseTW);
        String credentials = makeCredentials(loginTW.getText().toString(), passwordET.getText().toString());
        HttpRequest.Post(this, credentials);
    }

    private String makeCredentials(String login, String pass) {
        return login+":"+pass;
    }

    public void LogUserIn() {
        dbAdapter.deleteAllData();
        dbAdapter.insertData(1);
    }

    public void LogUserOut() {
        dbAdapter.deleteAllData();
        dbAdapter.insertData(0);
    }
}

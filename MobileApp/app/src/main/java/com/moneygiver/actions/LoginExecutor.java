package com.moneygiver.actions;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.moneygiver.communication.HttpRequest;

/**
 * Created by Szymon on 2014-11-02.
 */
public class LoginExecutor {

    private TextView responseTW;
    private AutoCompleteTextView loginTW;
    private EditText passwordET;

    private HttpRequest HttpRequest;

    public LoginExecutor(Object response, Object login, Object password) {
        responseTW = (TextView) response;
        loginTW = (AutoCompleteTextView) login;
        passwordET = (EditText) password;
    }

    public void press() {
        // METODA DO ZROBIENIA
        String json = "{\"userCredentials\": {\"login\": \"" +loginTW.getText().toString() +
                "\", \"password\":\"" + passwordET.getText().toString() +"\"}}";

        HttpRequest = new HttpRequest("http://178.62.111.179/userCredentials", json, responseTW);
        HttpRequest.Post();
    }
}

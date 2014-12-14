package com.moneygiver.threading;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.actions.Message;
import com.moneygiver.views.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by Szymon on 2014-11-02.
 */
public class NewThreadExecutor implements Runnable {

    private HttpURLConnection connection;
    private String JSONObject;
    private StringBuilder sb = new StringBuilder();
    private Activity activity;
    private Handler handler;
    private LoginExecutor loginExecutor;


    public NewThreadExecutor(HttpURLConnection connection, String JSONObject, Context context, LoginExecutor loginExecutor) {
        this.connection = connection;
        this.JSONObject = JSONObject;
        this.activity = (Activity) context;
        this.handler = new Handler();
        this.loginExecutor = loginExecutor;
    }

    @Override
    public void run() {
        try {
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(JSONObject);
            wr.flush();
            sb = new StringBuilder();
            final int HttpResult = connection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(HttpResult == 200) {
                        loginExecutor.LogUserIn();
                    }
                    else if(HttpResult == 403){
                        Message.message(activity, activity.getString(R.string.incorrect_credentials));
                    }
                }
            });
        } catch (java.net.ConnectException c) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Message.message(activity, activity.getString(R.string.login_error));
                };
            });
            }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

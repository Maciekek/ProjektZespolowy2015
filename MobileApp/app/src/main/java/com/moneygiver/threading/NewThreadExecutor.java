package com.moneygiver.threading;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;

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
    private TextView responseTW;
    private Handler handler;
    private LoginExecutor loginExecutor;


    public NewThreadExecutor(HttpURLConnection connection, String JSONObject, TextView responseTW, LoginExecutor loginExecutor) {
        this.connection = connection;
        this.JSONObject = JSONObject;
        this.responseTW = responseTW;
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
            int HttpResult = connection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }else{
                System.out.println(connection.getResponseMessage());
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    responseTW.setText("Otrzymano od serwera:\n"+ sb.toString());
                    if(sb.toString().contains("admin")) {
                        loginExecutor.LogUserIn();
                    }


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

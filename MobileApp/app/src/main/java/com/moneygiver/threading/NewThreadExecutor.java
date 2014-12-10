package com.moneygiver.threading;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.moneygiver.actions.LoginExecutor;

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
        loginExecutor.LogUserIn();
        /*try {
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
            }else {
                System.out.println(connection.getResponseMessage());
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Message.message(activity, "Otrzymano od serwera:\n" + sb.toString());
                    if(sb.toString().contains("admin")) {
                        loginExecutor.LogUserIn();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}

package com.moneygiver.communication;

import android.content.Context;
import android.widget.TextView;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.threading.NewThreadExecutor;

import android.os.Handler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Szymon on 2014-10-29.
 */
public class HttpRequest {
    private String url;
    private TextView responseTW;
    private String JSONObject;
    private NewThreadExecutor thr;

    public HttpRequest(String url, String JSONObject, TextView responseTW) {
        this.url = url;
        this.JSONObject = JSONObject;
        this.responseTW = responseTW;
    }

    public void Post(LoginExecutor loginExecutor) {
        try {
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            thr = new NewThreadExecutor(con, JSONObject, responseTW, loginExecutor);
            Thread t = new Thread(thr);
            t.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
       }
    }
}

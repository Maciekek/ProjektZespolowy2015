package com.moneygiver.communication;

import android.widget.TextView;

import com.moneygiver.threading.NewThreadExecutor;

import android.os.Handler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Szymon on 2014-10-29.
 */
public class Post {
    public String result = "dupa";
    private String url;
    private TextView responseTW;
    private String JSONObject;
    private NewThreadExecutor thr;

    public Post(String url, String JSONObject, TextView responseTW) {
        this.url = url;
        this.JSONObject = JSONObject;
        this.responseTW = responseTW;
    }

    public void execute() {
        try {
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            thr = new NewThreadExecutor(con, JSONObject, responseTW);
            Thread t = new Thread(thr);
            t.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
       }
    }
}

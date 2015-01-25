package com.moneygiver.communication;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.moneygiver.actions.LoginChecker;
import com.moneygiver.actions.LoginExecutor;
import com.moneygiver.threading.NewThreadExecutor;
import com.moneygiver.views.loggedIn.fragments.LeftFragment;

import android.os.Handler;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Szymon on 2014-10-29.
 */
public class HttpRequest {
    private String url;
    private Context context;
    private String JSONObject;
    private NewThreadExecutor thr;

    public HttpRequest(String url, String JSONObject, Context context) {
        this.url = url;
        this.JSONObject = JSONObject;
        this.context = context;
    }

//    public HttpRequest(String url, String JSONObject, Context context) {
//        this.url = url;
//        this.JSONObject = JSONObject;
//        this.activity = (Activity)context;
//    }

    public void PostLogin(LoginExecutor loginExecutor, String credentials) {
        try {
            String encoding = new String(Base64.encodeBase64(credentials.getBytes("UTF-8")),
                    "UTF-8");
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic " + encoding);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            thr = new NewThreadExecutor(con, JSONObject, context, loginExecutor);
            Thread t = new Thread(thr);
            t.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
       }
    }

    public void postRefresh(LeftFragment leftFragment, String credentials) {
        try {
            String encoding = new String(Base64.encodeBase64(credentials.getBytes("UTF-8")),
                    "UTF-8");
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic " + encoding);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            thr = new NewThreadExecutor(con, JSONObject, context, leftFragment);
            Thread t = new Thread(thr);
            t.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}

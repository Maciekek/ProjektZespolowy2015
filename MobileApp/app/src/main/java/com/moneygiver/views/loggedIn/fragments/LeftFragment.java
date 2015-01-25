package com.moneygiver.views.loggedIn.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moneygiver.DBObjects.UserData;
import com.moneygiver.communication.HttpRequest;
import com.moneygiver.database.DatabaseAdapter;
import com.moneygiver.views.R;
import com.moneygiver.views.loggedIn.SwipeLayout.LayoutObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Szymon on 2014-12-09.
 */
public class LeftFragment extends android.support.v4.app.Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private UserData userData;
    private DatabaseAdapter db;
    private TextView tw;
    private static final String LOGIN_URL = "http://178.62.111.179/userCredentials";
    private HttpRequest httpRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_left, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeLayout = new LayoutObject(this, view).getSwipeLayout();

        tw = (TextView)getView().findViewById(R.id.data);
        getDataFromDB();
        setData(false);
    }

    public void getDataFromDB() {
        db = new DatabaseAdapter(getActivity().getApplicationContext());
        userData = db.getUserData();
        userData.setMonthly(db.getMonthly());
    }



    public void setData(boolean fresh) {
        Context context = getActivity().getApplicationContext();
        String txt = "WITAJ "+userData.getUsername() + "\nTwoje miesięczne przychody to:\n"+userData.getIncome() +" zł";
        double sum = 0;

        Iterator it = userData.getMonthly().entrySet().iterator();

        if(it.hasNext()) {
            txt += "\n\nWydatki:";
        }
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry) it.next();
            txt += "\n" + pairs.getKey().toString() + ": " + pairs.getValue().toString()+ " zł";
            sum+= (Double)pairs.getValue();
        }
        txt += "\n\nPozostało ci: "+ (userData.getIncome() - sum)+ " zł";
        tw.setText(txt);
        if(fresh) {
            Toast.makeText(context, "Odświeżono!", Toast.LENGTH_LONG).show();
        }
    }

    private String makeJSON(String login, String password) {
        return "{\"username\": \"" +login +
                "\", \"password\":\"" + password +"\"}";
    }

    private String makeCredentials(String login, String pass) {
        return login+":"+pass;
    }


    private void refresh() {
        String login = userData.getUsername();
        String pass = userData.getPassword();

        String json = makeJSON(login, pass);

        httpRequest = new HttpRequest(LOGIN_URL, json, getActivity().getApplicationContext());
        String credentials = makeCredentials(login, pass);
        httpRequest.postRefresh(this, credentials);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                refresh();
            }
        }, 3000);
    }
}

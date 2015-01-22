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
        setData();
    }

    private void getDataFromDB() {
        db = new DatabaseAdapter(getActivity().getApplicationContext());
        userData = db.getUserData();
        userData.setMonthly(db.getMonthly());
    }



    private void setData() {
        String txt = "WITAJ "+userData.getUsername() + "\nTwoje miesięczne przychody to: "+userData.getIncome();


        Iterator it = userData.getMonthly().entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry) it.next();
            txt += "\n" + pairs.getKey().toString() + ": " + pairs.getValue().toString();
        }

        tw.setText(txt);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Odświeżono!", Toast.LENGTH_LONG).show();
            }
        }, 2000);
    }
}

package com.moneygiver.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.Toast;

import com.moneygiver.activities.R;

/**
 * Created by Szymon on 2014-12-09.
 */
public class CenterFragment extends Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_center, container, false);

        return inflater.inflate(R.layout.activity_center, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_center_container);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, "Refreshed!", Toast.LENGTH_LONG).show();
//                        Message.message(context, "Refreshed :)");
            }
        }, 2000);
    }
}
package com.moneygiver.views.loggedIn.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moneygiver.views.R;
import com.moneygiver.views.loggedIn.SwipeLayout.LayoutObject;

/**
 * Created by Szymon on 2014-12-09.
 */
public class RightFragment extends android.support.v4.app.Fragment implements OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_right, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeLayout = new LayoutObject(this, view).getSwipeLayout();
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

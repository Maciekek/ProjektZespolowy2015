package com.moneygiver.views.loggedIn.SwipeLayout;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;

import com.moneygiver.views.R;

/**
 * Created by Szymon on 2014-12-10.
 */
public class LayoutObject {
    OnRefreshListener context;
    View view;

    private static final int COLOR_1 = android.R.color.holo_blue_bright;
    private static final int COLOR_2 = android.R.color.holo_green_light;
    private static final int COLOR_3 = android.R.color.holo_orange_light;
    private static final int COLOR_4 = android.R.color.holo_red_light;

    public LayoutObject(OnRefreshListener context, View view) {
        this.context = context;
        this.view = view;
    }

    public SwipeRefreshLayout getSwipeLayout() {
        SwipeRefreshLayout swipeLayout;
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(context);

        swipeLayout.setColorScheme(COLOR_1, COLOR_2, COLOR_3, COLOR_4);

        return swipeLayout;
    }
}

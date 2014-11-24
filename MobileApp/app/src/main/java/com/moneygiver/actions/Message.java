package com.moneygiver.actions;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Szymon on 2014-11-23.
 */
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

package com.moneygiver.actions;

import android.content.Context;

import com.moneygiver.database.DatabaseAdapter;

/**
 * Created by Szymon on 2014-11-23.
 */
public class LoginChecker {
    private Context context;
    private DatabaseAdapter dbAdapter;
    public LoginChecker(Context context) {
        this.context = context;
        dbAdapter = new DatabaseAdapter(context);
    }

    public boolean isLoggedIn() {
        int isLogged = dbAdapter.getLoggedIn();
        if (isLogged == 1) {
            Message.message(context, "Zalogowany");
            return true;
        } else {
            Message.message(context, "Niezalogowany");
            return false;
        }
    }
}

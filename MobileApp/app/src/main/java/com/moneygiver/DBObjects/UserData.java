package com.moneygiver.DBObjects;

import java.util.HashMap;

/**
 * Created by Szymon on 2015-01-17.
 */
public class UserData {
    private String username;
    private int income;
    private HashMap<String, Double> monthly = new HashMap<String, Double>();


    public HashMap<String, Double> getMonthly() {
        return monthly;
    }

    public void setMonthly(HashMap<String, Double> monthly) {
        this.monthly = monthly;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

        public String getUsername() {
            return username;
        }

        public int getIncome() {
            return income;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setIncome(int income) {
            this.income = income;
        }
}

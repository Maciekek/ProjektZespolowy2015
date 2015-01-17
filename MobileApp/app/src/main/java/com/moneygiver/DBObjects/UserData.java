package com.moneygiver.DBObjects;

/**
 * Created by Szymon on 2015-01-17.
 */
public class UserData {
        private String username;
        private int income;

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

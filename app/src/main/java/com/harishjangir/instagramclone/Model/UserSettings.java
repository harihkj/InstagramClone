package com.harishjangir.instagramclone.Model;

/**
 * Created by Harish Jangir on 21-11-2017.
 */

public class UserSettings {

    GetInfo getInfo;
    User_Account_Settings user_account_settings;

    public UserSettings() {

    }

    public UserSettings(GetInfo getInfo, User_Account_Settings user_account_settings) {
        this.getInfo = getInfo;
        this.user_account_settings = user_account_settings;
    }

    public GetInfo getGetInfo() {
        return getInfo;
    }

    public void setGetInfo(GetInfo getInfo) {
        this.getInfo = getInfo;
    }

    public User_Account_Settings getUser_account_settings() {
        return user_account_settings;
    }

    public void setUser_account_settings(User_Account_Settings user_account_settings) {
        this.user_account_settings = user_account_settings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "getInfo=" + getInfo +
                ", user_account_settings=" + user_account_settings +
                '}';
    }
}

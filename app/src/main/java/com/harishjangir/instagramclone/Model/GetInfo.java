package com.harishjangir.instagramclone.Model;

/**
 * Created by Harish Jangir on 05-11-2017.
 */

public class GetInfo {

    private String email,user_id,username;
    long phone_number;


    public GetInfo() {
    }

    public GetInfo(String email, String user_id, String username, long phone_number) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "GetInfo{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", phone_number=" + phone_number +
                '}';
    }
}

package com.example.shopper;

public class UserInfo {
    private static UserInfo instance;
    private UserInfo() {

    }

    public static UserInfo getInstance(){
        if (instance == null){
            instance = new UserInfo();
            return instance;
        }
        return instance;
    }

    public String username;
    public String shopname;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getShopname(){return  shopname;}
    public void setShopname(String shopname){this.shopname=shopname;}

}
package com.example.shopper;

public class Shopper {
    private static Shopper shopper;
    private Shopper(){

    }

    public static Shopper getShopper() {
        return shopper;
    }

    public static void setShopper(Shopper shopper) {
        Shopper.shopper = shopper;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public static Shopper getInstance(){
        if (shopper == null){
            return new Shopper();
        }else {
            return shopper;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String shopName = "";
    String username;

}

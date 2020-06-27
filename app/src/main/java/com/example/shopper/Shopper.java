package com.example.shopper;

public class Shopper {
    private static Shopper shopper;
    private Shopper(){

    }
    public static Shopper getInstance(){
        if (shopper == null){
            return new Shopper();
        }else {
            return shopper;
        }
    }

    String shopName = "鸡公煲";

}

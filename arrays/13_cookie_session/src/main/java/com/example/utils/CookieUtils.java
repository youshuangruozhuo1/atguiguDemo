package com.example.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {


    public static Cookie getCookieByName(String name , Cookie[] cookies){
        if (name==null || cookies == null || cookies.length==0){
            return null;
        }
        for (Cookie cook : cookies) {
            if (name.equals(cook.getName())){
                return cook;
            }
        }
        return null;
    }
}

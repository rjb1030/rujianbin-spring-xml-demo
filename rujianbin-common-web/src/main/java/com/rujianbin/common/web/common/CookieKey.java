package com.rujianbin.common.web.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/4.
 */
public class CookieKey {

    public static final String cookie_user_key="ck_u";

    public static String getCookie(HttpServletRequest request, String cookieKey){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie c : cookies){
                if(cookieKey.equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }
}

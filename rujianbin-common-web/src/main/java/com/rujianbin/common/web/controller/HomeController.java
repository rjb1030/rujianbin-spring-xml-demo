package com.rujianbin.common.web.controller;

import com.rujianbin.principal.api.security.RjbSecurityUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("")
    public String login(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                System.out.println("cookie name="+cookie.getName()+",value="+cookie.getValue()+",domain="+cookie.getDomain());
            }
        }
        Object obj = request.getSession().getAttribute("userInfo");
        if(obj!=null){
            RjbSecurityUser rjbSecurityUser  = (RjbSecurityUser)obj;
            model.put("user",rjbSecurityUser.getName()+"("+rjbSecurityUser.getUsername()+")");
            model.put("authority",rjbSecurityUser.getAuthorities());
        }

        System.out.println("sessionId----->"+request.getSession().getId());
        return "home/home";
    }



    @RequestMapping("/chat-room")
    public String chatRoom(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "websocket/chat-room";
    }


}

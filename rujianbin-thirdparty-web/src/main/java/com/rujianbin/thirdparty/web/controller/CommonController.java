package com.rujianbin.thirdparty.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rujianbin@xinyunlian.com on 2017/7/6.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    //http://127.0.0.1:8080/rujianbin-thirdparty-web/common/index
    @RequestMapping("/index")
    public String login(ModelMap model,HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        System.out.println("code--->"+code);
        model.put("name","rujianbin-thirdparty-web");
        return "index";
    }

    @RequestMapping("/oauth2")
    public String oauth2(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                System.out.println("cookie name="+cookie.getName()+",value="+cookie.getValue()+",domain="+cookie.getDomain());
            }
        }
        model.put("client_id","client_rjb");
        model.put("grant_type","authorization_code");
        model.put("redirect_uri","http://127.0.0.1:8080/rujianbin-thirdparty-web/common/index");//这个跳转地址要和oauth_client_details的对应client的web_server_direct_uri一致
        model.put("response_type","code");
        return "redirect:http://127.0.0.1:8080/rujianbin-oauth2/oauth/authorize";
    }
}

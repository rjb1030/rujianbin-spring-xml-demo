package com.rujianbin.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/login")
public class LoginController {

    //http://127.0.0.1:8080/rujianbin-common-web/login
    @RequestMapping("")
    public String login(ModelMap model) {
        return "login/login";
    }



    @RequestMapping(value = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public String logout(ModelMap model) {
        System.out.println("成功退出。。。。");
        return "403";
    }
}

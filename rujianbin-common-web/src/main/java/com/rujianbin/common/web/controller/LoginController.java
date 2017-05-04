package com.rujianbin.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/4.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    //http://127.0.0.1:8080/rujianbin-common-web/login
    @RequestMapping("")
    public String login(ModelMap model) {
        return "login/login";
    }
}

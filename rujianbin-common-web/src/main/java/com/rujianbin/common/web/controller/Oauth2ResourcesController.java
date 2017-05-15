package com.rujianbin.common.web.controller;

import com.rujianbin.common.web.security.RjbSecurityUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/15.
 */
@Controller
@RequestMapping("/oauth2-resource")
public class Oauth2ResourcesController {

    /**
     * 先POST请求获取token
     * http://127.0.0.1:8080/rujianbin-oauth2/oauth/token?client_id=client_rjb&client_secret=123456&grant_type=client_credentials
     * 然后带上token访问资源
     * http://127.0.0.1:8080/rujianbin-common-web/oauth2-resource/resource1?access_token=
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("resource1")
    @ResponseBody
    public String resource1(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map<String,String> map = new HashMap<>();
        map.put("msg","资源服务器-返回资源 hello world!");
        return "资源服务器-返回资源 hello world!";
    }
}

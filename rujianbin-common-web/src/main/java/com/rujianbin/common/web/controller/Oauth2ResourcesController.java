package com.rujianbin.common.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.rujianbin.principal.api.entity.AuthorityEntity;
import com.rujianbin.principal.api.entity.UserEntity;
import com.rujianbin.principal.api.security.RjbSecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public List<UserEntity> resource1(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity current=null;
        if(authentication.getPrincipal() instanceof RjbSecurityUser){
            RjbSecurityUser rjbSecurityUser  = (RjbSecurityUser)authentication.getPrincipal();
            current = new UserEntity();
            current.setName(rjbSecurityUser.getName());
            current.setUsername(rjbSecurityUser.getUsername());
            current.setAuthorityEntityList(rjbSecurityUser.getAuthorityEntityList());
        }else{
            current = new UserEntity();
            current.setUsername((String)authentication.getPrincipal());
            List<AuthorityEntity> list = Lists.newArrayList();
            current.setAuthorityEntityList(list);
            for(GrantedAuthority authority : authentication.getAuthorities()){
                list.add(new AuthorityEntity(authority.getAuthority()));
            }
        }


//        UserEntity e1 = new UserEntity();
//        e1.setName("测试1");
//        e1.setUsername("测试1-username");
//        UserEntity e2 = new UserEntity();
//        e2.setName("测试2");
//        e2.setUsername("测试2-username");

        List list = Lists.newArrayList();
        if(current!=null){
            list.add(current);
        }
        return list;
    }

    @RequestMapping("resource2")
    @ResponseBody
    public List<String> resource2(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return Lists.newArrayList("oauth资源请求成功 resource2 hello world!","api rest success");
    }
}

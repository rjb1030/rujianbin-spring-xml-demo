package com.rujianbin.common.web.security;

import com.rujianbin.principal.api.security.MyWebAuthenticationDetails;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by 汝建斌 on 2017/4/11.
 */
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails)authentication.getDetails();
        System.out.println("客户自定义数据-验证码："+details.getCaptcha());
        return super.authenticate(authentication);
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    protected UserDetailsService getUserDetailsService() {
        return super.getUserDetailsService();
    }


}

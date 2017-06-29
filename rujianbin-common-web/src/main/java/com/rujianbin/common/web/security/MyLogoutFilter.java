package com.rujianbin.common.web.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by rujianbin@xinyunlian.com on 2017/6/29.
 */
public class MyLogoutFilter extends LogoutFilter {

    public MyLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,LogoutHandler... handlers){
        super(logoutSuccessHandler,handlers);
    }
}

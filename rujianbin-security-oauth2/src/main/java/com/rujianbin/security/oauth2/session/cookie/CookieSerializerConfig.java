package com.rujianbin.security.oauth2.session.cookie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created by rujianbin@xinyunlian.com on 2017/7/7.
 * 配置一个CookieSerializerConfig 使session的cookie在根目录，则不同context可以互通
 */
@Configuration
public class CookieSerializerConfig {

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookiePath("/");
        return cookieSerializer;
    }
}

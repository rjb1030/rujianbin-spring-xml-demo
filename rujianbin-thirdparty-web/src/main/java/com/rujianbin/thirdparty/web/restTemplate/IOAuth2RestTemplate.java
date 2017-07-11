package com.rujianbin.thirdparty.web.restTemplate;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/9.
 */
public interface IOAuth2RestTemplate {

    public OAuth2RestTemplate getTemplate(String code);
}

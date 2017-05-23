package com.rujianbin.security.oauth2.test;

import com.mchange.v1.util.ArrayUtils;
import com.rujianbin.security.oauth2.test.component.oauth2.IOAuth2RestTemplate;
import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/15.
 */

@ContextConfiguration({"classpath:applicationContext.xml","classpath:application-security.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TokenTest {

    @Autowired
    private IOAuth2RestTemplate oAuth2RestTemplate;

    @Value("${api.url:http://127.0.0.1:8080/rujianbin-common-web/oauth2-resource/resource1}")
    private String restApiUrl;

    @Test
    public void getToken(){

        OAuth2RestTemplate template = oAuth2RestTemplate.getTemplate();
        ParameterizedTypeReference<List<String>> typeRef = new ParameterizedTypeReference<List<String>>(){};

        ResponseEntity<List<String>> result = template.exchange(restApiUrl,
                HttpMethod.GET,
                new HttpEntity<String>(new HttpHeaders()),
                typeRef
                );

        System.out.println(Arrays.toString(result.getBody().toArray()));
    }

}

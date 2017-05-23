package com.rujianbin.security.oauth2.test.component.oauth2;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import org.apache.http.Consts;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/9.
 */
@Component
public class Oauth2RestTemplateImpl implements IOAuth2RestTemplate {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String split = "[,]|[ ]|[;]";

    private volatile ClientCredentialsResourceDetails details;

    @Value("${oauth2.identification.accessTokenUri:http://127.0.0.1:8080/rujianbin-oauth2/oauth/token}")
    private String accessTokenUri;
    @Value("${oauth2.identification.clientId:client_rjb}")
    private String clientId;
    @Value("${oauth2.identification.clientSecret:123456}")
    private String clientSecret;
    @Value("${oauth2.identification.scope:read,write}")
    private String scope;


    /**
     * 获取restTemplate
     * @return
     */
    @Override
    public OAuth2RestTemplate getTemplate(){
        ClientCredentialsResourceDetails resourceDetails = getDetails();
        OAuth2AccessToken token = getToken();
        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext(token));
        Long currentTimeMillis = System.currentTimeMillis();
        String sign = getSign(currentTimeMillis);
        filterHeader(template,sign,currentTimeMillis);
        return template;
    }

    /**
     * 获取ResourceDetails对象
     * @return
     */
    private ClientCredentialsResourceDetails getDetails(){
        if(details!=null){
            return details;
        }else{
            synchronized (Oauth2RestTemplateImpl.class){
                if(details==null){
                    details = new ClientCredentialsResourceDetails();
                    details.setAccessTokenUri(accessTokenUri);
                    details.setClientId(clientId);
                    details.setClientSecret(clientSecret);
                    details.setScope(Lists.newArrayList(scope.split(split)));
                    return details;
                }else{
                    return details;
                }
            }
        }
    }


    /**
     * 获取token
     * @return
     */
    private OAuth2AccessToken getToken(){
        ClientCredentialsResourceDetails resourceDetails = getDetails();
        ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resourceDetails, new DefaultAccessTokenRequest());
        logger.info("oauth2 get token: "+accessToken.getValue());
        return accessToken;
    }



    /**
     * 获取签名
     * @return
     */
    private String getSign(Long currentTimeMillis){
        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("appSecret",clientSecret));
        basicNameValuePairs.add(new BasicNameValuePair("sendTime",String.valueOf(currentTimeMillis)));
        Collections.sort(basicNameValuePairs, new Comparator<BasicNameValuePair>() {
            public int compare(BasicNameValuePair o1, BasicNameValuePair o2) {
                return o1.getName().compareTo(o2.getName());
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                return String.valueOf(this).equals(String.valueOf(obj));
            }
        });

        String str = URLEncodedUtils.format(basicNameValuePairs, Consts.UTF_8);
        return Hashing.md5().hashBytes(str.getBytes()).toString();
    }

    /**
     * 设置请求头
     * @param template
     * @param sign
     * @param currentTimeMillis
     */
    private void filterHeader(OAuth2RestTemplate template,final String sign,final Long currentTimeMillis){
        template.setInterceptors(Arrays.<ClientHttpRequestInterceptor>asList(new ClientHttpRequestInterceptor(){
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().add("Content-type", "application/json;charset=UTF-8");
                request.getHeaders().add("sign", sign);
                request.getHeaders().add("sendTime",String.valueOf(currentTimeMillis));
                return execution.execute(request, body);
            }
        }));
    }


}

package com.rujianbin.thirdparty.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rujianbin.principal.api.entity.UserEntity;
import com.rujianbin.thirdparty.web.restTemplate.IOAuth2RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rujianbin@xinyunlian.com on 2017/7/6.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private IOAuth2RestTemplate oAuth2RestTemplate;

    @Value("${oauth2.userInfo.uri:http://127.0.0.1:8080/rujianbin-common-web/oauth2-resource/resource1}")
    private String userInfoUri;

    //http://127.0.0.1:8080/rujianbin-thirdparty-web/common/index
    @RequestMapping("/index")
    public String login(ModelMap model,HttpServletRequest request, HttpServletResponse response) throws Exception{
        String code = request.getParameter("code");
        System.out.println("code--->"+code);

        model.put("name","rujianbin-thirdparty-web");
        OAuth2RestTemplate template = oAuth2RestTemplate.getTemplate(code);
        List<UserEntity> userList = getForObjectList(template,userInfoUri,new ParameterizedTypeReference<List<UserEntity>>(){});
        List<UserEntity> userList2 = getForObjectList(template,userInfoUri,new ParameterizedTypeReference<List<UserEntity>>(){});
        model.put("userinfo",new ObjectMapper().writeValueAsString(userList));
        return "index";
    }

    @RequestMapping("/oauth2")
    public String oauth2(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                System.out.println("cookie name="+cookie.getName()+",value="+cookie.getValue()+",domain="+cookie.getDomain());
            }
        }
        model.put("client_id","client_rjb");
        model.put("grant_type","authorization_code");
        model.put("redirect_uri","http://127.0.0.1:8080/rujianbin-thirdparty-web/common/index");//这个跳转地址要和oauth_client_details的对应client的web_server_direct_uri一致
        model.put("response_type","code");
        return "redirect:http://127.0.0.1:8080/rujianbin-oauth2/oauth/authorize";
    }


    private  <T> List<T> getForObjectList(OAuth2RestTemplate template,String url, ParameterizedTypeReference<List<T>> typeRef){
        ResponseEntity<List<T>> result = template.exchange(url,
                HttpMethod.GET,
                new HttpEntity<String>(new HttpHeaders()),
                typeRef);
        return result.getBody();
    }
}

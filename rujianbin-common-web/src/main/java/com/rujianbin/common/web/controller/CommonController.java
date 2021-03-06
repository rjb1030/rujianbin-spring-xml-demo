package com.rujianbin.common.web.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.rujianbin.common.web.util.RSAUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 汝建斌 on 2017/4/10.
 */

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private Producer captchaProducer = null;

    @RequestMapping("/403")
    public String _403(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "403";
    }

    @RequestMapping("/404")
    public String _404(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "404";
    }

    @RequestMapping("/500")
    public String _500(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "500";
    }

    @RequestMapping("/kaptcha")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println("******************验证码是: " + capText + "******************");
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    @RequestMapping("/publicKey")
    @ResponseBody
    public Map<String, String> publicKey(HttpServletRequest request){
        KeyPair keyPair = RSAUtils.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        HttpSession session = request.getSession();
        session.setAttribute(RSAUtils.PRIVATE_KEY_SESSION_ATTRIBUTE_NAME, privateKey);

        Map<String, String> data = new HashMap<String, String>();
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return data;
    }
}

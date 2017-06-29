package com.rujianbin.common.web.util;

import com.google.common.hash.Hashing;

/**
 * Created by rujianbin@xinyunlian.com on 2017/6/29.
 */
public class MD5Util {

    public static String md5(String str){
        return Hashing.md5().hashBytes(str.getBytes()).toString();
    }
}

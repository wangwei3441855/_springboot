package com.example.demo.utils;

import com.example.demo.configure.shiro.ShiroConfig;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

public class Utils {

    public static String pwdToHex(String pwd) {
        return new SimpleHash(ShiroConfig.HASH_ALGORITHM_NAME,
                pwd,
                null,
                ShiroConfig.HASH_ITERATIONS).toHex();
    }

    public static String createUUid(){
        return UUID.randomUUID().toString();
    }
}

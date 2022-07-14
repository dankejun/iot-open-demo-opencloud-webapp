package com.cloud.util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class SignUtil {
    /**
     * iot API请求的签名算法实现
     *
     * @param uri    路由地址字符串
     * @param params 参数k-v
     * @param appKey app秘钥
     * @return String
     */
    public static <T> String requestSign(String uri, MultiValueMap<String, T> params, String appKey) {
        List<String> keyArr = new ArrayList<>();
        params.keySet().forEach(key -> {
            keyArr.add(key);
        });
        Collections.sort(keyArr);
        List<String> paramArr = new ArrayList<>();
        keyArr.forEach(keyName -> {
            paramArr.add(keyName + "=" + params.get(keyName).get(0));
        });
        Joiner joiner = Joiner.on("&");
        String shaSource = uri + joiner.join(paramArr) + appKey;
        return getSha256Str(shaSource);
    }

    /**
     * 计算字符串的hash值，使用sha-256算法
     *
     * @param str 待计算hash值的字符串
     * @return String
     */
    private static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Charsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * byte转16进制字符串
     *
     * @param bytes 待转换的字节数组
     * @return String
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}

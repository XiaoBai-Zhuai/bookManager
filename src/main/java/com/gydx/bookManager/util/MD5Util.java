package com.gydx.bookManager.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

@Component
public class MD5Util {

    public static String getMD5(String context) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(context.getBytes());
        return md5DigestAsHex;
    }

    public static String getMD52(String context) {
        StringBuffer sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(context.getBytes());
            byte[] digest = md.digest();
            int i;
            sb = new StringBuffer();
            for (int j = 0; j < digest.length; j++) {
                i = digest[j];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

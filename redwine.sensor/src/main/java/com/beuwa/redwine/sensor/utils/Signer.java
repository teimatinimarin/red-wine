package com.beuwa.redwine.sensor.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Signer {
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "HmacSHA256";
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    @Inject
    private PropertiesFacade propertiesFacade;

    public String encode(String verb, String path, long expires, String data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String key = propertiesFacade.getApiSecret();

        byte[] bytes = key.getBytes(CHARSET);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, ALGORITHM);

        Mac sha256 = Mac.getInstance(ALGORITHM);
        sha256.init(secretKeySpec);

        String content = String.format("%s%s%d%s", verb, path, expires, data);
        byte[] signature = sha256.doFinal(content.getBytes(CHARSET));

        return bytesToHex(signature);
    }


    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

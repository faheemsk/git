/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

/**
 *
 * @author hpasupuleti
 */
import com.optum.oss.exception.AppException;
import java.security.Key;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class EncryptDecrypt {

    private static final String ALGO = "AES";
    //private static final String ALGO = "AES/CBC/PKCS5Padding";
//    private static final byte[] keyValue
//            = new byte[]{'O', 'P', 'T', 'U', 'M', 'I', 'R',
//                'M', '@', '!', 'r', 'm', '@', 'A', '$', '$'};

    @Value("{application-pass-key}")
    private String applicationKey;

    public String encrypt(String data) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = new Base64(true).encodeToString(encVal);
            return encryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Encrypting:" + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new Base64().decode(encryptedData.getBytes());
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Decrypting:" + e.getMessage());
        }
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(getKeyValueBytes(), ALGO);
        return key;
    }

    private byte[] getKeyValueBytes() {
        byte[] hKeyValue = applicationKey.getBytes();
        hKeyValue = Arrays.copyOf(hKeyValue, 16);
        return hKeyValue;
    }
}

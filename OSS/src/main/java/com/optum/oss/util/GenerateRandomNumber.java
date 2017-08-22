/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author srkora
 */
@Component
public class GenerateRandomNumber {

    public String generateToken() {
        String token = RandomStringUtils.randomAlphanumeric(10);
        return token;
    }

    public String generateNumericToken() {
        String token = RandomStringUtils.randomNumeric(8);
        return token;
    }
    
    public String generatePasswordToken() {
        String token = RandomStringUtils.randomAscii(10);
        return token;
    }
    
    public String generateResPasswordToken() {
        String[] pswdKey = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "#", "%", "!", "$","0","1","2","3","4","5","6","7","8","9"};

        Random random = new Random();
        String pswd = "";
        for(int i=0;i<10;i++){
            pswd= pswd+ pswdKey[random.nextInt(66)];
        }
        return pswd;

    }
    
    
    public int generateRandomFromList()
    {
        int [] arr = {0,1,2};
        Random random = new Random();
        int randomInt = random.nextInt(arr.length);
        return randomInt;
    }
}

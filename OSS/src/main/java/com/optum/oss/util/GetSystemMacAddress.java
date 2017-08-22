/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.exception.AppException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class GetSystemMacAddress {

    private static final Logger logger = Logger.getLogger(GetSystemMacAddress.class);
    
    public String GetAddress(String addressType,HttpServletRequest request) {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
           
            if (ip == null) {
                ip = request.getRemoteAddr();
            }
        return ip;
    }
    
//    public String GetAddress(String addressType, HttpServletRequest request) {
//        String address = "";
//        InetAddress lanIp = null;
//        try {
//            String ipAddress = null;
//
////            Enumeration<NetworkInterface> net = null;
////            net = NetworkInterface.getNetworkInterfaces();
////            while (net.hasMoreElements()) {
////                NetworkInterface element = net.nextElement();
////                Enumeration<InetAddress> addresses = element.getInetAddresses();
////                while (addresses.hasMoreElements()) {
////                    InetAddress ip = addresses.nextElement();
////                    if (ip instanceof Inet4Address) {
////                        if (ip.isSiteLocalAddress()) {
////                            ipAddress = ip.getHostAddress();
////                            lanIp = InetAddress.getByName(ipAddress);
////                        }
////                        else
////                        {
////                            ipAddress = request.getRemoteAddr();
////                            lanIp = InetAddress.getByName(ipAddress);
////                        }
////                    }
////                }
////            }
////            if (lanIp == null) {
////                return null;
////            }
//            if (addressType.equals("ip")) {
//                address = ip;//lanIp.toString().replaceAll("^/+", "");
//            } else if (addressType.equals("mac")) {
//                address = GetMacAddress(lanIp);
//            } else {
//                throw new Exception("Specify \"ip\" or \"mac\"");
//            }
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return address;
//
//    }

    private String GetMacAddress(InetAddress ip) {
        String address = "";
        try {

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (null != network) {
                byte[] mac = network.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                address = sb.toString();
            }
        } catch (SocketException e) {

            e.printStackTrace();

        }

        return address;
    }
    
    public String getMac(final HttpServletRequest request) throws AppException
    {
        String macAddress = "";
        try {
            String hostname = request.getRemoteHost();
            String remoteAddress = request.getRemoteAddr();
            InetAddress ip = InetAddress.getByName(remoteAddress);
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
            logger.error("UnknownHostException occurred : getMac:" + ex.getMessage());
            throw new AppException("UnknownHostException Occured while Executing the "
                    + "getMac() :: " + ex.getMessage());
        } catch (SocketException ex) {
            ex.printStackTrace();
            logger.error("SocketException occurred : getMac:" + ex.getMessage());
            throw new AppException("SocketException Occured while Executing the "
                    + "getMac() :: " + ex.getMessage());
        }
        return macAddress;
    }
    

}

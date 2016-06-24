package com.example.appchat;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Md5 {
	
	public static String encrypt(String cadena) { 
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
        s.setPassword("uniquekey"); 
        return s.encrypt(cadena); 
    } 

    public static String decrypt(String cadena) { 
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
        s.setPassword("uniquekey"); 
        String devuelve = ""; 
        try { 
        devuelve = s.decrypt(cadena); 
        } catch (Exception e) { 
        } 
        return devuelve; 
    }
}

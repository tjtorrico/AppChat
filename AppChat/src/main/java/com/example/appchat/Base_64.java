package com.example.appchat;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Base_64 {

	public String encodeImage(Bitmap bm) {
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		  bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object   
		  byte[] b = baos.toByteArray();
		  return Base64.encodeToString(b, Base64.DEFAULT);   
	}
	    
	public Bitmap decodeImage(String imageDataString) {
		  byte[] aux = Base64.decode(imageDataString,Base64.DEFAULT);
		  Bitmap decodedByte = BitmapFactory.decodeByteArray(aux, 0, aux.length); 
		  return decodedByte;
	}
}

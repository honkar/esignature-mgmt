package com.calpers.esignaturemgmt.helper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class ImageDecoder {
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

	public static boolean decodeToImage(String imageString) {
		// decode Base64 String to image
		FileOutputStream fos;
		boolean status;
		try {
			fos = new FileOutputStream(uploadDirectory+"/testSign.png");
			byte byteArray[] = Base64.decodeBase64(imageString);
			fos.write(byteArray);
			fos.close();
			status = true;
		  } 
		catch (IOException e) {
			status =false;
			e.printStackTrace();
		} 

		return status;
	}
}

package com.calpers.esignaturemgmt.helper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class ImageDecoder {
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";

	public boolean decodeToImage(String imageString, String fileName) {
		// decode Base64 String to image (signature)
		FileOutputStream fos;
		boolean status;
		try { // Save signature to 'upload' folder
			fos = new FileOutputStream(uploadDirectory+"/"+fileName);
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

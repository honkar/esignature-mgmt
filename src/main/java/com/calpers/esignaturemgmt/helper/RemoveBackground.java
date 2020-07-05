package com.calpers.esignaturemgmt.helper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RemoveBackground {
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	
	public boolean removeBackground(String fileName) {
		boolean status;
		try {
		
		    File inFile = new File(uploadDirectory, fileName);
		   
		    BufferedImage signatureImage = ImageIO.read(inFile);
 
		    // Get the color of the first pixel. (Background color)
		    int backgroundColor = signatureImage.getRGB(0, 0);
		    
		    if (backgroundColor != 0) { // image does not have transparent background

		    	Image image = setBackgroundTransparent(signatureImage, new Color(backgroundColor));

		    	BufferedImage signBackgroundTransparent = imageToBufferedImage(image);

		    	File out = new File(uploadDirectory, fileName);
		    	ImageIO.write(signBackgroundTransparent, "PNG", out);
		    }
	        status = true;
		} catch(IOException e) {
			status = false;
			e.printStackTrace();
		}
	        
		return status;
	}
	
	 private static BufferedImage imageToBufferedImage(Image image) {

	        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = bufferedImage.createGraphics();
	        g2.drawImage(image, 0, 0, null);
	        g2.dispose();

	        return bufferedImage;

	 }

	 public static Image setBackgroundTransparent(BufferedImage im, final Color color) {
	        ImageFilter filter = new RGBImageFilter() {

	        	// Get the background color in the image and set its Alpha bits to opaque
	            public long markerRGB = color.getRGB() | 0xFF000000;

	            public final int filterRGB(int x, int y, int rgb) {
	                if ((rgb | 0xFF000000) >= markerRGB - 0xF0F0F) {  // Pixel color same or lighter than background
	                    //Set alpha bits to zero (transparent)
	                    return 0x00FFFFFF & rgb;
	                } else { // Pixel color darker than background 
	                    // nothing to do
	                    return rgb;
	                }
	            }
	        };

	        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	        return Toolkit.getDefaultToolkit().createImage(ip);
	  }

}

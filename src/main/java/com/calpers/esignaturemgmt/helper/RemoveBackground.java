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
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	
	public boolean removeBackground(String fileName) {
		boolean status;
		try {
		
		    File inFile = new File(uploadDirectory, fileName);
		   
		    BufferedImage source = ImageIO.read(inFile);
 
		    int color = source.getRGB(0, 0);
		    
		    if (color != 0) { // image does not have transparent background

		    	Image image = makeColorTransparent(source, new Color(color));

		    	BufferedImage transparent = imageToBufferedImage(image);

		    	File out = new File(uploadDirectory, "testSign.png");
		    	ImageIO.write(transparent, "PNG", out);
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

	 public static Image makeColorTransparent(BufferedImage im, final Color color) {
	        ImageFilter filter = new RGBImageFilter() {

	            // the color we are looking for... Alpha bits are set to opaque
	            public long markerRGB = color.getRGB() | 0xFF000000;

	            public final int filterRGB(int x, int y, int rgb) {
	                if ((rgb | 0xFF000000) >= markerRGB - 0xB0B0B) {
	                    // Mark the alpha bits as zero - transparent
	                    return 0x00FFFFFF & rgb;
	                } else {
	                    // nothing to do
	                    return rgb;
	                }
	            }
	        };

	        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	        return Toolkit.getDefaultToolkit().createImage(ip);
	  }

}

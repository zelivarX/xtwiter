package com.x.helper;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.x.programlogic.User;
 
/*
 * @author mkyong
 *
 */
public class ImageTest { 
	
	public static BufferedImage resize(BufferedImage img, int w, int h) {
		try{
			
			BufferedImage originalImage = img;
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	 
			return resizeImage(originalImage, type, w, h);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static BufferedImage resize(BufferedImage img) {
		try{
			BufferedImage originalImage = img;
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	 
			return resizeImage(originalImage, type, User.pW, User.pH);
	 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static final int offline = 0, online = 1;
	
	
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int w, int h){
		BufferedImage resizedImage = new BufferedImage(w, h, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, w, h, null);
		g.dispose();
	 
		return resizedImage;
    }
 
    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
 
	BufferedImage resizedImage = new BufferedImage(User.pW, User.pH, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, User.pW, User.pH, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
 
	return resizedImage;
    }	
}
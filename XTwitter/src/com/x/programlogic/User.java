package com.x.programlogic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.x.helper.ImageTest;


public class User {
	int bWidth = 20, bHeight = 40;
	int[] px;
	
	int x = 0, y = 0;
	ImageIcon i;
	public static int pW = 30, pH = 30;
	
	
	List<String> hist ;
	
	static final int ow = 15, oh = 15;
	
	BufferedImage picture;
	
	ImageIcon prev;
	ImageIcon pic;
	
	int id;
	String name;
	String about;
	boolean stat = false;
	
	User(byte[] bytes) {
		try {
			if(bytes == null) {
				System.exit(0);
			}
			
			picture = ImageIO.read(new ByteArrayInputStream(bytes));
			
			prev = new ImageIcon(ImageTest.resize(picture));
			pic = new ImageIcon(ImageTest.resize(picture, Constants.PROFILE_PIC_W, Constants.PROFILE_PIC_H));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}
	
	User() {
		try {
			picture = ImageIO.read(new File("/home/velizar/bg2.png"));
			
			prev = new ImageIcon(ImageTest.resize(picture));
			pic = new ImageIcon(ImageTest.resize(picture, Constants.PROFILE_PIC_W, Constants.PROFILE_PIC_H));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}

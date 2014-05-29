package com.x.programlogic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.x.helper.ImageTest;

public class UserFollowerFollowedPOJO {
	
	private int id;
	private String fname,lname;
	private String followedByMe;
	private ImageIcon pic;
	
	
	public String getFname() {
		return fname;
	}
	
	public String getFollowedByMe() {
		return followedByMe;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLname() {
		return lname;
	}
	
	public ImageIcon getPic() {
		return pic;
	}
	
	
	public UserFollowerFollowedPOJO(int id, String fname, String lname, String followedByMe) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.followedByMe = followedByMe;
		
		BufferedImage img = null;
		try {
			
			if(pic == null) {
				img = ImageIO.read(new File("/home/velizar/bg2.png"));
			}
			
			this.pic = new ImageIcon(ImageTest.resize(img, TweetPOJO.PICW, TweetPOJO.PICH));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

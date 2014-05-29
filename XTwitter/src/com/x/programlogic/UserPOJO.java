package com.x.programlogic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.x.helper.ImageTest;

public class UserPOJO {
	
	public static final int PICW = 130;
	public static final int PICH = 130;
	
	
	private int uid;
	private ImageIcon pic;
	private boolean isFollowed;
	private String names;
	private TweetPOJO[] tweets;
	private int TWEETS_N;
	
	public String getNames() {
		return names;
	}
	
	public ImageIcon getPic() {
		return pic;
	}
	
	public TweetPOJO[] getTweets() {
		return tweets;
	}
	
	public int getTWEETS_N() {
		return TWEETS_N;
	}
	
	public int getUid() {
		return uid;
	}
	
	public UserPOJO(int id, String names, boolean isFollowed, byte[] pic, TweetPOJO[] tweets) {
		this.uid = id;
		this.names = names;
		this.setFollowed(isFollowed);
		this.tweets = tweets;
		
		if(tweets == null) TWEETS_N = 0;
		else TWEETS_N = tweets.length;
		
		BufferedImage img;
		try {
			
			if(pic == null) {
				System.out.println("nulll1");
				img = ImageIO.read(new File("/home/velizar/bg2.png"));
			} else {
				img = ImageIO.read(new ByteArrayInputStream(pic));
			}
			
			this.pic = new ImageIcon(ImageTest.resize(img, PICW, PICH));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	
}

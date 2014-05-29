package com.x.programlogic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.x.helper.ImageTest;

public class TweetPOJO {
	
	public static final int PICW = 30;
	public static final int PICH = 30;
	
	
	private int uid;
	private int tweet_id;
	private ImageIcon pic;
	private Date send;
	private String text;
	private String names;
	private int retweets;
	private boolean ismine;
	
	public String getNames() {
		return names;
	}
	
	public ImageIcon getPic() {
		return pic;
	}
	
	public int getRetweets() {
		return retweets;
	}
	
	public Date getSend() {
		return send;
	}
	
	public String getText() {
		return text;
	}
	
	public int getTweet_id() {
		return tweet_id;
	}
	
	public int getUid() {
		return uid;
	}
	
	
	public TweetPOJO(int id, String names, String text, byte[] pic, int retweets, Date send, int tweet_id) {
		this(id, names, text, pic, retweets, (new Random().nextBoolean()), send, tweet_id);
	}
	
	public TweetPOJO(int id, String names, String text, byte[] pic, int retweets, boolean ismine, Date send, int tweet_id) {
		this.tweet_id = tweet_id;
		this.uid = id;
		this.names = names;
		this.text = text;
		this.retweets = retweets;
		this.setIsmine(ismine);
		this.send = send;
		
		BufferedImage img;
		try {
			
			if(pic == null) {
				img = ImageIO.read(new File("/home/velizar/bg2.png")); // example
			} else {
				img = ImageIO.read(new ByteArrayInputStream(pic));
			}
			
			this.pic = new ImageIcon(ImageTest.resize(img, PICW, PICH));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean isIsmine() {
		return ismine;
	}

	public void setIsmine(boolean ismine) {
		this.ismine = ismine;
	}
	
}

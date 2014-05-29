package com.X.Twitter;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.x.helper.PostPanel;

public class Wall extends JPanel {

	private UserProfilePanel up;
	
	public Wall(UserProfilePanel up) {
		this.up = up;
		this.init();
	}
	
	private void init() {
		
		this.setBounds(up.cont.getX(), up.cont.getY(), up.cont.getWidth(), up.cont.getHeight());
		this.setBackground(Color.white);
		
		this.setBorder(null);
		
		if(XTwiter.currUser != null)
			this.f1(XTwiter.currUser.getTWEETS_N());
	}
	
	JPanel p = new JPanel();
	
	void f1(int N) {
		 this.setLayout(null);
		    
		 	PostPanel[] friends = new PostPanel[N];
		 
		    p.removeAll();
		    this.remove(p);
		    p.setLayout(new GridLayout(N, 1, 0, 0));
		   
			for(int i = 0; i < friends.length; i++) {
				  friends[i] = new PostPanel(XTwiter.currUser.getTweets()[i]);
				  p.add(friends[i]);
			  }
			
			  p.setBounds(0, 0, 400, N * 105);
			  this.setVisible(false);
			  this.add(p);
			  this.setVisible(true);
	}

}

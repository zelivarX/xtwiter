package com.X.Twitter;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import com.x.helper.PostPanel;
import com.x.helper.UserFollowedFollowerPanel;
import com.x.programlogic.UserFollowerFollowedPOJO;

public class MDialogContent extends JPanel {

	private MDialog up;
	
	public MDialogContent(MDialog up, List<UserFollowerFollowedPOJO> usersList) {
		this.up = up;
		this.usersList = usersList;
		this.init();
	}
	
	private void init() {
		
		this.setBounds(0, 0, up.getWidth(), up.getHeight());
		this.setBackground(UserProfilePanel.BG);
		
		this.setBorder(null);
		
		this.populate(usersList.size());
	}
	List<UserFollowerFollowedPOJO> usersList;
	JPanel p = new JPanel();
	
	private void populate(int N) {
		 this.setLayout(null);
		    
		 UserFollowedFollowerPanel[] friends = new UserFollowedFollowerPanel[N];
		 
		    p.removeAll();
		    this.remove(p);
		    p.setLayout(new GridLayout(N, 1, 0, 0));
		   
			for(int i = 0; i < friends.length; i++) {
				  friends[i] = new UserFollowedFollowerPanel(usersList.get(i));
				  p.add(friends[i]);
			  }
			
			  p.setBounds(0, 0, 400, N * 42);
			  this.setVisible(false);
			  this.add(p);
			  this.setVisible(true);
			  
			  
	}
	

}


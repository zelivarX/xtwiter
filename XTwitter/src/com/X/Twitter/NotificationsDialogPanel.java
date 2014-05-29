package com.X.Twitter;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import com.x.helper.NotificationPanel;
import com.x.helper.PostPanel;
import com.x.helper.UserFollowedFollowerPanel;
import com.x.programlogic.NotificationPOJO;

public class NotificationsDialogPanel extends JPanel {

	private MDialog up;
	
	public NotificationsDialogPanel(MDialog up, List<NotificationPOJO> notifList) {
		this.up = up;
		this.notifList = notifList;
		this.init();
	}
	
	private void init() {
		
		this.setBounds(0, 0, up.getWidth(), up.getHeight());
		this.setBackground(UserProfilePanel.BG);
		
		this.setBorder(null);
		
		this.populate(notifList.size());
	}
	
	List<NotificationPOJO> notifList;
	JPanel p = new JPanel();
	
	private void populate(int N) {
		 this.setLayout(null);
		    
		 NotificationPanel[] friends = new NotificationPanel[N];
		 
		    p.removeAll();
		    this.remove(p);
		    p.setLayout(new GridLayout(N, 1, 0, 0));
		   
			for(int i = 0; i < friends.length; i++) {
				  friends[i] = new NotificationPanel(notifList.get(i));
				  p.add(friends[i]);
			  }
			
			  p.setBounds(0, 0, 400, N * 42);
			  this.setVisible(false);
			  this.add(p);
			  this.setVisible(true);
	}

}


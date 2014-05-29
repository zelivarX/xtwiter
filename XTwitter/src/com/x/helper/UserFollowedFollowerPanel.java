package com.x.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.X.Twitter.XTwiter;
import com.x.programlogic.UserFollowerFollowedPOJO;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;


public class UserFollowedFollowerPanel extends JPanel implements ActionListener {
	
	static final int ow = 15, oh = 15;
	public static final int PANEL_WIDTH = 400;
	
	UserFollowerFollowedPOJO user;

	JLabel picl;
	JButton names;
	JButton follow;

	public static Color tweetColor = new Color(0xD5E8F5);
	
	public UserFollowedFollowerPanel(final UserFollowerFollowedPOJO user) {
		this.user = user;
		
		int h = 40;
		
		
		setMinimumSize(new Dimension(PANEL_WIDTH, h));
        setMaximumSize(new Dimension(PANEL_WIDTH, h));
        setPreferredSize(new Dimension(PANEL_WIDTH, h));
		
		
		setBorder(null);

		picl = new JLabel();
		picl.setIcon(user.getPic());
		
		picl.setBounds(2, 2, 20, 20);
		
		this.setBorder(BorderFactory.createLineBorder(new Color(0x666662), 1));
		
		
		this.setBackground(tweetColor);
		
        FlowLayout f = new FlowLayout();
        f.setAlignment(FlowLayout.LEFT);
        this.setLayout(f);
        names = new JButton(this.user.getFname());
        names.setForeground(Color.blue);
        this.add(picl);
        this.add(names);
        
        names.setBorder(null);
        names.setBackground(tweetColor);
        names.addActionListener(this);
        
        
        follow = new JButton(user.getFollowedByMe());
        
        if(!user.getFollowedByMe().equals("ME")) {
        	JPanel nil = new JPanel();
        	Dimension dim = new Dimension(400 - 
            		picl.getMaximumSize().width - 
            		follow.getMaximumSize().width - 
            		names.getMaximumSize().width
            		,
        			names.getMaximumSize().height);
        	nil.setMaximumSize(dim);
        	nil.setMinimumSize(dim);
        	nil.setPreferredSize(dim);
        	nil.setBackground(getBackground());
        	this.add(nil);
        	
        	follow.setBackground(new Color(0xA9C6CF));
        	follow.setBorder(null);
        	this.add(follow);
        	follow.addActionListener(this);
        }
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(names)) {
			XTwiter.cmds.add(new Message(MessageType.show_user_profile, user.getId()));
		} else if(e.getSource().equals(follow)) {
			String fol = (follow.getText().equals("follow")) ? "fol" : "unfol";
			if(follow.getText().equals("follow")) follow.setText("unfollow");
			else follow.setText("follow");
			
			XTwiter.cmds.add(new Message(MessageType.follow, fol, user.getId()));
		}
	}
}


package com.x.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.X.Twitter.UserProfilePanel;
import com.X.Twitter.XTwiter;
import com.x.programlogic.NotificationPOJO;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;

public class NotificationPanel extends JPanel implements ActionListener, MouseListener {

	
	NotificationPOJO notification;

	JButton names;
	JLabel content;
	JButton delete;
	Color notSeen = Color.lightGray;

	
	public static Color tweetColor = new Color(0xD5E8F5);
	
	public NotificationPanel(final NotificationPOJO notification) {
		this.notification = notification;

		setBorder(null);   
		
		this.setBackground(UserProfilePanel.BG);
		this.setBackground(Color.BLUE);
		
		this.setBorder(BorderFactory.createLineBorder(new Color(0x666662), 1));
		int h = 40;
		int w = 400;
		this.setMinimumSize(new Dimension(w, h));
		this.setMaximumSize(new Dimension(w, h));
		this.setPreferredSize(new Dimension(w, h));
		
		
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEADING);
		this.setLayout(fl);
		
		names = new JButton(notification.getNames());
		names.setForeground(Color.blue);
        names.setBorder(null);
        
        names.addActionListener(this);
        this.add(names);
		
		JTextArea content = new JTextArea();
        content.setEditable(false);
        
        
		
        this.add(content);
		
		delete = new JButton("delete");
    	delete.setBackground(new Color(0xA9C6CF));
    	
    	Dimension dim = new Dimension(400 - 20 - names.getMaximumSize().width - delete.getMaximumSize().width ,
    			names.getMaximumSize().height);
    	content.setMaximumSize(dim);
    	content.setMinimumSize(dim);
    	content.setPreferredSize(dim);
    	content.setText(notification.getContent());
    	
    	this.add(content);
    	this.add(delete);
    	delete.addActionListener(this);
    	
    	if(!notification.isSeen()) {
    		this.setBackground(tweetColor);
    		names.setBackground(tweetColor);
    		content.setBackground(tweetColor);
    		
    	}
    	else {
    		this.setBackground(notSeen);
    		names.setBackground(notSeen);
    		content.setBackground(notSeen);
    	}
    	this.addMouseListener(this);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(names)) {
			XTwiter.cmds.add(new Message(MessageType.show_user_profile, notification.getUid()));
		} else if(e.getSource().equals(delete)) {
			XTwiter.cmds.add(new Message(MessageType.delete_notification, notification.getNid()));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

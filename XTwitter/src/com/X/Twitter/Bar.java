package com.X.Twitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.x.programlogic.Constants;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;

public class Bar extends JPanel implements MouseListener, ActionListener {
	
	JButton names;
	JButton logout;
	JButton ser;
	public JTextField search;
	public static final Color barColor = new Color(0x4B4D42);
	public static final int barHeight = 30;
	
	public JLabel notif;
	
	public Bar() {
		this.init();
	}
	
	private void init() {
		this.setBackground(barColor);
		this.setBounds(0, 0, XTwiter.width, Bar.barHeight);
		this.setLayout(null);
		names = new JButton(Constants.APPNAME);
		names.setForeground(Color.white);
		names.setFont(new Font("Bauhaus 93", 0, 15));
		names.setBorder(null);
		names.setBackground(barColor);
		names.setBounds(5, 3, 70, 25);
		
		
		notif = new JLabel("12");
		notif.setForeground(Color.red);
		//notif.setBackground(Color.);
		notif.setBounds(80, 0, 40, 20);
		notif.addMouseListener(this);
		
		search = new JTextField(20);
		search.setBounds(200, 3, 200, 20);
		search.setText("Search...");
		search.setForeground(Color.gray);
		search.setBorder(null);
		search.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String s = search.getText();
					ser(s);
				}
			}

			
		});
		search.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				if(search.getText().trim().equals("Search...")) {
					search.setText("");
					search.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(search.getText().trim().equals("")) {
					search.setText("Search...");
					search.setForeground(Color.gray);
				}
			}
			
		});
		
		logout = new JButton("Logout");
		logout.setBorder(null);
		logout.setBackground(new Color(0x8E969C));
		logout.setBounds(XTwiter.width - 85, 5, 75, 20);
		
		ser = new JButton("Search");
		ser.setBounds(405, 3, 80, 20);
		ser.setBorder(null);
		ser.setBackground(new Color(0x8E969C));
		
		names.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				XTwiter.cmds.add(new Message(MessageType.show_user_profile, XTwiter.MYID));
			}
			
		});
		logout.addActionListener(this);
		ser.addMouseListener(this);
		ser.addActionListener(this);
		logout.addMouseListener(this);
		names.addMouseListener(this);
		
		this.add(names);
		this.add(search);
		this.add(ser);
		this.add(logout);
		this.add(notif);
		//this.add(new JLabel("Logout"), FlowLayout.RIGHT);
	}
	
	private Color preCol;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(notif)) {
			XTwiter.cmds.add(new Message(MessageType.show_notifications, XTwiter.MYID));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			
			if(b.equals(names)) {
				preCol = b.getForeground();
				b.setForeground(UserProfilePanel.BG);
			}else {
				preCol = b.getBackground();
				b.setBackground(UserProfilePanel.BG);
			}
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			
			if(b.equals(names)) {
				b.setForeground(preCol);
			}else {
				b.setBackground(preCol);
			}
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void ser(String s) {
		search.setText("Search...");
		search.setForeground(Color.gray);
		
		s = s.trim();
		
		XTwiter.cmds.add(new Message(MessageType.search, s));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ser)) {
			String s = search.getText();
			ser(s);
			
		}
		else if(e.getSource().equals(logout)) {
			XTwiter.cmds.add(new Message(MessageType.logout));
		}
	}
	
}

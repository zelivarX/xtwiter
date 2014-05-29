package com.X.Twitter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;


public class LoginP extends JPanel implements KeyListener, ActionListener {
	
	private JLabel acl;
	private JLabel pal;
	private JTextField acc;
	private JPasswordField pass;
	private JButton submit;
	private JButton register;
	
	public static final int W = 300;
	public static final int H = 250;
	public static final Dimension S = new Dimension(W, H);
	
	public static int loged = 0;
	
	private XTwiter xs;
	
	public LoginP(XTwiter xs) {
		this.xs = xs;
		this.setLayout(null);
		this.setBackground(UserProfilePanel.BG);
		this.init();
		
	}
	
	private void init() {
		int tw = 170;
		int lw = 85;
		
		acl = new JLabel("Username: ");
		pal = new JLabel("Password: ");
		
		acl.setBounds( (int) S.getWidth()  - lw >> 1, 30, lw, 25);
		this.add(acl);
		
		pal.setBounds((int) S.getWidth()  - lw >> 1, 90, lw, 25);
		this.add(pal);
		
		
		
		acc = new JTextField();
		
		acc.addKeyListener(this);
		
		acc.setBounds((int) S.getWidth()  - tw >> 1, 55, tw, 25);
		this.add(acc);
		
		pass = new JPasswordField();
		pass.setEchoChar('*');
		
		pass.addKeyListener(this);
		
		pass.setBounds((int) S.getWidth()  - tw >> 1, 115, tw, 25);
		this.add(pass);
		
		submit = new JButton("Login");
		register = new JButton("Register");
		
		submit.addActionListener(this);
		register.addActionListener(this);
		
		submit.setBounds((W >> 1) - (70 << 1) + (70 >> 1), 155, 100, 30);
		this.add(submit);
		register.setBounds((W >> 1), 155, 100, 30);
		this.add(register);
		
		acl.setForeground(Register.fore);
		pal.setForeground(Register.fore);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.submit)) {
			String s = pass.getText();
			pass.setText("");
			login(s);
		} else {
			pass.setText("");
			xs.show(XTwiter.REG);
		}
	}
	
	private void login(String pass) {
		String ac = acc.getText();
		
		if(ac.trim().equals("") || pass.trim().equals("")) {
			warn("Invalid username or password");
			return;
		}
		
		if(!XTwiter.socket.isset()) {
			switch(XTwiter.socket.setUp()) {
			case 1:
				warn("Unnable to connect to server.");
				return;
			case 2:
				warn("Cannot find server.");
				return;
			}
		}
		
		XTwiter.cmds.add(new Message(MessageType.login, ac, pass));
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 13 || e.getKeyChar() == '\n' || e.getKeyChar() == '\r') {
			String s = pass.getText();
			pass.setText("");
			//login(acc.getText(), s);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	@SuppressWarnings("static-access")
	private void warn(String s) {
		UIManager UI = new UIManager();
		UI.put("OptionPane.messageForeground", Color.white);
		UI.put("OptionPane.foreground", Color.white);
		UI.put("OptionPane.background", UserProfilePanel.BG);
		UI.put("Panel.background", UserProfilePanel.BG);

		JOptionPane.showMessageDialog(null,s,"Login error", JOptionPane.WARNING_MESSAGE);
	}
	/*
	private void login(String un, String pas) {
		
		if(!game22.th.isset()) {
			switch(game22.th.setup()) {
			case 1:
				warn("Cannot find server.");
				return;
			case 2:
				warn("Unnable to connect to server.");
				return;
			}
		}
		
			if(un == null || pass == null ||
				un.equals("") || pas.equals("")) {
				
				warn("Password or username field is left blank.");
			} else {
				switch(game22.th.send("log", un.toLowerCase(), pas.toLowerCase())) {
				case 1:
					warn("successfuly logedin.");
					game22.frame.setSize(game22.size);
					game22.frame.setLocationRelativeTo(null);
					game22.cl.show(game22.p, "play");
					game22.th.start();
					
					/*game22.begin(mw.ss);
					mw.dispose(); ///
					break;
				case 2:
					warn("Wrong username or password.");
				}
			}
			
			
			
		}*/
}

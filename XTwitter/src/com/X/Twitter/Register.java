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


public class Register extends JPanel implements KeyListener, ActionListener {
	
	private JLabel acl;
	private JLabel pal;
	private JLabel palk;
	private JTextField acc;
	private JTextField fname;
	private JTextField lname;
	private JPasswordField pass;
	private JPasswordField passk;
	private JButton register;
	private JButton back;
	
	public static Dimension REG_SIZE = new Dimension(300,340);
	
	public static Color fore = Color.white;
	
	private XTwiter xs;
	
	public Register(XTwiter xs) {
		this.xs = xs;
		//this.setSize(MainW.WIDTH, MainW.HEIGHT);
		this.setLayout(null);
		this.setBackground(UserProfilePanel.BG);
		
		init();
	}
	
	private void init() {
		int tw = 170;
		int lw = 100;
		
		acl = new JLabel("Username: ");
		acl.setBounds((int) REG_SIZE.getWidth() - lw >> 1, 30, lw, 25);
		this.add(acl);
		
		pal = new JLabel("Password: ");
		pal.setBounds((int) REG_SIZE.getWidth() - lw >> 1, 80, lw, 25);
		this.add(pal);
		
		palk = new JLabel("Password: ");
		palk.setBounds((int) REG_SIZE.getWidth() - lw >> 1, 130, lw, 25);
		this.add(palk);
		
		
		acc = new JTextField();
		acc.addKeyListener(this);
		acc.setBounds((int) REG_SIZE.getWidth() - tw >> 1, 55, tw, 25);
		this.add(acc);
		
		pass = new JPasswordField();
		pass.addKeyListener(this);
		pass.setBounds((int) REG_SIZE.getWidth() - tw >> 1, 105, tw, 25);
		this.add(pass);
		
		passk = new JPasswordField();
		
		pass.setEchoChar('*');
		passk.setEchoChar('*');
		
		
		passk.addKeyListener(this);
		
		passk.setBounds((int) REG_SIZE.getWidth() - tw >> 1, 155, tw, 25);
		this.add(passk);
		
		register = new JButton("Register");
		back = new JButton("Back");
		
		register.addActionListener(this);
		back.addActionListener(this);
		
		register.setBounds((REG_SIZE.width >> 1) - (70 << 1) + (70 >> 1), 300, 100, 30);
		this.add(register);
		back.setBounds((REG_SIZE.width >> 1), 300, 100, 30);
		this.add(back);
		
		
		JLabel fnamel = new JLabel("First Name: ");
		fnamel.setBounds((int) REG_SIZE.getWidth() - lw >> 1, 180, lw, 25);
		this.add(fnamel);
		
		JLabel lnamel = new JLabel("Last Name: ");
		lnamel.setBounds((int) REG_SIZE.getWidth() - lw >> 1, 230, lw, 25);
		this.add(lnamel);
		
		fname = new JTextField();
		fname.addKeyListener(this);
		fname.setBounds((int) REG_SIZE.getWidth() - tw >> 1, 205, tw, 25);
		this.add(fname);
		
		lname = new JTextField();
		lname.addKeyListener(this);
		lname.setBounds((int) REG_SIZE.getWidth() - tw >> 1, 255, tw, 25);
		this.add(lname);
		
		lnamel.setForeground(fore);
		fnamel.setForeground(fore);
		acl.setForeground(fore);
		pal.setForeground(fore);
		palk.setForeground(fore);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.register)) {
			String s1 = pass.getText();
			String s2 = passk.getText();
			pass.setText("");
			passk.setText("");
			register(s1, s2);
		} else {
			pass.setText("");
			passk.setText("");
			xs.show(XTwiter.LOGIN);
		}
	}
	
	public static void warn(String s) {
		UIManager UI = new UIManager();
		UIManager.put("OptionPane.messageForeground", Color.white);
		UIManager.put("OptionPane.foreground", Color.white);
		UIManager.put("OptionPane.background", UserProfilePanel.BG);
		UIManager.put("Panel.background", UserProfilePanel.BG);

		JOptionPane.showMessageDialog(null,s,"Login error", JOptionPane.WARNING_MESSAGE);
	}
	
	private void register(String pass, String passk) {
		String ac = acc.getText();
		
		if(ac == null || pass == null || passk == null || fname == null || lname == null ||
			ac.trim().equals("") || pass.trim().equals("") || passk.trim().equals("") || fname.getText().trim().equals("") || lname.getText().trim().equals("")) {
			warn("You must not leave blank fields.");
			return;
		} else if(!pass.equals(passk)) {
			warn("The passwords doesn't match.");
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
		
		if(ac.length() <= 4 || ac.length() > 12) {
			warn("account must be from 5 to 12 characters.");
			return;
		}
		if(pass.length() <= 4 || pass.length() > 12) {
			warn("password must be from 5 to 12 characters.");
			return;
		}
		
		for(int i = 0; i < ac.length(); i++) {
			char c = ac.charAt(i);
			if((c | 32) < 97 || (c | 32) > 122) {
				warn("account can only contain characters.");
				return;
			}
		}
		XTwiter.cmds.add(new Message(MessageType.register,
				ac,
				pass,
				fname.getText().trim(),
				lname.getText().trim()
				));
	}
	
}

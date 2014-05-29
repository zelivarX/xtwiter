package com.X.Twitter;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import server.comun.Sock;

import com.x.programlogic.Constants;
import com.x.programlogic.User;
import com.x.programlogic.UserPOJO;
import com.x.xtwiter.Message;


@SuppressWarnings("serial")
public class XTwiter extends JFrame {
	public static final int width = 700;
	public static final int height = 500;
	public static final Dimension SIZE = new Dimension(width, height);
	
	
	public static UserPOJO currUser;
	public static CardLayout cl;
	
	public static JPanel in;
	public static JPanel mp;
	
	public static int MYID = 2;
	public Bar BAR;
	public static final int LOGIN = 0, REG = 1, IN = 2;
	
	public static User me;
	
	public static Sock socket;
	
	public static List<User> users = new LinkedList<User>();
	
	public static Queue<Message> cmds = new LinkedList<Message>();
	
	
	
	public UserProfilePanel PROFILE;
	
	public void show(int stat) {
		switch(stat) {
		case LOGIN:
			cl.show(mp, "login");
			this.setSize(LoginP.S);
			break;
		case REG:
			cl.show(mp, "reg");
			this.setSize(Register.REG_SIZE);
			break;
		case IN:
			cl.show(mp, "in");
			this.setSize(SIZE);
			break;
		}
	}
	
	public void RESIZE(int h) {
		h *= 105;
		h += 300;
		PROFILE.setMaximumSize(new Dimension(XTwiter.width - 100, h));
		PROFILE.setMinimumSize(new Dimension(XTwiter.width - 100, h));
		PROFILE.setPreferredSize(new Dimension(XTwiter.width - 100, h));
	}
	
	public static JScrollBar XBAR;
	public static JScrollBar YBAR;
	
	public void CHANGEUSER(UserPOJO user) {
		XTwiter.currUser = user;
		
		PROFILE.getFollow().setText(user.isFollowed() ? "unfollow" : "follow");
		PROFILE.getNames().setText(user.getNames());
		
		PROFILE.getPost().setText("Whats on my mind...");
		PROFILE.getPost().setForeground(Color.gray);
		
		if(user.getUid() == MYID) {
			PROFILE.getFollow().setVisible(false);
			PROFILE.getPost().setVisible(true);
			PROFILE.getPostB().setVisible(true);
			PROFILE.getMe().setVisible(true);
			PROFILE.getFeed().setVisible(true);
			PROFILE.getMe().setBackground(UserProfilePanel.MEFEED_CLICKED);
			PROFILE.getFeed().setBackground(UserProfilePanel.MEFEED);
		} else {
			PROFILE.getFollow().setVisible(true);
			PROFILE.getPost().setVisible(false);
			PROFILE.getPostB().setVisible(false);
			PROFILE.getMe().setVisible(false);
			PROFILE.getFeed().setVisible(false);
		}
		
		int n = XTwiter.currUser.getTWEETS_N();
		PROFILE.cont.setBounds(200, UserProfilePanel.FOLLY, 400, n * 105);
		RESIZE(n);
		PROFILE.M.f1(n);
		XTwiter.YBAR.setValue(XTwiter.YBAR.getMinimum());
	}
	
	public XTwiter() {
		super(Constants.APPNAME);
		this.setSize(SIZE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(null);
		
		socket = new Sock(this);
		socket.setUp();

		currUser = new UserPOJO(-1, "", true, null, null);
		
		cl = new CardLayout();
		in = new JPanel();
		
		in.setLayout(cl);
		in.setLayout(null);
		in.setSize(SIZE);
		BAR = new Bar();
		
		mp = new JPanel();
		
		
		PROFILE = new UserProfilePanel();
		PROFILE.setBounds(0, Bar.barHeight, 0, 0);
		
		RESIZE(11);
		
		JScrollPane scr = new JScrollPane(PROFILE, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scr.setBounds(0, Bar.barHeight, XTwiter.width - 5, height - Bar.barHeight - 23);
		
		YBAR = scr.getVerticalScrollBar();
		YBAR.setUnitIncrement(10);
		
		
		in.add(BAR);
		in.add(scr);
		
		mp.setLayout(cl);
		mp.setSize(SIZE);
		
		mp.add(new LoginP(this), "login");
		mp.add(new Register(this), "reg");
		mp.add(in, "in");
		
		this.add(mp);
		this.show(XTwiter.LOGIN);
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new XTwiter();
			}
		});
	}

}

package com.X.Twitter;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.x.helper.ImageTest;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;

public class UserProfilePanel extends JPanel implements ActionListener {

	public static final int PICWIDTH = 100, PICHEIGHT = 100;
	public static final int FOLLWIDTH = 100, FOLLHEIGHT = 20;
	public static final int FOLLX = 80, FOLLY = 160;
	public static final Color BG = new Color(0x8ED7E8);
	
	private JButton followers;
	private JButton following;
	private JButton follow;
	private JButton me;
	private JButton feed;
	private JButton postB;
	private JLabel names;
	private JLabel pic;
	private JTextArea post;
	
	public static final Color MEFEED_CLICKED = new Color(0x4B8D9C);
	public static final Color MEFEED = Color.gray;
	
	Wall M;
	public JPanel cont;
	CardLayout cl;
	
	public UserProfilePanel() {
		this.init();
	}
	
	
	public static final int other = FOLLY - FOLLHEIGHT * 2, me1 = other + 20;
	public static int mefeed = me1;
	
	public JButton getFollow() {
		return follow;
	}
	
	public JButton getMe() {
		return me;
	}
	
	public JButton getFeed () {
		return feed;
	}
	
	public JTextArea getPost() {
		return post;
	}
	
	public JButton getPostB () {
		return postB;
	}
	
	public JLabel getNames () {
		return names;
	}
	
	private void init() {
		this.setBounds(0, Bar.barHeight, XTwiter.width, XTwiter.height - Bar.barHeight);
		this.setBackground(BG);
		this.setLayout(null);
		
		followers = new JButton("followers");
		followers.setBounds(FOLLX, FOLLY, FOLLWIDTH, FOLLHEIGHT);
		followers.addActionListener(this);
		following = new JButton("following");
		following.setBounds(FOLLX, FOLLY + FOLLHEIGHT, FOLLWIDTH, FOLLHEIGHT);
		following.addActionListener(this);
		follow = new JButton("follow");
		follow.setBounds(FOLLX, FOLLY + (FOLLHEIGHT << 1), FOLLWIDTH, FOLLHEIGHT);
		
		follow.addActionListener(this);
		
		post = new JTextArea();
		post.setBounds(210, other - 30, 350, 40);
		post.setText("Whats on my mind...");
		post.setForeground(Color.gray);
		post.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				if(post.getText().trim().equals("Whats on my mind...")) {
					post.setText("");
					post.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(post.getText().trim().equals("")) {
					post.setText("Whats on my mind...");
					post.setForeground(Color.gray);
				}
			}
			
		});
		
		postB = new JButton("Tweet");
		postB.setBounds(350 + 140, other + 10, 70, 20);
		postB.setBorder(null);
		postB.addActionListener(this);
		this.add(postB);
		
		post.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				if(k == KeyEvent.VK_ENTER){
					e.consume();
					return;
				}
				int c = post.getText().length();
				if(c != 0 && c % 50 == 0) {
					post.setText(post.getText() + '\n');
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int k = e.getKeyCode();
				if(k == KeyEvent.VK_ENTER){
					POST(post.getText());
					post.setText("");
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		me = new JButton("me");
		me.setBounds(200, me1, 70, FOLLHEIGHT);
		
		feed = new JButton("feed");
		feed.setBounds(270, me1, 70, FOLLHEIGHT);
		
		
		followers.setBorder(null);
		following.setBorder(null);
		follow.setBorder(null);
		
		me.setBorder(null);
		feed.setBorder(null);
		feed.setBackground(MEFEED);
		
		
		this.add(post);
		this.add(followers);
		this.add(following);
		this.add(follow);
		this.add(me);
		this.add(feed);
		
		me.addActionListener(this);
		feed.addActionListener(this);
		
		cont = new JPanel();
		cont.add(M = new Wall(this), "me");
		cont.setBounds(200, FOLLY, 400, 11 * 105);
		cont.setLayout(cl = new CardLayout());
		cl.show(cont, "me");
		this.setBounds(0, Bar.barHeight, XTwiter.width, FOLLY + 11 * 80);
		me.setBackground(MEFEED_CLICKED);
		
		this.add(cont);
		
		f();
		
	}
	
	private void POST(String content) {
		if(!content.trim().equals("Whats on my mind..."))
			XTwiter.cmds.add(new Message(MessageType.tweet, content));
	}
	
	public void update() {
		
	}

	JTextArea t1;
	JLabel profile;
	
	public void update(boolean online, String name, String about, ImageIcon pic) {
		profile.setIcon(pic);
		this.names.setText(name);
	}
	
	public static int profw = 130, profh = 130;
	
	public void f () {
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File("/home/velizar/bg2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			this.setLayout(null);
			profile = new JLabel();
			this.profile.setIcon(new ImageIcon(ImageTest.resize(im, PICWIDTH, PICHEIGHT)));
			
			profile.setBounds(FOLLX, 50, PICWIDTH, PICHEIGHT);
			this.add(profile);
			JTextArea t = new JTextArea();
			t.setEditable(false);
			t.setBounds(5, profh + 15, this.getWidth() - 20 ,200);
			t.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			t1 = new JTextArea(2, 2);
			int h = 90;
			t1.setBounds(5,this.getHeight() - 10, this.getWidth() - 20 , h);
			
			t1.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					int k = e.getKeyCode();
					switch(k) {
					case KeyEvent.VK_ENTER:
						e.consume();
						t1.setText("");
						break;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					int k = e.getKeyCode();
					switch(k) {
					case KeyEvent.VK_ENTER:
						//t1.setText("");
						break;
					}
				}

				@Override
				public void keyTyped(KeyEvent e) {
					
				}
				
			});
			
			names = new JLabel(XTwiter.currUser.getNames());
			names.setBounds(FOLLX + PICWIDTH + 20, 50, 200, 30);
			names.setFont(new Font("Aharoni", 20, 20));
			
			this.add(names);
			
			JScrollPane j = new JScrollPane(t1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			j.setBounds(5,this.getHeight() - 140, this.getWidth() - 20 , h);
			j.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(postB)) {
			
			POST(post.getText());
			post.setText("");
			
			post.setText("Whats on my mind...");
			post.setForeground(Color.gray);
			
		} else if(e.getSource().equals(follow)) {
			
			String fol = (follow.getText().equals("follow")) ? "fol" : "unfol";
			XTwiter.cmds.add(new Message(MessageType.follow, fol, XTwiter.currUser.getUid()));
			
		} else if(e.getSource().equals(me)) {
			me.setBackground(MEFEED_CLICKED);
			feed.setBackground(MEFEED);
			
			XTwiter.cmds.add(new Message(MessageType.show_user_profile, XTwiter.MYID));
		} else if(e.getSource().equals(feed)) {
			me.setBackground(MEFEED);
			feed.setBackground(MEFEED_CLICKED);
			
			XTwiter.cmds.add(new Message(MessageType.get_feed, XTwiter.MYID));
		} else if(e.getSource().equals(followers)) {
			XTwiter.cmds.add(new Message(MessageType.get_followers, XTwiter.currUser.getUid()));
		} else if(e.getSource().equals(following)) {
			XTwiter.cmds.add(new Message(MessageType.get_following, XTwiter.currUser.getUid()));
		}
		
		
	}
}

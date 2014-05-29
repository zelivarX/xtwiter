package com.x.helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.X.Twitter.UserProfilePanel;
import com.X.Twitter.XTwiter;
import com.x.programlogic.TweetPOJO;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;


public class PostPanel extends JPanel implements ActionListener {
	
	static final int ow = 15, oh = 15;

	
	TweetPOJO tweet;

	JLabel picl;
	JLabel stat;
	JButton names;
	JLabel aboutl;
	JButton retweet;
	
	
	private JPanel setP(int w, int h) {
		JPanel p = new JPanel();
		p.setMinimumSize(new Dimension(w, h));
        p.setMaximumSize(new Dimension(w, h));
        p.setPreferredSize(new Dimension(w, h));
        return p;
	}

	
	public static Color tweetColor = new Color(0xD5E8F5);
	
	public PostPanel(final TweetPOJO tweet) {
		
		this.tweet = tweet;
		
		setBorder(null);
		
		setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		
        
        
		picl = new JLabel();
		picl.setIcon(tweet.getPic());
		
		c.gridx = 0;
        c.gridy = 0;       
		
		picl.setBounds(2, 2, 20, 20);
		this.setBackground(UserProfilePanel.BG);
		this.setBackground(Color.BLUE);
		
		this.setBorder(BorderFactory.createLineBorder(new Color(0x666662), 1));
		int h = 40;
		JPanel header = setP(XTwiter.width, h);
		
		header.setBackground(tweetColor);
		add(header, c); 
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        FlowLayout f = new FlowLayout();
        f.setAlignment(FlowLayout.LEFT);
        header.setLayout(f);
        names = new JButton(this.tweet.getNames());
        names.setForeground(Color.blue);
        header.add(picl);
        header.add(names);
        names.setBorder(null);
        names.setBackground(tweetColor);
        names.addActionListener(this);
        header.add(new JLabel("tweeted on " + ft.format(tweet.getSend())));
        
        
        
        JPanel p2 = setP(XTwiter.width, h );
        JPanel p3 = setP(400, 25);
        c.gridy = 1;
        add(p2, c);
        JTextArea text = new JTextArea(10,5);
        p2.setLayout(null);
        p2.setBackground(tweetColor);
        text.setBounds(10, 0, 350, 40);
        text.setEditable(false);
        text.setBackground(tweetColor);
        text.setText(tweet.getText());
        
        p2.add(text);
       
        p3.setLayout(new BorderLayout());
        if(!tweet.isIsmine()) {

        	retweet = new JButton("Retweet ");
            retweet.setBackground(new Color(0xA9C6CF));
            retweet.setBorder(null);
        	p3.add(retweet, BorderLayout.LINE_END);
        	retweet.addActionListener(this);
        }
        
        
        p3.add(new JLabel(tweet.getRetweets() + " retweets"), BorderLayout.LINE_START);
        c.gridy = 2;
        p3.setBackground(tweetColor);
        p3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        add(p3, c);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(names)) {
			XTwiter.cmds.add(new Message(MessageType.show_user_profile, tweet.getUid()));
		} else if(e.getSource().equals(retweet)) {
			XTwiter.cmds.add(new Message(MessageType.retweet, tweet.getTweet_id()));
		}
	}
}

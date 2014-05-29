package com.x.server;

import java.awt.TrayIcon.MessageType;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.x.xtwiter.Message;

class Client extends Thread {
	
	private ObjectInputStream inn;
	private ObjectOutputStream outt;
	
	Queue<Message> cmds = new LinkedList<Message>();
	HashMap<Integer, Client> clients;
	
	DatabaseManager databaseManager;
	
	private Connection con = null;
	private Statement st = null;
	
	
	Client(Socket s, HashMap<Integer, Client> clients, Statement statement) {
		
		this.clients = clients;
		
		try {
			outt = new ObjectOutputStream(s.getOutputStream());
			outt.flush();
			inn = new ObjectInputStream(s.getInputStream());
			
			databaseManager = new DatabaseManager(statement, this);
			this.start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//--
		public void run() {
			try {
				OUTER : while(true) {
					Message cmd = (Message) inn.readObject();
					switch(cmd.getType()) {
					case login:
						databaseManager.login(cmd.getCmds());
						break;
					case logout:
						System.out.println(databaseManager.logout() + " has disconnected.");
						cmds.add(new Message(MessageType.logout));
						break;
					case quit:
						System.out.println(databaseManager.logout() + " has disconnected.");
						break OUTER;
					case register:
						databaseManager.register(cmd.getCmds());
						break;
					case follow:
						databaseManager.follow((String) cmd.getCmds()[0], (int) cmd.getCmds()[1]);
						break;
					case show_user_profile:
						databaseManager.showUser((int) cmd.getCmds()[0]);
						break;
					case delete_notification:
						databaseManager.deleteNotific(cmd.getCmds());
						break;
					case tweet:
						databaseManager.tweet(cmd.getCmds());
						break;
					case retweet:
						databaseManager.retweet(cmd.getCmds());
						break;
					case get_feed:
						databaseManager.getFeed((int) cmd.getCmds()[0]);
						break;
					case get_followers:
						databaseManager.getFollowers((int) cmd.getCmds()[0]);
						break;
					case get_following:
						databaseManager.getFollowing((int) cmd.getCmds()[0]);
						break;
					case search:
						databaseManager.SEARCH((String) cmd.getCmds()[0]);
						break;
					case show_notifications:
						databaseManager.showNotifications();
						break;
					}
					
					Thread.sleep(60);
					
					if(!cmds.isEmpty()) {
						outt.writeObject(cmds.remove());
						outt.flush();
					} else {
						outt.writeObject(new Message());
						outt.flush();
					}
					
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // RUN

	
}
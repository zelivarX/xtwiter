package server.comun;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.X.Twitter.XTwiter;
import com.x.xtwiter.Message;


public class Sock extends Thread {

	private Socket s;


	private ObjectInputStream inn;
	private ObjectOutputStream outt;
	
	private CommandsManager commandManager;
	
	public Sock(XTwiter xtwiter) {
		commandManager = new CommandsManager(xtwiter);
	}
	
	public boolean isset() {
		return s == null ? false : true;
	}
	
	public int setUp() {
		try {
			s = new Socket("localhost", 1121);
			
			outt = new ObjectOutputStream(s.getOutputStream());
			outt.flush();
			inn = new ObjectInputStream(s.getInputStream());
			
			start();
		} catch (UnknownHostException e) {
			return 2;
		} catch (IOException e) {
			return 1;
		}
		return 0;
	}
	
	
	public void run() {
		
		while(true) { // MAIN LOOP
			try {
				Thread.sleep(100);
				if(!XTwiter.cmds.isEmpty()) {
					outt.writeObject(XTwiter.cmds.remove());
					outt.flush();
				} else {
					outt.writeObject(new Message());
					outt.flush();
				}
				
				Message cmd = (Message) inn.readObject();
				
				switch(cmd.getType()) {
				case no_message: break;
				
				case show_user_profile:
					commandManager.GETUSER(cmd.getCmds());
					break;					
				case login:
					commandManager.login(cmd.getCmds());
					break;
				case logout:
					commandManager.logout();
					break;
				case register:
					commandManager.register( (String) cmd.getCmds()[0]);
					break;
				case follow:
					commandManager.follow((String) cmd.getCmds()[0], (int) cmd.getCmds()[1]);
					break;
				case get_feed:
					commandManager.getFeed(cmd.getCmds());
					break;
				case send_notification:
					commandManager.sendNotif((int) cmd.getCmds()[0]);
					break;
				case get_followers:
					commandManager.getFollowers( (Object[]) cmd.getCmds());
					break;
				case search:
					commandManager.getSearchResults( (Object[]) cmd.getCmds());
					break;
				case get_following:
					commandManager.getFollowing( (Object[]) cmd.getCmds());
					break;
				case show_notifications:
					commandManager.showNotifications(cmd.getCmds());
					break;
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			

		} // MAIN LOOP
	}

	
}

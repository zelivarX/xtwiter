package server.comun;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.X.Twitter.MDialog;
import com.X.Twitter.Register;
import com.X.Twitter.UserProfilePanel;
import com.X.Twitter.XTwiter;
import com.X.Twitter.XTwiter;
import com.x.programlogic.NotificationPOJO;
import com.x.programlogic.TweetPOJO;
import com.x.programlogic.UserFollowerFollowedPOJO;
import com.x.programlogic.UserPOJO;

public class CommandsManager {
	
	private XTwiter xtwiter;
	
	public CommandsManager(XTwiter xtwiter) {
		this.xtwiter = xtwiter;
	}


	void GETUSER(Object[] o) {
		
		Object[] user = (Object[]) o[0];
		Object[] tweets = (Object[]) o[1];
		
		List<TweetPOJO> t = new LinkedList<TweetPOJO>();
		
		boolean ismine = (XTwiter.MYID == (int) user[0]) ? true : false;
		
		if( ((String) tweets[0]).equals("_ok")) {
			
			Object[] t1 = (Object[]) tweets[1];
			
			for(Object i : t1) {
				Object[] j = (Object[]) i;
				
				TweetPOJO tw = new TweetPOJO(
						(int) j[4], //id
						(String) j[3],//user[1], //names
						(String) j[1], //content
						null, //picture
						(int) j[2], //retweets
						(boolean) j[5], //is mine tweet
						(Timestamp) j[0],
						(int) j[6] // tweet id
				);
				
				t.add(tw);
			}
		} else ;

		UserPOJO u = new UserPOJO(
				(int) user[0],
				(String) user[1],
				(boolean) user[2],
				null,//(byte[]) o[3],
				(t.isEmpty()) ? null :  t.toArray(new TweetPOJO[t.size()])//(Tweet[]) t.toArray()
			);
		xtwiter.CHANGEUSER(u);
		
	}
	
	void follow (String f, int uid) {
		if(XTwiter.currUser.getUid() == uid) {
			xtwiter.PROFILE.getFollow().setText(f.equals("unfol") ? "follow" : "unfollow");
		}
	}
	
	void login(Object[] cmds) {
		final String reply = (String) cmds[0];
		switch(reply) {
		case "_ok":	
			XTwiter.MYID = (int) cmds[1];
			Register.warn("successfuly registered.");
			xtwiter.show(XTwiter.IN);
			
			break;
		}
		Register.warn("invalid username or password.");
		
	}
	
	void logout() {
		xtwiter.show(XTwiter.LOGIN);
	}
	
	void register(final String response) {
		switch(response) {
		case "_exists":
			Register.warn("account alredy exists.");
			break;
		}
		Register.warn("successfuly registered.");
		xtwiter.show(XTwiter.LOGIN);
	}
	
	void showNotifications(Object[] users) {
		
		
		
		List<NotificationPOJO> notifList = new ArrayList<NotificationPOJO>();
		
		for(Object user : users) {
			Object[] j  = (Object[]) user;	
			
			notifList.add(
				new NotificationPOJO(
					(int) j[4], // user id
					(String) j[0], // names
					(String) j[1], // content
					(int) j[3] == 0 ? false : true, // is seen
					(String) j[2], // send on
					(int) j[5] // notification id
				)
			);
		}
		MDialog dialog = new MDialog(xtwiter);
		dialog.populate1(notifList);
		dialog.setTitle("Notifications");
	}
	
	void getFollowers(Object[] users) {
		List<UserFollowerFollowedPOJO> usersList = new ArrayList<UserFollowerFollowedPOJO>();
		for(Object user : users) {
			Object[] j  = (Object[]) user;
			usersList.add(
				new UserFollowerFollowedPOJO(
					(int) j[2], // user id
					(String) j[0], // first_name
					(String) j[1], // last_name
					(String) j[3] // is followed by me	
				)
			);
		}
		MDialog dialog = new MDialog(xtwiter);
		dialog.populate(usersList);
		dialog.setTitle("Followers");
	}
	
	void getSearchResults(Object[] users) {
		List<UserFollowerFollowedPOJO> usersList = new ArrayList<UserFollowerFollowedPOJO>();
		for(Object user : users) {
			Object[] j  = (Object[]) user;
			usersList.add(
				new UserFollowerFollowedPOJO(
					(int) j[2], // user id
					(String) j[0], // first_name
					(String) j[1], // last_name
					(String) j[3] // is followed by me	
				)
			);
		}
		MDialog dialog = new MDialog(xtwiter);
		dialog.populate(usersList);
		dialog.setTitle("Search Results");
	}
	
	void getFeed(Object[] cmds) {
		GETUSER(cmds);
		xtwiter.PROFILE.getMe().setBackground(UserProfilePanel.MEFEED);
		xtwiter.PROFILE.getFeed().setBackground(UserProfilePanel.MEFEED_CLICKED);
	}
	
	void sendNotif(int i) {
		xtwiter.BAR.notif.setText(String.valueOf(i));
	}
	
	void getFollowing(Object[] users) {
		List<UserFollowerFollowedPOJO> usersList = new ArrayList<UserFollowerFollowedPOJO>();
		for(Object user : users) {
			Object[] j  = (Object[]) user;
			usersList.add(
				new UserFollowerFollowedPOJO(
					(int) j[2], // user id
					(String) j[0], // first_name
					(String) j[1], // last_name
					(String) j[3] // is followed by me	
				)
			);
		}
		MDialog dialog = new MDialog(xtwiter);
		dialog.populate(usersList);
		dialog.setTitle("Following");
	}
	
}

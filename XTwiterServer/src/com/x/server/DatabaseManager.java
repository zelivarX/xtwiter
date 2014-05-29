package com.x.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.x.constants.Constants;
import com.x.xtwiter.Message;
import com.x.xtwiter.MessageType;

public class DatabaseManager {
	
	private Statement statement;
	private Client me;
	
	public DatabaseManager(Statement statement, Client me) {
		this.statement = statement;
		this.me = me;
	}
	
	

	private int id;
	
	void getFollowing(int uid) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select u.id, u.first_name, u.last_name, case ");
			sb.append("when f.followed_id = ");
			sb.append(this.id);
			sb.append(" then 'ME' ");
			sb.append("when f.followed_id IN (select f.followed_id from users u join followers f on f.followed_id = u.id AND f.follower_id = ");
			sb.append(this.id);
			sb.append(") then 'unfollow' ");
			sb.append("else 'follow' ");
			sb.append("end c ");
			sb.append("from users u join followers f ");
			sb.append("ON f.followed_id = u.id AND f.follower_id = ");
			sb.append(uid);
			
			/*String query = String.format(
			"select u.id, u.first_name, u.last_name, case " +
			"when f.followed_id = %d then 'ME' " +
			"when f.followed_id IN (select f.followed_id from users u join followers f on f.followed_id = u.id AND f.follower_id = %d) then 'unfollow' " +
			"else 'follow' " +
			"end c " +
			"from users u join followers f " +
			"ON f.followed_id = u.id AND f.follower_id = %d"
			, id, id, uid
			);*/
			
			ResultSet rs = statement.executeQuery(sb.toString());
			
			List<Object> users = new ArrayList<Object>();
			
			while(rs.next()) {
				users.add(new Object[] {
						
						rs.getString(Constants.user_first_name),
						rs.getString(Constants.user_last_name),
						rs.getInt(Constants.user_id),
						rs.getString("c")
						
				});
				
			}
			
			me.cmds.add(new Message(MessageType.get_following, users.toArray()));
			this.showUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void getFollowers(int uid) {
		try {
			
			String query = String.format(
			"select u.id, u.first_name, u.last_name, case " +
			"when f.follower_id = %d then 'ME' " +
			"when f.follower_id IN (select f.followed_id from users u join followers f on f.followed_id = u.id AND f.follower_id = %d) then 'unfollow' " +
			"else 'follow' " +
			"end c " +
			"from users u join followers f " +
			"ON f.follower_id = u.id AND f.followed_id = %d"
			, id, id, uid
			);
			
			ResultSet rs = statement.executeQuery(query);
			
			List<Object> users = new ArrayList<Object>();
			
			while(rs.next()) {
				users.add(new Object[] {
						
						rs.getString(Constants.user_first_name),
						rs.getString(Constants.user_last_name),
						rs.getInt(Constants.user_id),
						rs.getString("c")
						
				});
			}
			
			me.cmds.add(new Message(MessageType.get_followers, users.toArray()));
			this.showUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	String logout() {
		String acc = "";
		try {
			
			String query = String.format("SELECT acc FROM users WHERE id = %d", this.id);
			
			ResultSet rs = statement.executeQuery(query);
			if(rs.next())
				acc = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		me.clients.remove(this.id);
		this.id = -1;
		return acc;
	}
	
	void login(Object[] cmds) {

		try {
			String acc = (String) cmds[0];
			String pass = (String) cmds[1];
			
			String query = String.format("SELECT id FROM %s WHERE acc = '%s' and pass = '%s'", 
					Constants.users_table,
					acc,
					pass);
			
			ResultSet rs = statement.executeQuery(query);
			
			
			if(rs.next()) {
				this.id = rs.getInt(Constants.user_id);
				me.clients.put(this.id, me);
				System.out.println(acc + " has connected.");
				me.cmds.add(new Message(MessageType.login, "_ok", this.id));
				this.showUser(id);
				this.sendNotif();
			} else {
				me.cmds.add(new Message(MessageType.login, "", this.id));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	void register(Object[] cmds) {

		try {
			String acc = (String) cmds[0];
			String pass = (String) cmds[1];
			String fname = (String) cmds[2];
			String lname = (String) cmds[3];
			String query = String.format("INSERT INTO %s (acc, pass, first_name, last_name) VALUES ('%s', '%s', '%s', '%s')", 
					Constants.users_table,
					acc,
					pass,
					fname,
					lname
					
				);
			
			
			statement.executeUpdate(query);
			
			me.cmds.add(new Message(MessageType.register, ""));
		} catch (SQLException e) {
			me.cmds.add(new Message(MessageType.register, "_exists"));
		}
		
	}
	
	void showNotifications() {
		StringBuilder sb = new StringBuilder();
		sb.append("select u.first_name, u.last_name, n.content, n.send_on, n.SEEN, n.from_id, n.id ");
		sb.append("from users u join notifications n ");
		sb.append("ON n.from_id = u.id AND n.to_id = %d order by send_on desc");
		
		String query = String.format(sb.toString(), this.id);
		
		try {
			ResultSet rs = statement.executeQuery(query);
			List<Object> notifications = new ArrayList<Object>();
			while(rs.next()) {
				Object[] notification = {
					rs.getString(1) + " " + rs.getString(2), // names
					rs.getString(3), // content
					rs.getTimestamp(4).toString(), // send_on
					rs.getInt(5), // seen
					rs.getInt(6), // from
					rs.getInt(7) // notification id
				};
				notifications.add(notification);
			}
			me.cmds.add(new Message(MessageType.show_notifications, notifications.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addNotif(int to) {
		
		try {
			//ResultSet rs = statement.executeQuery("select first_name from users where id = " + this.id);
			
			
			String query = String.format("insert into notifications (to_id, from_id,content,send_on) values (%d,%d,'%s', now())", 
					to,
					this.id,
					" has followed you"
					);
			
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void sendNotif() {
		try {
			String query = String.format("select max(@a := @a + 1) from users u join notifications n ON u.id = n.to_id  AND u.id = %d AND n.seen = 0", this.id);
			statement.executeUpdate("set @a = 0");
			ResultSet rs = statement.executeQuery(query);
			
			if(rs.next()) {
				me.cmds.add(new Message(MessageType.send_notification, rs.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	void follow (String f, int uid) {
		try {
			if(f.equals("unfol")) {
				statement.executeUpdate("DELETE FROM followers WHERE follower_id = " + this.id + " AND followed_id = " + uid);
			} else {
				statement.executeUpdate("INSERT INTO followers values(" + this.id + ", " + uid + ")");
				
				addNotif(uid);
				if(me.clients.get(uid) != null)
					me.clients.get(uid).databaseManager.sendNotif();
			}
			
			me.cmds.add(new Message(MessageType.follow, f, uid));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // follow
	
	void showUser(int uid) {
		try {
			boolean isFollowed = false;
			
			ResultSet rs = null;
			if(uid != this.id) {
				rs = statement.executeQuery(
						String.format("SELECT * FROM followers WHERE follower_id = %d AND followed_id = %d", this.id, uid) 
					);
				if(rs.next()) {
					isFollowed = true;
				}
			}
			

			StringBuilder sb = new StringBuilder();
			sb.append("select t.poster_id, u.first_name, u.last_name, t.content, t.send_on as POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = ");
			sb.append(this.id);
			sb.append(") OR t.id IN (select tweet_id from retweets where user_who_retweeted = ");
			sb.append(this.id);
			sb.append(" AND tweet_id = t.id),'yes','no') as ismine, t.id ");
			sb.append("from tweets t join users u ON t.poster_id = u.id AND u.id = ");
			sb.append(uid);
			sb.append(" union ");
			sb.append("select t.poster_id, u.first_name, u.last_name, t.content, r.retweeted_on as POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = ");
			sb.append(this.id);
			sb.append(") OR t.id IN (select tweet_id from retweets where user_who_retweeted = ");
			sb.append(this.id);
			sb.append(" AND tweet_id = t.id),'yes','no') as ismine, t.id ");
			sb.append("from tweets t join retweets r ON r.tweet_id = t.id join users u ON u.id = r.user_who_retweeted AND u.id = ");
			sb.append(uid);
			sb.append( " order by POSTED_ON desc");
			
			rs = statement.executeQuery(sb.toString());
			
			List<Object> t = new LinkedList<Object>();
			
			
			while(rs.next()) {
				int posterId = rs.getInt(Constants.tweet_poster);
				String names = rs.getString(Constants.user_first_name) + " " + rs.getString(Constants.user_last_name);
				
				Date postedOn  = rs.getTimestamp("POSTED_ON");
				String content = rs.getString(Constants.tweet_content);
				int retweets = rs.getInt(Constants.tweet_retweets);
				
				Object[] o = {
						
						postedOn,
						content,
						retweets,
						names,
						posterId,
						rs.getString("ismine").equals("yes") ? true : false,
						rs.getInt("id")
				};
				t.add(o);
				
			}
			rs = statement.executeQuery("SELECT * FROM users WHERE id = " + uid);
			if(rs.next()) {
				
				 
				Object[] user = {
						rs.getInt("id"),
						rs.getString("first_name") + " " + rs.getString("last_name"),
						isFollowed,
						
				};
				
				Object[] o;
				
				if(t.size() == 0) {
					Object[] o1 = {"_no"};
					o = o1;
				} else {
					Object[] o1 = {"_ok", t.toArray()};
					o = o1;
				}
				
				me.cmds.add(new Message(MessageType.show_user_profile, user, o));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // show user
	
	
	void deleteNotific(Object[] cmds) {
		int nid = (int) cmds[0];
		try {
			
			statement.executeUpdate("DELETE FROM notifications WHERE id = " + nid);
			//me.cmds.add(new Message(MessageType.delete_notification));
			sendNotif();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	void tweet(Object[] o) {
		String content = (String) o[0];
		
		String query = String.format("INSERT INTO tweets (%s, %s, %s) VALUES (%d, now(), '%s')", 
				Constants.tweet_poster,
				Constants.tweet_send,
				Constants.tweet_content,
				this.id,
				content
				
				);
		try {
			statement.executeUpdate(query);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void retweet(Object[] o) {
		int tweet_id = (int) o[0];
		
		String query = String.format("INSERT INTO %s VALUES (%d, %d, now())", 
				Constants.retweets_table,
				this.id,
				tweet_id
				
				);
		try {
			statement.executeUpdate(query);
			statement.executeUpdate("UPDATE tweets SET retweets = retweets + 1 WHERE id = " + tweet_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void getFeed(int uid) {
		try {
			boolean isFollowed = false;
			
			uid = this.id;
			
			ResultSet rs = null;
			
			
			String t_query = "select t.poster_id, u.first_name, u.last_name, t.content, t.send_on AS POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = "+this.id+") OR t.id IN (select tweet_id from retweets where user_who_retweeted = "+this.id+" AND tweet_id = t.id),'yes','no') as ismine, t.id from tweets t join users u "
					+ 				"ON t.poster_id = u.id AND u.id = " + uid +
					
							" union " +
													
							"select t.poster_id, u.first_name, u.last_name, t.content, r.retweeted_on AS POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = "+this.id+") OR t.id IN (select tweet_id from retweets where user_who_retweeted = "+this.id+" AND tweet_id = t.id),'yes','no') as ismine, t.id "
									+ "from tweets t join retweets r ON r.tweet_id = t.id join users u ON u.id = r.user_who_retweeted AND u.id = " + uid +
							
							" union " +
							
							"select t.poster_id, u.first_name, u.last_name, t.content, t.send_on AS POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = "+this.id+") OR t.id IN (select tweet_id from retweets where user_who_retweeted = "+this.id+" AND tweet_id = t.id),'yes','no') as ismine, t.id  from tweets t " +
							"join users u " +
							"ON t.poster_id = u.id AND u.id in " +
							"(select f.followed_id from users u " +
							"join followers f " +
							"on f.follower_id = u.id and u.id = "+uid+") " +
							
							"union " +
							
							"select t.poster_id, u.first_name, u.last_name, t.content, r.retweeted_on AS POSTED_ON, t.retweets, if(t.id in (select id from tweets where poster_id = "+this.id+") OR t.id IN (select tweet_id from retweets where user_who_retweeted = "+this.id+" AND tweet_id = t.id),'yes','no') as ismine, t.id " +
							"from tweets t " +
							"join retweets r " +
							"ON r.tweet_id = t.id " +
							"join users u " +
							"ON t.id in (select tweet_id from retweets WHERE tweet_id = t.id AND user_who_retweeted in " +
										"(select f.followed_id from users u " +
										"join followers f " +
										"on f.follower_id = u.id and u.id = "+uid+")"
									+ " ) " +
							" AND u.id in " +
									"(select f.followed_id from users u " +
									"join followers f " +
									"on f.follower_id = u.id and u.id = "+uid+") " +
							"AND u.id = t.poster_id " +
							"order by POSTED_ON desc";
/*
			
			"from tweets t " +
			"join retweets r " +
			"ON r.tweet_id = t.id " +
			"join users u " +
			"ON u.id = t.poster_id " +
			"join followers f " +
			"ON r.user_who_retweeted = f.followed_id " +
			"AND f.follower_id = u.id " +
			"AND u.id = " + uid +
			" order by POSTED_ON desc";
			
			+ "t.id in (select r.tweet_id from r.retweets join followers f ON"
			+ "WHERE r.tweet_id = t.id AND ruser_who_retweeted in " +
			"(select f.followed_id from users u " +
			"join followers f " +
			"on f.follower_id = u.id and u.id = "+uid+")"
		+ " ) " +
			
			
			+ "t.id in (select tweet_id from retweets WHERE tweet_id = t.id AND user_who_retweeted in " +
						"(select f.followed_id from users u " +
						"join followers f " +
						"on f.follower_id = u.id and u.id = "+uid+")"
					+ " ) " +
			" AND u.id in " +
					"(select f.followed_id from users u " +
					"join followers f " +
					"on f.follower_id = u.id and u.id = "+uid+") " +
			"AND u.id = t.poster_id " +
			
			*/
			rs = statement.executeQuery(t_query);
			
			
			
			List<Object> t = new LinkedList<Object>();
			
			while(rs.next()) {
				Object[] o = {
						
						rs.getTimestamp("POSTED_ON"),
						rs.getString("content"),
						rs.getInt("retweets"),
						rs.getString("first_name") + " " + rs.getString("last_name"),
						rs.getInt(Constants.tweet_poster),
						rs.getString("ismine").equals("yes") ? true : false,
						rs.getInt(Constants.tweets_id)
				};
				t.add(o);
			}
			rs = statement.executeQuery("SELECT * FROM users WHERE id = " + uid);
			if(rs.next()) {
				Object[] user = {
						rs.getInt("id"),
						rs.getString("first_name") + " " + rs.getString("last_name"),
						isFollowed,
						
				};
				
				Object[] o;
				
				if(t.size() == 0) {
					Object[] o1 = {"_no"};
					o = o1;
				} else {
					Object[] o1 = {"_ok", t.toArray()};
					o = o1;
				}
				
				me.cmds.add(new Message(MessageType.get_feed, user, o));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // FEED

	void SEARCH(String n) {
		
		try {
			String query = "SELECT first_name, last_name, id, case when 0 < (select count(followed_id) from followers WHERE follower_id = "+this.id+" AND followed_id = us.id) then 'unfollow' when us.id = "+this.id+" then 'ME' else 'follow' end as c FROM users us WHERE first_name LIKE '%"+n+"%' OR last_name LIKE '%"+n+"%' ";
			
			ResultSet rs = statement.executeQuery(query);
			
			List<Object> users = new ArrayList<Object>();
			
			while(rs.next()) {
				users.add(new Object[] {
						
						rs.getString(Constants.user_first_name),
						rs.getString(Constants.user_last_name),
						rs.getInt(Constants.user_id),
						rs.getString("c")
						
				});
				
			}
			
			me.cmds.add(new Message(MessageType.search, users.toArray()));
			this.showUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}

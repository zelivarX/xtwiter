package com.x.programlogic;

public class Constants {
	
	public static final int PROFILE_PIC_W = 50;
	public static final int PROFILE_PIC_H = 50;
	
	public static final String APPNAME = "XTwitter";
	
	public static final String users_table = "users";
	public static final String user_first_name = "first_name";
	public static final String user_last_name = "last_name";
	public static final String user_id = "id";
	public static final String user_pass = "pass";
	public static final String users_acc = "acc";
	
	public static final String tweets_id = "id";
	public static final String tweets_table = "tweets";
	public static final String tweet_poster = "poster_id";
	public static final String tweet_retweets = "retweets";
	public static final String tweet_content = "content";
	public static final String tweet_send = "send_on";
	
	public static final String retweets_table = "retweets";
	public static final String retweet_user = "user_who_retweeted";
	public static final String retweet_tweet = "tweet_id";
	
	public static final String followers_table = "followers";
	public static final String followers_follower = "follower_id";
	public static final String followers_followed = "followed_id";
	
	public static final String notifications_table = "notifications";
	public static final String notification_to = "to_id";
	public static final String notification_from = "from_id";
	public static final String notification_content = "content";
	public static final String notification_send = "send_on";
	public static final String notification_is_seen = "SEEN";
	
}

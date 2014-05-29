package com.x.programlogic;


public class NotificationPOJO {
	
	private int uid;
	private String names;
	private String content;
	private String sendOn;
	private boolean seen;
	private int nid;
	
	public String getContent() {
		return content;
	}
	
	public String getNames() {
		return names;
	}
	
	public int getNid() {
		return nid;
	}
	
	public String getSendOn() {
		return sendOn;
	}
	
	public int getUid() {
		return uid;
	}
	
	public NotificationPOJO(int uid, String names, String content, boolean seen, String sendOn, int nid) {
		this.uid = uid;
		this.names = names;
		this.setSeen(seen);
		this.content = content;
		this.sendOn = sendOn;
		this.nid = nid;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	
}

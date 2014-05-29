package com.x.xtwiter;


import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = -7515019448146960814L;
	private MessageType type;
	private Object[] cmds;
	
	public Message() {
		type = MessageType.no_message;
	}
	
	public Message(MessageType type, Object...cmds) {
		this.type = type;
		this.cmds = cmds;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public Object[] getCmds() {
		return cmds;
	}
	
}

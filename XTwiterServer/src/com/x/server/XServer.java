package com.x.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;


public class XServer {

	private ServerSocket s;
	private static final int PORT = 1121;
	private static final String database_name = "xtwiter";
	private HashMap<Integer, Client> clients = new HashMap<Integer, Client> ();
	private Connection connection;
	
	private void setUpDatabaseConnection () {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database_name, "root", "velizar1");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private void setUpServer() {
		try {
			s = new ServerSocket(PORT);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
	}
	
	public XServer() {
		
		setUpServer();
		setUpDatabaseConnection();
		System.out.println("waiting for connections...");
		while(true) {
			try {
				Socket ss = s.accept();
				new Client(ss, clients, connection.createStatement());
				//clients.add(new Client(ss, clients, connection.createStatement()));
			} catch (Exception e) {
				
			}
			
		}
	}
	

	public static void main(String[] args) {
		System.out.println();
		new XServer();
	}
	
}

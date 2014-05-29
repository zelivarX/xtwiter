package com.X.Twitter;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.x.helper.UserFollowedFollowerPanel;
import com.x.programlogic.NotificationPOJO;
import com.x.programlogic.UserFollowerFollowedPOJO;

public class MDialog extends JDialog implements WindowFocusListener {

	public static enum DialogTypes {
		FollowerFollowed, Notifications
	}
	
	private JPanel cont;
	
	public MDialog(XTwiter xs) {
		super(xs);
		setSize(UserFollowedFollowerPanel.PANEL_WIDTH, 500);
		
		cont = new JPanel();
		cont.setLayout(null);
		cont.setSize(getWidth(), getHeight());
		cont.setBackground(Color.green);
		setLayout(null);
		this.setBackground(Color.red);
		this.addWindowFocusListener(this);
		this.setLocationRelativeTo(xs);
	}

	public void populate(List<UserFollowerFollowedPOJO> usersList) {
		setVisible(true);
		this.usersList = usersList;
		
		add(new MDialogContent(this, usersList));
		
	}

	public void populate1(List<NotificationPOJO> notifList) {
		setVisible(true);
		add(new NotificationsDialogPanel(this, notifList));
		
	}
	
	List<UserFollowerFollowedPOJO> usersList;
	
	@Override
	public void windowLostFocus(WindowEvent arg0) {
		dispose();
		
	}
	
	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		
	}
}

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import classes.MailCheckerConnection;

public class IntervalWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6651723210797604941L;
	private JPanel panel = new JPanel();
	private String username;
	private String password;
	private String interval;
	private String protocol;
	private int time;
	private Timer timer;
	private static final int SECONDS = 1000;

	public IntervalWindow(String username, String password, String interval, String protocol, int time){
		this.username = username;
		this.password = password;
		this.interval = interval;
		this.protocol = protocol;
		this.time = time;
		this.timer = new Timer();
		initUI();
	}
	class IntervalTask extends TimerTask{
		public void run(){
			//When time is up, connects to the email.
			MailCheckerConnection connection = new MailCheckerConnection(username, password, protocol);
			ArrayList<String> list = connection.checkMail();
			MailWindow window = new MailWindow(list);
			window.setVisible(true);
		}
	}
	
	private void initUI() {

		panel = new JPanel();

		//Everything added to this panel will be laid out on the Y Axis. 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//Label Panel.
		JLabel infoLabel = new JLabel("Checking "+username+" every "+ interval);
		panel.add(infoLabel);

		//Cancel Button.
		JButton cancelButton = new JButton("Cancel Mail Checker");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				timer.cancel();
				dispose();
			}
		});
		panel.add(cancelButton);
		timer.schedule(new IntervalTask(), time * SECONDS, time * SECONDS);

		add(panel);
		pack();
		setTitle("Mail Checker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}

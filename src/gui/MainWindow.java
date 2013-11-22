package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	private JPanel panel = new JPanel();
	public MainWindow(){
		initUI();
	}
	private void initUI() {

		panel = new JPanel();

		//Everything added to this panel will be laid out on the Y Axis. 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//Username Panel.
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
		JLabel usernameLabel = new JLabel("Username :");
		final JTextField usernameBox = new JTextField(24);
		usernameBox.setText("username@gmail.com");
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameBox);
		panel.add(usernamePanel);

		//Password Panel.
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		JLabel passwordLabel = new JLabel("Password :");
		final JPasswordField passwordBox = new JPasswordField(24);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordBox);
		panel.add(passwordPanel);

		//Interval Panel 
		JLabel intervalLabel = new JLabel("How often should this email be checked:");
		final String[] hours = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
		final String[] minutes = new String[60];
		for(int i = 0; i < minutes.length; i++){
			minutes[i] = (i+1)+"";
		}

		/*
		 * This handles setting up the interval portion.
		 */
		JPanel intervalPanel = new JPanel();
		intervalPanel.setLayout(new BoxLayout(intervalPanel, BoxLayout.X_AXIS));
		JLabel hourLabel = new JLabel("Hour(s):");
		final JComboBox<String> hourList = new JComboBox<String>(hours);
		JLabel minLabel = new JLabel("Minute(s):");
		final JComboBox<String> minList = new JComboBox<String>(minutes);

		intervalPanel.add(hourLabel);
		intervalPanel.add(hourList);
		intervalPanel.add(minLabel);
		intervalPanel.add(minList);
		panel.add(intervalLabel);
		panel.add(intervalPanel);
		
		/*
		 * Protocol Panel
		 */
		JPanel protocolPanel = new JPanel();
		protocolPanel.setLayout(new BoxLayout(protocolPanel, BoxLayout.X_AXIS));
		JLabel protocolLabel = new JLabel("Protocol Type:");
		final String[] protocols = {"imaps", "pop3"};
		final JComboBox<String> protocolList = new JComboBox<String>(protocols);
		protocolPanel.add(protocolLabel);
		protocolPanel.add(protocolList);
		panel.add(protocolPanel);
		
		//Connect Button.
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//Get the user settings for the hour and min.
				int hour = Integer.parseInt(hourList.getSelectedItem()+"");
				int min = Integer.parseInt(minList.getSelectedItem()+"");
				
				
				//Calculate the seconds for the alarm.
                DateTime now = DateTime.now();
                DateTime dateTime = now.plusHours(hour);
                dateTime = dateTime.plusMinutes(min);
                Seconds seconds = Seconds.secondsBetween(now, dateTime);
				IntervalWindow window = new IntervalWindow(usernameBox.getText(), new String(passwordBox.getPassword()), (hourList.getSelectedItem()+"H "+minList.getSelectedItem()+"m"), protocolList.getSelectedItem()+"", seconds.getSeconds());
				window.setVisible(true);
			}
		});
		panel.add(connectButton);

		add(panel);
		pack();
		setTitle("Mail Checker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}

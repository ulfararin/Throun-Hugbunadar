import java.awt.EventQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.simple.JSONObject;
import throunHugbo.*;

public class DayTrips {

	private JFrame frame;
	private JTextField userName;
	private JTextField userPassWord;
	private DBConnector leit = new DBConnector();
	private JLabel successLogin;
	private JButton createAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DayTrips window = new DayTrips();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DayTrips() {
		initialize();
	}
	private class LoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String pw = userPassWord.getText();
			String name = userName.getText();
			if(leit.findUser(name, pw)){
				System.out.println(userName.getText());
			}
			else{
				successLogin.setVisible(true);
				successLogin.setText("We were unable to login to your account, may be wrong password user name combination");
			}
		}
	}
	private class accCreate implements ActionListener {
		public void actionPerformed(ActionEvent e){
			AccountCreation acc = new AccountCreation();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		JButton button1 = new JButton();
		button1.setPreferredSize(new Dimension(100, 40));
		button1.setText("Login");
		button1.setBackground(Color.red);
		button1.setOpaque(true);
		button1.addActionListener(new LoginButton());
		createAccount = new JButton();
		createAccount.setPreferredSize(new Dimension(150, 40));
		createAccount.setText("Create Account");
		createAccount.setBackground(Color.blue);
		createAccount.setOpaque(true);
		createAccount.addActionListener(new accCreate());
		successLogin = new JLabel();
		successLogin.setVisible(false);
		userName = new JTextField(15);
		userPassWord = new JTextField(15);
		frame.add(userName);
		frame.add(userPassWord);
		frame.add(button1);
		frame.add(successLogin);
		successLogin.setVisible(false);
		frame.add(createAccount);
	}

}

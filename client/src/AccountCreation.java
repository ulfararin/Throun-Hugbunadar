import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.simple.JSONObject;
import throunHugbo.*;
import java.sql.*;

public class AccountCreation {
	private JFrame frame;
	private JTextField userName;
	private JTextField pw1;
	private JTextField pw2;
	private JButton confirm;
	private Update putInDB;
	private JTextField emailField;
	private JLabel errorLabel;
	
	public AccountCreation(){
		initialize();
	}
	
	private class confirmAccount implements ActionListener {
		public void actionPerformed(ActionEvent e){
			errorLabel = new JLabel();
			String p1 = pw1.getText();
			String p2 = pw2.getText();
			String name = userName.getText();
			String email = emailField.getText();
			if(p1.equals(p2) && p1 != null && p1 != "" && name != null && name != ""){
				try{
					putInDB = new Update();
					putInDB.addUser(name, email, p1);
					frame.setVisible(false);
					frame.dispose();
				}
				catch(SQLException ex){
					frame.add(errorLabel);
					errorLabel.setVisible(true);
					errorLabel.setText("Already exists or dangerous input");
				}
			}
			else{
				frame.add(errorLabel);
				errorLabel.setVisible(true);
				errorLabel.setText("passwords don't match or some fields are empty");
			}
		}
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		userName = new JTextField(15);
		pw1 = new JTextField(15);
		pw2 = new JTextField(15);
		emailField = new JTextField(15);
		confirm = new JButton();
		confirm.setBackground(Color.blue);
		confirm.setOpaque(true);
		confirm.setPreferredSize(new Dimension(150, 40));
		confirm.setText("Confirm");
		confirm.addActionListener(new confirmAccount());
		frame.add(userName);
		frame.add(pw1);
		frame.add(pw2);
		frame.add(emailField);
		frame.add(confirm);
	}
}

package GUIpackage;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.simple.JSONObject;
import throunHugbo.*;
import java.text.ParseException;
import javax.swing.border.LineBorder;
import GUIpackage.*;


public class booking {
	private JFrame frame;
	private JLabel tripName;
	private JLabel tripDesc;
	private JButton timeToBook;
	private JTextField howMany;
	private JButton leaveAReview;
	private DBConnector DB;
	private JButton leaveWindow;
	private JLabel errorMessage;
	private JButton removeBooking;
	private JPanel reviewPanel;
	
	private Trip bookThis;
	private JTextField review;
	private String Id;
	private String name;
	private Update update;
	public booking(String i, Trip t, String n){
		this.bookThis = t;
		this.Id = i;
		this.name = n;
		initialize();
	}
	
	private class book implements ActionListener{
		public void actionPerformed(ActionEvent e){
			update = new Update();
			try{
				update.bookATrip(bookThis.getId(), Integer.parseInt(howMany.getText()), Id, name);
			}
			catch(SQLException ex){
				errorMessage.setText("Account already exists");
				errorMessage.setVisible(true);
			}
			catch(NumberFormatException exc){
				errorMessage.setText("Put a number in the textfield");
				errorMessage.setVisible(true);
				
			}
		}
	}
	private class leaveReview implements ActionListener{
		public void actionPerformed(ActionEvent e){
			update = new Update();
			try{
				if(!review.getText().equals("")){
					update.addAReview(Id, name, bookThis.getId(), review.getText());
				}
				errorMessage.setText("You left a review");
			}
			catch(NumberFormatException exc){	
			}
			catch(SQLException n){
				errorMessage.setText("Was not able to leave a review, you are not booked on this trip");
				System.out.println("sql");
			}
			frame.revalidate();
			frame.repaint();
		}
	}
	
	private class closeWindow implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.setVisible(false);
			frame.dispose();
		}
	}
	
	private class remove implements ActionListener{
		public void actionPerformed(ActionEvent e){
			update = new Update();
			try{
				update.removeABooking(Id, bookThis.getId(), name);
			}
			catch(SQLException ex){
				System.out.println(e);
			}
		}
	}
	
	public void initialize(){
		try{
			DB = new DBConnector();
			List<String[]> s = DB.findReviews(bookThis.getId());
			if(!s.isEmpty()){
				reviewPanel = new JPanel(new GridLayout(0, 1));
				for(String[] k: s){
					JLabel lab = new JLabel();
					lab.setText(k[1] + ": " + k[0]);
					lab.setPreferredSize(new Dimension(100, 100));
					lab.setBorder(new LineBorder(Color.black));
					lab.setFont(new Font("TimesRoman", Font.BOLD, 45));
					reviewPanel.add(lab);
				}
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
		errorMessage = new JLabel();
		errorMessage.setBackground(Color.GREEN);
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout( FlowLayout.CENTER));
		
		timeToBook = new JButton();
		timeToBook.setBackground(Color.blue);
		timeToBook.setOpaque(true);
		timeToBook.setPreferredSize(new Dimension(150, 40));
		timeToBook.setText("Find  Booked trips");
		timeToBook.addActionListener(new book());
		
		removeBooking = new JButton();
		removeBooking.setBackground(Color.CYAN);
		removeBooking.setOpaque(true);
		removeBooking.setPreferredSize(new Dimension(150, 40));
		removeBooking.setText("Remove this booking");
		removeBooking.addActionListener(new remove());
		
		leaveWindow = new JButton();
		leaveWindow.setBackground(Color.black);
		leaveWindow.setOpaque(true);
		leaveWindow.setPreferredSize(new Dimension(150, 40));
		leaveWindow.setText("Leave this window");
		leaveWindow.addActionListener(new closeWindow());
		
		leaveAReview = new JButton();
		leaveAReview.setBackground(Color.red);
		leaveAReview.setOpaque(true);
		leaveAReview.setPreferredSize(new Dimension(150, 40));
		leaveAReview.setText("Leave A review");
		leaveAReview.addActionListener(new leaveReview());
		
		tripName = new JLabel();
		tripDesc = new JLabel();
		tripName.setText("Name:"+bookThis.getName());
		tripDesc.setText("Description" + bookThis.getDesc());
		tripName.setVisible(true);
		tripDesc.setVisible(true);
		review = new JTextField(15);
		howMany = new JTextField(10);
		
		frame.add(howMany);
		frame.add(review);
		frame.add(tripName);
		frame.add(tripDesc);
		frame.add(timeToBook);
		frame.add(leaveAReview);
		frame.add(leaveWindow);
		frame.add(removeBooking);
		frame.add(errorMessage);
		if(reviewPanel != null){
			JScrollPane scroll = new JScrollPane(reviewPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setPreferredSize(new Dimension(frame.getWidth() - 25, 250));
			frame.add(scroll);
		}
		errorMessage.setVisible(true);
	}
}

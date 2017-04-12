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
				System.out.println(e);
			}
			catch(NumberFormatException exc){
				
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
			}
			catch(SQLException ex){
				System.out.println(e);
			}
			catch(NumberFormatException exc){
				
			}
		}
	}
	public void initialize(){
		try{
			DB = new DBConnector();
			List<String> s = DB.findReviews(bookThis.getId());
			for(String k: s)System.out.println(k);
		}
		catch(SQLException e){
			
		}
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
	}
}

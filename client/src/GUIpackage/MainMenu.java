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


public class MainMenu {
	//I don't know what to call these
	private String Id;
	private String name;
	private List<Trip> results;
	private Search Engine = new Search();
	private Double[] startOptions = {1.0, 2.0, 3.0, 4.0, 7.0, 8.0, 9.0, 10.0, 14.0};
	private Integer[] durationOptions = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	private List<Double> startList = new ArrayList<Double>();
	private List<Integer> durationList = new ArrayList<Integer>();
	
	//Here are all the graphic classes
	private JFrame frame;
	private JButton bookedTrips;
	private JComboBox<Double> start;
	private JComboBox<Integer> duration;
	private JTextField cost;
	private JTextField capacity;
	private JButton queryButton;
	private JTextField date1;
	private JTextField date2;
	private JLabel badInput;
	private List<TripLabel> presentedTrips = new ArrayList<TripLabel>();
	
	public MainMenu(String i,String n){
		this.Id = i;
		this.name = n;
		initialize();
	}
	
	private class findBookedTrips implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Engine.findBookedTrips(Id, name);
			results = Engine.getFoundTrips();
		}
	}
	
	private class addToStartList implements ActionListener {
		public void actionPerformed(ActionEvent e){
			startList.add((Double)start.getSelectedItem());
		}
	}
	
	private class addToDurationList implements ActionListener {
		public void actionPerformed(ActionEvent e){
			durationList.add((int)duration.getSelectedItem());
			System.out.println(duration.getSelectedItem());
		}
	}
	
	private static class TripLabel extends JLabel{
		private static Trip t;
		public TripLabel(Trip s, String HTML){
			super(HTML);
			this.t = s;
			
		}
	}
	
	public static String toHTML(Trip s){
		String html = "<html>";
		html = html + s.getName() + "<br>";
		html = html + s.getDesc() + "<br>";
		html = html + s.getCapacity() + "<br>";
		html = html + s.getDate() + "</html>";
		return html;
	}
	
	private class pressQueryButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			int price = 0;
			int howMany = 0;
			String d1 = "";
			String d2 = "";
			try{
				String c = cost.getText();
				if(!c.equals(""))price = Integer.parseInt(c);
				c = capacity.getText();
				if(!c.equals(""))howMany = Integer.parseInt(c);
				String arrive = date1.getText();
				String depart = date2.getText();
				System.out.println(Engine.isValidDate(arrive));
				if(Engine.isValidDate(arrive) && Engine.isValidDate(depart)){
					d1 = arrive;
					d2 = depart;
				}
				System.out.println(d1);
				Engine.resolveQuery(durationList, startList, d1, d2, price, howMany);
				List<Trip> res = Engine.getFoundTrips();
				for(TripLabel t: presentedTrips) frame.remove(t);
				presentedTrips.clear();
				for(Trip t: res){
					TripLabel temp = new TripLabel(t, toHTML(t));
					presentedTrips.add(temp);
				}
				for(TripLabel t: presentedTrips){
					frame.add(t);
					t.setVisible(true);
				}
				frame.remove(badInput);
				frame.revalidate();
				frame.repaint();
			}
			catch(NumberFormatException p){
				for(TripLabel t: presentedTrips)frame.remove(t);
				presentedTrips.clear();
				frame.add(badInput);
				badInput.setVisible(true);
				badInput.setText("One of the fields is not formatted right, date is supposed to be yyyy-mm-dd");
			}
			finally{
				System.out.println("er i finally");
			}
		}
	}
	
	public void initialize(){
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		//Buttons
		bookedTrips = new JButton();
		bookedTrips.setBackground(Color.blue);
		bookedTrips.setOpaque(true);
		bookedTrips.setPreferredSize(new Dimension(150, 40));
		bookedTrips.setText("Find  Booked trips");
		bookedTrips.addActionListener(new findBookedTrips());
		queryButton = new JButton();
		queryButton.setBackground(Color.black);
		queryButton.setOpaque(true);
		queryButton.setPreferredSize(new Dimension(150, 40));
		queryButton.setText("Find relevant trips");
		queryButton.addActionListener(new pressQueryButton());
		//combo boxes
		start = new JComboBox<Double>(startOptions);
		start.addActionListener(new addToStartList());
		duration = new JComboBox<Integer>(durationOptions);
		duration.addActionListener(new addToDurationList());
		
		
		//TextFields
		cost = new JTextField(10);
		capacity = new JTextField(10);
		date1 = new JTextField(10);
		date2 = new JTextField(10);
		
		//Label
		badInput = new JLabel();
		
		frame.add(bookedTrips);
		frame.add(start);
		frame.add(duration);
		frame.add(cost);
		frame.add(capacity);
		frame.add(date1);
		frame.add(date2);
		frame.add(queryButton);
		frame.add(badInput);
		badInput.setVisible(false);
	}
}

package GUIpackage;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.simple.JSONObject;
import throunHugbo.*;


public class MainMenu {
	private String Id;
	private String name;
	private List<Trip> results;
	private JFrame frame;
	public MainMenu(String i,String n){
		this.Id = i;
		this.name = n;
		initialize();
	}
	
	public void initialize(){
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
	}
}

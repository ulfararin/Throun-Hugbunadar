package throunHugbo;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Iterator;
import java.text.*;
import java.lang.NumberFormatException;


public class Search{
  private List<Trip> trips = new ArrayList<Trip>();
  //Put the initial value of key and email as null in case a user without an account logs in without any authenication
  private String key = null;
  private DBConnector connection;
  private List<Trip> foundTrips;
  
  public Search(){
    //this.trips = test();
    //sortTrips(prio);
  }
  public List<Trip> getFoundTrips(){
	  return this.foundTrips;
  }
  
  public void resolveQuery(List<Integer> duration, List<Double> start, String d1, String d2, int cost, int capacity){
	  connection = new DBConnector();
	  JSONObject obj = new JSONObject();
	  obj.put("duration", duration);
	  obj.put("start", start);
	  obj.put("arrive", d1);
	  obj.put("depart", d2);
	  obj.put("cost", cost);
	  obj.put("capacity", capacity);
	  foundTrips = connection.query(obj);
  }
  
  public boolean isValidDate(String date)throws NumberFormatException{
	  if(date.length() != 10) return false;
	  try{
		  String[] format = date.split("-");
		  if(format.length != 3) return false;
		  System.out.println(format[0]+ format[1] + format[2]);
		  if(format[0].length() != 4 || format[1].length() != 2 || format[2].length() != 2) return false;
		  int year = Integer.parseInt(format[0]);
		  int mm = Integer.parseInt(format[1]);
		  int dd = Integer.parseInt(format[2]);
	  }
	  finally{
		  
	  }
	  return true;
  }

  public static void main(String[] args){
    Search t = new Search();
  }

  /*public List<Trip> test(){
    int i = 0;
    List<Trip> b = new ArrayList<Trip>();
    while(i < 10) {
      try{
        int cost = (int)(26*Math.random() + 1);
        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
        String paramDateAsString = "2007-12-" + cost;
        Date myDate = null;
        myDate = textFormat.parse(paramDateAsString);
        int capacity = (int)(26*Math.random() + 1);
        String id = "bjo" + cost + 2;
        String name = "bjo" + capacity;
        String desc = "bjo" + cost;
        Trip a = new Trip(name, desc, capacity, id, myDate);
        b.add(a);
        i++;
      }
      catch(ParseException e){
        System.out.println(e);
      }
    }
    return b;
  }*/

  public void sortTrips(String call){
    String func = "get" + call;
    java.lang.reflect.Method method;
    if(!trips.isEmpty()){
      TreeMap tree = new TreeMap();

      for(Trip piv: trips){
        try{
          method = piv.getClass().getMethod(func);
          tree.put(method.invoke(piv), piv);
        }
        catch(IllegalAccessException x){
          System.out.println("Can't do that");
          break;
        }
        catch(InvocationTargetException x){
          System.out.println("bleh");
          break;
        }
        catch(NoSuchMethodException x){
          System.out.println("not available");
          break;
        }
      }

      Set set = tree.entrySet();
      Iterator i = set.iterator();
      while(i.hasNext()) {
          Map.Entry me = (Map.Entry)i.next();
          Trip temp = (Trip)me.getValue();
          trips.add(temp);
       }
    }
  }
  public void findBookedTrips(String name, String pw){
	  this.connection = new DBConnector();
	  this.foundTrips = connection.findBookedTrips(name, pw);
  }
}

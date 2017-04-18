package throunHugbo;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author Björn Guðmundsson
 */
public class DBConnector {
    private  String url = "jdbc:sqlite:C:/Users/Bjorn Gudmundsson/Documents/GitHub/throun-hugbo/Throun-Hugbunadar/Thround.db";
    private  Connection connection = null;

    public DBConnector(){
    }
    public List<DayTrips> query(JSONObject preferences)
   {
       List<DayTrips> results = new ArrayList<DayTrips>();
       try{
         this.connection = DriverManager.getConnection(url);
       }
       catch(SQLException e){
         System.out.println(e);
       }
       try {
         // db parameteresults
         // create a connection to the database

         try{
         String select = "SELECT * FROM Trip WHERE";
         List<Double> start = (List<Double>)preferences.get("start");
         List<Integer> duration = (List<Integer>)preferences.get("duration");
         int cost = (int)preferences.get("cost");
         int capacity = (int)preferences.get("capacity");
         String d1 = (String)preferences.get("arrive");
         String d2 = (String)preferences.get("depart");
         /*Date date1 = (Date)preferences.get("date1");
         Date date2 = (Date)preferences.get("date2");*/

         if(!start.isEmpty()){
           select = select + " (";
           for(Double st: start.subList(0, start.size() - 1)) {
               select = select + " start = " + st + " OR ";
             }
             select = select + " start = " + start.get(start.size() - 1) + ")";
         }

         if(!duration.isEmpty()){
           int len = select.length();
           String k = select.substring(len - 5, len);
           if(k.equals("WHERE")){
             select = select + " (";
           }
           else select = select + " AND (";
           for(int dur: duration.subList(0, duration.size() - 1))
           {
               select = select + " duration = " + dur + " OR ";
           }
           select = select + " duration = " + duration.get(duration.size() - 1) + ")";
         }

         String where = select.substring(select.length()-5, select.length());
         if(cost > 0){
           if(!where.equals("WHERE")){
             select = select + " AND cost <= " + cost;
           }
           else select = select + " cost <= " + cost;
         }
         if(capacity > 0){
           where = select.substring(select.length()-5, select.length());
           if(!where.equals("WHERE")){
             select = select + " AND cap >= " + capacity;
           }
           else select = select + " cap >= " + capacity;
         }
         
         if(!d1.equals("") || !d2.equals("")){
           where = select.substring(select.length()-5, select.length());
           if(!where.equals("WHERE")){
             select = select + " AND time BETWEEN " + "\'" + d1 + "\'" + " AND " + "\'" + d2 + "\'";
           }
           else select = select + " time BETWEEN " +  "\'" + d1 + "\'" +  " AND " +  "\'" + d2 + "\'";
         }
         System.out.println(select);

         if(select.substring(select.length() - 5, select.length()).equals("WHERE")) select = "SELECT * FROM Trip";
         //System.out.println(select);
         Statement statement = connection.createStatement();
         //statement.setQueryTimeout(30);

         ResultSet rs = statement.executeQuery(select);

         while(rs.next())
         {
           String name = rs.getString("name");
           String desc = rs.getString("desc");
           int capa = rs.getInt("cap");
           String deit = rs.getString("time");
           int i = rs.getInt("id");
           DayTrips newTrip = new DayTrips(name, desc, capa, i, deit);
           results.add(newTrip);
         }
         return results;
       }
       catch(NullPointerException e){
         System.out.println("Not the right format");
         return results;
       }
       catch(ClassCastException e){
         System.out.println("wrong formatting of json object");
         return results;
       }
       }
       catch (SQLException e) {
         return results;
       }
       finally
       {
         try {
             if (connection != null) {
               connection.close();
               return results;
             }
         }
         catch (SQLException ex) {
             System.out.println(ex.getMessage());
             return results;
         }
       }
   }

    public List<DayTrips> findBookedTrips(String key, String key2){
      List<DayTrips> bookedTrips = new ArrayList<DayTrips>();
      try{
        String search = "SELECT * FROM Trip WHERE Id = (SELECT tripID FROM Booking WHERE userID = ? AND userName = ?)";
        this.connection = DriverManager.getConnection(url);
        PreparedStatement booked = connection.prepareStatement(search);
        booked.setString(1, key);
        booked.setString(2, key2);
        ResultSet rs = booked.executeQuery();
        while(rs.next()){
          String name = rs.getString("name");
          String desc = rs.getString("desc");
          int capa = rs.getInt("cap");
          String deit = rs.getString("time");
          int i = rs.getInt("id");
          DayTrips newTrip = new DayTrips(name, desc, capa, i, deit);
          bookedTrips.add(newTrip);
        }
        connection.close();
        return bookedTrips;
      }
      catch(SQLException e){
        System.out.println("yo");
        return bookedTrips;
      }
    }

    public boolean findUser(String name, String Id){
      boolean result = false;
      PreparedStatement user = null;
      try{
        this.connection = DriverManager.getConnection(url);
        String select = "SELECT * FROM User WHERE name = ? " + " AND Id = ?";
        user = connection.prepareStatement(select);
        user.setString(1, name);
        user.setString(2, Id);
        result = user.executeQuery().next();
        System.out.println(user.executeQuery().next());
      }
      catch(SQLException e){
    	  System.out.println(e);
        return false;
      }
      finally{
        if(user != null){
          try{
            user.close();
          }
          catch(SQLException e){}
        }
        if(connection != null){
          try{
            connection.close();
          }
          catch(SQLException e){}
        }
        return result;
      }
    }
    
    public List<String[]> findReviews(int tripId) throws SQLException{
    	PreparedStatement search = null;
    	List<String[]> res = new ArrayList<String[]>();
    	try{
    		this.connection = DriverManager.getConnection(url);
    		String sel = "SELECT review, userName FROM Booking WHERE tripId = ?";
    		search = connection.prepareStatement(sel);
    		
    		search.setInt(1,tripId);
    		ResultSet rs = search.executeQuery();
    		while(rs.next()){
    			String r = rs.getString("review");
    			String r2 = rs.getString("userName");
    			String[] arr = {r, r2};
    			if(r != null && !r.equals("")){
    				res.add(arr);
    			}
    		}
    		return res;
    	}
    	finally{
    		if(search != null){
    			search.close();
    		}
    		if(connection != null){
    			connection.close();
    		}
    	}
    }

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws ClassNotFoundException{
       JSONObject obj = new JSONObject();
       int arg = Integer.parseInt(args[0]);
       int arg1 = Integer.parseInt(args[1]);
       Double arg2 = Double.parseDouble(args[2]);
       int arg3 = Integer.parseInt(args[3]);
       List<Integer> s = new ArrayList<Integer>();
       //s.add(arg);
       List<Double> b = new ArrayList<Double>();
       //b.add(arg2);
       String key = args[4];
       obj.put("duration", s);
       obj.put("start", b);
       obj.put("cost", arg1);
       obj.put("capacity", arg3);
       DBConnector test = new DBConnector();
       //List<Trip> query = test.query(obj);
       //List<Trip> booked = test.findBookedTrips("2", "2");
       //for(Trip t: query) System.out.println(t);
     }
}

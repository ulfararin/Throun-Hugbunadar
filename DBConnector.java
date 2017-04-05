import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;

/**
 *
 * @author Björn Guðmundsson
 */
public class DBConnector {
    private  String url = "jdbc:sqlite:Thround.db";
    private  Connection connection = null;

    public DBConnector(){
    }
    public List<Trip> query(JSONObject preferences)
   {
       List<Trip> results = new ArrayList<Trip>();
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
         /*Date date1 = (Date)preferences.get("date1");
         Date date2 = (Date)preferences.get("date2");*/

         if(!start.isEmpty()){
           select = select + " (";
           for(Double st: start.subList(0, start.size() - 1)) {
               select = select + " start = " + st + " OR ";
             }
             select = select + " start = " + start.get(start.size() - 1) + ")";
         }
         System.out.println(start);

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
         /*if(date1 != null && date2 != null){
           where = select.substring(select.length()-5, select.length());
           if(!where.equals("WHERE")){
             select = select + " AND time BETWEEN " + date1 + " AND " + date2;
           }
           else select = select + " time BETWEEN " + date1 + " AND " + date2;
         }*/
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
           Date deit = new Date(rs.getDate("time").getTime());
           int i = rs.getInt("id");
           Trip newTrip = new Trip(name, desc, capa, i, deit);
           results.add(newTrip);
         }
         //for(Trip t : results) System.out.println(t);
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

    public List<Trip> findBookedTrips(String key){
      List<Trip> bookedTrips = new ArrayList<Trip>();
      try{
        String search = "SELECT * FROM Trip WHERE Id = (SELECT tripID FROM Booking WHERE userID = ?)";
        this.connection = DriverManager.getConnection(url);
        PreparedStatement booked = connection.prepareStatement(search);
        booked.setString(1, key);
        ResultSet rs = booked.executeQuery();
        while(rs.next()){
          String name = rs.getString("name");
          String desc = rs.getString("desc");
          int capa = rs.getInt("cap");
          Date deit = new Date(rs.getDate("time").getTime());
          int i = rs.getInt("id");
          Trip newTrip = new Trip(name, desc, capa, i, deit);
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
       obj.put("capacity", key);
       DBConnector test = new DBConnector();
       //List<Trip> query = test.query(obj);
       List<Trip> booked = test.findBookedTrips("bjo");
       for(Trip t: booked) System.out.println(t);
     }
}

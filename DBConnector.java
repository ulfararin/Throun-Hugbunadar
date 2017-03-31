import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;

/**
 *
 * @author Björn Guðmundsson
 */
public class DBConnector {
    private  List<Trip> bookedTrips = new ArrayList<Trip>();
    private  String url = "jdbc:sqlite:thround.db";
    private  Connection connection = null;
    private  List<Trip> results = new ArrayList<Trip>();

    public DBConnector(){
    }
    public void query(JSONObject preferences)
    {
        try{
          this.connection = DriverManager.getConnection(url);
        }
        catch(SQLException e){
          System.out.println(e);
        }
        try {
          // db parameteresults
          // create a connection to the database
          String select = "SELECT * FROM Trip WHERE";
          List<Double> start = (List<Double>)preferences.get("start");
          List<Integer> duration = (List<Integer>)preferences.get("duration");
          int cost = (int)preferences.get("cost");
          int capacity = (int)preferences.get("capacity");

          if(!start.isEmpty()){
            select = select + " (";
            for(Double st: start.subList(0, start.size() - 1)) {
                select = select + " start = " + st + " OR ";
              }
              select = select + " start = " + start.get(start.size() - 1) + ")";
          }

          if(!duration.isEmpty()){
            select = select + " AND (";
            for(int dur: duration.subList(0, duration.size() - 1))
            {
                select = select + " duration = " + dur + " OR ";
            }
            select = select + " duration = " + duration.get(duration.size() - 1) + ")";
          }

          if(cost > 0){
            select = select + " AND cost <= " + cost;
          }
          if(capacity > 0){
            select = select + " AND cap >= " + capacity;
          }
          System.out.println(select);

          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);
          statement.executeQuery(select);
          ResultSet rs = statement.getResultSet();

          while(rs.next())
          {
            String name = rs.getString("name");
            String desc = rs.getString("desc");
            int capa = rs.getInt("cap");
            java.util.Date deit = new Date(rs.getDate("time").getTime());
            String i = rs.getString("id");
            Trip newTrip = new Trip(name, desc, capa, i, deit);
            results.add(newTrip);
          }
          for(Trip t : results) System.out.println(t);
        }
        catch (SQLException e) {
          System.out.println(e.getMessage());
        }
        finally
        {
          try {
              if (connection != null) {
                connection.close();
              }
          }
          catch (SQLException ex) {
              System.out.println(ex.getMessage());
          }
        }
    }

    public void findBookedTrips(String key){
      String newkey = "\"" + key + "\"";
      String book = "SELECT * FROM Trip WHERE Id = (SELECT tripID FROM Booking WHERE userID = " + newkey + ")";
      System.out.println(book);
      try{
        this.connection = DriverManager.getConnection(url);
        Statement booked = connection.createStatement();
        ResultSet rs = booked.executeQuery(book);
        while(rs.next()){
          System.out.println(rs.getString("name"));
        }
        connection.close();
      }
      catch(SQLException e){
        System.out.println(e);
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
      s.add(arg);
      List<Double> b = new ArrayList<Double>();
      b.add(arg2);
      obj.put("duration", s);
      obj.put("start", b);
      obj.put("cost", arg1);
      obj.put("capacity", arg3);
      String key = args[4];
      DBConnector test = new DBConnector();
      //test.query(obj);
      test.findBookedTrips("poop");
    }
}
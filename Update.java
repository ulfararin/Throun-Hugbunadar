import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;
/**
*
*@author Bjorn Gudmundsson
*/
public class Update{
  private  String url = "jdbc:sqlite:Thround.db";
  private Connection connection = null;
  public Update(){}
  public void bookATrip(String tripId, int howMany, String userId){
    PreparedStatement check = null;
    PreparedStatement update = null;
    PreparedStatement change = null;
    try{
      this.connection = DriverManager.getConnection(url);
      String booking = "INSERT INTO Booking values(?, ?, null)";
      String search = "SELECT * FROM User WHERE Id = ?";
      String toChange = "Update Trip SET cap = cap - ? " + " WHERE Id = ?";
      check = connection.prepareStatement(search);
      check.setString(1, userId);
      ResultSet rs = check.executeQuery();
      if(!rs.next()){
        return;
      }
      else{
        update = connection.prepareStatement(booking);
        update.setString(1, userId);
        update.setString(2, tripId);
        update.executeUpdate();
        change = connection.prepareStatement(toChange);
        change.setInt(1, howMany);
        change.setString(2, tripId);
        change.executeUpdate();
      }
      if(check != null) check.close();
      if(update != null) update.close();
      if(change != null) change.close();
      if(connection != null) update.close();
    }
    catch(SQLException e){
      System.out.println(e);
    }
  }
  public void removeABooking(String userId, String tripId){
    PreparedStatement delete = null;
    try{
      String del = "DELETE FROM Booking WHERE userID = ? " + "AND tripID = ?";
      this.connection = DriverManager.getConnection(url);
      delete = connection.prepareStatement(del);
      delete.setString(1, userId);
      delete.setString(2, tripId);
      delete.executeUpdate();
      if(delete != null) delete.close();
      if(connection != null) connection.close();
    }
    catch(SQLException e){
      System.out.println(e);
    }
  }
  public void removeATrip(String tripId){
    PreparedStatement remove = null;
    PreparedStatement removeBooking;
    try{
      this.connection = DriverManager.getConnection(url);
      String rem = "DELETE FROM Trip WHERE Id = ?";
      String remBook = "DELETE FROM Booking WHERE tripId = ?";
      removeBooking = connection.prepareStatement(remBook);
      removeBooking.setString(1, tripId);
      removeBooking.executeUpdate();
      remove = connection.prepareStatement(rem);
      remove.setString(1, tripId);
      remove.executeUpdate();
    }
    catch(SQLException e){

    }
  }

  public void addATrip(){

  }
  public static void main(String args[]){
    Update test = new Update();
    //test.bookATrip("Gt7fO", 3, "ket");
    test.removeABooking("bjo", "Gt7fO");
    test.removeATrip("Gt7fO");
  }
}

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
  public void bookATrip(int tripId, int howMany, int userId){
    PreparedStatement check = null;
    PreparedStatement update = null;
    PreparedStatement change = null;
    try{
      this.connection = DriverManager.getConnection(url);
      String booking = "INSERT INTO Booking values(?, ?, null)";
      String search = "SELECT * FROM User WHERE Id = ?";
      String toChange = "Update Trip SET cap = cap - ? " + " WHERE Id = ?";
      check = connection.prepareStatement(search);
      check.setInt(1, userId);
      ResultSet rs = check.executeQuery();
      if(!rs.next()){
        return;
      }
      else{
        update = connection.prepareStatement(booking);
        update.setInt(1, userId);
        update.setInt(2, tripId);
        update.executeUpdate();
        change = connection.prepareStatement(toChange);
        change.setInt(1, howMany);
        change.setInt(2, tripId);
        change.executeUpdate();
      }
    }
    catch(SQLException e){
      System.out.println(e);
    }
    finally{
      if(check != null){
        try{
           check.close();
        }
        catch(SQLException e){

        }
      }
      if(update != null){
        try{
           update.close();
        }
        catch(SQLException e){

        }
      }
      if(change != null){
        try{
           change.close();
        }
        catch(SQLException e){
        }
      }
      if(connection != null){
        try{
          connection.close();
        }
        catch(SQLException e){}
      }
    }
  }
  public void removeABooking(int userId, int tripId){
    PreparedStatement delete = null;
    try{
      String del = "DELETE FROM Booking WHERE userID = ? " + "AND tripID = ?";
      this.connection = DriverManager.getConnection(url);
      delete = connection.prepareStatement(del);
      delete.setInt(1, userId);
      delete.setInt(2, tripId);
      delete.executeUpdate();
    }
    catch(SQLException e){
      System.out.println(e);
    }
    finally{
      if(delete != null){
        try{
           delete.close();
        }
        catch(SQLException e){}
      }
      if(connection != null){
        try{
           connection.close();
        }
        catch(SQLException e){}
      }
    }
  }
  public void removeATrip(int tripId){
    PreparedStatement remove = null;
    PreparedStatement removeBooking = null;
    try{
      this.connection = DriverManager.getConnection(url);
      String rem = "DELETE FROM Trip WHERE Id = ?";
      String remBook = "DELETE FROM Booking WHERE tripId = ?";
      removeBooking = connection.prepareStatement(remBook);
      removeBooking.setInt(1, tripId);
      removeBooking.executeUpdate();
      remove = connection.prepareStatement(rem);
      remove.setInt(1, tripId);
      remove.executeUpdate();
    }
    catch(SQLException e){}
    finally{
      if(connection != null){
        try{
           connection.close();
        }
        catch(SQLException e){

        }
      }
      if(remove != null){
        try{
           remove.close();
        }
        catch(SQLException e){

        }
      }
      if(removeBooking != null){
        try{
           removeBooking.close();
        }
        catch(SQLException e){
        }
      }
    }
  }

  public void addATrip(){
  }

  public void addAReview(String userId, String review){
    try{
      this.connection = DriverManager.getConnection(url);
      PreparedStatement add = null;
    }
    catch(SQLException e){}
  }
  public static void main(String args[]){
    Update test = new Update();
    //test.bookATrip("Gt7fO", 3, "ket");
    //test.removeABooking("bjo", "Gt7fO");
    //test.removeATrip("Gt7fO");
  }
}

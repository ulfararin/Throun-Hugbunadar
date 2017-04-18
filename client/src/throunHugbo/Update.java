package throunHugbo;

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
  private  String url = "jdbc:sqlite:C:/Users/Bjorn Gudmundsson/Documents/GitHub/throun-hugbo/Throun-Hugbunadar/Thround.db";
  private Connection connection = null;
  public Update(){}
  public void bookATrip(int tripId, int howMany, String userId, String userName) throws SQLException, org.sqlite.SQLiteException{
    PreparedStatement check = null;
    PreparedStatement update = null;
    PreparedStatement change = null;
    try{
      this.connection = DriverManager.getConnection(url);
      String booking = "INSERT INTO Booking values(?, ?, ?,null)";
      String search = "SELECT * FROM User WHERE Id = ?";
      String toChange = "Update Trip SET cap = cap - ? " + " WHERE Id = ?";
      check = connection.prepareStatement(search);
      check.setString(1, userId);
      ResultSet rs = check.executeQuery();
      if(!rs.next()){
    	throw new SQLException();
      }
      else{
        update = connection.prepareStatement(booking);
        update.setString(1, userId);
        update.setString(2, userName);
        update.setInt(3, tripId);
        update.executeUpdate();
        change = connection.prepareStatement(toChange);
        change.setInt(1, howMany);
        change.setInt(2, tripId);
        change.executeUpdate();
      }
    }
    catch(SQLException e){
      System.out.println("yo");
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
  public void removeABooking(String userId, int tripId, String userName) throws SQLException{
    PreparedStatement delete = null;
    try{
      String del = "DELETE FROM Booking WHERE userID = ? " + "AND tripID = ? AND UserName = ?";
      this.connection = DriverManager.getConnection(url);
      delete = connection.prepareStatement(del);
      delete.setString(1, userId);
      delete.setInt(2, tripId);
      delete.setString(3, userName);
      delete.executeUpdate();
    }
    finally{
      if(delete != null){
          delete.close();
      }
      if(connection != null){
          connection.close();
      }
    }
  }
  
  public void addUser(String name, String email, String Id) throws SQLException {
	  PreparedStatement newUser = null;
	    try{
	      this.connection = DriverManager.getConnection(url);
	      String newAccount = "INSERT INTO USER values (?, ?, ?)";
	      newUser = connection.prepareStatement(newAccount);
	      newUser.setString(1,  Id);
	      newUser.setString(2, email);
	      newUser.setString(3, name);
	      newUser.executeUpdate();
	    }
	    finally{
	      if(newUser != null){
	        newUser.close();
	      }
	      if(connection != null){
	        connection.close();
	      }
	    }
  }
  
  public void removeATrip(int tripId){
    PreparedStatement remove = null;
    PreparedStatement removeBooking = null;
    try{
      this.connection = DriverManager.getConnection(url);
      System.out.println("bjo");
      String rem = "DELETE FROM Trip WHERE Id = ?";
      String remBook = "DELETE FROM Booking WHERE tripId = ?";
      removeBooking = connection.prepareStatement(remBook);
      removeBooking.setInt(1, tripId);
      removeBooking.executeUpdate();
      remove = connection.prepareStatement(rem);
      remove.setInt(1, tripId);
      remove.executeUpdate();
    }
    catch(SQLException e){
    	System.out.println("nadi ekki ad tengja");
    }
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

  public void addAReview(String userId, String userName, int tripId, String review)throws SQLException{
	  DBConnector DB = new DBConnector();
	  PreparedStatement add = null;
	  try{
		  String insert = "UPDATE Booking SET review = ? WHERE tripId = ? AND userName = ? AND userId = ?";
		  List<DayTrips> check = DB.findBookedTrips(userId, userName);
		  if(check.isEmpty()){
			  System.out.println("yoyoyo");
			  throw new SQLException();
		  }
		  else{
			  this.connection = DriverManager.getConnection(url);
			  add = connection.prepareStatement(insert);
			  add.setString(1, review);
			  add.setInt(2, tripId);
			  add.setString(3, userName);
			  add.setString(4, userId);
			  add.executeUpdate();
		  }
    }
    finally{
    	if(add != null){
    		add.close();
    	}
    	if(connection != null){
    		connection.close();
    	}
    }
  }
  public static void main(String args[]){
    Update test = new Update();
    //test.removeABooking(1, 2);
    test.removeATrip(1);
  }
}

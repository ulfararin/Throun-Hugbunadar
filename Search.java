import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;
import java.util.SortedMap;


public class Search{
  private List<Trip> trips = new ArrayList<Trip>();
  //Put the initial value of key and email as null in case a user without an account logs in without any authenication
  private String key = null;
  private String priorities;
  public Search(String k, String prio){
    this.key = k;
    this.priorities = prio;
  }
  public static void main(String[] args){
    System.out.println("yo");
  }
}

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


public class Search{
  private List<Trip> trips = new ArrayList<Trip>();
  //Put the initial value of key and email as null in case a user without an account logs in without any authenication
  private String key = null;
  public Search(String k, String prio){
    this.key = k;
    //this.trips = test();
    //sortTrips(prio);
  }

  public static void main(String[] args){
    System.out.println("yo");
    Search t = new Search("bab", "Capacity");
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
          String key = "" + method.invoke(piv);
          try{
            int intkey = Integer.parseInt(key);
            tree.put(intkey, piv);
          }
          catch(NumberFormatException e){
            tree.put(key, piv);
          }
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
          System.out.print(me.getKey() + ": ");
          System.out.println(me.getValue());
       }
    }
  }
}

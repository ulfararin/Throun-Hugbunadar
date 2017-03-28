import java.util.Calendar;
import java.util.Date;

public class Trip{
  private String name;
  private String desc;
  private java.util.Date date;
  private int capacity;
  private String id;


  public Trip (String n, String d, int c, String i, java.util.Date da){
    this.name = n;
    this.desc = d;
    this.date = da;
    this.capacity = c;
    this.id = i;
  }
  public String getName(){
    return this.name;
  }

  public String getDesc(){
    return this.desc;
  }

  public java.util.Date getDate(){
    return this.date;
  }

  public int getCapacity(){
    return this.capacity;
  }

  public String getId(){
    return this.id;
  }

  public String toString(){
    String print = name +  " " + desc +  " "  + capacity +  " "  + id;
    return print;
  }
  public static void main(String[] args){
    String nafn = args[0];
    String des = args[1];
    int cap = Integer.parseInt(args[2]);
    int slots = Integer.parseInt(args[3]);
    String idee = args[4];
  }
}

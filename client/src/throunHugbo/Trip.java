package throunHugbo;

import java.util.Calendar;
import java.util.Date;

public class Trip{
  private String name;
  private String desc;
  private String date;
  private int capacity;
  private int id;


  public Trip (String n, String d, int c, int i, String da){
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

  public String getDate(){
    return this.date;
  }

  public int getCapacity(){
    return this.capacity;
  }

  public int getId(){
    return this.id;
  }

  public String toString(){
    String print = name +  " " + desc +  " "  + capacity +  " "  + id + " " + date;
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

import static org.junit.Assert.*;
import org.junit.*;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.util.Date;

public class SearchTest{
  @Test
  public void evaluatesSearchEmpty(){
    List<String> evaluate = new ArrayList<String>();
    List<String> Ids = new ArrayList<String>();
    evaluate.add("Gt7fO");
    evaluate.add("W3jgY");
    evaluate.add("MxuCe");
    evaluate.add("yoGWU");
    evaluate.add("zekKd");
    evaluate.add("Jz7F1");
    evaluate.add("zSdPS");
    evaluate.add("EM2vx");
    evaluate.add("E0GV6");
    evaluate.add("cCszZ");
    DBConnector toCompare = new DBConnector();
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    int cost = 0;
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("cost", cost);
    obj.put("capacity", capacity);
    List<Trip> results = toCompare.query(obj);
    for(Trip t: results) Ids.add(t.getId());
    assertTrue(Ids.equals(evaluate));
  }

  @Test
  public void evaluateSearchWrongFormat(){
    DBConnector toCompare = new DBConnector();
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    int cost = 0;
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("NotRightName", cost);
    obj.put("NotRightName2", capacity);
    List<Trip> compare = toCompare.query(obj);
    List<Trip> toEvaluate = new ArrayList<Trip>();
    assertTrue(compare.isEmpty());
  }

  @Test
  public void evaluateSpecificSearchWithCost(){
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    int cost = 10000;
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("cost", cost);
    obj.put("capacity", capacity);
    DBConnector toCompare = new DBConnector();
    List<Trip> toEvaluate = toCompare.query(obj);
    Trip compare = toEvaluate.get(0);
    assertEquals("Gt7fO", compare.getId());
  }

  @Test
  public void evaluateWrongJSONFormatSearch(){
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    String cost = "ThisShouldGiveEmpty";
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("cost", cost);
    obj.put("capacity", capacity);
    DBConnector toCompare = new DBConnector();
    List<Trip> evaluate = toCompare.query(obj);
    assertTrue(evaluate.isEmpty());
  }

  @Test
  public void evaluateSpecificSearchWithManyVariable(){
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    s.add(9);
    s.add(3);
    int cost = 12000;
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("cost", cost);
    obj.put("capacity", capacity);
    DBConnector toCompare = new DBConnector();
    List<Trip> toEvaluate = toCompare.query(obj);
    List<String> temp = new ArrayList<String>();
    List<String> compareTo = new ArrayList<String>();
    for(Trip t: toEvaluate) temp.add(t.getId());
    compareTo.add("Gt7fO");
    compareTo.add("MxuCe");
    assertTrue(compareTo.equals(temp));
  }

  @Test
  public void evaluateSpecificSearchWithStart(){
      JSONObject obj = new JSONObject();
      List<Integer> s = new ArrayList<Integer>();
      List<Double> b = new ArrayList<Double>();
      int cost = 0;
      int capacity = 0;
      b.add(10.00);
      obj.put("duration", s);
      obj.put("start", b);
      obj.put("cost", cost);
      obj.put("capacity", capacity);
      DBConnector toCompare = new DBConnector();
      List<Trip> toEvaluate = toCompare.query(obj);
      Trip compare = toEvaluate.get(0);
      assertEquals("Gt7fO", compare.getId());
  }

  @Test
  public void evaluateSpecificSearchWithCapacity(){
      JSONObject obj = new JSONObject();
      List<Integer> s = new ArrayList<Integer>();
      List<Double> b = new ArrayList<Double>();
      int cost = 0;
      int capacity = 50;
      obj.put("duration", s);
      obj.put("start", b);
      obj.put("cost", cost);
      obj.put("capacity", capacity);
      DBConnector toCompare = new DBConnector();
      List<Trip> toEvaluate = toCompare.query(obj);
      Trip compare = toEvaluate.get(0);
      assertEquals("EM2vx", compare.getId());
  }

  @Test
  public void evaluateSpecificSearchWithDuration(){
    JSONObject obj = new JSONObject();
    List<Integer> s = new ArrayList<Integer>();
    List<Double> b = new ArrayList<Double>();
    s.add(9);
    int cost = 0;
    int capacity = 0;
    obj.put("duration", s);
    obj.put("start", b);
    obj.put("cost", cost);
    obj.put("capacity", capacity);
    DBConnector toCompare = new DBConnector();
    List<Trip> toEvaluate = toCompare.query(obj);
    List<String> temp = new ArrayList<String>();
    List<String> compareTo = new ArrayList<String>();
    for(Trip t: toEvaluate) temp.add(t.getId());
    compareTo.add("Gt7fO");
    compareTo.add("MxuCe");
    assertTrue(compareTo.equals(temp));
  }
}

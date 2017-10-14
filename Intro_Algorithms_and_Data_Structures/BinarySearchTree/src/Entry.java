public class Entry {
  private int key;
  private String value;

  public Entry(int k, String v) {
    key = k;
    value = v;
  }

  public int getKey() {return key;}
  public String getValue() {return value;}
  public void setValue(String v) {value = v;}
}
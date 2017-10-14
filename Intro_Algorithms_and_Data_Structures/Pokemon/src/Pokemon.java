import java.util.*;

public class Pokemon implements Comparable<Pokemon>{
  
  private String name;
  private String type1, type2;
  private int total, hp, attack, defense, spAttack, spDefense, speed;
  
  // example stats = "Bulbasaur GRASS POISON 318 45 49	49	65	65	45"
  public Pokemon(String stats) {
    List<String> dataList = new ArrayList<String>(Arrays.asList(stats.split(" ")));
    Iterator<String> data = dataList.iterator();

    name = data.next();
    type1 = data.next();
    if(dataList.size() == 10) { type2 = data.next(); }
    total = Integer.parseInt(data.next());
    hp = Integer.parseInt(data.next());
    attack = Integer.parseInt(data.next());
    defense = Integer.parseInt(data.next());
    spAttack = Integer.parseInt(data.next());
    spDefense = Integer.parseInt(data.next());
    speed = Integer.parseInt(data.next());
  }

  public String getName() { return name; }
  public String getType1() {return type1; }
  public String getType2() {return type2; }
  public int getTotal() {return total; }
  public int getHP() {return hp; }
  public int getAttack() {return attack; }
  public int getDefense() {return defense; }
  public int getSpAttack() {return spAttack; }
  public int getSpDefense() {return spDefense; }
  public int getSpeed() {return speed; }
  
  @Override
  public int compareTo(Pokemon other) {
    return other.total - total;
  }
  
}
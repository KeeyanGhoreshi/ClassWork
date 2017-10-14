import java.util.*;

class PokedexTest {
  public static void  main(String[] args){
    
    Pokedex dex = new MyPokedex();
    
    dex.add(new Pokemon("Charmeleon FIRE 405 58 64 58 80 65 80"));
    dex.add(new Pokemon("Charmeleon FIRE 406 58 64 58 80 65 80"));
    dex.add(new Pokemon("Charmeleon FIRE 407 58 64 58 80 65 80"));
    dex.add(new Pokemon("Charmeleon FIRE 411 58 64 58 80 65 80"));
    dex.add(new Pokemon("Charmeleon FIRE 408 58 64 58 80 65 80"));
    dex.add(new Pokemon("Charmeleon FIRE 410 58 64 58 80 65 80"));
    dex.add(new Pokemon("Bulbasaur GRASS POISON 318 45 49 49 65 65 45"));
    dex.add(new Pokemon("Charizard FIRE DRAGON 634 78 130 111 130 85 100"));
    dex.add(new Pokemon("Charizard FIRE FLYING 634 78 104 78 159 115 100"));
    dex.add(new Pokemon("Squirtle WATER 314 44 48 65 50 64 43"));
    dex.add(new Pokemon("Wartortle WATER 405 59 63 80 65 80 58"));
    dex.add(new Pokemon("Blastoise WATER 530 79 83 100 85 105 78"));
    dex.add(new Pokemon("Blastoise WATER 630 79 103 120 135 115 78"));
    dex.add(new Pokemon("Caterpie BUG 195 45 30 35 20 20 45"));
    dex.add(new Pokemon("Metapod BUG 205 50 20 55 25 25 30"));
    
    if(dex.count("Charmeleon") == 6){
    	System.out.println("Case 1 passed");
    }else{
    	System.out.println("Case 1 failed");
    }
    if(dex.remove("Charmeleon").getTotal() == 411){
    	System.out.println("Case 2 passed");
    }else{
    	System.out.println("Case 2 failed");
    }
    assert(dex.remove("Charmeleon").getTotal() == 410);
    dex.add(new Pokemon("Charmeleon FIRE 409 58 64 58 80 65 80"));
    assert(dex.remove("Charmeleon").getTotal() == 409);
    assert(dex.count("Charmeleon") == 4);
    assert(dex.count("Squirtle") == 1);
    assert(dex.remove("Squirtle").getTotal() == 314);
    assert(dex.count("Squirtle") == 0);
    assert(dex.count("Blastoise") == 2);
    assert(dex.remove("Blastoise").getTotal() == 630);
    assert(dex.count("Blastoise") == 1);
    assert(dex.remove("Blastoise").getTotal() == 530);
    assert(dex.count("Blastoise") == 0);
  }
}
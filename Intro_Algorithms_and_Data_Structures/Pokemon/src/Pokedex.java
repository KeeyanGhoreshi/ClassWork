public interface Pokedex {

  // add a Pokemon v into the Pokedex, the name is given v.
  public void add(Pokemon v);
  
  // add a Pokemon v with name k into the Pokedex
  public void add(String k, Pokemon v);
  
  // Remove a Pokemon with name k from the Pokedex.
  // If there are several with the same name, return the one with the largest total points.
  // If there are no Pokemon with the given name, return null.
  public Pokemon remove(String k);
  
  // Return the number of pokemon with a given name in the Pokedex.
  public int count(String k);
}
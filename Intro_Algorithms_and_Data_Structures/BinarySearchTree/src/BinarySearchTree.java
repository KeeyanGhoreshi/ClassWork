public interface BinarySearchTree {
  
  // Return the String stored in the root node of the tree.
  String getRootValue();
  
  // Insert a new key,value pair and rotate it to the root.
  // Return null of the key is not already present in the tree.
  // Return the old value associated with this key if it is already present in the tree.
  String insert(int key, String value);
  
  // Remove the entry with the given key k (if it exists).
  String remove(int k);
  
  // Return the entry with the smallest key that is greater than or equal to k.
  Entry ceilingEntry(int k);
  
  // Return the entry with the largest key that is less than or equal to k
  Entry floorEntry(int k);
  
}
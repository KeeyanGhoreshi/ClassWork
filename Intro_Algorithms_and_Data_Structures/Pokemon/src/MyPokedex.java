import java.util.ArrayList;

public class MyPokedex implements Pokedex {
	private ArrayList<PriorityQueue> pokedex;
	
	public MyPokedex(){
		pokedex = new ArrayList<>();
		int i = 0;
		while(i<151){
			pokedex.add(null);
			i++;
		}
	}

	@Override
	public void add(Pokemon v) {
		String name = v.getName();
		int index = name.hashCode()%151;
		if(index <0){
			index = index*-1;
		}
		PriorityQueue bucket = pokedex.get(index);
		if(bucket == null){
			PriorityQueue newbucket = new PriorityQueue();
			newbucket.insert(v);
			pokedex.set(index, newbucket);
		}else{
			bucket.insert(v);
		}
		
		

	}

	@Override
	public void add(String k, Pokemon v) {
		int index = k.hashCode()%151;
		if(index <0){
			index = index*-1;
		}
		PriorityQueue bucket = pokedex.get(index);
		if(bucket == null){
			PriorityQueue newbucket = new PriorityQueue();
			newbucket.insert(v);
			pokedex.set(index, newbucket);
		}else{
			bucket.insert(v);
		}

	}

	@Override
	public Pokemon remove(String k) {
		int index = k.hashCode()%151;
		if(index <0){
			index = index*-1;
		}
		PriorityQueue bucket = pokedex.get(index);
		if(bucket == null){
			return null;
		}else{
			return bucket.removeHighest();
		}
		
	}

	@Override
	public int count(String k) {
		int index = k.hashCode()%151;
		if(index <0){
			index = index*-1;
		}
		PriorityQueue bucket = pokedex.get(index);
		if(bucket == null){
			return 0;
		}else{
			return bucket.getList().size()-1;
		}

	}
}

import java.util.ArrayList;

public class PriorityQueue {
	private ArrayList<Pokemon> list;
		
	public PriorityQueue(){
		list = new ArrayList<>();		
		list.add(null);
		}
	public void insert(Pokemon p){
		Pokemon newnode = p;
			list.add(newnode);
			if(list.size()>2){
				sort(list.size()-1);
				}
			
			}
		public void sort(int index){
			Pokemon currentnode = list.get(index);
			Pokemon parentnode = list.get(index/2);
			while(currentnode.getTotal()>parentnode.getTotal()){
				list.set(index, parentnode);
				list.set(index/2, currentnode);
				currentnode = list.get(index);
				parentnode = list.get(index/2);
				index = index/2;
				if ((index)>1){
					currentnode = list.get(index);
					parentnode = list.get(index/2);
				}
			}
		}
		public Pokemon getHighest(){
			return list.get(1);
		}
		public Pokemon removeHighest(){
			Pokemon rootnode = list.get(1);
			if(list.size()>2){
				int index = list.size() -1 ;
				Pokemon newestnode = list.get(index);
				list.remove(index);
				list.set(1, newestnode);
				moveDown(1);
			}else{
				list.remove(1);
			}
			return rootnode;
		}
		public void moveDown(int index){
			if(list.size()>index*2+1){
				if(list.get(index).getTotal() < list.get(index*2).getTotal() & list.get(index*2).getTotal()> list.get(index*2+1).getTotal()){
					swap(index, index*2);
					moveDown(index*2);
				}else if(list.get(index).getTotal()<list.get(index*2+1).getTotal()){
					swap(index, index*2+1);
					moveDown(index*2+1);
				}
			}else if(list.size()>index*2){
				if(list.get(index).getTotal() < list.get(index*2).getTotal()){
					swap(index, index*2);
					moveDown(index*2);
				}
			}
		}
		public void swap(int index1, int index2){
			Pokemon node1 = list.get(index1);
			Pokemon node2 = list.get(index2);
			list.set(index1, node2);
			list.set(index2, node1);
		}
		public ArrayList<Pokemon> getList(){
			return list;
		}
		public void printList(){
			for(int i=1;i<list.size();i++){
				System.out.print(list.get(i).getName() + " ");
			}
		}
	}



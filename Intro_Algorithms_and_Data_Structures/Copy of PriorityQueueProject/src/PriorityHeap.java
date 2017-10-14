import java.util.ArrayList;

public class PriorityHeap {
	
	private ArrayList<QueueNode> list;
	
	public PriorityHeap(){
		list = new ArrayList<>();
		list.add(null);
	}
	public void insert(int value, int duration, int deadline, int n){
		QueueNode newnode = new QueueNode(value, duration, deadline, n);
		list.add(newnode);
		if(list.size()>2){
			sort(list.size()-1);
			}
		
		}
	public void sort(int index){
		QueueNode currentnode = list.get(index);
		QueueNode parentnode = list.get(index/2);
		while(currentnode.getValue()>parentnode.getValue()){
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
	public QueueNode getHighest(){
		return list.get(1);
	}
	public void removeHighest(){
		QueueNode rootnode = list.get(1);
		if(list.size()>2){
			int index = list.size() -1 ;
			QueueNode newestnode = list.get(index);
			list.remove(index);
			list.set(1, newestnode);
			moveDown(1);
		}else{
			list.remove(1);
		}
	}
	public void moveDown(int index){
		if(list.size()>index*2+1){
			if(list.get(index).getValue() < list.get(index*2).getValue() & list.get(index*2).getValue()> list.get(index*2+1).getValue()){
				swap(index, index*2);
				moveDown(index*2);
			}else if(list.get(index).getValue()<list.get(index*2+1).getValue()){
				swap(index, index*2+1);
				moveDown(index*2+1);
			}
		}else if(list.size()>index*2){
			if(list.get(index).getValue() < list.get(index*2).getValue()){
				swap(index, index*2);
				moveDown(index*2);
			}
		}
	}
	public void swap(int index1, int index2){
		QueueNode node1 = list.get(index1);
		QueueNode node2 = list.get(index2);
		list.set(index1, node2);
		list.set(index2, node1);
	}
	public ArrayList<QueueNode> getList(){
		return list;
	}
	public void printList(){
		for(int i=1;i<list.size();i++){
			System.out.print(list.get(i).getValue() + " ");
		}
	}
}

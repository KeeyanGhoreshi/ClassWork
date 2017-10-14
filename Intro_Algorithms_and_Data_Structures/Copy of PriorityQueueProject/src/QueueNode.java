
public class QueueNode {

	private int value;
	private int duration;
	private int deadline;
	private int number;

	public QueueNode(int v, int d, int de, int n){
		value = v;
		duration = d;
		deadline = de;
		number = n;

	}
	public int getValue(){
		return value;
	}
	public int getDuration(){
		return duration;
	}
	public int getDeadline(){
		return deadline;
	}
	
	public void setValue(int val){
		value = val;
	}
	public void setDuration(int dur){
		duration = dur;
	}
	public void setDeadline(int ded){
		deadline = ded;
	}
	public int getNumber(){
		return number;
	}
	public void setNumber(int n){
		number = n;
	}
}

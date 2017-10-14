import java.util.ArrayList;
import java.util.Scanner;

public class PriorityQueueProject {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = null;
		int time = 0;
		int currenttime = 0;
		ArrayList<int[]> inputs = new ArrayList<>();
		ArrayList<Integer> output = new ArrayList<>();
		PriorityHeap heap = new PriorityHeap();
		int i = 0;
		while(sc.hasNextLine()){
			line = sc.nextLine();
			String[] info = line.split("[ ]");
			int[] nums = new int[4];
			nums[0] = Integer.parseInt(info[0]);
			nums[1] = Integer.parseInt(info[1]);
			nums[2] = Integer.parseInt(info[2]);
			nums[3] = i;
			inputs.add(nums);
			i++;
		} 
		Boolean jobrunning = true;
		heap.insert(inputs.get(0)[0], inputs.get(0)[1], inputs.get(0)[2], inputs.get(0)[3]);
		inputs.remove(0);
		QueueNode currentjob = heap.getHighest();
		heap.removeHighest();
		while(currentjob.getDuration() + currenttime>currentjob.getDeadline()){
			currenttime = currenttime + 10;
			heap.insert(inputs.get(0)[0], inputs.get(0)[1], inputs.get(0)[2], inputs.get(0)[3]);
			inputs.remove(0);
			currentjob = heap.getHighest();
			heap.removeHighest();
		}
		int endtime = currentjob.getDuration()+currenttime;
		int forceend = 1;
		while((heap.getList().size() > 0 | inputs.size()>0) & forceend ==1){
			currenttime++;
			if(currenttime%10 == 0 & inputs.size()>0){
				heap.insert(inputs.get(0)[0], inputs.get(0)[1], inputs.get(0)[2], inputs.get(0)[3]);
				inputs.remove(0);
			}
			if(currenttime == endtime){
				output.add(currentjob.getNumber());
				jobrunning = false;
				if(heap.getList().size()==1 & inputs.size()==0){
					heap.getList().remove(0);
					forceend = 0;
				}
				
			}
			
			if(jobrunning == false & heap.getList().size()>1){
				currentjob = heap.getHighest();
				heap.removeHighest();
				int dur = currentjob.getDuration();
				int ded = currentjob.getDeadline();
				while((dur+currenttime) > ded & heap.getList().size()>1){
				currentjob = heap.getHighest();
				heap.removeHighest();
				
				dur = currentjob.getDuration();
				ded = currentjob.getDeadline();
				if(heap.getList().size()==1 & inputs.size() == 0){
					forceend = 0;
				}
				}
				if(heap.getList().size()==1 & inputs.size() == 0 & (dur+currenttime) > ded){
					forceend = 0;
				}
				if(!(dur+currenttime>ded & heap.getList().size()==1 & inputs.size()>0)){
				endtime = currentjob.getDuration() + currenttime;
				jobrunning = true;
				}
			}
		}
		for(i=0; i<output.size();i++){
			System.out.print(output.get(i) + " ");
		}
	}
}

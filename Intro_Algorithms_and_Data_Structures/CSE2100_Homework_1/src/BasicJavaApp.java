import java.util.ArrayList;
import java.util.Scanner;

public class BasicJavaApp {
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Scanner k = new Scanner(System.in);
		
		while(k.hasNextInt()){
			Integer i = k.nextInt();
			list.add(i);
		}
		k.close();
		Integer sum = 0;
		for(Integer i : list){
			sum = sum+i;
			
		}
		long product = 1;
		for(Integer i : list){
			product = product * i;
			
		}
		double average = 0;
		average = (double)sum/(double)list.size();
		
		Integer currentmax = 0;
		for(Integer i : list){
			if (i>currentmax){
				currentmax = i;
			}
		}
		Integer currentmin = list.get(1);
		for(Integer i : list){
			if(i<currentmin){
				currentmin = i;
			}
			
		}

		System.out.println("Sum: " + sum);
		System.out.println("Product: " + product); 
		System.out.printf("Average: %.2f \n",average);
		System.out.println("Max: "+ currentmax);
		System.out.println("Min: " + currentmin); 

	}
}

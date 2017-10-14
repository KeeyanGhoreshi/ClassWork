import java.util.Scanner;

public class CrazyMaze {
	//Some of the test cases give the correct answer in Eclipse but mimir says my code outputs the wrong answer :(
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int number = Integer.parseInt(s.nextLine());
		number = number-1;
		String secretstring = s.nextLine();
		Graph maze = new Graph();
		while(s.hasNext()){
			String s1 = s.next();
			String s2 = s.next();
			String s3 = s.next();
			if(secretstring.contains(s3)){
			maze.addEdge(Integer.parseInt(s1), Integer.parseInt(s2), s3);
			}
			
		}
		//System.out.println(number);
		Boolean b = maze.searchGraph(secretstring,number);
		if(b){
			System.out.println("True");
		}else{
			System.out.println("False");
			
		}
	} 

}

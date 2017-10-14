import java.util.ArrayList;
import java.util.Scanner;

public class BooleanExpressionsProject {

	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		ArrayList<String> list = new ArrayList<>();
		while(s.hasNextLine()){			
			String word = s.nextLine();
			list.add(word);
			
		}
		String expandedform = expand(list);
		
		String withoutnot = cleanup(expandedform);
		
		BinaryTree tree = new BinaryTree();
		BinaryNode rootnode = new BinaryNode(null,null);
		tree.addRoot(rootnode);
		Operations operator = new Operations(tree, withoutnot.split("[ ]"));
		String answer = operator.evaluate();
		System.out.println("Evaluation: " + answer);
		System.out.println("Expansion: " + expandedform);
		System.out.println("Without Negation: " + withoutnot);
		
	}

	public static String expand(ArrayList<String> newlist){
		for(int b = 0; b<(newlist.size()); b++){
			String newlast = newlist.get(b);
			String[] numbers= newlast.split("[()|&!FT ]+");
			String newstring = replacer(numbers, newlist, newlast);
			newlist.remove(b);
			newlist.add(b,newstring);
		}
		String last = newlist.get(newlist.size()-1);
		return last;
	}
	public static String replacer(String[] nums, ArrayList<String> list1, String line){
		for(int i =0; i<nums.length; i++){
			String number = nums[i];
			if(number.length()>0){
				int num = Integer.parseInt(number);
				line = line.replace(number, list1.get(num));
			}
		}
		return line;
	}
	
	public static String cleanup(String s){
		String[] tokens= s.split("[ ]");
		for(int i=0;i<tokens.length;i++){
			String token = tokens[i];
			if(token.equals("!")){
				if(tokens[i+1].equals("F")){
					tokens[i] = "";
					tokens[i+1]="T";
				}else if(tokens[i+1].equals("T")){
					tokens[i] = "";
					tokens[i+1] = "F";
				}else if(tokens[i+1].equals("!")){
					tokens[i] = "";
					tokens[i+1] = "";
				}else if(tokens[i+1].equals("(")){
					tokens = helper(tokens, i);
				}
			}
		}
		String fullline = "";
		for(int i=0;i<tokens.length;i++){
			if(!(tokens[i].equals(""))){
				fullline = fullline + tokens[i] + " ";
			}
		}
		return fullline;
		
	}
	public static String[] helper(String[] tokens, int i){
		tokens[i]="";
		int counter = 1;
		int othercounter = 2;
		while(counter>0){
			String newtoken = tokens[i+othercounter];
			if(newtoken.equals(")")){
				counter--;
			}else if(newtoken.equals("T")){
				tokens[i+othercounter] = "F";
			}else if(newtoken.equals("F")){
				tokens[i+othercounter] = "T";
			}else if(newtoken.equals("&")){
				tokens[i+othercounter] = "|";
			}else if(newtoken.equals("|")){
				tokens[i+othercounter] = "&";
			}else if(newtoken.equals("(")){
				counter++;
			}else if(newtoken.equals("!")){
				if(tokens[i+othercounter+1].equals("(")){
					tokens = helper(tokens,i+othercounter);
				}else{
				tokens[i+othercounter] = "";
				tokens = swap(tokens, i+othercounter);
				}
			}
			othercounter++;
		}
		return tokens;
	}
	public static String[] swap(String[] tokens, int i){
		if(tokens[i+1].equals("F")){
			tokens[i] = "";
			tokens[i+1]="T";
		}else if(tokens[i+1].equals("T")){
			tokens[i] = "";
			tokens[i+1] = "F";
		}else if(tokens[i+1].equals("!")){
			tokens[i] = "";
			tokens[i+1] = "";
		}else if(tokens[i+1].equals("(")){
			tokens = helper(tokens, i);
		}
	return tokens;	
	}
}

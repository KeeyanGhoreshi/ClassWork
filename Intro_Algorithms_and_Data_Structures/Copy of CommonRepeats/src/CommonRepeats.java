import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

public class CommonRepeats {

	public static void main(String[] args) {
		Scanner k = new Scanner(System.in);
		int length = k.nextInt();
		k.nextLine();
		String word = k.nextLine();
		
		
		SuffixTree tree = new SuffixTree(word);
		tree.search(length,tree.root, "");
		Collections.sort(tree.destructor);
		for(String s : tree.destructor){
			System.out.println(s);
		}
		
		
	}
}
 
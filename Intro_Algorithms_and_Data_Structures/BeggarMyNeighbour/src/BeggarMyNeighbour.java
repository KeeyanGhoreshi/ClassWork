import java.util.Scanner;

public class BeggarMyNeighbour {
	public static void main(String[ ] args){
		Scanner s = new Scanner(System.in);
		SinglyLinkedList deck1 = new SinglyLinkedList();
		SinglyLinkedList deck2 = new SinglyLinkedList();
		Gameplay game = new Gameplay(deck1, deck2);
		while(s.hasNext()){
			String a = s.next();
			game.addToDeck1(a);
			String b = s.next();
			game.addToDeck2(b);
			
		}
		SinglyLinkedList d = game.getDeck1();
		game.playRound(d);
		int winner =3;
		if(game.getDeck1().isEmpty()){
			winner = 1;
		}else if(game.getDeck2().isEmpty()){
			winner = 0;
		}else{
			System.out.println("Draw after 5791 rounds.");
		}
		if(winner<3){
		System.out.println("Player " + winner + " wins after " + game.getRounds() + " rounds." );
		}
	}
}

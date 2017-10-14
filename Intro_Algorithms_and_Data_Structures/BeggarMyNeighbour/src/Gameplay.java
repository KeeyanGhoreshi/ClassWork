
public class Gameplay {
	private SinglyLinkedList _deck1;
	private SinglyLinkedList _deck2;
	private SinglyLinkedList _pile;
	private SinglyLinkedList temp;
	private SinglyLinkedList _deck;
	private int _rounds;
	private int _wincondition;
	private int _Player;
	
	public Gameplay(SinglyLinkedList deck1, SinglyLinkedList deck2){
		_deck1 = deck1;
		_deck2 = deck2;
		_pile = new SinglyLinkedList();
		_rounds = 1;
		_wincondition = 0;
		_Player = 0;
		temp = new SinglyLinkedList();
		_deck = null;
	}
	
	public int getRounds(){
		return _rounds;
	}
	public boolean checkwin(SinglyLinkedList deck){
		if (deck.isEmpty()){
			_wincondition = 1;
			return true;
		}
		return false;
	}
	
	public SinglyLinkedList getDeck1(){
		return _deck1;
	}
	public SinglyLinkedList getDeck2(){
		return _deck2;
	}
	public int transform(String card){
		if (card.equals("J")){
			card = "11";
		}
		if (card.equals("Q")){
			card = "12";
		}
		if (card.equals("K")){
			card = "13";
			
		}
		if (card.equals("A")){
			card = "14";
		}
		int newcard = Integer.parseInt(card);
		return newcard;
	}
	public boolean isFace(Object card){
		if (card.equals(11)){
			return true;
		}
		if(card.equals(12)){
			return true;
		}
		if (card.equals(13)){
			return true;
			
		}
		if(card.equals(14)){
			return true;
		}
		return false;
	}
	
	public SinglyLinkedList switchDecks(SinglyLinkedList deck){
		if(deck.equals(_deck1)){
			_Player = 1;
			return _deck2;
		}
		_Player  = 0;
		return _deck1;
	}
	public void collect(SinglyLinkedList deck){
		
		
		while(!_pile.isEmpty()){
			temp.addTop(_pile.playCard());
		}
		while(!temp.isEmpty()){
			deck.addBottom(temp.playCard());
		}
		_rounds++;
		
		_deck = switchDecks(deck);
		
	}
	public void PenaltyJack(SinglyLinkedList deck){
		Object currentcard = deck.playCard();

		_pile.addTop(currentcard);
		if(isFace(currentcard)){
			condition(currentcard, deck);
		}else if(!checkwin(deck)){
			collect(switchDecks(deck));
		}
		
	}
	public void PenaltyQueen(SinglyLinkedList deck){
		Object currentcard = deck.playCard();

		_pile.addTop(currentcard);
		if(isFace(currentcard)){
			condition(currentcard, deck);
		}else if(!checkwin(deck)){
			
			currentcard = deck.playCard();

			_pile.addTop(currentcard);
			if(isFace(currentcard)){
				condition(currentcard, deck);
			}else if(!checkwin(deck)){
				collect(switchDecks(deck));
			}
			
		}
		
	}
	
	public void PenaltyKing(SinglyLinkedList deck){
		
		Object currentcard = deck.playCard();
	
		_pile.addTop(currentcard);
		if(isFace(currentcard)){
			condition(currentcard, deck);
		}else if(!checkwin(deck)){
			currentcard = deck.playCard();
	
			_pile.addTop(currentcard);
			if(isFace(currentcard)){
				condition(currentcard, deck);
			}else if(!checkwin(deck)){
				currentcard = deck.playCard();
	
				_pile.addTop(currentcard);
				if(isFace(currentcard)){
					condition(currentcard, deck);
				}else if(!checkwin(deck)){
					collect(switchDecks(deck));
				}
				
			}
		}
	}
	
	public void PenaltyAce(SinglyLinkedList deck){
		Object currentcard = deck.playCard();

		_pile.addTop(currentcard);
		if(isFace(currentcard)){
			condition(currentcard, deck);
		}else if(!checkwin(deck)){
			
			currentcard = deck.playCard();

			_pile.addTop(currentcard);
			if(isFace(currentcard)){
				condition(currentcard, deck);
			}else if(!checkwin(deck)){
				
				currentcard = deck.playCard();
		
				_pile.addTop(currentcard);
				if(isFace(currentcard)){
				condition(currentcard, deck);
				}else if(!checkwin(deck)){
					
					currentcard = deck.playCard();
			
					_pile.addTop(currentcard);
					
					if(isFace(currentcard)){
						condition(currentcard, deck);
					}else if(!checkwin(deck)){
						collect(switchDecks(deck));
					}
					
				}
			}
		}
	}
	
	public void condition(Object card, SinglyLinkedList deck){
		if(!switchDecks(deck).isEmpty()){
			if (card.equals(11)){
				deck = switchDecks(deck);
				PenaltyJack(deck);
			}
			if (card.equals(12)){
				deck = switchDecks(deck);
				PenaltyQueen(deck);
			}
			if (card.equals(13)){
				deck = switchDecks(deck);
				PenaltyKing(deck);
			}
			if (card.equals(14)){
				deck = switchDecks(deck);
				PenaltyAce(deck);
			}
		}
	}
	
	
	public void addToDeck1(String card){
		int card1 = transform(card);
		_deck1.addBottom(card1);
		}
	
	public void addToDeck2(String card){
		int card1 = transform(card);
		_deck2.addBottom(card1);
		}

	
	public void playRound(SinglyLinkedList deck){
		_deck = deck;
		while(_wincondition==0 && !checkwin(_deck)){
		if (_deck1.isEmpty()){
			_wincondition = 1;
			_rounds++;
		}
		if (_deck2.isEmpty()){
			_wincondition = 1;
			if(!_deck1.isEmpty()){
			_rounds++;
			}
		}
		if(_rounds>=5791){
			_wincondition = 1;
		}
		if(_wincondition == 0 && !checkwin(_deck)){
		Object card1 = _deck.playCard();
		_pile.addTop(card1);
		condition(card1, _deck);
		_deck = switchDecks(_deck);
		}

		}
	}
	
}


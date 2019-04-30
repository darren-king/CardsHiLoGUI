package sample;

public class DeckOfCards {


    private Card[] arrayOfCards;
    private final int deckSize = 52;


    // Here's a constructor for a deck of cards
    public DeckOfCards() {

        arrayOfCards = new Card[deckSize];
        int x = 0;

        for (int r = 1; r <= 13; r++) {

            for (int s = 0; s <= 3; s++) {

                arrayOfCards[x] = new Card(r, s);
                x++;

            } // end of s loop

        } // end of r loop

    } // end of constructor


    // This getter returns the deck of cards as an order array with aces first, 2's second and so forth - unshuffled
    public Card[] getDeck() {
        return arrayOfCards;
    } //getter

    public void setArrayOfCards(Card[] arrayOfCards) {
        this.arrayOfCards = arrayOfCards;
    } //setter













/*

    public sample.Card dealTopCard(){


    }// dealTopCard


    public sample.DeckOfCards shuffle(){



    }// shuffle


    public boolean isEmpty(){


    } //isEmpty

*/

    public static void main (String args[]){

        DeckOfCards newDeck = new DeckOfCards();

        for (int x = 0; x<newDeck.getDeck().length; x++){
            System.out.println(newDeck.getDeck()[x]);
        }

    }



}



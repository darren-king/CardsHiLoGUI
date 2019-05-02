package sample;

import java.util.Random;

public class DeckOfCards {


    private Card[] arrayOfCards;
    private final int deckSize = 52;


    // Here's a constructor for a deck of cards - this creates the deck - it doesn't give us the deck as an array though!
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



    // This getter returns the deck of cards as an ordered array with aces first, 2's second and so forth - unshuffled!
    // This getter gives us the Array of Cards

    public Card[] getDeck() {
        return arrayOfCards;
    } //getter


    public void shuffleDeck() {

        Card[] deckIn = getDeck();

        Random rand = new Random();


        for (int x = 0; x < deckIn.length; x++) {

            int random = rand.nextInt(deckSize);

            Card singleStore = deckIn[random];

            deckIn[random] = deckIn[x];

            deckIn[x] = singleStore;

        }

    }


    public Card dealTopCard() {

        Card[] deckIn = getDeck();
        Card singleton = null;

        if (!isEmpty()) {

            for (int x = 0; x < deckSize; x++) {
                if (deckIn[x] != null) {
                    singleton = deckIn[x];
                    deckIn[x] = null;
                    break;
                } // end of inner if statement

            } // end of for statement

        } // end of outer if

        return singleton;

    } // end of dealTopCard



    public boolean isEmpty(){

        Card[] deckIn = getDeck(); // get the deck

        int counter = 0; // start a counter that's going to count the number of empty spots in the deck array

        while (deckIn[counter] == null) {
            counter ++;
        }

        if (counter == 51){ // The counter started at 0 - if it hits 51 it means theres no cards in the deck
            return true;
        } else {
            return false;
        }


    }

}



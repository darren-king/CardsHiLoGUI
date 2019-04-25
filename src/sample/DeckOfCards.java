package sample;

public class DeckOfCards {


    private Card[] arrayOfCards;

    public DeckOfCards (Card[] aOC){
        this.arrayOfCards = aOC;
    }

    public Card[] getArrayOfCards() {
        return arrayOfCards;
    } //getter

    public void setArrayOfCards(Card[] arrayOfCards) {
        this.arrayOfCards = arrayOfCards;
    } //setter

    public DeckOfCards(){

        arrayOfCards = new Card[52];


        for (int s = 0; s<=3; s++){

            for (int r = 1; r<=13; r++){

                for (int a = 0; a<52; a++) {

                    arrayOfCards[a] = new Card(r,s);

                } // end of a loop

            } // end of r loop


        }// end of s loop


    } // end of deck of cards





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

        System.out.println(newDeck.arrayOfCards[12]);


    }



}



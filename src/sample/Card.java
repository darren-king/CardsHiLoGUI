package sample;

public class Card {

    // I want to use integers to completely define cards
    // e.g. 2,3 could define 2 of Diamonds

    private int rank;
    private int suit;

    public Card(){
        //TODO Auto-generated constructor stub
    } // constructor

    public Card(int r, int s){

        this.rank = r;
        this.suit = s;

    } // constructor that takes arguments

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public boolean rankIsLessThan(Card c){
        boolean rankIsLess = false;

        if (this.rank<c.getRank()){
            rankIsLess = true;
        }

        return rankIsLess;

    } // rankIsLessThan

    public boolean rankIsGreaterThan (Card c){
        boolean rankIsGreater = false;

        if (this.rank>c.getRank()){
            rankIsGreater = true;
        }

        return rankIsGreater;

    } // rankIsGreaterThan

    public boolean rankIsEqualTo (Card c){
        boolean rankIsEqual = false;

        if (this.rank == c.getRank()){
            rankIsEqual = true;
        }

        return rankIsEqual;

    } // rankIsEqualTo


    public String toString(){

        String cardSuit = "";
        String cardRank = "";
        String cardString = "";

        int cs = getSuit();
        int cr = getRank();

        switch(cr){
            case 1:
                cardRank = "ace";
                break;
            case 2:
                cardRank = "2";
                break;
            case 3:
                cardRank = "3";
                break;
            case 4:
                cardRank = "4";
                break;
            case 5:
                cardRank = "5";
                break;
            case 6:
                cardRank = "6";
                break;
            case 7:
                cardRank = "7";
                break;
            case 8:
                cardRank = "8";
                break;
            case 9:
                cardRank = "9";
                break;
            case 10:
                cardRank = "10";
                break;
            case 11:
                cardRank = "jack";
                break;
            case 12:
                cardRank = "queen";
                break;
            case 13:
                cardRank = "king";
                break;
            default:
                cardRank = "n/a";

        }// end of switch rank

        // Now get a string representation of the suit

        switch(cs){

            case 0:
                cardSuit = "hearts";
                break;
            case 1:
                cardSuit = "diamonds";
                break;
            case 2:
                cardSuit = "spades";
                break;
            case 3:
                cardSuit = "clubs";

        }// end of cs switch

        cardString = cardRank + "_of_" + cardSuit +".png";

        return cardString;

    }


}

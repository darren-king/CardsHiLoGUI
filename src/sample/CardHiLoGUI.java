package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class CardHiLoGUI extends Application {


    // Declare components at class level

    // Components for the MenuBar

    MenuBar mBar;
    Menu menuFile;
    Menu menuHelp;

    MenuItem menuNewGame;
    MenuItem menuShuffle;
    MenuItem menuExit;
    MenuItem menuAbout;

    Label lblFirstCard;
    Label lblSecondCard;
    Label lblNxtCard;
    Label lblWinnerOrLoser;

    Image imgFirstCard;
    Image imgSecondCard;
    ImageView imgVFirstCard;
    ImageView imgVSecondCard;

    RadioButton rbHigher;
    RadioButton rbLower;

    ToggleButton btnFirstCard;
    ToggleButton btnSecondCard;

    ProgressBar progBar;
    ProgressIndicator progInd;

    ToggleGroup radioToggleGroup;
    ToggleGroup buttonToggleGroup;

    DeckOfCards DOC;

    String imageName1;
    String imageName2;

    Card card1;
    Card card2;


    int counter;

    public CardHiLoGUI(){
        //TODO
    } // Constructor


    //This method is going to deal generating the appropriate image
    public String imageGenerator(Card cardToBeImaged){

        File file = new File("cards/" + cardToBeImaged.toString() ); // need to put the ending in here

        return file.toURI().toString();



    }


    // This method will run if a new game is initiated

    public void newGame(){

        // I need a deck of cards - setting up the cards.

        DOC = new DeckOfCards(); // this creates a deck of cards for us to start playing - these don't exist until a newGame is started

        //I'm setting the image on the deck of cards to start

        imgVFirstCard.setImage(imgFirstCard);
        imgVSecondCard.setImage(imgSecondCard);

        // I'll need this counter for scoring
        counter = 0;

        DOC.shuffleDeck(); // no point in having a deck in order

        // Now setting up the radio buttons

        rbHigher.setDisable(false);
        rbLower.setDisable(false);

        btnFirstCard.setDisable(false);


        btnFirstCard.setOnAction(actionEvent -> {

            lblWinnerOrLoser.setVisible(false);

            card1 = DOC.dealTopCard();

            Image img = new Image(imageGenerator(card1));

            imgVFirstCard.setImage(img);

            /* btnSecondCard.selectedProperty().addListener((observable, oldValue, newValue) -> {

                //If selected, which I will turn to true to attract the users attention to it after I've written this code
                // Change the CSS and duplicate in btnSecond Card

                if (newValue) {
                    btnSecondCard.setStyle(
                            "-fx-background-color:red;" +
                                    "-fx-text-fill: white");
                } else {
                    btnSecondCard.setStyle(null);
                }

                } ); */

            btnFirstCard.setDisable(true); // So the first card can't be pressed again until the second card has been selected

            btnSecondCard.setDisable(false);


        }); // end of set on action for first card

        btnSecondCard.setOnAction(actionEvent -> {

            card2 = DOC.dealTopCard();

            Image img2 = new Image(imageGenerator(card2));

            imgVSecondCard.setImage(img2);

            /* btnFirstCard.selectedProperty().addListener((observable, oldValue, newValue) -> {

                //If selected, which I will turn to true to attract the users attention to it after I've written this code
                // Change the CSS and duplicate in btnSecond Card

                if (newValue) {
                    btnFirstCard.setStyle(
                            "-fx-background-color:red;" +
                                    "-fx-text-fill: white");
                } else {
                    btnFirstCard.setStyle(null);
                }

            } ); */

            btnSecondCard.setDisable(true);

            // Now implement the comparison logic and scoring - do this in a separate method to separate out the code

            compareCards();


        });





    } // end of new game


    public void compareCards(){


        String message;

        boolean higher = rbHigher.isSelected();

        boolean lower = rbLower.isSelected();


        if (higher && card2.rankIsGreaterThan(card1)){
            message = "You guessed that your card would be higher. You win this round!";
            counter++;
        } else if (lower && card2.rankIsLessThan(card1)){
            message = "You guessed that your card would be lower. You win this round!";
            counter++;
        } else if ((higher || lower) && (card2.rankIsEqualTo(card1))){
            message = "It's a draw.";
        } else if (higher && card2.rankIsLessThan(card1)) {
            message = "You guessed that your card would be higher. You lose this round!";
        } else {
            message = "You guessed that your card would be lower. You lose this round!";
        }

        lblWinnerOrLoser.setText(message);

        lblWinnerOrLoser.setVisible(true);

        btnFirstCard.setDisable(false);


    }




    @Override
    public void init(){

        // I'm going to initialise all my components here
        // And I'm going to put any actions on components here.
        // The layout will be provided for in the start method

        // So let's look at the menu bar first.
        mBar = new MenuBar();
        menuFile = new Menu("File");
        menuHelp = new Menu("Help");
        menuNewGame = new MenuItem("New Game");
        menuShuffle = new MenuItem("Shuffle");
        menuExit = new MenuItem("Exit");
        menuAbout = new MenuItem("About");

        // Now let's look at the first card

        imageName1 = new File("cards/blue_back.png").toURI().toString();
        lblFirstCard = new Label("First Card Dealt");
        imgFirstCard = new Image(imageName1); // Use the imageOpener method to return a string to here later
        imgVFirstCard = new ImageView();

        //Now let's look at the middle section
        lblNxtCard = new Label("Next card will be:");
        rbHigher = new RadioButton("Higher");
        rbLower = new RadioButton("Lower");
        btnFirstCard = new ToggleButton("<- Deal First Card");
        btnSecondCard = new ToggleButton("Deal Second Card ->");

        // I want to put the buttons in a toggle group as well

        buttonToggleGroup = new ToggleGroup();
        buttonToggleGroup.getToggles().addAll(btnFirstCard, btnSecondCard);
        btnFirstCard.setToggleGroup(buttonToggleGroup);
        btnSecondCard.setToggleGroup(buttonToggleGroup);
        btnFirstCard.setDisable(true); // I want both buttons disabled when the game opens
        btnSecondCard.setDisable(true);

        radioToggleGroup = new ToggleGroup();
        radioToggleGroup.getToggles().addAll(rbHigher,rbLower);
        rbHigher.setToggleGroup(radioToggleGroup);
        rbLower.setToggleGroup(radioToggleGroup);
        rbHigher.setDisable(true); // I want both buttons disabled when the game opens
        rbLower.setDisable(true);

        // Now let's look at the second card
        imageName2 = new File("cards/blue_back.png").toURI().toString();
        lblSecondCard = new Label("Second Card Dealt:");
        imgSecondCard = new Image(imageName2);
        imgVSecondCard = new ImageView();

        lblWinnerOrLoser = new Label();


        // Now to deal with clicking on the menuNewGame

        menuNewGame.setOnAction(actionEvent -> newGame()); //I've created a method above for New Game to seperate out the code


    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a stage

        primaryStage.setTitle("Hi-Lo Card Game");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        // Create a layout

        VBox vb = new VBox(); //Note: I don't like dealing with gridpane - I find it more intuitive and easier to manipulate a
                                // series of vertical and horizontal boxes.

        // Now to deal with the menubar and add it the the VBox

        menuFile.getItems().addAll(menuNewGame, menuShuffle, menuExit);
        menuHelp.getItems().addAll(menuAbout);
        mBar.getMenus().addAll(menuFile, menuHelp);
        vb.getChildren().add(mBar);

        //Now to deal with the first card: Put it in a vertical box

        VBox vbFirstCard = new VBox();
        lblFirstCard.setPadding(new Insets(5));
        //lblFirstCard.setTextAlignment(TextAlignment.CENTER);
        imgVFirstCard.setImage(imgFirstCard);
        imgVFirstCard.setFitWidth(150);
        imgVFirstCard.setFitHeight(250);
        vbFirstCard.getChildren().addAll(lblFirstCard, imgVFirstCard);


        // Now to deal with the middle section : Put it in a vertical box

        VBox vbMiddleSection = new VBox();
        vbMiddleSection.getChildren().addAll(lblNxtCard,rbHigher,rbLower,btnFirstCard,btnSecondCard);


        // Now to deal with the second card: Put it in a vertical box

        VBox vbSecondCard = new VBox();
        lblSecondCard.setPadding(new Insets(5));
        imgVSecondCard.setImage(imgSecondCard);
        imgVSecondCard.setFitWidth(150);
        imgVSecondCard.setFitHeight(250);
        vbSecondCard.getChildren().addAll(lblSecondCard, imgVSecondCard);

        // I want to put the three sections above into a horizontal box which i'll subsequently add to the vertical box

        HBox hb = new HBox();
        hb.getChildren().addAll(vbFirstCard, vbMiddleSection, vbSecondCard);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        vb.getChildren().add(hb);

        vb.getChildren().add(lblWinnerOrLoser);
        lblWinnerOrLoser.setTextAlignment(TextAlignment.CENTER);
        lblWinnerOrLoser.setAlignment(Pos.CENTER);



        // Create a scene

        Scene sc = new Scene(vb);

        //Set the scene

        primaryStage.setScene(sc);

        // It's showtime! Show the stage

        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}

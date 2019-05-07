package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    MenuItem menuHowToPlay;

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

    Label secondaryStageLabel;
    Button secondaryStageOK;


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

        //Make shuffle available

        menuShuffle.setDisable(false);

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

        //What if we shuffle here?? This is shuffling the4 all ready shuffled dealt deck
        //If we call it after x number of cards have been dealt it's shuffling the remainder of the cards
        //Is this of value?? I don't think so.
        //The alternative, which I had coded below was that shuffle would just start a new game.
        menuShuffle.setOnAction(actionEvent -> DOC.shuffleDeck());


        btnFirstCard.setOnAction(actionEvent -> {

            if (!DOC.isEmpty()) { //  need to make sure there's a card to deal.

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

            } else {

                String message = "Uh-oh - Someone's out of cards!";
                btnFirstCard.setDisable(true);
                btnSecondCard.setDisable(true);
                menuShuffle.setDisable(true);

                secondaryStage("Uh-oh - Someone's out of cards!");

            }


        }); // end of set on action for first card

        btnSecondCard.setOnAction(actionEvent -> {

            if(!DOC.isEmpty() && (rbHigher.isSelected() || rbLower.isSelected())) {

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

                rbHigher.setSelected(false);
                rbLower.setSelected(false);

            } else if (DOC.isEmpty()) {

                String message = "Uh-oh - Someone's out of cards!";
                btnFirstCard.setDisable(true);
                btnSecondCard.setDisable(true);
                menuShuffle.setDisable(true);

                secondaryStage(message);

            } else {

                String message = "You have to decide if the next card dealt is going to be higher or lower!";

                secondaryStage(message);

            }

        });





    } // end of new game


    public void compareCards(){


        String message;

        double progValue = progBar.getProgress();

        boolean higher = rbHigher.isSelected();

        boolean lower = rbLower.isSelected();


        if (higher && card2.rankIsGreaterThan(card1)){
            message = "You guessed that your card would be higher. You win this round! ";
            counter++;
            progValue = progValue + 0.2;
        } else if (lower && card2.rankIsLessThan(card1)){
            message = "You guessed that your card would be lower. You win this round! ";
            counter++;
            progValue = progValue + 0.2;
        } else if ((higher || lower) && (card2.rankIsEqualTo(card1))){
            message = "It's a draw. ";
        } else if (higher && card2.rankIsLessThan(card1)) {
            message = "You guessed that your card would be higher. You lose this round! ";
            counter = 0;
            progValue = 0;
        } else {
            message = "You guessed that your card would be lower. You lose this round! ";
            counter = 0;
            progValue = 0;
        }

        if (counter == 5) {
            secondaryStage("Congratulations. You won!");
            btnFirstCard.setDisable(true);
            btnSecondCard.setDisable(true);
            menuShuffle.setDisable(true);
        }

        lblWinnerOrLoser.setText(message + "Score: " + counter);

        lblWinnerOrLoser.setVisible(true);

        progBar.setProgress(progValue);
        progInd.setProgress(progValue);

        btnFirstCard.setDisable(false);


    }

    // Here I'm creating a method to read the instruction from a text file and feed them to the secondary stage
    // created below for when the user wants instructions on how  to play the game.

    public String howToPlay(){

       String rules = "";

       try {

           rules = new String(Files.readAllBytes(Paths.get("howToPlay.txt")));

           return rules;

       }
       catch (IOException e) {
           System.out.println("Error" + e.getMessage());
       }

       return rules;


    }


    // I want to create a secondary stage for when a player wins, loses, is out of cards etc.

    public void secondaryStage(String messageToStage){

        //Create the stage:
        Stage secondaryStage = new Stage();

        secondaryStage.setTitle("We've a message for you!");
        secondaryStage.setHeight(150);
        secondaryStage.setWidth(500);
        secondaryStage.setResizable(false);


        //Create a layout

        VBox vb  = new VBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();


        // I want different the button to say and do different things depending on the reason the secondary
        // stage has been called.
        // I equally want my message to be  different depending on the reason the stage was called.
        if (messageToStage == ("Uh-oh - Someone's out of cards!") || messageToStage == ("Congratulations. You won!")){
            secondaryStageLabel = new Label(messageToStage);
            secondaryStageOK = new Button("New Game?");
            secondaryStageOK.setOnAction(actionEvent -> {
                newGame();
                secondaryStage.close();
            });
        } else if (messageToStage == "How To Play"){
            secondaryStage.setTitle("How To Play...");
            secondaryStage.setWidth(800);
            secondaryStage.setHeight(500);
            messageToStage = howToPlay();
            secondaryStageLabel = new Label(messageToStage);
            secondaryStageOK = new Button ("Got It!");
            secondaryStageOK.setOnAction(actionEvent -> {
               secondaryStage.close();
            });
        } else if (messageToStage == "About"){
            messageToStage = "Darren King - 2989670";
            secondaryStage.setTitle("About");
            secondaryStageLabel = new Label(messageToStage);
            secondaryStageOK = new Button("OK");
            secondaryStageOK.setOnAction(actionEvent -> {
                secondaryStage.close();
            });
        }

        else {
            secondaryStageOK = new Button("OK");
            secondaryStageOK.setOnAction(actionEvent -> {
                secondaryStage.close();
            });
        }


        //Add elements to the layout

        hb1.getChildren().add(secondaryStageLabel);
        hb1.setPadding(new Insets(20,0,0,0));
        hb1.setAlignment(Pos.CENTER);

        hb2.getChildren().add(secondaryStageOK);
        hb2.setPadding(new Insets(20,0,0,0));
        hb2.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(hb1, hb2);

        //create a scene and give it the layout

        Scene sc2 = new Scene(vb);

        //Style the scene

        sc2.getStylesheets().add("https://fonts.googleapis.com/css?family=Aldrich");
        sc2.getStylesheets().add("funkyfunk.css");

        //Set the scene

        secondaryStage.setScene(sc2);

        // Show the stage

        secondaryStage.show();


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
        menuExit = new MenuItem("Exit"); menuExit.setOnAction(actionEvent -> exit()); // The method is at the very end
        menuAbout = new MenuItem("About"); menuAbout.setOnAction(actionEvent -> secondaryStage("About"));
        menuHowToPlay = new MenuItem("How To Play"); menuHowToPlay.setOnAction(actionEvent -> secondaryStage("How To Play"));

        // Now let's look at the first card

        imageName1 = new File("cards/card_back_blue.png").toURI().toString();
        lblFirstCard = new Label("First Card Dealt:");
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
        imageName2 = new File("cards/card_back_blue.png").toURI().toString();
        lblSecondCard = new Label("Second Card Dealt:");
        imgSecondCard = new Image(imageName2);
        imgVSecondCard = new ImageView();

        lblWinnerOrLoser = new Label();

        progBar = new ProgressBar(0);
        progInd = new ProgressIndicator(0);



        // Now to deal with clicking on the menuNewGame

        menuNewGame.setOnAction(actionEvent -> newGame()); //I've created a method above for New Game to seperate out the code

        // If you'rew going to shuffle the cards you need to have a deck and hence need to be in a game - therefore to shuffle the cards will start a new game.
        menuShuffle.setDisable(true);
        // menuShuffle.setOnAction(actionEvent -> newGame());


    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a stage

        primaryStage.setTitle("Hi-Lo Card Game");
        primaryStage.setWidth(700);
        primaryStage.setHeight(450);
        primaryStage.setResizable(false);

        // Create a layout

        VBox vb = new VBox(); //Note: I don't like dealing with gridpane - I find it more intuitive and easier to manipulate a
                                // series of vertical and horizontal boxes.

        // Now to deal with the menubar and add it the the VBox

        menuFile.getItems().addAll(menuNewGame, menuShuffle, menuExit);
        menuHelp.getItems().addAll(menuAbout, menuHowToPlay);
        mBar.getMenus().addAll(menuFile, menuHelp);
        vb.getChildren().add(mBar);

        //Now to deal with the first card: Put it in a vertical box

        VBox vbFirstCard = new VBox();
        lblFirstCard.setPadding(new Insets(5,5,10,5));
        //lblFirstCard.setTextAlignment(TextAlignment.CENTER);
        imgVFirstCard.setImage(imgFirstCard);
        imgVFirstCard.setFitWidth(175);
        imgVFirstCard.setFitHeight(250);
        vbFirstCard.getChildren().addAll(lblFirstCard, imgVFirstCard);


        // Now to deal with the middle section : Put it in a vertical box

        VBox vbMiddleSection = new VBox();
        vbMiddleSection.getChildren().addAll(lblNxtCard,rbHigher,rbLower,btnFirstCard,btnSecondCard);
        btnFirstCard.setPrefWidth(200);
        btnSecondCard.setPrefWidth(200);
        vbMiddleSection.setPadding(new Insets(30,15,0,15));
        vbMiddleSection.setSpacing(10);




        // Now to deal with the second card: Put it in a vertical box

        VBox vbSecondCard = new VBox();
        lblSecondCard.setPadding(new Insets(5,5,10,5));
        imgVSecondCard.setImage(imgSecondCard);
        imgVSecondCard.setFitWidth(175);
        imgVSecondCard.setFitHeight(250);
        vbSecondCard.getChildren().addAll(lblSecondCard, imgVSecondCard);

        // I want to put the three sections above into a horizontal box which i'll subsequently add to the vertical box

        HBox hb = new HBox();
        hb.getChildren().addAll(vbFirstCard, vbMiddleSection, vbSecondCard);
        hb.setSpacing(10);
        hb.setPadding(new Insets(20,0,0,0));
        hb.setAlignment(Pos.CENTER);

        vb.getChildren().add(hb);



        // This will deal with the winner or loser label
        HBox hbWinOrLose = new HBox();

        //Pad the central with two outer Hbox - it's a 'hacky' way to do what I want but it works nicely
        // Effectively I'm putting two boxes either side of my label and these boxes will expand and shrink to centre muy label
        // according to the text within it.
        HBox hb1 = new HBox();
        hb1.setHgrow(hb1, Priority.ALWAYS);
        HBox hb2 = new HBox();
        hb2.setHgrow(hb2, Priority.ALWAYS);
        hbWinOrLose.getChildren().addAll(hb1, lblWinnerOrLoser, hb2);
        hbWinOrLose.setPadding(new Insets(10,0,0,0));
        vb.getChildren().add(hbWinOrLose);


        // Now put the progress bar and progress indicator in a Hbox to be added to the Vbox

        HBox hbProg = new HBox();
        progBar.setPrefWidth(300);
        hbProg.getChildren().addAll(progBar, progInd);
        hbProg.setSpacing(10);
        hbProg.setPadding(new Insets(15,0,0,0));
        hbProg.setAlignment(Pos.CENTER);


        vb.getChildren().add(hbProg);



        // Create a scene

        Scene sc = new Scene(vb);

        // Style the scene

        // Let's change the fonts with a style sheet - and some funky fonts from google fonts
        sc.getStylesheets().add("https://fonts.googleapis.com/css?family=Aldrich");
        sc.getStylesheets().add("funkyfunk.css");

        //Set the scene

        primaryStage.setScene(sc);

        // It's showtime! Show the stage

        primaryStage.show();

    }

    public void exit(){
        Platform.exit();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

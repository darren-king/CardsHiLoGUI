package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    Button btnFirstCard;
    Button btnSecondCard;

    ProgressBar progBar;
    ProgressIndicator progInd;



    public CardHiLoGUI(){
        //TODO
    } // Constructor


    //This method is going to deal generating the appropriate image
    public String imageGenerator(){

        File file = new File("/Users/darrenking/Desktop/Cards/3C.png");

        return file.toURI().toString();

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
        lblFirstCard = new Label("First Card Dealt");
        imgFirstCard = new Image(imageGenerator()); // Use the imageOpener method to return a string to here later
        imgVFirstCard = new ImageView();

    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a stage

        primaryStage.setTitle("Hi-Lo Card Game");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);

        // Create a layout

        VBox vb = new VBox(); //Note: I don't like dealing with gridpane - I find it easier, more intuitive and easier to manipulate a
                                // series of vertical and horizontal boxes.

        // Now to deal with the menubar and add it the the VBox

        menuFile.getItems().addAll(menuNewGame, menuShuffle, menuExit);
        menuHelp.getItems().addAll(menuAbout);
        mBar.getMenus().addAll(menuFile, menuHelp);
        vb.getChildren().add(mBar);

        //Now to deal with the first card:

        VBox vbFirstCard = new VBox();
        imgVFirstCard.setImage(imgFirstCard);
        vbFirstCard.getChildren().addAll(lblFirstCard, imgVFirstCard);
        vb.getChildren().add(vbFirstCard);












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

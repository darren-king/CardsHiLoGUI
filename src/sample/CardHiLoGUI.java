package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

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

    RadioButton rbHigher;
    RadioButton rbLower;

    Button btnFirstCard;
    Button btnSecondCard;

    ProgressBar progBar;
    ProgressIndicator progInd;




    public CardHiLoGUI(){
        //TODO
    } // Constructor


    @Override
    public void init(){

    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a stage

        primaryStage.setTitle("Hi-Lo Card Game");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);

        // Create a layout

        VBox vb = new VBox();

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

package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RECINT extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

       FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/adminrec.fxml"));
        try {
            Parent root = loader2.load();
            primaryStage.setTitle("Dashboard");
            Scene sr = new Scene(root,1315,890);
            primaryStage.setScene(sr);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/userec.fxml"));
        try {
            Parent root = loader1.load();
            primaryStage.setTitle("reclamation");
            Scene sr = new Scene(root,1315,890);
            primaryStage.setScene(sr);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);*/
        }
    }


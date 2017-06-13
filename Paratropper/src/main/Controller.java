package main;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.tools.BaseController;
import main.tools.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller extends BaseController{
    @FXML
    private Button create_hotspot;
    @FXML
    private Button wifi_connected;
    @FXML
    private Label console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).
    }

    public void createHotspot(ActionEvent actionEvent){
        // create hotspot...
        println(console,"creating hotspot...");
        create_hotspot.setDisable(true);
        Thread t = new Thread(()-> {
            int time = new Random().nextInt(4000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Platform.runLater(() -> {
                println(console, "already set up");
                ObservableList<Stage> stages = FXRobotHelper.getStages();
                stages.get(0).setScene(SceneManager.create("auth/connectedlist.fxml"));
            });
        });
        t.start();
    }

    public void register(ActionEvent actionEvent){
        // send message to server
        println(console, "registering to the hotspot server");
        println(console, "already registered");
    }

}

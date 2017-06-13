package main.auth;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.Model.Trooper;
import main.tools.BaseController;
import main.tools.SceneManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by wesley shi on 2017/6/13.
 */
public class ConnectedListController extends BaseController{

    @FXML
    TableView<Trooper> iplist;

    @FXML
    Button authentication;

    @FXML
    Button back;

    @FXML
    Label console;

    ConnectedListModel clm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO (don't really need to do anything here).
        ObservableList<Trooper> troopers = iplist.getItems();
        clm = new ConnectedListModel(troopers);
        // connect to each column...

    }

    public void startAuthentication(ActionEvent actionEvent){
        // send message to other troopers
        println(console, "start authentication!!!!");
        

    }

    public void back(ActionEvent actionEvent){
        // stop all
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        stages.get(0).setScene(SceneManager.create("main.fxml"));
    }
}

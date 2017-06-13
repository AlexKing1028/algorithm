package main.auth;

import javafx.collections.ObservableList;
import main.Model.Trooper;

/**
 * Created by wesley on 2017/6/13.
 */
public class ConnectedListModel {
    ObservableList<Trooper> troopers;
    public ConnectedListModel(ObservableList<Trooper> ts){
        troopers = ts;
    }

    public void addItem(){

    }
}

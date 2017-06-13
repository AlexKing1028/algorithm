package main.tools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by wesley shi on 2017/6/13.
 */
public class SceneManager {
    public static Application app;

    public static Scene create(String path, int height, int width){
        try{
            Parent root = FXMLLoader.load(app.getClass().getResource(path));
            return new Scene(root, height, width);
        } catch (IOException ie){
            ie.printStackTrace();
        }
        return null;
    }

    public static Scene create(String path){
        return create(path, 600, 600);
    }
}

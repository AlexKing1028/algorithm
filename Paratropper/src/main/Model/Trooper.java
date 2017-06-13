package main.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by wesley on 2017/6/13.
 */
public class Trooper {
    private StringProperty ip;
    private StringProperty id;

    public StringProperty ip(){
        if (ip==null) ip=new SimpleStringProperty(this, "ip");
        return ip;
    }
    public void setIp(String value){
        ip().set(value);
    }
    public String getIp(){
        return ip().get();
    }

    public StringProperty id(){
        if (id==null) id=new SimpleStringProperty(this, "id");
        return ip;
    }
    public void setId(String value){
        id().set(value);
    }
    public String getId(){
        return id().get();
    }
}

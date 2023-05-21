package com.example.monitorizareangajati.Controllers;

import com.example.monitorizareangajati.Model.Angajat;
import com.example.monitorizareangajati.Service.Service;
import com.example.monitorizareangajati.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private Service service;

    public void setService(Service service)
    {
        this.service=service;
    }


    @FXML
    public void initialize()
    {

    }

    public void setScene(Parent root, ActionEvent actionEvent, int width, int height, String title) throws IOException
    {
        Stage stage=new Stage();
        Scene scene=new Scene(root,width,height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }


    public void handleStartApplicationButton(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader=new FXMLLoader(StartApplication.class.getResource("loginView.fxml"));
        Parent root=fxmlLoader.load();
        LoginController loginController=fxmlLoader.getController();
        loginController.setService(service);
        setScene(root,actionEvent,600,270,"Login");
    }
}

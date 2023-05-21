package com.example.monitorizareangajati.Controllers;

import com.example.monitorizareangajati.StartApplication;
import com.example.monitorizareangajati.Model.Angajat;
import com.example.monitorizareangajati.Model.TipAngajat;
import com.example.monitorizareangajati.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label warning;

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
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene=new Scene(root,width,height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }


    public void handleLoginButton(ActionEvent actionEvent) throws IOException
    {
        String name=String.valueOf(nameField.getText());
        String password=String.valueOf(passwordField.getText());
        String tipAngajat=service.findEmployeeType(name,password);
        if(tipAngajat!=null)
        {
            if(tipAngajat.equals("manager"))
            {
                FXMLLoader fxmlLoader=new FXMLLoader(StartApplication.class.getResource("managerView.fxml"));
                Parent root=fxmlLoader.load();
                ManagerController managerController=fxmlLoader.getController();
                Angajat userCurent=service.login(name,password,managerController);
                managerController.setService(service,userCurent);
                setScene(root,actionEvent,845,710,"ManagerWindow");
                return;
            }
            else
            {
                FXMLLoader fxmlLoader=new FXMLLoader(StartApplication.class.getResource("employeeView.fxml"));
                Parent root=fxmlLoader.load();
                EmployeeController employeeController=fxmlLoader.getController();
                Angajat userCurent=service.login(name,password,employeeController);
                employeeController.setService(service,userCurent);
                setScene(root,actionEvent,840,540,"EmployeeWindow");
                return;
            }
        }
        else
        {
            warning.setText("Invalid name and/or password.");
            warning.setVisible(true);
        }
    }
}

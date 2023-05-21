package com.example.monitorizareangajati.Controllers;

import com.example.monitorizareangajati.Model.*;
import com.example.monitorizareangajati.Observer.IObserver;
import com.example.monitorizareangajati.StartApplication;
import com.example.monitorizareangajati.Service.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController implements IObserver {
    private Service service;
    private Angajat userCurent;

    @FXML
    private TableView<Sarcina> tableViewSarcini;
    @FXML
    private TableColumn<Sarcina, StatutSarcina> tableColumnStatut;
    @FXML
    private TableColumn<Sarcina, String> tableColumnDescriere;
    private ObservableList<Sarcina> modelSarcini= FXCollections.observableArrayList();

    public void setService(Service service, Angajat userCurent)
    {
        this.service=service;
        this.userCurent=userCurent;
        initModel();
    }



    @FXML
    public void initialize()
    {
        tableColumnStatut.setCellValueFactory(new PropertyValueFactory<Sarcina,StatutSarcina>("statutSarcina"));
        tableColumnStatut.setResizable(false);
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<Sarcina,String>("descriere"));
        tableColumnDescriere.setResizable(false);
        tableViewSarcini.setItems(modelSarcini);
    }


    public void initModel()
    {
        modelSarcini.setAll(service.findAllTasksForEmployee(userCurent.getId()));
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


    public void handleLogoutButton(ActionEvent actionEvent) throws IOException
    {
        userCurent.setStatutAngajat(StatutAngajat.offline);
        service.logout(userCurent);
        FXMLLoader fxmlLoader=new FXMLLoader(StartApplication.class.getResource("loginView.fxml"));
        Parent root=fxmlLoader.load();
        LoginController loginController=fxmlLoader.getController();
        loginController.setService(service);
        setScene(root,actionEvent,600,270,"Login");
    }

    @Override
    public void updateTabele()
    {
        Platform.runLater(()->{
            initModel();
        });
    }
}

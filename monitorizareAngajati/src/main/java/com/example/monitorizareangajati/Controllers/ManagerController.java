package com.example.monitorizareangajati.Controllers;

import com.example.monitorizareangajati.Model.*;
import com.example.monitorizareangajati.Observer.IObserver;
import com.example.monitorizareangajati.StartApplication;
import com.example.monitorizareangajati.Service.Service;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManagerController implements IObserver {
    private Service service;
    private Angajat userCurent;

    @FXML
    private TextField descriereSarcina;
    @FXML
    private TableView<Angajat> tableViewAngajati;
    @FXML
    private TableColumn<Angajat,String> tableColumnNumeAngajat;
    @FXML
    private TableColumn<Angajat, String> tableColumnDataOra;


    @FXML
    private TableView<SarcinaDTO> tableViewSarcini;
    @FXML
    private TableColumn<SarcinaDTO,String> tableColumnTrimisaCatre;
    @FXML
    private TableColumn<SarcinaDTO, String> tableColumnDescriere;


    private ObservableList<Angajat> modelAngajati=FXCollections.observableArrayList();
    private ObservableList<SarcinaDTO> modelSarcini= FXCollections.observableArrayList();



    public void setService(Service service, Angajat userCurent)
    {
        this.service=service;
        this.userCurent=userCurent;
        initModel();
    }



    @FXML
    public void initialize()
    {
        tableColumnNumeAngajat.setCellValueFactory(new PropertyValueFactory<Angajat,String>("nume"));
        tableColumnNumeAngajat.setResizable(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        tableColumnDataOra.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getDataOraSosirii();
            return new SimpleStringProperty(dateTime.format(formatter));
        });
        tableColumnDataOra.setResizable(false);
        tableViewAngajati.setItems(modelAngajati);

        tableColumnTrimisaCatre.setCellValueFactory(new PropertyValueFactory<SarcinaDTO,String>("numeAngajat"));
        tableColumnTrimisaCatre.setResizable(false);
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<SarcinaDTO,String>("descriere"));
        tableColumnDescriere.setResizable(false);
        tableViewSarcini.setItems(modelSarcini);
    }



    public void initModel()
    {
        modelAngajati.setAll(service.getOnlineEmployees());
        modelSarcini.setAll(service.findAllTasksForBoss());
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


    public void handleTrimiteSarcina(ActionEvent actionEvent)
    {
        Long idAngajat=tableViewAngajati.getSelectionModel().getSelectedItem().getId();
        String descriere= descriereSarcina.getText();
        Sarcina s=new Sarcina(idAngajat,descriere,LocalDateTime.now(), StatutSarcina.nefinalizata);
        service.trimiteSarcina(s);
        tableViewAngajati.getSelectionModel().clearSelection();
        descriereSarcina.clear();
        modelSarcini.setAll(service.findAllTasksForBoss());
    }


    public void handleStergeSarcina(ActionEvent actionEvent)
    {
        Long idSarcina=tableViewSarcini.getSelectionModel().getSelectedItem().getId();
        service.deleteSarcina(idSarcina);
        modelSarcini.setAll(service.findAllTasksForBoss());
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

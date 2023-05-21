package com.example.monitorizareangajati;

import com.example.monitorizareangajati.Controllers.LoginController;
import com.example.monitorizareangajati.Controllers.StartController;
import com.example.monitorizareangajati.Repository.Database.AngajatiDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.AnunturiDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.CereriConcediuDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.SarciniDatabaseRepository;
import com.example.monitorizareangajati.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        Properties applicationProps=new Properties();
        try {
            applicationProps.load(new FileReader("C:\\Users\\Marius Andreiasi\\JavaProjects\\monitorizareAngajati\\server.properties"));
            System.out.println("Server properties set. ");
            applicationProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        AngajatiDatabaseRepository repo_angajati=new AngajatiDatabaseRepository(applicationProps);
        SarciniDatabaseRepository repo_sarcini=new SarciniDatabaseRepository(applicationProps);
        AnunturiDatabaseRepository repo_anunturi=new AnunturiDatabaseRepository(applicationProps);
        CereriConcediuDatabaseRepository repo_cereri=new CereriConcediuDatabaseRepository(applicationProps);
        Service service=new Service(repo_angajati,repo_anunturi,repo_cereri,repo_sarcini);

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 80);
        stage.setTitle("Login");
        stage.setScene(scene);
        StartController startController=fxmlLoader.getController();
        startController.setService(service);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
package com.example.monitorizareangajati.Service;

import com.example.monitorizareangajati.Model.Angajat;
import com.example.monitorizareangajati.Model.Sarcina;
import com.example.monitorizareangajati.Model.SarcinaDTO;
import com.example.monitorizareangajati.Observer.IObserver;
import com.example.monitorizareangajati.Repository.Database.AngajatiDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.AnunturiDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.CereriConcediuDatabaseRepository;
import com.example.monitorizareangajati.Repository.Database.SarciniDatabaseRepository;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {
    private AngajatiDatabaseRepository repo_angajati;
    private AnunturiDatabaseRepository repo_anunturi;
    private CereriConcediuDatabaseRepository repo_cereri;
    private SarciniDatabaseRepository repo_sarcini;
    private Map<String, IObserver> loggedEmployees;
    private final int defaultThreadsNo=5;

    public Service(AngajatiDatabaseRepository repo_angajati,AnunturiDatabaseRepository repo_anunturi,
                   CereriConcediuDatabaseRepository repo_cereri,SarciniDatabaseRepository repo_sarcini)
    {
        this.repo_angajati=repo_angajati;
        this.repo_anunturi=repo_anunturi;
        this.repo_cereri=repo_cereri;
        this.repo_sarcini=repo_sarcini;
        loggedEmployees=new ConcurrentHashMap<>();
    }


    public String findEmployeeType(String name,String password)
    {
        return repo_angajati.findEmployeeType(name,password);
    }


    public Angajat login(String name,String password,IObserver angajat)
    {
        Angajat a=repo_angajati.findOneByNameAndPassword(name,password);
        if(a!=null)
        {
            repo_angajati.update(a);
            loggedEmployees.put(a.getNume(),angajat);
        }
        notifyObservers();
        return a;
    }


    public boolean logout(Angajat a)
    {
        loggedEmployees.remove(a.getNume());
        notifyObservers();
        return repo_angajati.update(a);
    }


    public List<Angajat> getOnlineEmployees()
    {
        return repo_angajati.findAllOnlineEmployees();
    }


    public List<SarcinaDTO> findAllTasksForBoss()
    {
        List<Angajat> angajati=repo_angajati.findAllEmployees();
        List<Sarcina> sarcini=repo_sarcini.findAllTasksForBoss();
        Map<Long,String> map_angajati=new HashMap<>();
        List<SarcinaDTO> sarciniDTOs=new ArrayList<>();
        for(Angajat a:angajati)
            map_angajati.put(a.getId(),a.getNume());
        for(Sarcina s:sarcini)
        {
            SarcinaDTO sarcinaDTO=new SarcinaDTO(map_angajati.get(s.getIdAngajat()),s.getDescriere());
            sarcinaDTO.setId(s.getId());
            sarciniDTOs.add(sarcinaDTO);
        }
        return sarciniDTOs;
    }


    public List<Sarcina> findAllTasksForEmployee(Long idAngajat)
    {
        return repo_sarcini.findAllTasksForEmployee(idAngajat);
    }


    public void trimiteSarcina(Sarcina s)
    {
        repo_sarcini.save(s);
        notifyObservers();
    }


    public void deleteSarcina(long idSarcina)
    {
        repo_sarcini.delete(idSarcina);
        notifyObservers();
    }


    public void notifyObservers()
    {
        ExecutorService executor=Executors.newFixedThreadPool(defaultThreadsNo);
        loggedEmployees.forEach((nume,angajat) -> {
            executor.execute(() -> {
                angajat.updateTabele();
                System.out.println("Notifying [" + nume + "]");
            });
        });
        executor.shutdown();
    }
}

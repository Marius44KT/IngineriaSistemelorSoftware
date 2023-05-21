package com.example.monitorizareangajati.Repository.Database;

import com.example.monitorizareangajati.Model.Angajat;
import com.example.monitorizareangajati.Model.StatutAngajat;
import com.example.monitorizareangajati.Model.TipAngajat;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class AngajatiDatabaseRepository {
    private final Properties props;


    public AngajatiDatabaseRepository(Properties props)
    {
        //logger.info("Initializing CarsDBRepository with properties: {} ",props);
        this.props=props;
    }



    public String findEmployeeType(String nume, String pass) {
        String sql="SELECT tipangajat from angajati where nume=? and parola=? and statut=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setString(1,nume);
            ps.setString(2,pass);
            ps.setString(3,"offline");
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getString("tipangajat");
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Angajat findOneByNameAndPassword(String nume, String pass) {
        String sql="SELECT * from angajati where nume=? and parola=? and statut=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setString(1,nume);
            ps.setString(2,pass);
            ps.setString(3,"offline");
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                TipAngajat tipAngajat= TipAngajat.valueOf(resultSet.getString("tipangajat"));
                LocalDateTime dataOraSosirii=LocalDateTime.now();
                StatutAngajat statutAngajat=StatutAngajat.valueOf("online");
                Angajat a=new Angajat(nume,pass,tipAngajat,dataOraSosirii,statutAngajat);
                a.setId(id);
                return a;
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public List<Angajat> findAllEmployees()
    {
        //logger.traceEntry();
        List<Angajat> lista_angajati=new ArrayList<>();
        String sql="SELECT * from angajati where tipangajat=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setString(1,"subaltern");
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                String nume=resultSet.getString("nume");
                String password=resultSet.getString("parola");
                TipAngajat tipAngajat= TipAngajat.valueOf(resultSet.getString("tipangajat"));
                LocalDateTime dataOraSosirii= resultSet.getTimestamp("dataorasosirii").toLocalDateTime();
                StatutAngajat statutAngajat=StatutAngajat.valueOf(resultSet.getString("statut"));
                Angajat a=new Angajat(nume,password,tipAngajat,dataOraSosirii,statutAngajat);
                a.setId(id);
                lista_angajati.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_angajati;
    }




    public List<Angajat> findAllOnlineEmployees()
    {
        //logger.traceEntry();
        List<Angajat> lista_angajati=new ArrayList<>();
        String sql="SELECT * from angajati where tipangajat=? and statut=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setString(1,"subaltern");
            ps.setString(2,"online");
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                String nume=resultSet.getString("nume");
                String password=resultSet.getString("parola");
                TipAngajat tipAngajat= TipAngajat.valueOf(resultSet.getString("tipangajat"));
                LocalDateTime dataOraSosirii= resultSet.getTimestamp("dataorasosirii").toLocalDateTime();
                StatutAngajat statutAngajat=StatutAngajat.valueOf("offline");
                Angajat a=new Angajat(nume,password,tipAngajat,dataOraSosirii,statutAngajat);
                a.setId(id);
                lista_angajati.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_angajati;
    }



    public boolean update(Angajat angajat)
    {
        String sql="update angajati set statut=?, dataorasosirii=? where id=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, angajat.getStatutAngajat().toString());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, angajat.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }
}

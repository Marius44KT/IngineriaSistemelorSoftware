package com.example.monitorizareangajati.Repository.Database;

import com.example.monitorizareangajati.Model.Anunt;
import com.example.monitorizareangajati.Model.Sarcina;
import com.example.monitorizareangajati.Model.StatutSarcina;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnunturiDatabaseRepository {
    private final Properties props;

    public AnunturiDatabaseRepository(Properties props)
    {
        //logger.info("Initializing CarsDBRepository with properties: {} ",props);
        this.props=props;
    }


    public boolean save(Anunt anunt)
    {
        //logger.traceEntry();
        String sql="insert into anunturi (id,idsef,dataoratrimiterii,subiect,descriere) values (?,?,?,?,?)";
        try (Connection connection= DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, anunt.getId());
            ps.setLong(2, anunt.getIdSef());
            ps.setTimestamp(3, Timestamp.valueOf(anunt.getDataOraTrimiterii()));
            ps.setString(4, anunt.getSubiect());
            ps.setString(5, anunt.getDescriere());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }



    public boolean delete(Long idAnunt)
    {
        //logger.traceEntry();
        String sql="delete from anunturi where id=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,idAnunt);
            ps.executeUpdate();
        } catch (SQLException e) {
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }



    public List<Anunt> findAllAnnouncements()
    {
        //logger.traceEntry();
        List<Anunt> lista_anunturi=new ArrayList<>();
        String sql="SELECT * from anunturi where DATE(dataoratrimiterii)=CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                long idSef=resultSet.getLong("idsef");
                LocalDateTime dataOraTrimiterii= resultSet.getTimestamp("dataoratrimiterii").toLocalDateTime();
                String subiect=resultSet.getString("subiect");
                String descriere=resultSet.getString("descriere");
                Anunt a=new Anunt(idSef,dataOraTrimiterii,subiect,descriere);
                a.setId(id);
                lista_anunturi.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_anunturi;
    }




    public Long getNewId()
    {
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps= connection.prepareStatement("SELECT max(id) from anunturi");
             ResultSet resultSet = ps.executeQuery()) {
            if(resultSet.next()) {
                return resultSet.getLong(1)+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

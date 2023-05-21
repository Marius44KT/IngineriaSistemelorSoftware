package com.example.monitorizareangajati.Repository.Database;

import com.example.monitorizareangajati.Model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SarciniDatabaseRepository {
    private final Properties props;


    public SarciniDatabaseRepository(Properties props)
    {
        //logger.info("Initializing CarsDBRepository with properties: {} ",props);
        this.props=props;
    }



    public boolean save(Sarcina s)
    {
        //logger.traceEntry();
        String sql="insert into sarcini (id,idangajat,descriere,dataoratrimiterii,statut) values (?,?,?,?,?)";
        try (Connection connection=DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, getNewId());
            ps.setLong(2, s.getIdAngajat());
            ps.setString(3, s.getDescriere());
            ps.setTimestamp(4, Timestamp.valueOf(s.getDataOraTrimiterii()));
            ps.setString(5, s.getStatutSarcina().toString());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            //logger.error(e);
            e.printStackTrace();
            return false;
        }
        //logger.traceExit();
        return true;
    }


    public boolean update(Sarcina s)
    {
        String sql="update sarcini set statut=? where id=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getStatutSarcina().toString());
            ps.setLong(2, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }



    public boolean delete(Long idSarcina)
    {
        //logger.traceEntry();
        String sql="delete from sarcini where id=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,idSarcina);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }




    public List<Sarcina> findAllTasksForBoss()
    {
        //logger.traceEntry();
        List<Sarcina> lista_sarcini=new ArrayList<>();
        String sql="SELECT * from sarcini where DATE(dataoratrimiterii)=CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                long idAngajat=resultSet.getLong("idangajat");
                String descriere=resultSet.getString("descriere");
                LocalDateTime dataOraTrimiterii= resultSet.getTimestamp("dataoratrimiterii").toLocalDateTime();
                String statut=resultSet.getString("statut");
                Sarcina s=new Sarcina(idAngajat,descriere,dataOraTrimiterii, StatutSarcina.valueOf(statut));
                s.setId(id);
                lista_sarcini.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_sarcini;
    }





    public List<Sarcina> findAllTasksForEmployee(long idAngajat)
    {
        //logger.traceEntry();
        String statut="nefinalizata";
        List<Sarcina> lista_sarcini=new ArrayList<>();
        String sql="SELECT * from sarcini where idangajat=? and statut=? and DATE(dataoratrimiterii)=CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setLong(1,idAngajat);
            ps.setString(2,statut);
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                String descriere=resultSet.getString("descriere");
                LocalDateTime dataOraTrimiterii= resultSet.getTimestamp("dataoratrimiterii").toLocalDateTime();
                Sarcina s=new Sarcina(idAngajat,descriere,dataOraTrimiterii, StatutSarcina.valueOf(statut));
                s.setId(id);
                lista_sarcini.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_sarcini;
    }



    public Integer getNumarSarciniIndeplinite(Long idAngajat)
    {
        //logger.traceEntry();
        String sql="SELECT count(*) from sarcini where idangajat=? and statut=? and DATE(dataoratrimiterii)=CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setLong(1,idAngajat);
            ps.setString(2,"nefinalizata");
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
                return resultSet.getInt(1);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
    }



    public Integer getNumarTotalSarcini(Long idAngajat)
    {
        //logger.traceEntry();
        String sql="SELECT count(*) from sarcini where idangajat=? and DATE(dataoratrimiterii)=CURRENT_DATE";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setLong(1,idAngajat);
            ResultSet resultSet=ps.executeQuery();
            if(resultSet.next())
                return resultSet.getInt(1);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
    }



    public Long getNewId()
    {
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps= connection.prepareStatement("SELECT max(id) from sarcini");
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

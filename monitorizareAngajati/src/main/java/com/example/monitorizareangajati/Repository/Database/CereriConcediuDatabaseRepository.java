package com.example.monitorizareangajati.Repository.Database;
import com.example.monitorizareangajati.Model.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CereriConcediuDatabaseRepository {

    private final Properties props;


    public CereriConcediuDatabaseRepository(Properties props)
    {
        //logger.info("Initializing CarsDBRepository with properties: {} ",props);
        this.props=props;
    }


    public List<CerereConcediu> findAllPendingRequests()
    {
        //logger.traceEntry();
        List<CerereConcediu> lista_cereri=new ArrayList<>();
        String sql="SELECT * from concedii where statut=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps=connection.prepareStatement(sql))
        {
            ps.setString(1,"inAsteptare");
            ResultSet resultSet=ps.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                Long idAngajat=resultSet.getLong("idAngajat");
                Date dataTrimiterii= resultSet.getDate("datatrimiterii");
                String motiv=resultSet.getString("motiv");
                Date dataInceput= resultSet.getDate("datainceput");
                Date dataSfarsit= resultSet.getDate("datasfarsit");
                StatutCerere statut=StatutCerere.valueOf(resultSet.getString("statut"));
                CerereConcediu cerere=new CerereConcediu(idAngajat,dataTrimiterii,motiv,dataInceput,dataSfarsit,statut);
                cerere.setId(id);
                lista_cereri.add(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return null;
        }
        //logger.traceExit(concurenti);
        return lista_cereri;
    }



    public boolean save(CerereConcediu c)
    {
        //logger.traceEntry();
        String sql="insert into concedii (id,idangajat,datatrimiterii,motiv,datainceput,datasfarsit,statut) values (?,?,?,?,?,?,?)";
        try (Connection connection=DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, getNewId());
            ps.setLong(2, c.getIdAngajat());
            ps.setDate(3, (Date)c.getDataTrimiterii());
            ps.setString(4, c.getMotiv());
            ps.setDate(5, (Date)c.getDataInceput());
            ps.setDate(6, (Date)c.getDataSfarsit());
            ps.setString(7,c.getStatutCerere().toString());
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


    public boolean update(CerereConcediu c)
    {
        String sql="update concedii set statut=? where id=?";
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getStatutCerere().toString());
            ps.setLong(2, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e);
            return false;
        }
        //logger.traceExit();
        return true;
    }



    public Long getNewId()
    {
        try (Connection connection = DriverManager.getConnection(props.getProperty("jdbc.url"),props.getProperty("jdbc.username"),props.getProperty("jdbc.password"));
             PreparedStatement ps= connection.prepareStatement("SELECT max(id) from concedii");
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

package com.example.monitorizareangajati.Model;

import java.time.LocalDateTime;

public class Anunt extends Entity<Long>{
    private long idSef;
    private LocalDateTime dataOraTrimiterii;
    private String subiect;
    private String descriere;

    public Anunt(long idSef, LocalDateTime dataOraTrimiterii, String subiect, String descriere)
    {
        this.idSef = idSef;
        this.dataOraTrimiterii = dataOraTrimiterii;
        this.subiect = subiect;
        this.descriere = descriere;
    }

    public long getIdSef()
    {
        return idSef;
    }

    public void setIdSef(long idSef)
    {
        this.idSef = idSef;
    }

    public LocalDateTime getDataOraTrimiterii()
    {
        return dataOraTrimiterii;
    }

    public void setDataOraTrimiterii(LocalDateTime dataOraTrimiterii)
    {
        this.dataOraTrimiterii = dataOraTrimiterii;
    }

    public String getSubiect()
    {
        return subiect;
    }

    public void setSubiect(String subiect)
    {
        this.subiect = subiect;
    }

    public String getDescriere()
    {
        return descriere;
    }

    public void setDescriere(String descriere)
    {
        this.descriere = descriere;
    }
}

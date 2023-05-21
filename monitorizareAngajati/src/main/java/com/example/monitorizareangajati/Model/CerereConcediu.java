package com.example.monitorizareangajati.Model;

import java.util.Date;

public class CerereConcediu extends Entity<Long>{
    private long idAngajat;
    private Date dataTrimiterii;
    private String motiv;
    private Date dataInceput;
    private Date dataSfarsit;
    private StatutCerere statutCerere;

    public CerereConcediu(long idAngajat, Date dataTrimiterii, String motiv, Date dataInceput, Date dataSfarsit, StatutCerere statutCerere)
    {
        this.idAngajat = idAngajat;
        this.dataTrimiterii = dataTrimiterii;
        this.motiv = motiv;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.statutCerere = statutCerere;
    }

    public long getIdAngajat()
    {
        return idAngajat;
    }

    public void setIdAngajat(long idAngajat)
    {
        this.idAngajat = idAngajat;
    }

    public Date getDataTrimiterii()
    {
        return dataTrimiterii;
    }

    public void setDataTrimiterii(Date dataTrimiterii)
    {
        this.dataTrimiterii = dataTrimiterii;
    }

    public String getMotiv()
    {
        return motiv;
    }

    public void setMotiv(String motiv)
    {
        this.motiv = motiv;
    }

    public Date getDataInceput()
    {
        return dataInceput;
    }

    public void setDataInceput(Date dataInceput)
    {
        this.dataInceput = dataInceput;
    }

    public Date getDataSfarsit()
    {
        return dataSfarsit;
    }

    public void setDataSfarsit(Date dataSfarsit)
    {
        this.dataSfarsit = dataSfarsit;
    }

    public StatutCerere getStatutCerere()
    {
        return statutCerere;
    }

    public void setStatutCerere(StatutCerere statutCerere)
    {
        this.statutCerere = statutCerere;
    }
}

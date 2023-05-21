package com.example.monitorizareangajati.Model;

import java.time.LocalDateTime;

public class Sarcina extends Entity<Long>{
    private long idAngajat;
    private String descriere;
    private LocalDateTime dataOraTrimiterii;
    private StatutSarcina statutSarcina;


    public Sarcina(long idAngajat, String descriere, LocalDateTime dataOraTrimiterii, StatutSarcina statutSarcina)
    {
        this.idAngajat = idAngajat;
        this.descriere = descriere;
        this.dataOraTrimiterii = dataOraTrimiterii;
        this.statutSarcina = statutSarcina;
    }

    public long getIdAngajat()
    {
        return idAngajat;
    }

    public void setIdAngajat(long idAngajat)
    {
        this.idAngajat = idAngajat;
    }

    public String getDescriere()
    {
        return descriere;
    }

    public void setDescriere(String descriere)
    {
        this.descriere = descriere;
    }

    public LocalDateTime getDataOraTrimiterii()
    {
        return dataOraTrimiterii;
    }

    public void setDataOraTrimiterii(LocalDateTime dataOraTrimiterii)
    {
        this.dataOraTrimiterii = dataOraTrimiterii;
    }

    public StatutSarcina getStatutSarcina()
    {
        return statutSarcina;
    }

    public void setStatutSarcina(StatutSarcina statutSarcina)
    {
        this.statutSarcina = statutSarcina;
    }
}

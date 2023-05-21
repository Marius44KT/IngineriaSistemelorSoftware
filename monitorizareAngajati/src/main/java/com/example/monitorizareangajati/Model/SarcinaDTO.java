package com.example.monitorizareangajati.Model;

public class SarcinaDTO extends Entity<Long>{
    private String numeAngajat;
    private String descriere;

    public SarcinaDTO(String numeAngajat, String descriere)
    {
        this.numeAngajat = numeAngajat;
        this.descriere = descriere;
    }

    public String getNumeAngajat()
    {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat)
    {
        this.numeAngajat = numeAngajat;
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

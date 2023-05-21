package com.example.monitorizareangajati.Model;

import java.time.LocalDateTime;

public class Angajat extends Entity<Long>{
    private String nume;
    private String parola;
    private TipAngajat tipAngajat;
    private LocalDateTime dataOraSosirii;
    private StatutAngajat statutAngajat;

    public Angajat(String nume, String parola, TipAngajat tipAngajat, LocalDateTime dataOraSosirii, StatutAngajat statutAngajat)
    {
        this.nume = nume;
        this.parola = parola;
        this.tipAngajat = tipAngajat;
        this.dataOraSosirii = dataOraSosirii;
        this.statutAngajat = statutAngajat;
    }

    public String getNume()
    {
        return nume;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public String getParola()
    {
        return parola;
    }

    public void setParola(String parola)
    {
        this.parola = parola;
    }

    public TipAngajat getTipAngajat()
    {
        return tipAngajat;
    }

    public void setTipAngajat(TipAngajat tipAngajat)
    {
        this.tipAngajat = tipAngajat;
    }

    public LocalDateTime getDataOraSosirii()
    {
        return dataOraSosirii;
    }

    public void setDataOraSosirii(LocalDateTime dataOraSosirii)
    {
        this.dataOraSosirii = dataOraSosirii;
    }

    public StatutAngajat getStatutAngajat()
    {
        return statutAngajat;
    }

    public void setStatutAngajat(StatutAngajat statutAngajat)
    {
        this.statutAngajat = statutAngajat;
    }
}

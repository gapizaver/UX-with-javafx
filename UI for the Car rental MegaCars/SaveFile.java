package dn3;

import java.io.Serializable;
import java.time.LocalDate;

public class SaveFile implements Serializable {
    public String ime;
    public String priimek;
    public String naslov;
    public String posta;
    public String kraj;
    public int danRojstva;
    public String mesecRojstva;
    public int letoRojstva;
    public boolean mladiVoznik;
    public String vrstaVozila;
    public int stSedezev;
    public String prostornina;
    public String moc;
    public boolean gorivoElektrika;
    public boolean gorivoDiesel;
    public boolean gorivoBencin;
    public String oznaka;
    public String znamka;
    public String regPrvaKraj;
    public String reg;
    public boolean ao;
    public boolean aoPlus;
    public boolean kaskoPolno;
    public boolean kaskoOdbitna;
    public boolean kaskoBrez;
    public boolean zavarovanjeStekel;
    public boolean zavarovanjeParkirisce;
    public boolean zavarovanjeTretjeOsebe;
    public boolean zavarovanjeGum;
    public boolean zavarovanjeProtiKraji;
    public boolean zavarovanjeProtiToci;
    public LocalDate datumPrveRegistracije;
}

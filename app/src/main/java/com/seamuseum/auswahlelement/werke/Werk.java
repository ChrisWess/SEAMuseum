package com.seamuseum.auswahlelement.werke;

/**
 * Created by Elham on 01.06.2017.
 */

public class Werk {

    private String titel;
    private String kuenstler;
    private String beschreibung;
    private String bildUrl;
    private String key;

    public Werk()
    {

    }

    public Werk(String titel, String kuenstler, String beschreibung, String bildUrl) {
        this.titel = titel;
        this.kuenstler = kuenstler;
        this.beschreibung = beschreibung;
        this.bildUrl = bildUrl;
    }
//
//    public Werk(String titel, String kuenstler, String beschreibung, String bildUrl, String key) {
//        this.titel = titel;
//        this.kuenstler = kuenstler;
//        this.beschreibung = beschreibung;
//        this.bildUrl = bildUrl;
//    }

    public void setTitel(String titel) {
        this.titel = titel;
    }


    public void setKuenstler(String kuenstler) {
        this.kuenstler = kuenstler;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setBildUrl(String bildUrl) {
        this.bildUrl = bildUrl;
    }

    public String getTitel() {
        return titel;
    }

    public String getKuenstler() {
        return kuenstler;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getBildUrl() {
        return bildUrl;
    }

    public void setKey(String s){key=s;}
    public String getKey() { return key;}

    @Override
    public String toString()
    {
        return titel;
    }






}

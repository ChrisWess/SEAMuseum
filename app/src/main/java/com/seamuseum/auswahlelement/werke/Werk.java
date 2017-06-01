package com.seamuseum.auswahlelement.werke;

/**
 * Created by Elham on 01.06.2017.
 */

public class Werk {

    public Werk()
    {

    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setBildUrl(String bildUrl) {
        this.bildUrl = bildUrl;
    }

    public Werk(String titel, String beschreibung, String bildUrl) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.bildUrl = bildUrl;
    }

    private String titel, beschreibung, bildUrl;

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getBildUrl() {
        return bildUrl;
    }



}

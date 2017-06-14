package com.seamuseum.auswahlelement;

import android.content.Context;

public final class AuswahlElement
{

    private final int auswahlelement;

    public AuswahlElement(int auswahlelement)
    {
        this.auswahlelement = auswahlelement;
    }

    // Java-typische Getter und Setter

    public int getAuswahlElement()
    {
        return auswahlelement;
    }

    public String getName(Context context)
    {
        return context.getString(getAuswahlElement());
    }

    /**
     * Liefert einen Wert aus {@code R.drawable}, der für das Zeichnen des
     * Sternzeichens verwendet werden kann.
     *
     * @return Wert aus {@code R.drawable}
     */
    public int getIdForDrawable()
    {
        switch (getAuswahlElement())
        {
            case R.string.auswahl1:
                return R.drawable.icon_werke;
            case R.string.auswahl2:
                return R.drawable.icon_games;
            case R.string.auswahl3:
                return R.drawable.icon_service;
            case R.string.auswahl6:
                return R.drawable.icon_place;
            case R.string.auswahl4:
                return R.drawable.icon_maps;
            case R.string.auswahl5:
                return R.drawable.icon_guestbook;
            case R.string.auswahl7:
                return R.drawable.icon_offers;
            default:
                return R.drawable.icon;
        }
    }
}
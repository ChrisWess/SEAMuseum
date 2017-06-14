package com.seamuseum.auswahlelement;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Diese Klasse bildet das Modell für die Listenansicht der App.
 */
public class AuswahlElementAdapter extends BaseAdapter
{

    private final List<AuswahlElement> zodiak;
    private final LayoutInflater inflator;

    public AuswahlElementAdapter(Context context)
    {
        // wird für das Aufblasen der XML-Datei benötigt
        inflator = LayoutInflater.from(context);
        // Auswahlelement für alle Monate ermitteln
        zodiak = new ArrayList<AuswahlElement>();
        zodiak.add(new AuswahlElement(R.string.auswahl1));
        zodiak.add(new AuswahlElement(R.string.auswahl2));
        zodiak.add(new AuswahlElement(R.string.auswahl5));
        zodiak.add(new AuswahlElement(R.string.auswahl6));
        zodiak.add(new AuswahlElement(R.string.auswahl4));
        zodiak.add(new AuswahlElement(R.string.auswahl7));
        zodiak.add(new AuswahlElement(R.string.auswahl3));
    }

    @Override
    public int getCount()
    {
        return zodiak.size();
    }

    @Override
    public Object getItem(int position)
    {
        return zodiak.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        // falls nötig, convertView bauen
        if (convertView == null)
        {
            // Layoutdatei entfalten
            convertView = inflator.inflate(R.layout.icon_text_text, parent, false);

            // Holder erzeugen
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text1);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else
        {
            // Holder bereits vorhanden
            holder = (ViewHolder) convertView.getTag();
        }
        Context context = parent.getContext();
        AuswahlElement auswahl = (AuswahlElement) getItem(position);
        holder.name.setText(auswahl.getName(context));
        holder.icon.setImageResource(auswahl.getIdForDrawable());
        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
        ImageView icon;
    }
}
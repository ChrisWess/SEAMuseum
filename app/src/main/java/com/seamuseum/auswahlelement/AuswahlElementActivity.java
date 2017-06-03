package com.seamuseum.auswahlelement;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.seamuseum.auswahlelement.comments.EntriesActivity;
import com.seamuseum.auswahlelement.service.KontaktActivity;
import com.seamuseum.auswahlelement.service.ServiceActivity;
import com.seamuseum.auswahlelement.spiele.UebersichtMenu;
import com.seamuseum.auswahlelement.werke.WerkeMainActivity;

/**
 * Dies ist die Listenansicht der App.
 */
public class AuswahlElementActivity extends ListActivity implements OnItemClickListener
{

    private AuswahlElementAdapter adapter;

    @Override
    protected void onStart()
    {
        super.onStart();
//        getActionBar().setDisplayShowHomeEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // hier werden die Auswahlelement gespeichert
        adapter = new AuswahlElementAdapter(this);
        setListAdapter(adapter);
        // auf das Antippen von Listenelementen
        // reagieren
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        AuswahlElement zeichen = (AuswahlElement) adapter.getItem(position);
        Intent i;
        switch (zeichen.getAuswahlElement())
        {
            case R.string.auswahl1:
                i = new Intent();
                i.setClass(getApplicationContext(), WerkeMainActivity.class);
                startActivity(i);
                break;
            case R.string.auswahl2:
                i = new Intent();
                i.setClass(getApplicationContext(), UebersichtMenu.class);
                startActivity(i);
                break;
            case R.string.auswahl3:
                i = new Intent();
                i.setClass(getApplicationContext(), ServiceActivity.class);
                startActivity(i);
                break;
            case R.string.auswahl6:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Museum für Kunst und Gewerbe Hamburg"));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
                break;
            case R.string.auswahl5:
                i = new Intent();
                EntriesActivity.key = "guestbookentries";
                i.setClass(getApplicationContext(), EntriesActivity.class);
                startActivity(i);
                break;
            default:
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_anmelden:
                return true;
            case R.id.menu_ueber_uns:
                Intent infoIntent = new Intent(this, KontaktActivity.class);
                startActivity(infoIntent);
                return true;
            case R.id.menu_rechtliches:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.seamuseum.auswahlelement.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.kontakt.KontaktActivity;
import com.seamuseum.auswahlelement.preis.PreisActivity;

public class ServiceActivity extends Activity
{

    private Button _preise;
    private Button _angebote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        buttonInit();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, AuswahlElementActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void buttonInit()
    {
        _preise = (Button) findViewById(R.id.preis_button);
        _preise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent test = new Intent(ServiceActivity.this, PreisActivity.class);
                startActivity(test);
            }
        });

        _angebote = (Button) findViewById(R.id.angebot_button);
        _angebote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent test = new Intent(ServiceActivity.this, KontaktActivity.class);
                startActivity(test);
            }
        });

    }
}

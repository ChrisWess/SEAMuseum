package com.seamuseum.auswahlelement.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;

public class AngebotActivity extends Activity
{

    private TextView _angebote;
    private DatabaseReference _rootRef;
    private DatabaseReference _angebotRef;
    private Button _kontakt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot);

        _angebote = (TextView) findViewById(R.id.angebote_aktuell);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        _angebotRef = _rootRef.child("Angebote");
        buttonInit();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.mipmap.ic_home_white_24dp);

        _angebotRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String angebotText = dataSnapshot.getValue(String.class);
                _angebote.setText(angebotText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
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

    private void buttonInit()
    {
        _kontakt = (Button) findViewById(R.id.kontakt_button_angebot);
        _kontakt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent kontakt = new Intent(AngebotActivity.this, KontaktActivity.class);
                startActivity(kontakt);
            }
        });
    }
}

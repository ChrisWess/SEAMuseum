package com.seamuseum.auswahlelement.werke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.LoginActivity;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.comments.EntriesActivity;


import uk.co.senab.photoview.PhotoViewAttacher;


public class WerkSingleActivity extends Activity {

    public static String _werkKey = null;
    private DatabaseReference _database;

    private ImageView _image;
    private TextView _description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_werk_single);

        _database = FirebaseDatabase.getInstance().getReference().child(
                getApplicationContext().getString(R.string.werkeDbRef));
        //_werkKey = getIntent().getExtras().getString("werkId");

        _image = (ImageView) findViewById(R.id.singleWerkImage);
        _description = (TextView) findViewById(R.id.singleWerkDescription);

        _database.child(_werkKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String titel = dataSnapshot.child(getApplicationContext().
                        getString(R.string.titelDb)).getValue(String.class);
                String kuenstler = dataSnapshot.child(getApplicationContext().
                        getString(R.string.kuenstlerDb)).getValue(String.class);
                String beschreibung = dataSnapshot.child(getApplicationContext().
                        getString(R.string.beschreibungDb)).getValue(String.class);
                String bildUrl = dataSnapshot.child(getApplicationContext().
                        getString(R.string.bildDb)).getValue(String.class);

                setTitle(titel + " - " + kuenstler);
                _description.setText(beschreibung);
                Glide.with(getApplicationContext())
                        .load(bildUrl)
                        .into(_image);
                PhotoViewAttacher pAttacher;
                pAttacher = new PhotoViewAttacher(_image);
                pAttacher.update();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.werke_single_menu, menu);
        onPrepareOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_comment)
        {
            EntriesActivity.key = _werkKey;
            startActivity(new Intent(getApplicationContext(), EntriesActivity.class));
        }
        if (item.getItemId() == R.id.action_update)
        {
            startActivity(new Intent(getApplicationContext(), WerkActivity.class));
        }
        if (item.getItemId() == R.id.action_remove)
        {
            _database.child(_werkKey).removeValue();
            Intent homeIntent = new Intent(getApplicationContext(), WerkeMainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem bearbeiten = menu.findItem(R.id.action_update);
        MenuItem entfernen = menu.findItem(R.id.action_remove);
        bearbeiten.setVisible(LoginActivity.loginFlag);
        entfernen.setVisible(LoginActivity.loginFlag);
        return true;
    }
}

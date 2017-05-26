package com.seamuseum.auswahlelement.kontakt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;

public class KontaktActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakt);
        initMuseumImage();

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

    private void initMuseumImage()
    {
        ImageView museum = (ImageView) findViewById(R.id.iv_museum);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/museumsapp-entwicklung.appspot.com/o/Museum.jpg?alt=media&token=964db0e3-a6e8-4edc-8f62-c05d7e7ed3e8")
                .centerCrop()
                .into(museum);
    }
}

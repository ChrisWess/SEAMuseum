package com.seamuseum.auswahlelement.guestbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seamuseum.auswahlelement.R;

public class GuestbookActivity extends Activity {

    public Button lesen;
    public Button verfassen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_options);
        lesen = (Button) findViewById(R.id.guestbookRead);
        lesen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), EntriesActivity.class);
                startActivity(i);
            }
        });
        verfassen = (Button) findViewById(R.id.guestbookWrite);
        verfassen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), WriteEntryActivity.class);
                startActivity(i);
            }
        });
    }

}

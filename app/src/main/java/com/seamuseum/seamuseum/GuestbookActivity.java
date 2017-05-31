package com.seamuseum.seamuseum;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seamuseum.auswahlelement.R;

public class GuestbookActivity extends AppCompatActivity {

    public Button bttn;
    public EditText textfeld;
    public EditText namefeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook);
        bttn = (Button) findViewById(R.id.button4);
        bttn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                CharSequence text = "Sie glauben ihre Nachricht wurde abgeschickt?! Da muss ich sie leider enttäuschen!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        textfeld = (EditText) findViewById(R.id.textfeld);
        namefeld = (EditText) findViewById(R.id.namefeld);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
    }

}

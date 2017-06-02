package com.seamuseum.auswahlelement.guestbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;

import java.util.Date;
import java.util.regex.Pattern;

public class WriteEntryActivity extends Activity {

    public Button bttn;
    public EditText textfeld;
    public EditText namefeld;
    private DatabaseReference _rootRef;
    private DatabaseReference _childRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_writeentry);
        textfeld = (EditText) findViewById(R.id.textfeld);
        namefeld = (EditText) findViewById(R.id.namefeld);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        bttn = (Button) findViewById(R.id.button4);
        bttn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                if(!textfeld.getText().toString().equals("") && namefeld.getText().toString().matches("[A-Za-z]+") && !namefeld.getText().toString().equals(""))
                {
                    User user = new User(namefeld.getText().toString(), textfeld.getText().toString());
                    _childRef = _rootRef.child("guestbookentries").child(user.toString());
                    _childRef.child("Nachricht").setValue(user.get_message());
                    _childRef.child("Name").setValue(user.get_name());
                    _childRef.child("Datum").setValue(user.get_time());
                    toast = Toast.makeText(context, "Abgesendet", duration);
                }
                else if(!namefeld.getText().toString().matches("[A-Za-z]+"))
                {
                    toast = Toast.makeText(context, "Sonderzeichen sind im Namen nicht erlaubt!", duration);
                }
                else
                {
                    toast = Toast.makeText(context, "Sie müssen einen Namen und eine Nachricht eingeben!!", duration);
                }

                    toast.show();

            }
        });
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

}

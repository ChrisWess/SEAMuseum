package com.seamuseum.auswahlelement.werke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;


public class SuchActivity extends Activity {

    public EditText suchfeld;
    public TextView suchergebnisse;
    public TextView leer;
    private DatabaseReference _rootRef;
    private int anzahlErgebnisse;
    private String lastResultKey;
    Query myTopPostsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_such);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        suchergebnisse = (TextView) findViewById(R.id.suchergebnisse);
        suchergebnisse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(anzahlErgebnisse == 1)
                {
                    WerkSingleActivity._werkKey = lastResultKey;
                    Intent intent = new Intent(getApplicationContext(), WerkSingleActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "Bitte Suche auf 1 Werk reduzieren!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        leer = (TextView) findViewById(R.id.leer);
        suchfeld = (EditText) findViewById(R.id.suchfeld);
        suchfeld.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s)
            {
                suchergebnisse.setText(suchfeld.getText().toString());
                myTopPostsQuery = _rootRef.child("Werke");// My top posts by number of stars

                myTopPostsQuery.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        anzahlErgebnisse=0;
                        Log.i("STATE", "BIN DA");
                        SpannableStringBuilder sb = new SpannableStringBuilder();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Log.d("STATE", postSnapshot.toString());
                            Werk werk = postSnapshot.getValue(Werk.class);
                            if(werk.getTitel() == null)
                            {
                                werk.setTitel("");
                            }
                            if (postSnapshot.getKey() != null && !suchfeld.getText().toString().equals("") && werk.getTitel().toLowerCase().contains(suchfeld.getText().toString().toLowerCase()))
                            {
                                ++anzahlErgebnisse;
                                sb.append(werk.getTitel() + "\n");
                                lastResultKey=postSnapshot.getKey();
                            }

                        }
                        suchergebnisse.setText(sb);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

}

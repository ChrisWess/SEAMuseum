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
    Query myTopPostsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_such);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        suchergebnisse = (TextView) findViewById(R.id.suchergebnisse);
        suchergebnisse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), WerkeMainActivity.class);
                startActivity(intent);
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
                        Log.i("STATE", "BIN DA");
                        SpannableStringBuilder sb = new SpannableStringBuilder();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Log.d("STATE", postSnapshot.toString());
                            if (postSnapshot != null && postSnapshot.getKey() != null && !suchfeld.getText().equals("") && postSnapshot.getKey().toLowerCase().contains(suchfeld.getText().toString().toLowerCase()))
                            {
                                sb.append(postSnapshot.getKey().toString() + "\n");
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

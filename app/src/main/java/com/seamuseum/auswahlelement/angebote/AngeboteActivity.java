package com.seamuseum.auswahlelement.angebote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;


/**
 * Created by Elham on 01.05.2017.
 */

public class AngeboteActivity extends Activity
{
    private TextView _textView;
    private Button _blaButton;

    private DatabaseReference _rootRef;
    private DatabaseReference _childRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebote);

        _textView = (TextView) findViewById(R.id.textView);
        _blaButton = (Button) findViewById(R.id.button2);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        _childRef = _rootRef.child("String");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        _childRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String text = dataSnapshot.getValue(String.class);
                _textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        _blaButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _childRef.setValue("Hallo Welt!");
            }
        });
    }
}

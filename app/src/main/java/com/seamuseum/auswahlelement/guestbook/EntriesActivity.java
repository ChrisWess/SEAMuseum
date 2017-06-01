package com.seamuseum.auswahlelement.guestbook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;

import org.w3c.dom.Text;

public class EntriesActivity extends Activity {

    public TextView text;
    private static int i;
    private DatabaseReference _rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_entries);
        text = (TextView) findViewById(R.id.textView3);
        refreshEntries();
    }

    private void refreshEntries()
    {
        //Zieh alle Daten
        _rootRef = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = _rootRef.child("guestbookentries");// My top posts by number of stars
        String result = "";
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    text.setText(text.getText() + "\n" +postSnapshot.getKey().toString() + " \n"+ postSnapshot.getValue().toString() +"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
        text.setText(result);
    }
}

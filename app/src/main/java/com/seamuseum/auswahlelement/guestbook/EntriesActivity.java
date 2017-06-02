package com.seamuseum.auswahlelement.guestbook;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SpannableStringBuilder sb = new SpannableStringBuilder();
                List<User> users = new ArrayList<User>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(postSnapshot != null && postSnapshot.child("Name") != null)
                    {
                        String nameOhneDatum= postSnapshot.child("Name").getValue().toString();
                        String nachricht = postSnapshot.child("Nachricht").getValue().toString();
                        long zeitpunkt = Long.parseLong(postSnapshot.child("Datum").getValue().toString());
                        User user = new User(nameOhneDatum, nachricht, zeitpunkt);
                        users.add(user);
                    }

                }
                Collections.sort(users);
                for(User user : users)
                {
                    int start = sb.length();
                    sb.append(user.get_name());
                    sb.setSpan(new RelativeSizeSpan(1.75f), start, sb.length(), 0);
                    sb.setSpan(new ForegroundColorSpan(Color.BLACK), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //String value= postSnapshot.getValue().toString();
                    sb.append("\n");
                    sb.append(user.get_date().toString());
                    sb.setSpan(new RelativeSizeSpan(0.8f), sb.length() - user.get_date().toString().length(), sb.length(), 0);
                    sb.setSpan(new ForegroundColorSpan(Color.GRAY), sb.length() - user.get_date().toString().length(), sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.append("\n");
                    sb.append(user.get_message());
                    sb.setSpan(new RelativeSizeSpan(1.25f), sb.length() - user.get_message().length(), sb.length(), 0);
                    sb.setSpan(new ForegroundColorSpan(Color.BLACK), sb.length() - user.get_message().length(), sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.append("\n \n");
                }
                text.setText(sb);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });
    }
}

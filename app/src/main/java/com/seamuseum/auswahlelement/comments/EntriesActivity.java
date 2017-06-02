package com.seamuseum.auswahlelement.comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.werke.WerkActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntriesActivity extends Activity {

    public TextView text;
    public static String key;
    private static int i;
    private DatabaseReference _rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_entries);
        text = (TextView) findViewById(R.id.textView3);
        refreshEntries();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.werke_main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add)
        {
            startActivity(new Intent(getApplicationContext(), WriteEntryActivity.class));
        }
        if(item.getItemId() == R.id.action_settings)
        {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Beleidigungen verboten!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshEntries()
    {
        _rootRef = FirebaseDatabase.getInstance().getReference();
        //Zieh alle Daten aus dem Gästebuch, falls es darum geht.
        if(key.equals("guestbookentries"))
        {
            Query myTopPostsQuery = _rootRef.child(key);// My top posts by number of stars
            myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    SpannableStringBuilder sb = new SpannableStringBuilder();
                    List<User> users = new ArrayList<User>();

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        if(postSnapshot != null && postSnapshot.child("Name") != null && postSnapshot.child("Name").getValue() != null
                                && postSnapshot.child("Nachricht").getValue() != null && postSnapshot.child("Datum").getValue() != null)
                        {
                            String nameOhneDatum= postSnapshot.child("Name").getValue().toString();
                            String nachricht = postSnapshot.child("Nachricht").getValue().toString();
                            long zeitpunkt = Long.parseLong(postSnapshot.child("Datum").getValue().toString());
                            User user = new User(nameOhneDatum, nachricht, zeitpunkt);
                            users.add(user);
                        }

                    }
                    Collections.sort(users);
                    int i = 0;
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
                        sb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                start, sb.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.append("\n");
                        sb.append(user.get_message());
                        sb.setSpan(new RelativeSizeSpan(1.25f), sb.length() - user.get_message().length(), sb.length(), 0);
                        sb.setSpan(new ForegroundColorSpan(Color.BLACK), sb.length() - user.get_message().length(), sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.append("\n \n");
                        //            if(i % 2 == 0)
                        //            {
                        //                //sb.setSpan(new BackgroundColorSpan(0xFFCCCCCC),start,sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //            }
                        //            ++i;
                    }
                    text.setText(sb);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a me
                    // ...
                }
            });
        }
        //Ziehe alle Kommentare zu diesem Werk
        else
        {
            Query myTopPostsQuery = _rootRef.child("Werke").child(key);// My top posts by number of stars
            myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Zeig alle Kommentare zu diesem Werk....
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            });
        }

    }
}

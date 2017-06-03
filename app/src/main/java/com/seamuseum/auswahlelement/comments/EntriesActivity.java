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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntriesActivity extends Activity {

    public TextView text;
    public static String key ="guestbookentries";
    private static int i;
    private DatabaseReference _rootRef;

    Query myTopPostsQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_entries);
        text = (TextView) findViewById(R.id.textView3);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        refreshEntries();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //refreshEntries();
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
            Toast toast = Toast.makeText(context, "Beleidigungen verboten!", Toast.LENGTH_SHORT); //TODO: ordentlich machen
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), AuswahlElementActivity.class));
    }

    protected void refreshEntries()
    {
        Log.d("STATE", "HEY");

        if(key.equals("guestbookentries")) {
            myTopPostsQuery = _rootRef.child(key);// My top posts by number of stars
        }
        else
        {
            myTopPostsQuery = _rootRef.child("Werke").child(key).child("Kommentare");// My top posts by number of stars
        }
        myTopPostsQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                Log.i("STATE", "BIN DA");
                SpannableStringBuilder sb = new SpannableStringBuilder();
                List<User> users = new ArrayList<User>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("STATE", "BIN DRIN");
                    Log.d("STATE", postSnapshot.toString());
                    if(postSnapshot != null && postSnapshot.child("Name") != null && postSnapshot.child("Name").getValue() != null
                            && postSnapshot.child("Datum") != null && postSnapshot.child("Datum").getValue() != null
                            && postSnapshot.child("Nachricht") != null && postSnapshot.child("Nachricht").getValue() != null)
                    {
                        String nameOhneDatum= postSnapshot.child("Name").getValue().toString();
                        String nachricht = postSnapshot.child("Nachricht").getValue().toString();
                        long zeitpunkt = Long.parseLong(postSnapshot.child("Datum").getValue().toString());
                        Log.d("INHALT",nameOhneDatum + " " + nachricht + " " + zeitpunkt);
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
                if(sb.toString().equals(""))
                {
                    Log.e("STATE", "LEERER STRING");
                }
                Log.i("STATE", sb.toString());
                text.setText(sb);
            }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // Getting Post failed, log a me
                    // ...
                }
        });

    }

}

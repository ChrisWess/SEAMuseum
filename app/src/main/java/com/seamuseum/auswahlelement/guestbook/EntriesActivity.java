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
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    int start = sb.length();
                    String key= nameWithoutDate(postSnapshot.getKey().toString());
                    sb.append(key);
                    sb.setSpan(new RelativeSizeSpan(1.5f), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new ForegroundColorSpan(Color.BLACK), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    String value= postSnapshot.getValue().toString();
                    sb.append("\n");
                    sb.append(value);
                    sb.setSpan(new ForegroundColorSpan(Color.GRAY), sb.length() - value.length(), sb.length(), 0);
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

    private String nameWithoutDate(String nameWithDate)
    {
        char c = nameWithDate.charAt(0);
        String result = "";
        int i = 0;
        while(c != '@')
        {
            result+=c;
            c=nameWithDate.charAt(++i);
        }
        return result;
    }
}

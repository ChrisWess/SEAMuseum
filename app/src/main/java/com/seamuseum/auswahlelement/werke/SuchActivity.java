package com.seamuseum.auswahlelement.werke;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
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


public class SuchActivity extends Activity {

    public EditText suchfeld;
    public ListView suchergebnisse;
    TextView titel;
    private DatabaseReference _rootRef;
    Query myTopPostsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_such);
        _rootRef = FirebaseDatabase.getInstance().getReference();
        titel = (TextView) findViewById(R.id.sucherklaerung);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("Suche\n\n");
        sb.setSpan(new RelativeSizeSpan(2f), 0, sb.length(), 0);
        sb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append("Geben Sie bitte hier ihren Suchbegriff oder eine Kombination aus Buchstaben ein. Unsere Suche wird entsprechend für Sie filtern. " +
                "Klicken Sie dann einfach das Werk an, das Sie betrachten möchten!\n");
        int start = sb.length();
        sb.setSpan(new RelativeSizeSpan(1f), start, sb.length(), 0);
        sb.setSpan(new ForegroundColorSpan(Color.GRAY), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),
                start, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titel.setText(sb);

        suchergebnisse = (ListView) findViewById(R.id.suchergebnisse);
        suchfeld = (EditText) findViewById(R.id.suchfeld);
        suchfeld.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s)
            {
                myTopPostsQuery = _rootRef.child("Werke");

                myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Werk> ergebnisse = new ArrayList<Werk>();
                        Log.i("STATE", "BIN DA");
                        SpannableStringBuilder sb = new SpannableStringBuilder();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.d("STATE", postSnapshot.toString());
                            Werk werk = postSnapshot.getValue(Werk.class);
                            if (werk.getTitel() == null) {
                                werk.setTitel("");
                            }
                            if (postSnapshot.getKey() != null && !suchfeld.getText().toString().equals("") && werk.getTitel().toLowerCase().contains(suchfeld.getText().toString().toLowerCase()))
                            {
                                werk.setKey(postSnapshot.getKey());
                                ergebnisse.add(werk);
                            }

                        }
                        Werk[] werke = ergebnisse.toArray(new Werk[ergebnisse.size()]);
                        ArrayAdapter<Werk> ergebnisAdapter = new ArrayAdapter<Werk>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, werke);
                        suchergebnisse = (ListView) findViewById(R.id.suchergebnisse);
                        suchergebnisse.setAdapter(ergebnisAdapter);
                        suchergebnisse.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                WerkSingleActivity._werkKey = ((Werk) suchergebnisse.getItemAtPosition(arg2)).getKey();
                                Intent intent = new Intent(getApplicationContext(), WerkSingleActivity.class);
                                startActivity(intent);
                            }

                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.mipmap.ic_home_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, AuswahlElementActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}

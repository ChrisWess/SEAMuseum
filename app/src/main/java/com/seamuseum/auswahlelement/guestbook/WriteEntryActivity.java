package com.seamuseum.auswahlelement.guestbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.seamuseum.auswahlelement.R;

public class WriteEntryActivity extends Activity {

    public Button bttn;
    public EditText textfeld;
    public EditText namefeld;
    private List<String>  autor, inhalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autor = new ArrayList<String>();
        inhalt = new ArrayList<String>();
        setContentView(R.layout.activity_guestbook);
        bttn = (Button) findViewById(R.id.button4);
        bttn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                autor.add(namefeld.getText().toString());
                inhalt.add(textfeld.getText().toString());
                String text="";
                for(int i = 0; i < autor.size(); ++i)
                {
                    text+= autor.get(i) + ": " + inhalt.get(i) + "\n";
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        textfeld = (EditText) findViewById(R.id.textfeld);
        namefeld = (EditText) findViewById(R.id.namefeld);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
    }

}

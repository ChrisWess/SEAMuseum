package com.seamuseum.auswahlelement.comments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.seamuseum.auswahlelement.R;

public class KommentarRegelnActivity extends Activity {

    TextView regeln;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kommentar_regeln);
        regeln = (TextView) findViewById(R.id.TextRegeln);
    }

}

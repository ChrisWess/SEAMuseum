package com.seamuseum.auswahlelement.gebaeudeplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuInflater;


import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.seamuseum.auswahlelement.R;

public class PlanAnzeigenActivity extends Activity {
    ImageView imageView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_anzeigen);

        imageView = (ImageView) findViewById(R.id.photo_view);
        mAttacher = new PhotoViewAttacher(imageView);
        showpicture("https://firebasestorage.googleapis.com/v0/b/museumsapp-entwicklung.appspot.com/o/EG_Plan.jpg?alt=media&token=dc618d5d-95f8-4464-a909-79448ce069f5");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_plan, menu);
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_eg:
                showpicture("https://firebasestorage.googleapis.com/v0/b/museumsapp-entwicklung.appspot.com/o/EG_Plan.jpg?alt=media&token=dc618d5d-95f8-4464-a909-79448ce069f5");
                return true;
            case R.id.menu_ob_eins:
                showpicture("https://firebasestorage.googleapis.com/v0/b/museumsapp-entwicklung.appspot.com/o/Etage1.jpg?alt=media&token=4441360c-0c5b-4d19-99fd-a61416a0e381");
                return true;
            case R.id.menu_ob_zwei:
                showpicture("https://firebasestorage.googleapis.com/v0/b/museumsapp-entwicklung.appspot.com/o/Etage2.jpg?alt=media&token=9d4129e0-04ff-407f-b9c1-35323cd2364e");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showpicture(String s)
    {
        ImageView museum = (ImageView) findViewById(R.id.photo_view);
        Glide.with(this)
                .load(s)
                .fitCenter()
                .into(museum);
    }




}
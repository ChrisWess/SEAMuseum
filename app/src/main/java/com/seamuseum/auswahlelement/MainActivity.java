package com.seamuseum.auswahlelement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.seamuseum.auswahlelement.comments.EntriesActivity;
import com.seamuseum.auswahlelement.gebaeudeplan.PlanAnzeigenActivity;
import com.seamuseum.auswahlelement.service.ServiceActivity;
import com.seamuseum.auswahlelement.spiele.UebersichtMenu;
import com.seamuseum.auswahlelement.werke.SuchActivity;
import com.seamuseum.auswahlelement.werke.WerkeMainActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;

        if (id == R.id.nav_werke) {
            i = new Intent();
            i.setClass(getApplicationContext(), WerkeMainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_spiele) {
            i = new Intent();
            i.setClass(getApplicationContext(), UebersichtMenu.class);
            startActivity(i);
        } else if (id == R.id.nav_service) {
            i = new Intent();
            i.setClass(getApplicationContext(), ServiceActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_museumskarte) {
                i = new Intent();
                i.setClass(getApplicationContext(), PlanAnzeigenActivity.class);
                startActivity(i);
        } else if (id == R.id.nav_anfahrt) {
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Museum für Kunst und Gewerbe Hamburg"));
            i.setPackage("com.google.android.apps.maps");
            startActivity(i);
        } else if (id == R.id.nav_gaestebuch) {
            i = new Intent();
            EntriesActivity.key = "guestbookentries";
            i.setClass(getApplicationContext(), EntriesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_suche) {
            i = new Intent();
            i.setClass(getApplicationContext(), SuchActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

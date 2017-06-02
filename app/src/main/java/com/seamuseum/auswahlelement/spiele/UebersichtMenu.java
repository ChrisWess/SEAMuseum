package com.seamuseum.auswahlelement.spiele;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.spiele.artsweeper.game.ArtSweeperActivity;
import com.seamuseum.auswahlelement.spiele.quiz.QuizActivity;

public class UebersichtMenu extends Activity {
    private static Context _context;

    @Override
    protected void onStart() {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        _context = getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, AuswahlElementActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebersicht_menu);
    }

    /**
     * Dieses Fragment zeigt eine Auswahlliste.
     */
    public static class SpielAuswahl extends ListFragment {

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            String quizItem = getString(R.string.spiel1);
            String memoryItem = getString(R.string.spiel2);
            String mineItem = getString(R.string.spiel3);
            // Liste aus einem String-Array befüllen
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, new
                    String[]{quizItem, memoryItem, mineItem}));
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            // zuletzt selektierten Eintrag merken

            //outState.putInt(STR_ZULETZT_SELEKTIERT, zuletztSelektiert);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            spielOeffnen(position);
        }

        private void spielOeffnen(int position) {
            Intent i;
            switch(position)
            {
                case 0:
                    i = new Intent();
                    i.setClass(_context, QuizActivity.class);
                    startActivity(i);
                    break;
                case 1:

                    break;
                case 2:
                    i = new Intent();
                    i.setClass(_context, ArtSweeperActivity.class);
                    startActivity(i);
            }
        }
    }
}

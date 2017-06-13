package com.seamuseum.auswahlelement.spiele;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.spiele.artsweeper.game.ArtSweeperActivity;
import com.seamuseum.auswahlelement.spiele.memory.MemoryGame;
import com.seamuseum.auswahlelement.spiele.puzzle.PuzzleActivity;
import com.seamuseum.auswahlelement.spiele.quiz.QuizActivity;

public class UebersichtMenu extends Activity {
    private Context _context;
    public ImageButton artsweeper;
    public ImageButton memory;
    public ImageButton quiz;
    public ImageButton puzzle;

    @Override
    protected void onStart() {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.mipmap.ic_home_white_24dp);
        _context = getApplicationContext();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MUSS NUR GEÄNDERT WERDEN, DAMIT DIE ALTE VERSION WIEDER LÄUFT!
        setContentView(R.layout.new_spieleuebersicht);

        artsweeper = (ImageButton) findViewById(R.id.buttonArtsweeper);
        artsweeper.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(_context, ArtSweeperActivity.class);
                startActivity(i);
            }
        });
        memory = (ImageButton) findViewById(R.id.buttonMemory);
        memory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(_context, MemoryGame.class);
                startActivity(i);
            }
        });
        quiz = (ImageButton) findViewById(R.id.buttonQuiz);
        quiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(_context, QuizActivity.class);
                startActivity(i);
            }
        });
        puzzle = (ImageButton) findViewById(R.id.buttonPuzzle);
        puzzle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent();
                i.setClass(_context, PuzzleActivity.class);
                startActivity(i);
            }
        });
    }
}

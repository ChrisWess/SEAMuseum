package com.seamuseum.auswahlelement.spiele;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seamuseum.auswahlelement.AuswahlElementActivity;
import com.seamuseum.auswahlelement.R;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class QuizActivity extends Activity {

    private final Button[] _buttonArray = new Button[4];
    private Button _loesungsButton;
    private TextView _frage_loesung;

    private XMLReader _xmlDaten;
    private String _loesungsSatz;

    @Override
    protected void onStart()
    {
        super.onStart();
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
        setContentView(R.layout.activity_quiz);

        _frage_loesung = (TextView) findViewById(R.id.frage_loesung);

        _buttonArray[0] = (Button) findViewById(R.id.antwort1button);
        _buttonArray[1] = (Button) findViewById(R.id.antwort2button);
        _buttonArray[2] = (Button) findViewById(R.id.antwort3button);
        _buttonArray[3] = (Button) findViewById(R.id.antwort4button);

        _xmlDaten = new XMLReader();
        leseAusDatenbestand();

        for (int i = 0; i < _buttonArray.length; ++i) {
            _buttonArray[i].setOnClickListener(initButton(_buttonArray[i]));
        }
    }

    private View.OnClickListener initButton(final Button buttonClicked) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Button b : _buttonArray) {
                    b.setClickable(false);
                }

                final Drawable defaultBackground = buttonClicked.getBackground();

                if (buttonClicked != _loesungsButton) {
                    buttonClicked.setBackgroundResource(R.drawable.quizbtnfalsch);
                    _frage_loesung.setText("Falsch: " + _loesungsSatz);
                } else {
                    _frage_loesung.setText("Korrekt: " + _loesungsSatz);
                }
                _loesungsButton.setBackgroundResource(R.drawable.quizbtnrichtig);

                Handler myHandler = new Handler();
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        buttonClicked.setBackground(defaultBackground);
                        _loesungsButton.setBackground(defaultBackground);

                        leseAusDatenbestand();

                        for (Button b : _buttonArray) {
                            b.setClickable(true);
                        }
                    }
                }, 3000);

            }
        };
    }

    private void leseAusDatenbestand() {
        String[] datenSatz = _xmlDaten.getDatensatz();

        _frage_loesung.setText(datenSatz[0]);

        String[] antworten = Arrays.copyOfRange(datenSatz, 1, 5);
        for (int i = 0; i < _buttonArray.length; ++i) {
            _buttonArray[i].setText(antworten[i]);
        }

        int loesung = Integer.parseInt(datenSatz[5]);
        _loesungsButton = _buttonArray[loesung - 1];
        _loesungsSatz = datenSatz[6];
    }

    private class XMLReader {
        private final int _datenProSatz;
        private int _aktuelleStelle;
        private ArrayList<String> _daten;

        public XMLReader() {
            _datenProSatz = 7;
            _aktuelleStelle = 0;
            _daten = new ArrayList<>();
            parseDaten();
        }

        public ArrayList<String> getDaten() {
            return _daten;
        }

        public String[] getDatensatz() {
            if (_daten.size() == _aktuelleStelle) {
                _aktuelleStelle = 0;
            }

            String[] satz = new String[_datenProSatz];
            for (int i = 0; i < _datenProSatz; ++i) {
                satz[i] = _daten.get(_aktuelleStelle + i);
            }
            _aktuelleStelle += 7;

            return satz;
        }

        private void parseDaten() {

            try {

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {

                    String[] datenSatz = new String[_datenProSatz];
                    boolean bfsatz = false;
                    boolean ba1 = false;
                    boolean ba2 = false;
                    boolean ba3 = false;
                    boolean ba4 = false;
                    boolean bra = false;
                    boolean blsatz = false;

                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                        switch (qName) {
                            case "fragesatz":
                                bfsatz = true;
                                break;
                            case "antwort1":
                                ba1 = true;
                                break;
                            case "antwort2":
                                ba2 = true;
                                break;
                            case "antwort3":
                                ba3 = true;
                                break;
                            case "antwort4":
                                ba4 = true;
                                break;
                            case "richtigeantwort":
                                bra = true;
                                break;
                            case "loesungssatz":
                                blsatz = true;
                        }
                    }

                    public void endElement(String uri, String localName,
                                           String qName) throws SAXException {

                        if (qName.equals("frage")) {
                            for (String s : datenSatz) {
                                _daten.add(s);
                            }
                        }
                    }

                    public void characters(char[] ch, int start, int length) throws SAXException {

                        if (bfsatz) {
                            datenSatz[0] = new String(ch, start, length);
                            bfsatz = false;
                        }

                        if (ba1) {
                            datenSatz[1] = new String(ch, start, length);
                            ba1 = false;
                        }

                        if (ba2) {
                            datenSatz[2] = new String(ch, start, length);
                            ba2 = false;
                        }

                        if (ba3) {
                            datenSatz[3] = new String(ch, start, length);
                            ba3 = false;
                        }

                        if (ba4) {
                            datenSatz[4] = new String(ch, start, length);
                            ba4 = false;
                        }

                        if (bra) {
                            datenSatz[5] = new String(ch, start, length);
                            bra = false;
                        }

                        if (blsatz) {
                            datenSatz[6] = new String(ch, start, length);
                            blsatz = false;
                        }
                    }

                };

                saxParser.parse(getResources().getAssets().open("quizdaten.xml"), handler);

            } catch (Exception e) {
                System.out.println("Error loading assets!");
            }

        }
    }
}
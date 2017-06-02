package com.seamuseum.auswahlelement.spiele.artsweeper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.seamuseum.auswahlelement.spiele.artsweeper.drawable.BeveledTileDrawable;
import com.seamuseum.auswahlelement.spiele.artsweeper.exceptions.InvalidArgumentException;

public class BeveledTileTextView extends TextView {
    public static String TAG = BeveledTileTextView.class.getName();

    public BeveledTileTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        BeveledTileDrawable.BeveledTileAttributeSet beveledAttributes =
                BeveledTileDrawable.extractAttributes(context, attrs);

        try {
            BeveledTileDrawable drawable =
                    new BeveledTileDrawable(beveledAttributes.getColorArray(), beveledAttributes.getFillPercent());

            setBackground(drawable);
        } catch (InvalidArgumentException e) {
            Log.e(TAG, e.getClass().getName());
        }
    }
}


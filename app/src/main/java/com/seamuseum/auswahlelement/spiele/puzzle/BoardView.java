package com.seamuseum.auswahlelement.spiele.puzzle;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import com.seamuseum.auswahlelement.spiele.puzzle.model.Board;
import com.seamuseum.auswahlelement.spiele.puzzle.model.Place;
import com.seamuseum.auswahlelement.R;

/**
 * The Class BoardView. It uses 2-D graphics to display the puzzle board.
 *
 * @author Caio Lopes
 * @version 1.0 $
 */
public class BoardView extends View {

    /** The board. */
    private Board board;

    /** The width. */
    private float width;

    /** The height. */
    private float height;

    private HashMap<Integer, Bitmap> tileImages;

    /**
     * Instantiates a new board view.
     *
     * @param context
     *            the context
     * @param board
     *            the board
     */
    public BoardView(Context context, Board board) {
        super(context);
        this.board = board;
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.tileImages = new HashMap<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w / this.board.size();
        this.height = h / this.board.size();
        super.onSizeChanged(w, h, oldw, oldh);

        //Zerschneide Bild
        Bitmap image = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mona), w, h);
        int count = 1;
        for(int y = 0; y < 4; ++y)
        {
            for(int x = 0; x < 4; ++x)
            {
                float xStart = x*this.width;
                float yStart = y*this.height;

                Bitmap tileImage = Bitmap.createBitmap(image,
                        (int)xStart, (int)yStart, (int)this.width, (int)this.height);
                this.tileImages.put(count, tileImage);
                count++;
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * Locate place.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return the place
     */
    private Place locatePlace(float x, float y) {
        int ix = (int) (x / width);
        int iy = (int) (y / height);

        return board.at(ix + 1, iy + 1);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        Place p = locatePlace(event.getX(), event.getY());
        if (p != null && p.slidable() && !board.solved()) {
            p.slide();
            invalidate();
        }
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(ContextCompat.getColor(getContext(), R.color.black));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Iterator<Place> it = board.places().iterator();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (it.hasNext()) {
                    Place p = it.next();
                    if (p.hasTile()) {
                        Bitmap tile = tileImages.get(p.getTile().number());
                        canvas.drawBitmap(tile, i*this.width, j*this.height, background);
                    }
                }
            }
        }

        /*Alter Look

        Paint background = new Paint();
        background.setColor(ContextCompat.getColor(getContext(), R.color.board_color));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Paint dark = new Paint();
        dark.setColor(ContextCompat.getColor(getContext(), R.color.tile_color));
        dark.setStrokeWidth(15);

        // Draw the major grid lines
        for (int i = 0; i < this.board.size(); i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
        }

        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(ContextCompat.getColor(getContext(), R.color.tile_color));
        foreground.setStyle(Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        float x = width / 2;
        FontMetrics fm = foreground.getFontMetrics();
        float y = (height / 2) - (fm.ascent + fm.descent) / 2;

        Iterator<Place> it = board.places().iterator();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (it.hasNext()) {
                    Place p = it.next();
                    if (p.hasTile()) {
                        String number = Integer.toString(p.getTile().number());
                        canvas.drawText(number, i * width + x, j * height + y,
                                foreground);
                    } else {
                        canvas.drawRect(i * width, j * height, i * width
                                + width, j * height + height, dark);
                    }
                }
            }
        }*/
    }
}
package com.seamuseum.auswahlelement.spiele.artsweeper.board;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.view.View;

import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.spiele.artsweeper.GameBus;
import com.seamuseum.auswahlelement.spiele.artsweeper.drawable.BeveledTileDrawable;
import com.seamuseum.auswahlelement.spiele.artsweeper.drawable.TextDrawable;
import com.seamuseum.auswahlelement.spiele.artsweeper.exceptions.InvalidArgumentException;
import com.seamuseum.auswahlelement.spiele.artsweeper.game.Game;
import com.seamuseum.auswahlelement.spiele.artsweeper.utilities.GraphicsUtils;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

public class TileView extends View {
    // Board Square states
    public static final int COVERED = 0;
    public static final int FLAGGED_AS_MINE = 1;
    public static final int UNCOVERED = 2;

    // User gestures
    public static final int CLICK = 0;
    public static final int LONG_CLICK = 1;

    private LevelListDrawable mDrawableContainer;
    private int mXGridCoordinate;
    private int mYGridCoordinate;

    private Bus mGameBus;

    static Map<Integer, Integer> sAdjacentMineCountToColorMap = new HashMap<>();

    // Colors for adjacent mines count
    static {
        sAdjacentMineCountToColorMap.put(1, Color.RED);
        sAdjacentMineCountToColorMap.put(2, Color.BLUE);
        sAdjacentMineCountToColorMap.put(3, Color.GREEN);
        sAdjacentMineCountToColorMap.put(4, Color.DKGRAY);
        sAdjacentMineCountToColorMap.put(5, Color.MAGENTA);
        sAdjacentMineCountToColorMap.put(6, Color.CYAN);
        sAdjacentMineCountToColorMap.put(7, Color.YELLOW);
        sAdjacentMineCountToColorMap.put(8, Color.RED);
    }

    public TileView(Context context, int xGridCoordinate, int yGridCoordinate) throws InvalidArgumentException {
        super(context);

        mXGridCoordinate = xGridCoordinate;
        mYGridCoordinate = yGridCoordinate;

        init();
    }

    private void init() throws InvalidArgumentException {
        mGameBus = GameBus.getGameBus();

        setupDrawableBackgrounds();
        setupListeners();
    }

    private void setupListeners() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the game that the user has performed an action on this tile.
                mGameBus.post(new Game.TileViewActionEvent(TileView.this, CLICK));
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Notify the game that the user has performed an action on this tile.
                mGameBus.post(new Game.TileViewActionEvent(TileView.this, LONG_CLICK));

                // Return true to consume event.
                return true;
            }
        });
    }

    private void setupDrawableBackgrounds() throws InvalidArgumentException {
        Drawable coveredTile = setupCoveredTile();
        LayerDrawable flaggedMineDrawable = new LayerDrawable(new Drawable[]{coveredTile, getResources().getDrawable(R.drawable.monaicon)});

        mDrawableContainer = new LevelListDrawable();
        mDrawableContainer.addLevel(0, COVERED, coveredTile);
        mDrawableContainer.addLevel(0, FLAGGED_AS_MINE, flaggedMineDrawable);

        setBackground(mDrawableContainer);
    }

    private Drawable setupCoveredTile() throws InvalidArgumentException {
        //Move this to a theme
        int colorInner = GraphicsUtils.getColor(getContext(), R.color.blue_grey_200);
        int colorTop = GraphicsUtils.getColor(getContext(), R.color.blue_grey_300);
        int colorLeft = GraphicsUtils.getColor(getContext(), R.color.blue_grey_400);
        int colorBottom = GraphicsUtils.getColor(getContext(), R.color.blue_grey_500);
        int colorRight = GraphicsUtils.getColor(getContext(), R.color.blue_grey_600);

        int[] tileColors = new int[]{colorInner, colorLeft, colorTop, colorRight, colorBottom};

        return new BeveledTileDrawable(tileColors, null);
    }

    public void setupUncoveredTileDrawable(BoardSquare boardSquare) {
        Drawable uncoveredDrawable;

        if(boardSquare != null && boardSquare.doesContainMine()) {
            uncoveredDrawable = getResources().getDrawable(R.drawable.schrei);
        }
        else {
            String adjacentMineCountText;
            int textColor = 0;

            if(boardSquare == null) {
                adjacentMineCountText = "";
            }
            else {
                int adjacentMinesCount = boardSquare.getAdjacentMinesCount();

                textColor = sAdjacentMineCountToColorMap.get(adjacentMinesCount);
                adjacentMineCountText = String.valueOf(adjacentMinesCount);
            }
            uncoveredDrawable = new TextDrawable(adjacentMineCountText, textColor);
        }
        mDrawableContainer.addLevel(0, UNCOVERED, uncoveredDrawable);
    }

    public int getXGridCoordinate() {
        return mXGridCoordinate;
    }

    public int getYGridCoordinate() {
        return mYGridCoordinate;
    }

    public int getState() {
        return mDrawableContainer.getLevel();
    }

    public void setState(int state) {
        mDrawableContainer.setLevel(state);
    }
}
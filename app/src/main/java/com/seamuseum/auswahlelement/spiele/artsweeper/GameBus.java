package com.seamuseum.auswahlelement.spiele.artsweeper;

import com.squareup.otto.Bus;

public class GameBus {
    private static Bus gameBus;

    public static Bus getGameBus() {
        if(gameBus == null) {
            gameBus = new Bus();
        }

        return gameBus;
    }
}

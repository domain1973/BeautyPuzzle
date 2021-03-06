package com.ads.puzzle.beauty;

import com.ads.puzzle.beauty.screen.MainScreen;
import com.badlogic.gdx.Game;

public class Puzzle extends Game {
    private MainScreen mainScreen;
    private PEvent pEvent;

    public Puzzle() {
    }

    public Puzzle(PEvent pe) {
        pEvent = pe;
    }

    @Override
    public void create() {
        Assets.load();
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public PEvent getPEvent() {
        return pEvent;
    }
}

package com.ads.puzzle.beauty.controller;

import com.ads.puzzle.beauty.Assets;
import com.ads.puzzle.beauty.actors.Piece;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by Administrator on 2014/7/4.
 */
public class PieceController extends IController {
    private Rectangle fixAreaBounds;

    public PieceController(String name) {
        setName(name);
        for (int i = 0; i < 4; i++) {
            Piece piece = new Piece(i);
            addActor(piece);
            if (i == 2) {
                fixAreaBounds = new Rectangle(piece.getX(), piece.getY(), Assets.SMALL_PIECE_SIZE * 2, Assets.SMALL_PIECE_SIZE * 2);
            }
        }
    }

    @Override
    public void handler() {
        SnapshotArray<Actor> actors = getChildren();
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            Piece piece = (Piece) actor;
            piece.return2BeginArea();
        }
    }

    public Rectangle getFixAreaBounds() {
        return fixAreaBounds;
    }
}

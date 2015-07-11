package com.ads.puzzle.beauty.screen;

import com.ads.puzzle.beauty.Assets;
import com.ads.puzzle.beauty.Puzzle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Administrator on 2014/7/21.
 */
public class BaseScreen extends ScreenAdapter {
    private boolean show;
    private boolean backFlag;
    private float y_bar;
    private Stage stage;
    private Batch batch;
    private Puzzle puzzle;
    private Image layerBg;
    protected ImageButton returnBtn;

    public BaseScreen(Puzzle game) {
        stage = new Stage();
        puzzle = game;
        y_bar = Assets.HEIGHT - Assets.TOPBAR_HEIGHT;
        batch = stage.getBatch();
        float scale = Assets.HEIGHT / 854;//default
        Assets.gameFont.setScale(scale);
        Assets.otherFont.setScale(scale);
    }

    @Override
    public void show() {
        Image bg = new Image(Assets.gameBg);
        bg.setBounds(0, 0, Assets.WIDTH, Assets.HEIGHT);
        addActor(bg);
        createBackground();
        Gdx.input.setInputProcessor(stage); // 设置输入接收器
    }

    protected void createBtns() {
        returnBtn = new ImageButton(new TextureRegionDrawable(Assets.returnTr));
        returnBtn.setBounds(0, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        addActor(returnBtn);
    }

    private void createBackground() {
        layerBg = new Image(Assets.layerBg);
        layerBg.setBounds(0, 0, Assets.WIDTH, Assets.HEIGHT - Assets.TOPBAR_HEIGHT);
        addActor(layerBg);
    }

    protected void removeLayerBg() {
        layerBg.remove();
    }

    protected void addActor(Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public BitmapFont getGameFont() {
        return Assets.gameFont;
    }

    public BitmapFont getOtherFont() {
        return Assets.otherFont;
    }

    public Stage getStage() {
        return stage;
    }

    public Batch getBatch() {
        return batch;
    }

    public float getY_bar() {
        return y_bar;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isBackFlag() {
        return backFlag;
    }

    public void setBackFlag(boolean backFlag) {
        this.backFlag = backFlag;
    }
}

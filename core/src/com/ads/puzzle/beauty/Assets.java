/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.ads.puzzle.beauty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assets {
    public static AssetManager assetManager;

    public static Music musicbg;
    public static Sound btnSound;
    public static Sound starSound;
    public static TextureRegion gameBg;
    public static TextureRegion theme;
    public static TextureRegion winBg;
    public static TextureRegion layerBg;
    public static TextureRegion areaBg;
    public static TextureRegion dlgBg;
    public static TextureRegion resultBg;
    public static TextureRegion star;
    public static TextureRegion star_null;
    public static TextureRegion readme;
    public static TextureRegion help;
    public static TextureRegion share;
    public static TextureRegion barShare;
    public static TextureRegion recommend;
    public static TextureRegion refresh;
    public static TextureRegion returnTr;
    public static TextureRegion light;
    public static TextureRegion gate;
    public static TextureRegion next;
    public static TextureRegion suspend;
    public static TextureRegion continueTr;
    public static TextureRegion music;
    public static TextureRegion sound;
    public static TextureRegion about;
    public static TextureRegion forbid;
    public static TextureRegion playBtn;
    public static TextureRegion resetGameBtn;
    public static TextureRegion levelPreBtn;
    public static TextureRegion levelNextBtn;
    public static TextureRegion gate_0star;
    public static TextureRegion gate_1star;
    public static TextureRegion gate_2star;
    public static TextureRegion gate_3star;
    public static TextureRegion gate_lock;
    public static TextureRegion lock;
    public static TextureRegion[] cubes;
    public static float TOPBAR_HEIGHT;//顶部按钮条的高度
    public static float WIDTH;
    public static float HEIGHT;
    public static float V_SPACE;
    public static float H_SPACE;
    public static float PIECE_SIZE;
    public static float SPRITE_SIZE;
    public static float SMALL_PIECE_SIZE;
    public static float LEVEL_IMAGE_SIZE;
    public static float LEVEL_IMAGE_OFF_SIZE;
    public static int LEVEL_MAX = 6;
    public static int LEVEL_ADS = LEVEL_MAX + 1;
    public static float SPRITESIZE;///关卡精灵图标大小

    public static List<Sprite> levels;
    public static Map<Integer, List<Sprite>> levelSpriteMap;//关卡精灵图标
    public static Sprite[] pieces;
    public static BitmapFont gameFont;
    public static BitmapFont otherFont;

    public static List<Series> seriesList;

    public static void load() {
        assetManager = new AssetManager();
        assetManager.load("p.atlas", TextureAtlas.class);
        assetManager.load("puzzle.fnt", BitmapFont.class);
        assetManager.load("game.fnt", BitmapFont.class);
        assetManager.load("data/musicbg.mp3", Music.class); //加载背景音乐
        assetManager.load("data/btn.wav", Sound.class);
        assetManager.load("data/star.mp3", Sound.class);
        assetManager.finishLoading();
        gameFont = assetManager.get("puzzle.fnt", BitmapFont.class);
        otherFont = assetManager.get("game.fnt", BitmapFont.class);
        btnSound = assetManager.get("data/btn.wav", Sound.class);
        starSound = assetManager.get("data/star.mp3", Sound.class);
        musicbg = assetManager.get("data/musicbg.mp3", Music.class);    //加载背景音乐
        initConstants();
        TextureAtlas atlas = assetManager.get("p.atlas", TextureAtlas.class);
        loadBmps(atlas);
        createLevels(atlas);
        createLevelSprite(atlas);
        creteMagicCubes(atlas);
        loadMusic();
        loadAd();
    }

    private static void initConstants() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        TOPBAR_HEIGHT = HEIGHT / 16;
        PIECE_SIZE = 29 * HEIGHT / 108;
        SMALL_PIECE_SIZE = PIECE_SIZE / 2;
        SPRITE_SIZE = PIECE_SIZE / 3;
        LEVEL_IMAGE_SIZE = WIDTH;
        LEVEL_IMAGE_OFF_SIZE = WIDTH / 7;
        V_SPACE = HEIGHT / 72;
        H_SPACE = (Assets.WIDTH - 2 * Assets.PIECE_SIZE) / 3;
    }

    private static void loadBmps(TextureAtlas atlas) {
        gameBg = atlas.findRegion("gamebg");
        theme = atlas.findRegion("theme");
        layerBg = atlas.findRegion("layerbg");//图层背景,对话框使用
        winBg = atlas.findRegion("winbg");
        dlgBg = atlas.findRegion("dlgbg");
        areaBg = atlas.findRegion("area");
        resultBg = atlas.findRegion("resultbg");
        readme = atlas.findRegion("readme");

        suspend = atlas.findRegion("suspend");
        share = atlas.findRegion("share");
        barShare = atlas.findRegion("barshare");
        recommend = atlas.findRegion("recommend");
        light = atlas.findRegion("light");
        next = atlas.findRegion("next");
        returnTr = atlas.findRegion("return");
        help = atlas.findRegion("help");
        refresh = atlas.findRegion("refresh");
        gate = atlas.findRegion("gate");
        music = atlas.findRegion("muisc");
        sound = atlas.findRegion("sound");
        continueTr = atlas.findRegion("continue");
        about = atlas.findRegion("about");
        star = atlas.findRegion("star");
        star_null = atlas.findRegion("star_null");
        forbid = atlas.findRegion("forbid");
        lock = atlas.findRegion("lock");
        playBtn = atlas.findRegion("playbtn");
        resetGameBtn = atlas.findRegion("resetgamebtn");
        levelPreBtn = atlas.findRegion("levelpre");
        levelNextBtn = atlas.findRegion("levelnext");

        gate_0star = atlas.findRegion("gate_0star");
        gate_1star = atlas.findRegion("gate_1star");
        gate_2star = atlas.findRegion("gate_2star");
        gate_3star = atlas.findRegion("gate_3star");
        gate_lock = atlas.findRegion("gate_lock");
    }

    private static void creteMagicCubes(TextureAtlas atlas) {
        cubes = new TextureRegion[4];
        pieces = new Sprite[4];
        for (int i = 0; i < 4; i++) {
            String name = "piece" + i;
            pieces[i] = atlas.createSprite(name);
            cubes[i] = atlas.findRegion(name);
        }
    }

    private static void createLevelSprite(TextureAtlas atlas) {
        SPRITESIZE = Assets.PIECE_SIZE / 3;
        levelSpriteMap = new HashMap<Integer, List<Sprite>>();
        for (int i = 0; i < LEVEL_MAX; i++) {
            List<Sprite> sprites = new ArrayList<Sprite>();
            for (int m = 0; m < 5; m++) {
                String spriteName = String.valueOf((i + 1) * 100 + m);
                Sprite s1 = atlas.createSprite(spriteName);
                sprites.add(s1);
            }
            levelSpriteMap.put(i, sprites);
        }
    }

    private static void createLevels(TextureAtlas atlas) {
        levels = new ArrayList<Sprite>();
        for (int i = 0; i < LEVEL_ADS; i++) {
            Sprite s = atlas.createSprite("s" + i);
            if (s == null) {
                break;
            }
            levels.add(s);
        }
    }

    private static void loadAd() {
        try {
            seriesList = new ArrayList<Series>();
            //动态获取系列、说明、图标
            FileHandle packFile = Gdx.files.external("ads/ad.atlas");
            TextureAtlas atlas = new TextureAtlas(packFile);
            List<Sprite> spriteNames = new ArrayList<Sprite>();
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Sprite s = atlas.createSprite("series" + i);
                if (s == null) {
                    break;
                }
                spriteNames.add(s);
            }
            FileHandle filehandle = Gdx.files.external("ads/url.txt");
            String[] urls = filehandle.readString("UTF-8").split("[#]");
            for (int i = 0; i < spriteNames.size(); i++) {
                String[] url = getUrl(urls, "series" + i).split("[|]");
                Series series = new Series().setImage(new Image(spriteNames.get(i)))
                        .setName(url[0])
                        .setDetail(url[1])
                        .setUrl(url[2]);
                seriesList.add(series);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getUrl(String[] urls, String name) {
        for (String url : urls) {
            String trim = url.trim();
            if (trim.split("[=]")[0].contains(name)) {
                return trim.split("=")[1];
            }
        }
        return null;
    }

    public static Series getGameInfo(String name) {
        for (Series series :  seriesList) {
            if (series.getName().equals(name)) {
                return series;
            }
        }
        return null;
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }

    private static void loadMusic() {
        musicbg.setLooping(true);  //设置背景音乐循环播放
        musicbg.setVolume(0.5f);   //设置音量
        if (Settings.musicEnabled) {
            musicbg.play();        //播放背景音乐
        }
    }
}

package com.ads.puzzle.beauty.screen;

import com.ads.puzzle.beauty.Answer;
import com.ads.puzzle.beauty.Assets;
import com.ads.puzzle.beauty.Settings;
import com.ads.puzzle.beauty.actors.Area;
import com.ads.puzzle.beauty.actors.Piece;
import com.ads.puzzle.beauty.controller.AreaController;
import com.ads.puzzle.beauty.controller.ChallengeController;
import com.ads.puzzle.beauty.controller.IController;
import com.ads.puzzle.beauty.controller.PieceController;
import com.ads.puzzle.beauty.listener.PieceDetector;
import com.ads.puzzle.beauty.listener.PieceListener;
import com.ads.puzzle.beauty.window.ResultWin;
import com.ads.puzzle.beauty.window.SupsendWin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/6/24.
 */
public class GameScreen extends BaseScreen {
    private ScheduledExecutorService executorGateEnd;
    private ScheduledExecutorService executStarCount;
    private ScheduledExecutorService executTime;

    private ParticleEffect effect;
    private GateScreen gateScreen;
    private AreaController areaCtrl;
    private PieceController pieceCtrl;
    private ChallengeController challengeCtrl;
    private PieceDetector gestureDetector;
    private SupsendWin supsendWin;
    private InputMultiplexer multiplexer;
    private Actor[] pieces;
    private String timeStr;
    private Label labTime;
    private Label labCount;

    private boolean openResultWin;
    private boolean isPass;
    private boolean isUsedHelp;
    private boolean isUsingHelp;
    private int level;
    private int gateNum;
    private int areaId;
    private int seconds;
    private int starNum;
    private Label.LabelStyle labTimeYellow;
    private Label.LabelStyle labTimeRed;

    public GameScreen(GateScreen gs) {
        super(gs.getPuzzle());
        gateNum = -1;
        gateScreen = gs;
        executorGateEnd = Executors.newSingleThreadScheduledExecutor();
        isUsingHelp = true;
        BitmapFont font = getOtherFont();
        font.setScale(Assets.WIDTH / 480);
        labTimeYellow = new Label.LabelStyle(font, Color.YELLOW);
        labTimeRed = new Label.LabelStyle(font, Color.RED);
    }

    @Override
    public void show() {
        if (!isShow()) {
            super.show();
            timeStr = "00:00";
            areaCtrl = new AreaController(level, IController.AREA_CTRL);
            addActor(areaCtrl);
            pieceCtrl = new PieceController(IController.PIECE_CTRL);
            addActor(pieceCtrl); // 添加块组到舞台
            pieces = pieceCtrl.getChildren().begin();
            challengeCtrl = new ChallengeController(level, gateNum, IController.CHALLENGE_CTRL);
            addActor(challengeCtrl);
            createTopBar();
            addLabels();
            initEffect();
            createTimer();
            removeLayerBg();
            setShow(true);
        }
        multiplexer = new InputMultiplexer(); // 多输入接收器
        gestureDetector = new PieceDetector(getStage(), new PieceListener(getStage(), GameScreen.this));
        multiplexer.addProcessor(gestureDetector); // 添加手势识别
        multiplexer.addProcessor(getStage()); // 添加舞台
        Gdx.input.setInputProcessor(multiplexer); // 设置多输入接收器为接收器
        resumeTimer();
    }

    private void buildNewGate(int num) {
        int lv = num / Answer.GATE_MAX;
        if (lv < Assets.LEVEL_MAX) {
            if (gateNum != -1 && num != gateNum) {
                refreshGame();
                if (challengeCtrl != null) {
                    challengeCtrl.buildChallenge(lv, num);
                }
                if (areaCtrl != null) {
                    areaCtrl.buildArea(lv);
                }
            }
        }
        gateNum = num;
        level = lv;
    }

    private void createTopBar() {
        super.createBtns();
        final ImageButton sos = new ImageButton(new TextureRegionDrawable(Assets.light));
        sos.setBounds(Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        sos.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, sos.getWidth(), sos.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    if (Settings.helpNum > 0) {
                        getPuzzle().getPEvent().sos(GameScreen.this);
                    } else {
                        getPuzzle().getPEvent().invalidateSos();
                    }
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(sos);

        final ImageButton share = new ImageButton(new TextureRegionDrawable(Assets.barShare));
        share.setBounds(Assets.WIDTH - 2 * Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        share.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, share.getWidth(), share.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    getPuzzle().getPEvent().share();
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(share);

        final ImageButton suspend = new ImageButton(new TextureRegionDrawable(Assets.suspend));
        suspend.setBounds(Assets.WIDTH - Assets.TOPBAR_HEIGHT, getY_bar(), Assets.TOPBAR_HEIGHT, Assets.TOPBAR_HEIGHT);
        suspend.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, suspend.getWidth(), suspend.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    suspendTimer();
                    supsendWin = new SupsendWin(GameScreen.this, level);
                    addActor(supsendWin);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
        addActor(suspend);

        returnBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle bound = new Rectangle(0, 0, returnBtn.getWidth(), returnBtn.getHeight());
                if (bound.contains(x, y)) {
                    Assets.playSound(Assets.btnSound);
                    suspendTimer();
                    gateScreen.buildGateImage(level);
                    getPuzzle().setScreen(gateScreen);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    private void addLabels() {
        BitmapFont font = getOtherFont();
        BitmapFont.TextBounds bounds = font.getBounds("00");
        labTime = new Label("", labTimeYellow);
        labTime.setPosition(Assets.TOPBAR_HEIGHT, Assets.HEIGHT - Assets.TOPBAR_HEIGHT / 2);
        addActor(labTime);
        float w = bounds.width;
        labCount = new Label("", labTimeRed);
        labCount.setPosition(Assets.WIDTH - 3 * Assets.TOPBAR_HEIGHT - w / 3, Assets.HEIGHT - Assets.TOPBAR_HEIGHT / 2);
        addActor(labCount);

        String s = "挑战";
        Label c = new Label(s, new Label.LabelStyle(getGameFont(), Color.YELLOW));
        float size = Assets.SMALL_PIECE_SIZE;
        float space = size / 20;
        float y_off = Assets.HEIGHT - (Assets.TOPBAR_HEIGHT + Assets.PIECE_SIZE * 2 + Assets.V_SPACE * 2);
        float y = y_off - 2 * size - space;
        c.setPosition(Assets.SPRITESIZE * 3 / 2, y);
        addActor(c);
    }

    private void initEffect() {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("data/test.p"), Gdx.files.internal("data/"));
    }

    private void changeStar() {
        Assets.playSound(Assets.starSound);
        executStarCount = Executors.newSingleThreadScheduledExecutor();
        executStarCount.scheduleAtFixedRate(new Runnable() {
            public void run() {
                areaId++;
                if (areaId < 3) {
                    Assets.playSound(Assets.starSound);
                }
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (!Settings.isSuspend && isClosedResultWin()) {
                gateScreen.setBackFlag(true);
                gateScreen.buildGateImage(level);
                getPuzzle().setScreen(gateScreen);
                return;
            }
        }
        super.render(delta);
        handleHelp();
        handlePass();
        labTime.setText(timeStr);
        labCount.setText(Settings.helpNum + "");
    }

    private void handleHelp() {
        if (isUsedHelp) {
            if (isUsingHelp) {
                changeStar();
                reset();
                isUsingHelp = false;
            }
            if (areaId < 3) {
                int temp = areaId;//防止定时器修改值不同步
                Area area = (Area) areaCtrl.getChildren().get(areaId);
                getBatch().begin();
                effect.setPosition(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
                effect.draw(getBatch(), Gdx.graphics.getDeltaTime());
                getBatch().end();
                String[] answers = Answer.VALUES[gateNum].split(",");
                String answer = answers[temp];
                int pieceId = answer.charAt(0) - 48;//Aciis转成int
                Piece piece = (Piece) pieces[pieceId];
                piece.setBounds(area.getX(), area.getY(), Assets.PIECE_SIZE, Assets.PIECE_SIZE);
                piece.setOrientation(answer.charAt(1) - 48);
                piece.setArea(temp);
                area.setPieceId(pieceId);
            } else {
                isUsedHelp = false;
                resumeTimer();
                areaId = 0;
                executStarCount.shutdown();
                multiplexer.addProcessor(gestureDetector); // 添加手势识别
                multiplexer.addProcessor(getStage());
            }
        }
    }

    private void reset() {
        areaId = 0;
        areaCtrl.handler();
        pieceCtrl.handler();
    }

    private void handlePass() {
        if (!isPass) {
            Gdx.input.setInputProcessor(multiplexer);
            handleGate();
        }
        if (openResultWin) {
            computerStarNum();
            addActor(new ResultWin(this, starNum));
            openResultWin = false;
            if (starNum == 0) {
                getPuzzle().getPEvent().spotAd();
            }
        }
    }

    private boolean isClosedResultWin() {
        Array<Actor> actors = getStage().getActors();
        for (Actor actor : actors) {
            if (actor instanceof ResultWin) {
                return false;
            }
        }
        return true;
    }

    private void computerStarNum() {
        int[] timeLevel = Answer.timeLevels.get(level);
        if (timeLevel[0] < seconds && timeLevel[1] >= seconds) {
            starNum = 1;
        } else if (timeLevel[1] < seconds && timeLevel[2] >= seconds) {
            starNum = 2;
        } else if (timeLevel[2] < seconds && timeLevel[3] >= seconds) {
            starNum = 3;
        } else {
            starNum = 0;
        }
        if (Answer.gateStars.size() > challengeCtrl.getGateNum()) {//可能重玩
            if (starNum >  0) {
                Answer.gateStars.set(challengeCtrl.getGateNum(), starNum);
            }
        } else {
            Answer.gateStars.add(starNum);
        }
    }

    private void handleGate() {
        if (seconds < 0) {//game over
            Gdx.input.setInputProcessor(null);
            executTime.shutdown();
            openResultWin = true; //关卡结束
            isPass = true;
        }  else if (gestureDetector.isPass(challengeCtrl.getGateNum())) {
            Gdx.input.setInputProcessor(null);
            executTime.shutdown();
            Runnable runner = new Runnable() {
                public void run() {
                    openResultWin = true; //关卡结束
                }
            };
            executorGateEnd.schedule(runner, 1500, TimeUnit.MILLISECONDS);
            isPass = true;
        }
    }

    private void suspendTimer() {
        Settings.isSuspend = true;
    }

    public void resumeTimer() {
        Settings.isSuspend = false;
    }

    private void createTimer() {
        Settings.isSuspend = false;
        seconds = Answer.timeLevels.get(level)[3];
        labTime.setStyle(labTimeYellow);
        executTime = Executors.newSingleThreadScheduledExecutor();
        executTime.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!Settings.isSuspend) {
                    seconds--; //
                    if (seconds < 15) {
                        if (seconds % 2 == 0) {
                            labTime.setStyle(labTimeRed);
                        } else {
                            labTime.setStyle(labTimeYellow);
                        }
                    }
                    buildTimeStr();
                }
            }

            private void buildTimeStr() {
                String str0 = "%d";
                String str1 = "%d";
                int minute = seconds / 60;
                int second = seconds % 60;
                if (seconds < 0) {
                    second = 0;
                }
                if (minute < 10) {
                    str0 = "0%d";
                }
                if (second < 10) {
                    str1 = "0%d";
                }
                timeStr = String.format("倒计时" + str0 + ":" + str1, minute, second);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void pause() {
    }

    public void return2init() {
        areaCtrl.handler();
        isPass = false;
        if (!executTime.isShutdown()) {
            executTime.shutdown();
        }
        createTimer();
    }

    public void refreshGame() {
        return2init();
        pieceCtrl.handler();
    }

    public void useSos() {
        multiplexer.removeProcessor(gestureDetector);
        multiplexer.removeProcessor(getStage());
        isUsingHelp = true;
        isUsedHelp = true;
        pieceCtrl.handler();
    }

    public void setGateNum(int num) {
        buildNewGate(num);
        gateNum = num;
    }

    public GateScreen getGateScreen() {
        return gateScreen;
    }
}

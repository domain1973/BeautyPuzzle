package com.ads.puzzle.beauty.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ads.puzzle.beauty.Answer;
import com.ads.puzzle.beauty.Puzzle;
import com.ads.puzzle.beauty.Settings;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.otomod.ad.AdView;
import com.otomod.ad.listener.O2OAdListener;

public class AndroidLauncher extends AndroidApplication {
    private static String APP_KEY = "b627622cc59711e4aca4f8bc123d7e98";
    private PEventImpl pEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        pEvent = new PEventImpl(AndroidLauncher.this);
        initialize(new Puzzle(pEvent), config);
        loadGameConfig();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void spot() {
        AdView adView = AdView.createPopup(this, APP_KEY);
        adView.setAdListener(new O2OAdListenerImpl());
        adView.request();
    }

    private void loadGameConfig() {
        SharedPreferences sharedata = getSharedPreferences("data", Context.MODE_PRIVATE);
        Settings.musicEnabled = sharedata.getBoolean("music", true);
        Settings.soundEnabled = sharedata.getBoolean("sound", true);
        Settings.unlockGateNum = sharedata.getInt("passNum", 0);
        Settings.helpNum = sharedata.getInt("helpNum", 1);
        Answer.gateStars.clear();
        String[] split = sharedata.getString("starNum", "0").split("[,]");
        for (String starNum : split) {
            if (!"".equals(starNum)) {
                Answer.gateStars.add(Integer.parseInt(starNum));
            }
        }
        Settings.adManager = sharedata.getBoolean("adManager", true);
    }

    public class O2OAdListenerImpl implements O2OAdListener {

        @Override
        public void onClick() {
        }

        @Override
        public void onClose() {
        }

        @Override
        public void onAdFailed() {
            System.out.print("");
        }

        @Override
        public void onAdSuccess() {
            System.out.print("");
        }
    }
}

package com.ads.puzzle.beauty.android;

import java.io.File;

/**
 * Created by Administrator on 2014/11/10.
 */
public class Constant {
    public final static String path = "/mnt/sdcard/ads/";
    public final static String SHARE_TITLE = "四美与男神";//TODO 替换
    public final static String host = "bcs.duapp.com";
    public final static String accessKey = "NESAXkQp7S1SIeqUncUnTCIl";
    public final static String secretKey = "ZIQ2NE02RNWimzjyGI0Yh8NF4cAjouLf";
    public final static String bucket = "ads-puzzle";//TODO 替换

    private final static String key = "Beauty";//TODO 替换
    public final static String yybStr = "/" + key + "/DownloadPage.txt";//下载地址
    public final static String adAtlasStr = "/ad.atlas";//系列图片信息
    public final static String urlStr = "/url.txt";//所有游戏下载地址
    public final static String adsStr = "/" + key + "/MoreEnable.txt";//爱迪开关
    public final static String adPngStr = "/ad.png";//所有游戏图片
    public final static File adAtlas = new File(path + "ad.atlas");//系列图片信息

    public final static File yyb = new File(path + "BeautyPuzzle.txt");//TODO 替换
    public final static File url = new File(path + "url.txt");//所有游戏下载地址
    public final static File ads = new File(path + "adsenable.txt"); //爱迪精品开关
    public final static File adPng = new File(path + "ad.png");

    public final static String adsUrl = "http://ads360.duapp.com/House";//官网
    public final static String gameUrl = adsUrl + "/BeautyPuzzle";//下载地址 TODO 替换

    public final static String SHARE_TEXT = "四美和男神跟您一起来躲猫猫啦,很休闲的很娱乐,您懂的!";
    public final static String title = "您好,我是小智";
}

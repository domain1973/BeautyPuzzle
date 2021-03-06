/*
 * 官网地站:http://www.ShareSDK.cn
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 ShareSDK.cn. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import com.ads.puzzle.beauty.Settings;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * OneKeyShareCallback是快捷分享功能的一个“外部回调”示例。通过
 *{@link cn.sharesdk.onekeyshare.OnekeyShare#setCallback(cn.sharesdk.framework.PlatformActionListener)}将
 *本类的示例传递进快捷分享，分享操作结束后，快捷分享会将分享结果
 *回调到本类中来做自定义处理。
 */
public class OneKeyShareCallback implements PlatformActionListener {

	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
        if (!plat.getName().equals("WechatFavorite")) {
            Settings.helpNum = Settings.helpNum + 1;
        }
    }

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();
		// 在这里添加分享失败的处理代码
	}

	public void onCancel(Platform plat, int action) {
		// 在这里添加取消分享的处理代码
	}

}

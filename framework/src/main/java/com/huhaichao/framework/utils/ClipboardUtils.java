package com.huhaichao.framework.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.huhaichao.framework.base.BaseApplication;

/**
 * Created by HuHaiChao on 2018/8/7.
 */

public class ClipboardUtils {
    /**
     * 复制内容到粘贴板
     */
    public static void CopyTextToClipboard(String text) {
        ClipboardManager mClipboardManager = (ClipboardManager) BaseApplication.getActivityReference().get().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText(text, text);
        mClipboardManager.setPrimaryClip(mClipData);
    }

    /**
     * 获取内容
     */
    public static String getTextFromClipboard() {
        ClipboardManager mClipboardManager = (ClipboardManager) BaseApplication.getActivityReference().get().getSystemService(Context.CLIPBOARD_SERVICE);
        if (!mClipboardManager.hasPrimaryClip())
            return "";
        ClipData mClipData = mClipboardManager.getPrimaryClip();
        ClipData.Item item = mClipData.getItemAt(0);
        return item.getText().toString();
    }
}

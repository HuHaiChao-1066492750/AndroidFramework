package com.huhaichao.framework.widget;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

/**
 * 倒计时
 */

public class CustomCountDown extends CountDownTimer {
    private Button countDown = null;

    public CustomCountDown(long millisInFuture, long countDownInterval, Button countDown) {
        super(millisInFuture, countDownInterval);
        this.countDown = countDown;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (countDown != null) {
            countDown.setClickable(false);//设置不能点击
            countDown.setText(millisUntilFinished / 1000 + " 秒后重发");//设置倒计时时间
            Spannable span = new SpannableString(countDown.getText().toString());//获取按钮的文字
            span.setSpan(new ForegroundColorSpan(Color.RED), 0,2 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
            countDown.setAllCaps(false);
            countDown.setText(span);
        }
    }

    @Override
    public void onFinish() {
        if (countDown != null) {
            countDown.setText("重新获取");
            countDown.setClickable(true);//重新获得点击
        }
    }
}

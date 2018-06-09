package com.huhaichao.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huhaichao.framework.R;


public class CustomDialog extends Dialog {

    private Context context;
    private TextView txtTitle;
    private TextView txtMessage;
    private TextView btnPostive;
    private TextView btnNegative;
    private OnClickListener onPostiveClickListener;
    private OnClickListener onNegativeClickListener;
    private String postiveText;
    private String negativeText;
    private String messageText;
    private String titleText;
    private boolean postiveButtonVisiable = false;
    private boolean negativeButtonVisiable = false;
    private boolean dialogCancelable = false;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        setContentView(view);

        txtTitle = (TextView) view.findViewById(R.id.view_custom_dialog_title);
        txtMessage = (TextView)view.findViewById(R.id.view_custom_dialog_message);
        btnPostive =(TextView) view.findViewById(R.id.view_custom_dialog_postive);
        btnNegative = (TextView)view.findViewById(R.id.view_custom_dialog_negative);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        if (postiveButtonVisiable) {
            btnPostive.setVisibility(View.VISIBLE);
        } else {
            btnPostive.setVisibility(View.GONE);
        }
        if (negativeButtonVisiable) {
            btnNegative.setVisibility(View.VISIBLE);
        } else {
            btnNegative.setVisibility(View.GONE);
        }
        txtTitle.setText(titleText);
        txtMessage.setText(messageText);
        btnPostive.setText(postiveText);
        btnNegative.setText(negativeText);
        btnPostive.setOnClickListener(new OnButtonClickListener());
        btnNegative.setOnClickListener(new OnButtonClickListener());
        setCancelable(dialogCancelable);
    }

    public OnClickListener getOnPostiveClickListener() {
        return onPostiveClickListener;
    }

    public void setOnPostiveClickListener(
            OnClickListener onPostiveClickListener) {
        this.onPostiveClickListener = onPostiveClickListener;
    }

    public OnClickListener getOnNegativeClickListener() {
        return onNegativeClickListener;
    }

    public void setOnNegativeClickListener(
            OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    private class OnButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
            if (R.id.view_custom_dialog_postive == v.getId()) {
                if (onPostiveClickListener != null) {
                    onPostiveClickListener.onClick(CustomDialog.this,
                            DialogInterface.BUTTON_POSITIVE);
                }

            } else if (R.id.view_custom_dialog_negative == v.getId()) {
                if (onNegativeClickListener != null) {
                    onNegativeClickListener.onClick(CustomDialog.this,
                            DialogInterface.BUTTON_NEGATIVE);
                }
            } else {
            }
        }

    }

    public static class Builder {
        private Context context = null;
        CustomDialog customDialog = null;

        public Builder(Context context) {
            customDialog = new CustomDialog(context);
            this.context = context;
        }

        public Builder setTitle(String titleText) {
            customDialog.titleText = titleText;
            return this;
        }

        public Builder setTitle(int titleText) {
            customDialog.titleText = context.getString(titleText);
            return this;
        }

        public Builder setMessage(String messageText) {
            customDialog.messageText = messageText;
            return this;
        }

        public Builder setMessage(int messageText) {
            customDialog.messageText = context.getString(messageText);
            return this;
        }

        public Builder setButton(int which, String text,
                                 OnClickListener onClickListener) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    customDialog.postiveButtonVisiable = true;
                    customDialog.postiveText = text;
                    customDialog.setOnPostiveClickListener(onClickListener);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    customDialog.negativeButtonVisiable = true;
                    customDialog.negativeText = text;
                    customDialog.setOnNegativeClickListener(onClickListener);
                    break;
                default:
                    break;
            }
            return this;
        }

        //设置该对话框能否被Cancel掉，默认可以
        public Builder setCancelable(boolean dialogCancelable) {
            customDialog.dialogCancelable = dialogCancelable;
            return this;
        }

        public CustomDialog create() {
            return customDialog;
        }
    }
}
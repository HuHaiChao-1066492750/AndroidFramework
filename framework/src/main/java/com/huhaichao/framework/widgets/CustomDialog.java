package com.huhaichao.framework.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.huhaichao.framework.R;
import com.huhaichao.framework.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialog extends Dialog {
    @BindView(R2.id.custom_dialog_title)
    protected TextView custom_dialog_title;
    @BindView(R2.id.custom_dialog_message)
    protected TextView custom_dialog_message;
    @BindView(R2.id.custom_dialog_negative)
    protected Button custom_dialog_negative;
    @BindView(R2.id.custom_dialog_positive)
    protected Button custom_dialog_positive;
    @BindView(R2.id.custom_dialog_view)
    protected View custom_dialog_view;
    private Context mContext = null;
    private OnClickListener onPositiveClickListener = null;
    private OnClickListener onNegativeClickListener = null;
    private String mPositiveText = null;
    private String mNegativeText = null;
    private String mMessageText = null;
    private String mTitleText = null;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private int gravity = Gravity.LEFT;

    protected CustomDialog(Context mContext) {
        super(mContext, R.style.CustomDialog);
        this.mContext = mContext;
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        if (mPositiveText != null) {
            custom_dialog_positive.setVisibility(View.VISIBLE);
            custom_dialog_positive.setText(mPositiveText);
        } else {
            custom_dialog_view.setVisibility(View.GONE);
            custom_dialog_positive.setVisibility(View.GONE);
        }

        if (mNegativeText != null) {
            custom_dialog_negative.setVisibility(View.VISIBLE);
            custom_dialog_negative.setText(mNegativeText);
        } else {
            custom_dialog_view.setVisibility(View.GONE);
            custom_dialog_negative.setVisibility(View.GONE);
        }

        if (mTitleText != null) {
            custom_dialog_title.setVisibility(View.VISIBLE);
            custom_dialog_title.setText(mTitleText);
        } else {
            custom_dialog_title.setVisibility(View.GONE);
        }
        custom_dialog_message.setText(mMessageText);
        custom_dialog_message.setGravity(gravity);
        custom_dialog_positive.setOnClickListener(new OnButtonClickListener());
        custom_dialog_negative.setOnClickListener(new OnButtonClickListener());
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mCanceledOnTouchOutside);
    }

    public OnClickListener getOnPositiveClickListener() {
        return onPositiveClickListener;
    }

    public void setOnPositiveClickListener(OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    public OnClickListener getOnNegativeClickListener() {
        return onNegativeClickListener;
    }

    public void setOnNegativeClickListener(OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    private class OnButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dismiss();
            if (R.id.custom_dialog_positive == v.getId()) {
                if (onPositiveClickListener != null) {
                    onPositiveClickListener.onClick(CustomDialog.this, DialogInterface.BUTTON_POSITIVE);
                }
            } else if (R.id.custom_dialog_negative == v.getId()) {
                if (onNegativeClickListener != null) {
                    onNegativeClickListener.onClick(CustomDialog.this, DialogInterface.BUTTON_NEGATIVE);
                }
            }
        }
    }

    public static class Builder {
        private Context mContext = null;
        CustomDialog mCustomDialog = null;

        public Builder(Context mContext) {
            mCustomDialog = new CustomDialog(mContext);
            this.mContext = mContext;
        }

        public Builder setTitle(String titleText) {
            mCustomDialog.mTitleText = titleText;
            return this;
        }

        public Builder setTitle(int titleTextId) {
            mCustomDialog.mTitleText = mContext.getString(titleTextId);
            return this;
        }

        public Builder setMessage(String messageText) {
            mCustomDialog.mMessageText = messageText;
            return this;
        }

        public Builder setMessage(String messageText, int gravity) {
            mCustomDialog.mMessageText = messageText;
            mCustomDialog.gravity = gravity;
            return this;
        }

        public Builder setMessage(int messageTextId) {
            mCustomDialog.mMessageText = mContext.getString(messageTextId);
            return this;
        }

        public Builder setMessage(int messageTextId, int gravity) {
            mCustomDialog.mMessageText = mContext.getString(messageTextId);
            mCustomDialog.gravity = gravity;
            return this;
        }


        public Builder setPositiveButton(String text, OnClickListener onClickListener) {
            mCustomDialog.mPositiveText = text;
            mCustomDialog.setOnPositiveClickListener(onClickListener);
            return this;
        }

        public Builder setPositiveButton(int textId, OnClickListener onClickListener) {
            mCustomDialog.mPositiveText = mContext.getString(textId);
            mCustomDialog.setOnPositiveClickListener(onClickListener);
            return this;
        }

        public Builder setNegativeButton(String text, OnClickListener onClickListener) {
            mCustomDialog.mNegativeText = text;
            mCustomDialog.setOnNegativeClickListener(onClickListener);
            return this;
        }

        public Builder setNegativeButton(int textId, OnClickListener onClickListener) {
            mCustomDialog.mNegativeText = mContext.getString(textId);
            mCustomDialog.setOnNegativeClickListener(onClickListener);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCustomDialog.mCancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCustomDialog.mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public CustomDialog create() {
            return mCustomDialog;
        }
    }
}
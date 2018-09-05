package com.huhaichao.framework.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.ScreenUtils;
import com.huhaichao.framework.R;
import com.huhaichao.framework.adapter.PopupMenuAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class CustomPopupMenuDialog extends PopupWindow {
    private Activity mContext;
    private View view;
    private View contentView;
    private PopupMenuAdapter popupMenuAdapter;
    private RecyclerView mRecyclerView;
    private List<Integer> iconList;
    private List<String> titleList;
    private PopupWindow mPopupWindow;
    private boolean isShowLine = false;
    private boolean isShowIcon = false;
    private int width = 0;
    private int height = 0;
    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;
    private int xOff = 0;
    private int yOff = 0;

    public CustomPopupMenuDialog(Activity context) {
        this.mContext = context;
        init();
    }

    public void init() {
        setBackgroundAlpha(0.7f);
        view = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_popup_menu, null);
        popupMenuAdapter = new PopupMenuAdapter(mContext);
        mPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public CustomPopupMenuDialog show() {
        popupMenuAdapter.setItemLayoutParams(width, height);
        popupMenuAdapter.setItemLayoutPadding(paddingTop, paddingBottom, paddingLeft, paddingRight);
        popupMenuAdapter.isShowLine(isShowLine);
        popupMenuAdapter.isShowIcon(isShowIcon);
        popupMenuAdapter.setIconList(iconList);
        popupMenuAdapter.setTitleList(titleList);
        mRecyclerView.setAdapter(popupMenuAdapter);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(R.style.TRM_ANIM_STYLE);
        mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //view和popupwindow左下角对齐
        mPopupWindow.showAsDropDown(contentView, contentView.getWidth() - mPopupWindow.getContentView().getMeasuredWidth() + xOff, yOff);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
        return this;
    }

    @Override
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mContext.getWindow().setAttributes(lp);
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * item点击回调接口
     */
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int itemPosition);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        popupMenuAdapter.setOnMenuItemClickListener(listener);
    }

    public static class Builder {
        CustomPopupMenuDialog customPopupMenuDialog;

        public Builder(Context context) {
            customPopupMenuDialog = new CustomPopupMenuDialog((Activity) context);
        }

        public Builder setIcon(List<Integer> iconList) {
            customPopupMenuDialog.iconList = iconList;
            return this;
        }

        public Builder setTitle(List<String> titleList) {
            customPopupMenuDialog.titleList = titleList;
            return this;
        }

        public Builder isShowLine(boolean isShowLine) {
            customPopupMenuDialog.isShowLine = isShowLine;
            return this;
        }

        public Builder isShowIcon(boolean isShowIcon) {
            customPopupMenuDialog.isShowIcon = isShowIcon;
            return this;
        }

        public Builder setItemLayoutParams(int width, int height) {
            customPopupMenuDialog.width = (int) (width * ScreenUtils.getScreenDensity());
            customPopupMenuDialog.height = (int) (height * ScreenUtils.getScreenDensity());
            return this;
        }

        public Builder setItemLayoutPadding(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
            customPopupMenuDialog.paddingTop = (int) (paddingTop * ScreenUtils.getScreenDensity());
            customPopupMenuDialog.paddingBottom = (int) (paddingBottom * ScreenUtils.getScreenDensity());
            customPopupMenuDialog.paddingLeft = (int) (paddingLeft * ScreenUtils.getScreenDensity());
            customPopupMenuDialog.paddingRight = (int) (paddingRight * ScreenUtils.getScreenDensity());
            return this;
        }

        public Builder setOnMenuItemClickListener(OnMenuItemClickListener listener) {
            customPopupMenuDialog.setOnMenuItemClickListener(listener);
            return this;
        }

        public Builder setContentView(View contentView) {
            customPopupMenuDialog.contentView = contentView;
            return this;
        }

        public Builder setOff(int xOff, int yOff) {
            customPopupMenuDialog.xOff = xOff;
            customPopupMenuDialog.yOff = yOff;
            return this;
        }

        public void dismiss() {
            customPopupMenuDialog.dismiss();
        }

        public CustomPopupMenuDialog create() {
            return customPopupMenuDialog;
        }
    }
}

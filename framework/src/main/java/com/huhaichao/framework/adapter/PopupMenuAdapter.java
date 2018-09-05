package com.huhaichao.framework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huhaichao.framework.R;
import com.huhaichao.framework.widgets.CustomPopupMenuDialog;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class PopupMenuAdapter extends RecyclerView.Adapter<PopupMenuAdapter.ViewHolder> {
    private Context context;
    private List<String> titleList = null;
    private List<Integer> iconList = null;//默认为空，即不需要show
    private boolean isShowLine = true;
    private boolean isShowIcon = true;

    //    private int width = RecyclerView.LayoutParams.WRAP_CONTENT;
//    private int height = RecyclerView.LayoutParams.WRAP_CONTENT;
    private int width;
    private int height;
    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;
    private CustomPopupMenuDialog.OnMenuItemClickListener onMenuItemClickListener;

    public PopupMenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_dialog_popup_menu_item, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(titleList.get(position));

        //是否显示图标(默认为空不显示)
        if (isShowIcon) {
            viewHolder.icon.setImageResource(iconList.get(position));
        } else {
            viewHolder.icon.setVisibility(View.GONE);
        }

        //是否显示分割线(默认不画最后一条)
        if (isShowLine) {
            if (position == titleList.size() - 1) {
                viewHolder.line.setVisibility(View.INVISIBLE);
            }
        } else {
            viewHolder.line.setVisibility(View.GONE);
        }
        //设置item布局LinearLayout 属性
        setItemLayout(viewHolder.custom_popupmenu_ll_item, width, height, paddingLeft, paddingTop, paddingRight, paddingBottom);

        //设置点击
        viewHolder.custom_popupmenu_ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuItemClickListener.onMenuItemClick(position);
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public void setItemLayout(View view, int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        view.requestLayout();
    }

    //设置图标
    public void setIconList(List<Integer> iconList) {
        this.iconList = iconList;
        notifyDataSetChanged();
    }

    public void isShowIcon(boolean isShowIcon) {
        this.isShowIcon = isShowIcon;
        notifyDataSetChanged();
    }

    //设置标题
    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
        notifyDataSetChanged();
    }

    //设置分割线
    public void isShowLine(boolean isShowLine) {
        this.isShowLine = isShowLine;
        notifyDataSetChanged();
    }

    //设置item的宽高
    public void setItemLayoutParams(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //设置item的padding
    public void setItemLayoutPadding(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
    }

    /**
     * 回调接口
     */
    public void setOnMenuItemClickListener(CustomPopupMenuDialog.OnMenuItemClickListener listener) {
        this.onMenuItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout custom_popupmenu_ll_item;
        public TextView title;
        public ImageView icon;
        private View line;

        public ViewHolder(View view) {
            super(view);
            custom_popupmenu_ll_item = (LinearLayout) view.findViewById(R.id.custom_popupmenu_ll_item);
            title = (TextView) view.findViewById(R.id.text);
            icon = (ImageView) view.findViewById(R.id.img);
            line = view.findViewById(R.id.line);
        }
    }
}

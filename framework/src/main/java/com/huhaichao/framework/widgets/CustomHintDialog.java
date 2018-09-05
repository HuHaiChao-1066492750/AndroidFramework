package com.huhaichao.framework.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huhaichao.framework.R;

/**
 * 提示对话框
 */
public class CustomHintDialog extends Dialog {
    /**
     * 不显示任何icon
     */
    public static final int ICON_TYPE_NOTHING = 0;
    /**
     * 显示 Loading 图标
     */
    public static final int ICON_TYPE_LOADING = 1;
    /**
     * 显示成功图标
     */
    public static final int ICON_TYPE_SUCCESS = 2;
    /**
     * 显示失败图标
     */
    public static final int ICON_TYPE_FAIL = 3;
    /**
     * 显示信息图标
     */
    public static final int ICON_TYPE_INFO = 4;
    private int mIconType = ICON_TYPE_NOTHING;
    private Context context;
    private TextView txtMessage;
    private String messageText;
    //    private GifImageView gifView;
    private ProgressBar progressBar;
    private ImageView imageView;

    public CustomHintDialog(Context context) {
        super(context, R.style.CustomTipDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog_hint, null);
        setContentView(view);
//        gifView = view.findViewById(R.id.view_custom_progress_dialog_gif);
        progressBar = (ProgressBar) view.findViewById(R.id.view_custom_progress_dialog_progress);
        imageView = (ImageView) view.findViewById(R.id.view_custom_progress_dialog_img);
        txtMessage = (TextView) view.findViewById(R.id.view_custom_progress_dialog_message);
        txtMessage.setText(messageText);
        switch (mIconType) {
            case ICON_TYPE_LOADING:
                //二〇一七年十二月二十三日 17:10:52 有问题
//                try {
//                    GifDrawable gifDrawable = new GifDrawable(context.getResources(),
//                            R.drawable.custom_dialog_notify_loading_progress);
//                    gifView.setImageDrawable(gifDrawable);
//                    gifView.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    imageView.setVisibility(View.GONE);
//                } catch (NotFoundException | IOException e) {
//                    progressBar.setVisibility(View.VISIBLE);
//                    gifView.setVisibility(View.GONE);
//                }
                //使用Glide
                progressBar.setVisibility(View.GONE);
                Glide.with(context).load(R.mipmap.custom_dialog_notify_loading_progress).into(imageView);
                imageView.setVisibility(View.VISIBLE);
                break;
            case ICON_TYPE_SUCCESS:
//                gifView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageView.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.custom_dialog_notify_done));
                imageView.setVisibility(View.VISIBLE);
                break;
            case ICON_TYPE_FAIL:
//                gifView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageView.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.custom_dialog_notify_error));
                imageView.setVisibility(View.VISIBLE);
                break;
            case ICON_TYPE_INFO:
//                gifView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageView.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.custom_dialog_notify_info));
                imageView.setVisibility(View.VISIBLE);
                break;
            case ICON_TYPE_NOTHING:
//                gifView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                break;
            default:
                break;
        }


    }

    public void setMessage(String msg) {
        messageText = msg;
    }

    public void setIconType(int iconType) {
        mIconType = iconType;
    }
}
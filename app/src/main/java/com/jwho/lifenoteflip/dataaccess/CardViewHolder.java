package com.jwho.lifenoteflip.dataaccess;

import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwho.lifenoteflip.app.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    protected TextView appName;
    protected TextView status;
    protected ImageView imgAppIcon;

    public CardViewHolder(View itemView) {
        super(itemView);
        appName = (TextView) itemView.findViewById(R.id.txtAppname);
        status = (TextView) itemView.findViewById(R.id.txtStatus);
        imgAppIcon = (ImageView) itemView.findViewById(R.id.imgAppIcon);
    }

    public TextView getAppName() {
        return appName;
    }

    public void setAppName(TextView appName) {
        this.appName = appName;
    }

    public TextView getStatus() {
        return status;
    }

    public void setStatus(TextView status) {
        this.status = status;
    }

    public ImageView getImgAppIcon() {
        return imgAppIcon;
    }

    public void setImgAppIcon(ImageView imgAppIcon) {
        this.imgAppIcon = imgAppIcon;
    }
}

package com.jwho.lifenoteflip.dataaccess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwho.lifenoteflip.app.R;

import java.io.File;
import java.util.List;

public class AppActivityItemAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<AppActivity> appActivities;
    private Context parent;

    // Provide a suitable constructor (depends on the kind of appActivities)
    public AppActivityItemAdapter(List<AppActivity> appActivities) {
        this.appActivities = appActivities;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent.getContext();
        View inflatedLayout = LayoutInflater.from(this.parent).inflate(R.layout.cardview_main, parent, false);
        return new CardViewHolder(inflatedLayout);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int i) {
        AppActivity appActivity = appActivities.get(i);
        holder.getAppName().setText(appActivity.appName);
        holder.getStatus().setText(appActivity.status);
        String resourceFromEvernotePath = parent.getExternalCacheDir() + "/evernote/resource" + "64s_70880458.jpg";
        if (new File(resourceFromEvernotePath).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(resourceFromEvernotePath);
            holder.getImgAppIcon().setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return appActivities.size();
    }
}

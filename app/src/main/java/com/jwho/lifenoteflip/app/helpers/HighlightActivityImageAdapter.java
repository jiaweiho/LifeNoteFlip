package com.jwho.lifenoteflip.app.helpers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jwho.lifenoteflip.app.R;
import com.jwho.lifenoteflip.dataaccess.AppActivity;
import com.jwho.lifenoteflip.utils.BitmapWorkerTask;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * This Adapter will tie the view from a model that contains the data.
 */
public class HighlightActivityImageAdapter extends RecyclerView.Adapter<HighlightActivityImageAdapter.ViewHolder> {
    private File mActivityImages;
    private List<AppActivity> mAppActivities;
    private static int mImageWidth, mImageHeight;
    private Resources mResources;
    private Bitmap mLoadingBitmap;

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapTaskWeakReference;

        AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapTaskWeakReference = new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapTaskWeakReference.get();
        }
    }

    public HighlightActivityImageAdapter(Context context, File activityImages, int mImageWidth, int mImageHeight) {
        this.mActivityImages = activityImages;
        HighlightActivityImageAdapter.mImageWidth = mImageWidth;
        HighlightActivityImageAdapter.mImageHeight = mImageHeight;
        mResources = context.getResources();
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        mAppActivities = new ArrayList<>();
        mLoadingBitmap = BitmapFactory.decodeResource(mResources, R.drawable.google_circle_color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View appView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main, parent, false);
        ImageView imageView = new ImageView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mImageWidth, mImageHeight);
        imageView.setLayoutParams(params);
        return new ViewHolder(appView);
    }

    public void swapData(ArrayList<AppActivity> appActivities) {
        if (appActivities != null) {
            mAppActivities = appActivities;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File iconFile = new File(mAppActivities.get(position).getImgPath());
        AppActivity appActivity = mAppActivities.get(position);
        loadBitmap(iconFile, holder.getImageView());
        holder.getAppName().setText(appActivity.getAppName());
        holder.getStatus().setText(appActivity.getStatus());
    }

    public void loadBitmap(Object data, ImageView imageView) {
        if (cancelPotentialWork(data, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView, data);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mResources, mLoadingBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute();
        }
    }

    public void setLoadingImage(Bitmap bitmap) {
        mLoadingBitmap = bitmap;
    }

    public static boolean cancelPotentialWork(Object data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = String.valueOf(bitmapWorkerTask.getData());
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == null || bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mAppActivities.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mAppName;
        private TextView mStatus;

        public ViewHolder(View appView) {
            super(appView);
            mImageView = (ImageView) appView.findViewById(R.id.activity_icon);
            mAppName = (TextView) appView.findViewById(R.id.txtAppname);
            mStatus = (TextView) appView.findViewById(R.id.txtStatus);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public TextView getAppName() {
            return mAppName;
        }

        public TextView getStatus() {
            return mStatus;
        }
    }
}

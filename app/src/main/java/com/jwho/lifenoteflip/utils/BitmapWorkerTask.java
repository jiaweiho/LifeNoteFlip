package com.jwho.lifenoteflip.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

public class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {
    private WeakReference<ImageView> imageViewWeakReference;
    final static int TARGET_IMAGE_VIEW_WIDTH = 50;
    final static int TARGET_IMAGE_VIEW_HEIGHT = 50;
    private Object mData = null;

    public BitmapWorkerTask(ImageView imageView, Object mData) {
        this.mData = mData;
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        //return BitmapFactory.decodeFile(params[0].getAbsolutePath());
        return decodeBitmapFromFile();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && imageViewWeakReference != null) {
            ImageView viewImage = imageViewWeakReference.get();
            if (viewImage != null) {
                viewImage.setImageBitmap(bitmap);
            }
        }
    }

    public Object getData() {
        return mData;
    }

    private Bitmap decodeBitmapFromFile() {
        // if mData is string value of filepath.
        String filePath = String.valueOf(mData);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options) {
        final int photoWidth = options.outWidth;
        final int photoHeight = options.outHeight;
        int inSampleSize = 1;

        if (photoWidth > TARGET_IMAGE_VIEW_WIDTH || photoHeight > TARGET_IMAGE_VIEW_HEIGHT) {
            final int halfWidth = photoWidth/2;
            final int halfHeight = photoHeight/2;

            while (halfWidth/inSampleSize > TARGET_IMAGE_VIEW_WIDTH
                    || halfHeight/inSampleSize > TARGET_IMAGE_VIEW_HEIGHT) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

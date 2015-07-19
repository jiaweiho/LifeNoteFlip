package com.jwho.lifenoteflip.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service for handling Evernote API.
 */
public class ImageDownloader extends AsyncTask<Boolean, Void, byte[]> {
    private static final String TAG = ImageDownloader.class.getSimpleName();
    private Context context;

    public ImageDownloader(Context context) {
        this.context = context;
    }

    public byte[] getImage() {
        try {
            Log.i(TAG, "Image downloading.... ");
            return new HttpImageClient().invoke("37822-andltahrefhttpwwwjpo-to5e.jpg");
        } catch (Exception e) {
            Log.i(TAG, e.getStackTrace().toString());
        }
        return new byte[0];
    }

    @Override
    protected byte[] doInBackground(Boolean[] objects) {
        return getImage();
    }

    @Override
    protected void onPostExecute(byte[] result) {
        Bitmap img = BitmapFactory.decodeByteArray(result, 0, result.length);
        File imagesDir = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/images");
        if (!imagesDir.exists()) {
            if (!imagesDir.mkdirs()) {
                return;
            }
        }
        File imageFile = new File(imagesDir, "album.jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "File saved: " + imageFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HttpImageClient {
        private static final String SERVER_URL = "http://www.jpopasia.com/img/album-covers/3/";

        public byte[] invoke(String imgName) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Log.i(TAG, "URL ["+SERVER_URL+"] - Name ["+imgName+"]");
                String url = SERVER_URL + imgName;

                HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();
                con.getOutputStream().write( ("name=" + imgName).getBytes());

                InputStream is = con.getInputStream();
                byte[] b = new byte[1024];

                while ( is.read(b) != -1)
                    baos.write(b);

                con.disconnect();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }

            return baos.toByteArray();
        }
    }
}

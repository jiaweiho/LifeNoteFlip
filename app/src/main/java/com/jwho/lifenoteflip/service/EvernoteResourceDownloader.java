package com.jwho.lifenoteflip.service;

import android.app.Fragment;
import android.util.Log;

import com.google.common.io.Files;
import com.jwho.lifenoteflip.app.HighlightFragment;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

public class EvernoteResourceDownloader {

    private static final String TAG = EvernoteResourceDownloader.class.getSimpleName();

    public enum EvernoteEnum {
        NOTE_STORE_URL("https://sandbox.evernote.com/shard/s1/notestore"),
        USER_STORE_URL("https://sandbox.evernote.com/edam/user"),
        AUTH_TOKEN("S=s1:U=90b39:E=15424eae2d9:C=14ccd39b348:P=1cd:A=en-devtoken:V=2:H=b806de52190ace048e535f4280c1ae4a");
        private String value;

        EvernoteEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private Fragment context;

    public EvernoteResourceDownloader(Fragment context) {
        super();
        this.context = context;
    }

    public String downloadFromUrl(String... strings) {
        final String url = strings[0];
        final String fileName = "noteResource.jpg";
        Ion.with(context)
                .load(url)
                .write(new File(getDownloadedFileFolder(fileName) + fileName))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        Log.d(TAG, ".downloadFromUrl: success! result=" + result);
                    }
                });
        return null;
    }

    public String getDownloadedFileFolder(String fileName) {
        File file = new File(context.getActivity().getExternalCacheDir() + "/evernote/resource/" + fileName);
        if (!file.exists() || !file.isDirectory()) {
            try {
                Files.createParentDirs(file);
                Files.touch(file.getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return context.getActivity().getExternalCacheDir() + "/evernote/resource/";
    }
}

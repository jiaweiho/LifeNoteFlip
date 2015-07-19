package com.jwho.lifenoteflip.handlers;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import com.jwho.lifenoteflip.dataaccess.NoteParcelable;
import com.jwho.lifenoteflip.service.EvernoteResourceDownloader;

import java.util.ArrayList;
import java.util.List;

import static com.jwho.lifenoteflip.handlers.EvernoteServiceMessage.MSG_KEY;

public class EvernoteMessageHandler extends Handler {
    public static final String KEY_RES_URLS = "resUrls";
    private EvernoteResourceDownloader resourceDownloader;

    public EvernoteMessageHandler(Fragment fragment) {
        this.resourceDownloader = new EvernoteResourceDownloader(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        // Handle message sent after notes are downloaded. Get resources for those notes.
        Bundle data = msg.getData();
        List<NoteParcelable> notesList = data.getParcelableArrayList(MSG_KEY);

        for (NoteParcelable note : notesList) {
            ResourceParcelable[] resUrls = note.getResUrls();
            // Set database tables and values for connection
            resourceDownloader.downloadFromUrl(data.getString(MSG_KEY));
        }
    }
}

package com.jwho.lifenoteflip.service;

import android.os.AsyncTask;
import android.util.Log;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Resource;
import com.evernote.edam.userstore.PublicUserInfo;
import com.evernote.edam.userstore.UserStore;
import com.evernote.thrift.TException;
import com.evernote.thrift.protocol.TBinaryProtocol;
import com.evernote.thrift.transport.THttpClient;
import com.evernote.thrift.transport.TTransportException;
import com.jwho.lifenoteflip.app.BuildConfig;
import com.jwho.lifenoteflip.dataaccess.NoteFromEvernote;

import java.util.ArrayList;
import java.util.List;

public class EvernoteGetResources extends AsyncTask<List<Note>, Void, List<Resource>> {

    private static final String TAG = EvernoteGetResources.class.getSimpleName();

    @Override
    protected List<Resource> doInBackground(List<Note>... notes) {
        List<Resource> resources = new ArrayList<>();
        THttpClient userStoreTrans;
        try {
            userStoreTrans = new THttpClient(BuildConfig.USER_STORE_URL);
            TBinaryProtocol userStoreProt = new TBinaryProtocol(userStoreTrans);
            UserStore.Client userStore = new UserStore.Client(userStoreProt, userStoreProt);
            PublicUserInfo userInfo = userStore.getPublicUserInfo("theitninja");

            for (Note note : notes[0]) {
                //NoteFromEvernote everNote = new NoteFromEvernote(note.getGuid(), note.getTitle(), note.getContent(), note.getResources());
                String resGuid = note.getResources().get(0).getGuid();
                String resUrl = userInfo.getWebApiUrlPrefix() + "res/" + resGuid;
                Log.i(TAG, "ResUrl: " + resUrl);
            }

            // TODO: Get multiple resources

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (EDAMUserException e) {
            e.printStackTrace();
        } catch (EDAMSystemException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (EDAMNotFoundException e) {
            e.printStackTrace();
        }
        return resources;
    }

    @Override
    protected void onPostExecute(List<Resource> resources) {
        if (resources != null && !resources.isEmpty()) {

        }
    }
}

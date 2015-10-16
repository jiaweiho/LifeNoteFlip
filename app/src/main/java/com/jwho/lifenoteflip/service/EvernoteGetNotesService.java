package com.jwho.lifenoteflip.service;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.notestore.NoteStore;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Resource;
import com.evernote.thrift.TException;
import com.evernote.thrift.protocol.TBinaryProtocol;
import com.evernote.thrift.transport.THttpClient;
import com.jwho.lifenoteflip.app.BuildConfig;
import com.jwho.lifenoteflip.dataaccess.AppActivityDbAdapter;
import com.jwho.lifenoteflip.handlers.EvernoteMessageHandler;
import com.jwho.lifenoteflip.handlers.EvernoteServiceMessage;

/**
 * Service for handling Evernote API.
 */
public class EvernoteGetNotesService extends AsyncTask<String, Void, NoteList> {
    private static final String TAG = EvernoteGetNotesService.class.getSimpleName();
    private Context context;
    private EvernoteMessageHandler evernoteHandler;

    public EvernoteGetNotesService(Fragment fragment) {
        context = fragment.getActivity();
        this.evernoteHandler = new EvernoteMessageHandler(fragment);
    }

    @Override
    protected NoteList doInBackground(String... filter) {
        return getNote(filter[0]);
    }

    public NoteList getNote(String filter) {
        try {
            THttpClient noteStoreTrans = new THttpClient(BuildConfig.NOTE_STORE_URL);
            TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
            NoteStore.Client noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);
            NoteFilter noteFilter = new NoteFilter();
            noteFilter.setWords(filter);

            NoteList result = noteStore.findNotes(BuildConfig.AUTH_TOKEN, noteFilter, 0, 50);
            Log.i(TAG, "Found: " + result.getTotalNotes());
            saveEvernoteToStore(result, noteStore);

            return result;
//            return new NoteFromEvernote(resGuid, note.getTitle(), note.getContent(), resUrl);
        } catch (Exception e) {
            Log.i(TAG, e.getStackTrace().toString());
        }
        return null;
    }

    private void saveEvernoteToStore(NoteList result, NoteStore.Client noteStore) throws EDAMUserException, EDAMSystemException, TException, EDAMNotFoundException {
        AppActivityDbAdapter dbAdapter = new AppActivityDbAdapter(context);
        Note noteWithData;
        for (Note note : result.getNotes()) {
            noteWithData = noteStore.getNote(BuildConfig.AUTH_TOKEN, note.getGuid(), true, true, true, false);
            dbAdapter.insertAppActivity(Services.EVERNOTE, noteWithData.getContent());
            dbAdapter.insertNote(note.getContent(), note.getTitle());
            for (Resource res : noteWithData.getResources()) {
                dbAdapter.insertResource(res.getGuid(), res.getNoteGuid(), "Started");
            }
        }
    }

    @Override
    protected void onPostExecute(NoteList o) {
        if (o != null) {
            EvernoteServiceMessage msg = new EvernoteServiceMessage();
            msg.addMessage(o);
            evernoteHandler.sendMessage(msg.getMessage());
        }
    }
}

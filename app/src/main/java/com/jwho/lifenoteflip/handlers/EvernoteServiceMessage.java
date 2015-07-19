package com.jwho.lifenoteflip.handlers;

import android.os.Bundle;
import android.os.Message;

import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Resource;
import com.jwho.lifenoteflip.dataaccess.NoteParcelable;

import java.util.ArrayList;

public class EvernoteServiceMessage {

    private Message msg;
    public static final String MSG_KEY = "message";

    public EvernoteServiceMessage() {
        this.msg = new Message();
    }

    public void addMessage(NoteList message) {
        ArrayList<NoteParcelable> notes = new ArrayList<>();
        for (Note note : message.getNotes()) {
            ResourceParcelable[] resourceParcelables = new ResourceParcelable[note.getResources().size()];
            for (int i = 0; i < note.getResources().size(); ++i) {
                Resource resource = note.getResources().get(i);
                ResourceParcelable resourceParcelable = new ResourceParcelable(resource.getMime(),
                        resource.getWidth(), resource.getHeight());
                resourceParcelables[i] = resourceParcelable;
            }

            NoteParcelable noteParcelable = new NoteParcelable(note.getGuid(),note.getTitle(), note.getContent());
            noteParcelable.setResUrls(resourceParcelables);
            notes.add(noteParcelable);
        }
        Bundle messageBundle = new Bundle();
        messageBundle.putParcelableArrayList(MSG_KEY, notes);
        msg.setData(messageBundle);
    }

    public Message getMessage() {
        return msg;
    }
}

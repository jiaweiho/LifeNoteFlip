package com.jwho.lifenoteflip.dataaccess;

import com.evernote.edam.type.Resource;

import java.util.List;

/**
 * Represents a note retrieved from Evernote API.
 */
public class NoteFromEvernote {

    private String resGuid;
    private String title;
    private String content;
    private List<Resource> resUrls;

    public NoteFromEvernote(String resGuid, String title, String content, List<Resource> resUrls) {
        this.resGuid = resGuid;
        this.title = title;
        this.content = content;
        this.resUrls = resUrls;
    }

    public String getResGuid() {
        return resGuid;
    }

    public void setResGuid(String resGuid) {
        this.resGuid = resGuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Resource> getResUrls() {
        return resUrls;
    }

    public void setResUrls(List<Resource> resUrls) {
        this.resUrls = resUrls;
    }
}

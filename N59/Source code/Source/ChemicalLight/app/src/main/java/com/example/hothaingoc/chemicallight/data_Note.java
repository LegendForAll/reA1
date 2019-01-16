package com.example.hothaingoc.chemicallight;

public class data_Note {

    private int noteid;
    private String noteTime;
    private String noteTopic;
    private String noteMain;

    public data_Note() {
    }

    public data_Note(int noteid, String noteTime, String noteTopic, String noteMain) {
        this.noteid = noteid;
        this.noteTime = noteTime;
        this.noteTopic = noteTopic;
        this.noteMain = noteMain;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteTopic() {
        return noteTopic;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }

    public String getNoteMain() {
        return noteMain;
    }

    public void setNoteMain(String noteMain) {
        this.noteMain = noteMain;
    }
}

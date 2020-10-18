package com.example.daily;

public class Note {
    private String title;
    private String content;
    private String time;
    private  String path;


    public Note(String title, String content,String path,String time) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.path = path;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

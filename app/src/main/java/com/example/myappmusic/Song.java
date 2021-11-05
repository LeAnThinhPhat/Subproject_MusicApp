package com.example.myappmusic;
import java.io.Serializable;

public class Song implements Serializable {
    private String title;
    private String singer;
    private int imgSong;
    private int resourece;


    public Song(String title, String singer, int imgSong, int resourece) {
        this.title = title;
        this.singer = singer;
        this.imgSong = imgSong;
        this.resourece = resourece;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getImgSong() {
        return imgSong;
    }

    public void setImgSong(int imgSong) {
        this.imgSong = imgSong;
    }

    public int getResourece() {
        return resourece;
    }

    public void setResourece(int resourece) {
        this.resourece = resourece;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", imgSong=" + imgSong +
                ", resourece=" + resourece +
                '}';
    }
}

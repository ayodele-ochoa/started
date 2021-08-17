package com.ayodeleochoa.ayophotos;

import com.google.gson.annotations.SerializedName;

public class JsonPhotos
{
    int albumId;
    int id;
    String title;
    String url;
    String thumbnailUrl;
    String currencyPair;

    public int getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(int albumId)
    {
        this.id = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() { return url; }

    public void setURL(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

package vn.sunasterisk.buoi15_sqlite.data.model;

import androidx.annotation.NonNull;

public class New {
    private int mId;
    private String mTitle;
    private String mContent;

    public New() {
    }

    public New(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public New(int id, String title, String content) {
        mId = id;
        mTitle = title;
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @NonNull
    @Override
    public String toString() {
        return mId + " " + mTitle + " " + mContent;
    }
}

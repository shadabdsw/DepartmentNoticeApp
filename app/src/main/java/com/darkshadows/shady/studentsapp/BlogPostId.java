package com.darkshadows.shady.studentsapp;

import com.google.firebase.firestore.Exclude;
import android.support.annotation.NonNull;

public class BlogPostId {

    @Exclude
    public String BlogPostId;

    public <T extends BlogPostId> T withId(@NonNull final String id)
    {
        this.BlogPostId = id;
        return (T) this;
    }
}

package com.mshoaibluqman.flashchatnewfirebase;

/**
 * Created by muhammadirfan on 04/10/2017.
 */

public class InstantMessage {

    String mMessage;
    String mAuther;

    public InstantMessage(String mMessage, String mAuther) {
        this.mMessage = mMessage;
        this.mAuther = mAuther;
    }

    public InstantMessage() {
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmAuther() {
        return mAuther;
    }
}

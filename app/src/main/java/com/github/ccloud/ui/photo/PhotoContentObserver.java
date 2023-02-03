package com.github.ccloud.ui.photo;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class PhotoContentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public PhotoContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        //(SDK>=16)
        // do s.th.
        // depending on the handler you might be on the UI
        // thread, so be cautious!

        // This is my AsyncTask that queries ContentResolver which now
        // is aware of newly created media file.
        // You implement your own query here in whatever way you like
        // This query will contain info for newly created image
    }
}

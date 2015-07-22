package com.wisdomlanna.filetoserver;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by suraphol on 7/8/15 AD.
 */
public class Retrofit {
    public static FeedbackUpLoadFile getUpLoadFile() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog("UpLoadFile"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://feedback.clouddha.com/a/service")
                .build();
        return restAdapter.create(FeedbackUpLoadFile.class);
    }

    public interface FeedbackUpLoadFile {
        @Multipart
        @POST("/uploadimage")
        void upLoad(@Part("f") TypedFile path, @Part("id") TypedString id, Callback<UpLoadResult> callback);
    }

    public class UpLoadResult {
        public boolean success;
        public String error;
    }
}

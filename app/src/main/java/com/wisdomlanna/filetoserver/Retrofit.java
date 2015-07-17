package com.wisdomlanna.filetoserver;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by suraphol on 7/8/15 AD.
 */
public class Retrofit {
    public static FeedbackUpLoadFile getUpLoadFile() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog("UpLoadFile"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://cm-smarthome.com/android")
                .build();
        return restAdapter.create(FeedbackUpLoadFile.class);
    }

    public interface FeedbackUpLoadFile {
        @Multipart
        @POST("/uploadImage")
        void upLoad(@Part("filUpload") TypedFile path, Callback<UpLoadResult> callback);
    }

    public class UpLoadResult{
        public String StatusID;
    }
}

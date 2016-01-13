package com.tassioauad.gamecatalog.model.api.asynctask.impl;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecatalog.model.api.resource.PlatformResource;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PlatformSearchLastsAsyncTaskTest extends AndroidTestCase {

    PlatformResource platformResource;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("yyyy'-'MM'-'dd HH':'mm':'ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.giantbombapi_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        platformResource = retrofit.create(PlatformResource.class);
    }

    public void testDoInBackground() throws Exception {

        PlatformSearchLastsAsyncTask platformSearchLastsAsyncTask =
                new PlatformSearchLastsAsyncTask(getContext(), platformResource);

        platformSearchLastsAsyncTask.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Platform> platformList = (List<Platform>) object;
                assertTrue(platformList.size() > 0);
            }

            @Override
            public void onException(Exception exception) {
                fail(exception.getMessage());
            }
        });

        platformSearchLastsAsyncTask.execute();

    }

}
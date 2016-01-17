package com.tassioauad.gamecatalog.model.api.asynctask.impl;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tassioauad.gamecatalog.R;
import com.tassioauad.gamecatalog.model.api.ItemTypeAdapterFactory;
import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecatalog.model.api.resource.GameResource;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class GameSearchByPlatformAsyncTaskTest extends AndroidTestCase {
    GameResource gameResource;

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

        gameResource = retrofit.create(GameResource.class);
    }

    public void testDoInBackground() throws Exception {

        final CountDownLatch signal = new CountDownLatch(1);

        GameSearchByPlatformAsyncTask gameSearchByPlatformAsyncTask =
                new GameSearchByPlatformAsyncTask(getContext(), gameResource);
        Platform platform = new Platform();
        platform.setId(94l);

        gameSearchByPlatformAsyncTask.setApiResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Game> gameList = (List<Game>) object;
                assertTrue(gameList.size() > 0);
                signal.countDown();
            }

            @Override
            public void onException(Exception exception) {
                fail(exception.getMessage());
                signal.countDown();
            }
        });

        gameSearchByPlatformAsyncTask.execute(platform);
        signal.await();

    }
}
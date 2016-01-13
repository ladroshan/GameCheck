package com.tassioauad.gamecatalog.model.api.asynctask.impl;

import android.content.Context;

import com.tassioauad.gamecatalog.model.api.asynctask.AsyncTaskResult;
import com.tassioauad.gamecatalog.model.api.asynctask.GenericAsyncTask;
import com.tassioauad.gamecatalog.model.api.asynctask.exception.BadRequestException;
import com.tassioauad.gamecatalog.model.api.resource.GameResource;
import com.tassioauad.gamecatalog.model.entity.Game;
import com.tassioauad.gamecatalog.model.entity.Platform;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class GameSearchByPlatformAsyncTask extends GenericAsyncTask<Platform, Void, List<Game>> {

    private GameResource gameResource;

    public GameSearchByPlatformAsyncTask(Context context, GameResource gameResource) {
        super(context);
        this.gameResource = gameResource;
    }

    @Override
    protected AsyncTaskResult<List<Game>> doInBackground(Platform... params) {

        try {
            Response<List<Game>> response = gameResource.searchByPlatform(getApiKey(), params[0].getId()).execute();
            switch (response.code()) {
                case HTTP_OK:
                    return new AsyncTaskResult<>(response.body());
                default:
                    return new AsyncTaskResult<>(new BadRequestException());
            }
        } catch (IOException e) {
            return new AsyncTaskResult<>(new BadRequestException());
        }
    }
}

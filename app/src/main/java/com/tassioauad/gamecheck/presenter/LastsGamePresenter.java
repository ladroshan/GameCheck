package com.tassioauad.gamecheck.presenter;


import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.LastsGameView;

import java.util.List;

public class LastsGamePresenter {

    private LastsGameView view;
    private GameApi gameApi;
    private final int NUMBER_OF_GAMES = 20;

    public LastsGamePresenter(LastsGameView view, GameApi gameApi) {
        this.view = view;
        this.gameApi = gameApi;
    }

    public void loadLastsGames() {
        view.showLoading();
        gameApi.setServiceResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                view.showGames((List<Game>) object);
                view.dismissLoading();
            }

            @Override
            public void onException(Exception exception) {
                view.dismissLoading();
                view.warnCouldNotLoadGames();
            }
        });

        gameApi.searchLasts(NUMBER_OF_GAMES);
    }
}

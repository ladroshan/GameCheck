package com.tassioauad.gamecheck.presenter;

import com.tassioauad.gamecheck.model.api.GameApi;
import com.tassioauad.gamecheck.model.api.asynctask.ApiResultListener;
import com.tassioauad.gamecheck.model.entity.Game;
import com.tassioauad.gamecheck.view.SearchGameView;

import java.util.List;

public class SearchGamePresenter {

    private SearchGameView view;
    private GameApi gameApi;

    public SearchGamePresenter(SearchGameView view, GameApi gameApi) {
        this.view = view;
        this.gameApi = gameApi;
    }

    public void searchByName(String name) {
        view.showLoading();
        gameApi.setServiceResultListener(new ApiResultListener() {
            @Override
            public void onResult(Object object) {
                List<Game> gameList = (List<Game>) object;
                if(gameList.size() > 0) {
                    view.showGames(gameList);
                } else {
                    view.warnAnyGameFound();
                }
                view.dismissLoading();
            }

            @Override
            public void onException(Exception exception) {
                view.warnFailuredToListGame();
                view.dismissLoading();
            }
        });

        gameApi.searchByName(name);
    }
}

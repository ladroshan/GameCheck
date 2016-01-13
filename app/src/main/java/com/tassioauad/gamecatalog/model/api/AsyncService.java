package com.tassioauad.gamecatalog.model.api;

import com.tassioauad.gamecatalog.model.api.asynctask.ApiResultListener;

public interface AsyncService {

    ApiResultListener getServiceResultListener();

    void setServiceResultListener(ApiResultListener listener);

    void cancelAllService();
}

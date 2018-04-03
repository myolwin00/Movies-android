package io.github.emrys919.movies.data.models;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.github.emrys919.movies.network.MovieDataAgent;
import io.github.emrys919.movies.network.RetrofitAgent;

/**
 * Created by myolwin00 on 6/15/17.
 */

public abstract class BaseModel {

    protected MovieDataAgent dataAgent;

    public BaseModel() {
        dataAgent = RetrofitAgent.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(Object obj) {
    }
}

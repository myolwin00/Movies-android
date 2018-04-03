package io.github.emrys919.movies.mvp.presenters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by myolwin00 on 7/9/17.
 */

public abstract class BasePresenter {

    public void onCreate() {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Object obj) {

    }
}

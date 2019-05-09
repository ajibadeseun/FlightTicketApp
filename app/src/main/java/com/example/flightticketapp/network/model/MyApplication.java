package com.example.flightticketapp.network.model;

import android.app.Application;

import com.example.flightticketapp.app.Const;

public class MyApplication extends Application {
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule(Const.BASE_URL))
                .appModule(new AppModule(this))
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}

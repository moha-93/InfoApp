package com.mycompany.testtask.base;

import android.app.Application;

import com.mycompany.testtask.component.AppComponent;
import com.mycompany.testtask.component.DaggerAppComponent;
import com.mycompany.testtask.module.ApplicationModule;
import com.mycompany.testtask.module.RetrofitModule;

public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}

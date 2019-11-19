package com.mycompany.testtask.component;

import com.mycompany.testtask.activity.MainActivity;
import com.mycompany.testtask.module.ApplicationModule;
import com.mycompany.testtask.module.RetrofitModule;
import com.mycompany.testtask.rest.JsonPlaceHolderApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,RetrofitModule.class})
public interface AppComponent {
    JsonPlaceHolderApi getHolderApi();
    void inject(MainActivity mainActivity);
}

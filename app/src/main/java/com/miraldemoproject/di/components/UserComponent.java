package com.miraldemoproject.di.components;

import com.miraldemoproject.di.modules.UserModule;
import com.miraldemoproject.di.scope.PerActivity;
import com.miraldemoproject.user.view.UserListActivity;

import dagger.Component;

@PerActivity
@Component(modules = UserModule.class, dependencies = ApplicationComponent.class)
public interface UserComponent {

    void inject(UserListActivity userListActivity);
}

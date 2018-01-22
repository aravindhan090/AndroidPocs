package com.miraldemoproject.user.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.miraldemoproject.R;
import com.miraldemoproject.application.MiralDemoApplication;
import com.miraldemoproject.di.components.DaggerUserComponent;
import com.miraldemoproject.di.modules.UserModule;
import com.miraldemoproject.network.RequestInterface;
import com.miraldemoproject.user.adapter.DataAdapter;
import com.miraldemoproject.user.model.AndroidUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserListActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.learn2crack.com/";

    private RecyclerView mRecyclerView;

    //private CompositeDisposable mCompositeDisposable;

    private DataAdapter mAdapter;

    private ArrayList<AndroidUser> mAndroidArrayList;

    @Inject
    protected RequestInterface requestInterface;

    @Inject
    protected CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        resolveDaggerDependency();

      //  mCompositeDisposable = new CompositeDisposable();
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {
        compositeDisposable.add(requestInterface.getAndroidUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<AndroidUser>>() {
            @Override
            public void accept(@NonNull List<AndroidUser> items) throws Exception {
                handleResponse(items);
            }
        }));

    }

    private void handleResponse(List<AndroidUser> androidList) {
        mAndroidArrayList = new ArrayList<>(androidList);
        mAdapter = new DataAdapter(mAndroidArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    /**
     * Initialize DaggerUserComponent with usermodule and
     */
    protected void resolveDaggerDependency() {
        DaggerUserComponent.builder()
                .applicationComponent(((MiralDemoApplication)getApplication()).getApplicationComponent())
                .userModule(new UserModule())
                .build().inject(this);

    }

}

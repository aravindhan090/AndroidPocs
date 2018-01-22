package com.miraldemoproject.uiretrofitrx;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miraldemoproject.R;
import com.miraldemoproject.network.RequestInterface;
import com.miraldemoproject.uiretrofitrx.Adapter.RideAdapter;
import com.miraldemoproject.uiretrofitrx.model.Rides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RiderActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://192.168.188.2:5000/";
    private RecyclerView mRecyclerView;
    private CompositeDisposable mCompositeDisposable;

    private RideAdapter mAdapter;

    private ArrayList<Rides> mAndroidArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_main);

        mCompositeDisposable = new CompositeDisposable();
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

    }

    /*
    Creating instance for Retrofit to make network calls
    Using the Observable returned by API and using subscribe to handle the data
     */
    private void loadJSON() {

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);

        mCompositeDisposable.add(requestInterface.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Rides>>() {
                    @Override
                    public void accept(@NonNull List<Rides> items) throws Exception {
                        handleResponse(items);
                    }
                }));
       /* Gson gson = null;
        gson=new Gson();
        Model model=gson.fromJson(RiderJson.getRiderJson(), Model.class);
        Log.v("Model is::",model.toString());*/
    }

    /*
    Getting the List from Observable and setting them in Adapter
     */
    private void handleResponse(List<Rides> androidList) {
        mAndroidArrayList = (ArrayList<Rides>) androidList;
        mAdapter = new RideAdapter(mAndroidArrayList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /*
    Displaying Error Message
     */
    private void handleError(Throwable error) {

        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    /*
    Clearing the Disposable
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}

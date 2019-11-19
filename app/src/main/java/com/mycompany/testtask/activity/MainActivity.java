package com.mycompany.testtask.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.mycompany.testtask.R;
import com.mycompany.testtask.adapter.UserAdapter;
import com.mycompany.testtask.base.BaseApplication;
import com.mycompany.testtask.pojo.User;
import com.mycompany.testtask.rest.JsonPlaceHolderApi;
import com.mycompany.testtask.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    public static final String EXTRA_NAME = "com.mycompany.testtask.extraName";
    public static final String EXTRA_EMAIL = "com.mycompany.testtask.extraEmail";
    public static final String EXTRA_PHONE_NUMBER = "com.mycompany.testtask.extraPhoneNum";
    public static final String EXTRA_ADDRESS = "com.mycompany.testtask.extraAddress";
    public static final String EXTRA_WEBSITE = "com.mycompany.testtask.extraWebsite";
    public static final String EXTRA_LAT = "com.mycompany.testtask.extraLat";
    public static final String EXTRA_LNG = "com.mycompany.testtask.extraLng";

    private RecyclerView recyclerView;
    private CompositeDisposable disposable = new CompositeDisposable();
    private UserAdapter adapter;
    private List<User> userList;
    private ArrayList<User> userInstance = new ArrayList<>();

    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";

    @Inject
    JsonPlaceHolderApi holderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Users");
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        if (savedInstanceState != null) {
            userInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            displayData();
        } else {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (NetworkUtils.isNetworkAvailable(this)) {
                fetchData();
            } else {
                Toast.makeText(this, "Oops! No internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userInstance, this);
        recyclerView.setAdapter(adapter);
        restoreLayoutManagerPosition();
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);
    }

    private void fetchData() {
        disposable.add(holderApi.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if (users != null) {
                            userList = users;
                            userInstance.addAll(userList);
                            adapter = new UserAdapter(userList, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.smoothScrollToPosition(0);
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(MainActivity.this);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }

    @Override
    public void OnItemClick(int position) {
        Intent detailIntent = new Intent(MainActivity.this, DetailsActivity.class);
        User user = userInstance.get(position);
        detailIntent.putExtra(EXTRA_NAME, user.getName());
        detailIntent.putExtra(EXTRA_EMAIL, user.getEmail());
        detailIntent.putExtra(EXTRA_PHONE_NUMBER, user.getPhone());
        detailIntent.putExtra(EXTRA_ADDRESS, user.getAddress().getStreet());
        detailIntent.putExtra(EXTRA_LAT, user.getAddress().getGeo().getLat());
        detailIntent.putExtra(EXTRA_LNG, user.getAddress().getGeo().getLng());
        detailIntent.putExtra(EXTRA_WEBSITE, user.getWebsite());
        startActivity(detailIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void restoreLayoutManagerPosition() {
        if (savedRecyclerLayoutState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, userInstance);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        userInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
            recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


}

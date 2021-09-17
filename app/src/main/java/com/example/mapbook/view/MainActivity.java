package com.example.mapbook.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mapbook.R;
import com.example.mapbook.adapter.PlaceAdapter;
import com.example.mapbook.databinding.ActivityMainBinding;
import com.example.mapbook.model.Place;
import com.example.mapbook.roomDB.PlaceDao;
import com.example.mapbook.roomDB.PlaceDatabase;
import com.example.mapbook.util.RecyclerViewClickInterface;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private ActivityMainBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();
    PlaceDao dao;
    PlaceDatabase placeDatabase;
    PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        placeDatabase = Room.databaseBuilder(getApplicationContext(), PlaceDatabase.class, "Places").build();
        dao = placeDatabase.placeDao();
        disposable.add(dao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                MainActivity.this::handleResponse
        ));
    }

    private void handleResponse(List<Place> places) {
        adapter = new PlaceAdapter(places, this);
        binding.mainActivityRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addNewPlace) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("isNew", "new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        disposable.clear();
    }

    @Override
    public void itemOnClick(Place place) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("place", place);
        intent.putExtra("isNew", "old");
        startActivity(intent);
    }
}
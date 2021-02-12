package com.android.developer.Nikhilanand.audioplayer.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;


import com.android.developer.Nikhilanand.audioplayer.Adapter.MusicAdapter;
import com.android.developer.Nikhilanand.audioplayer.Interface.RecyclerViewClickInterface;
import com.android.developer.Nikhilanand.audioplayer.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tunes extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    ArrayList<File> mySongs;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunes);
        recyclerView = findViewById(R.id.listView);


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                display();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findSong(File root) {
        ArrayList<File> at = new ArrayList<File>();
        root = new File(Environment.getExternalStorageDirectory() + "/" + "DCIM"+"/"+"Record");
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                at.addAll(findSong(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3") ||
                        singleFile.getName().endsWith(".wav") ||
                singleFile.getName().endsWith(".3gp")) {
                    at.add(singleFile);
                }
            }
        }
        return at;
    }

    void display() {
        mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }


        MusicAdapter musicAdapter = new MusicAdapter(this, items, this);
        recyclerView.setAdapter(musicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onItemClick(int position) {
        String songName = items[position];
        startActivity(new Intent(getApplicationContext(), PlayerActivity.class)

                .putExtra("pos", position).putExtra("songs", mySongs).putExtra("songname", songName));
    }
}

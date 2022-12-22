package com.example.netflix.Mainscreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media2.*;


import androidx.media2.common.MediaMetadata;
import androidx.media2.common.UriMediaItem;
import androidx.media2.player.MediaPlayer;
import androidx.media2.widget.VideoView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.netflix.R;


import java.util.Objects;

public class VideoPlayer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        VideoView videoView = findViewById(R.id.exoplayer);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MediaMetadata mediaMetaData = new MediaMetadata.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, "Big Buck Bunny").build();
        UriMediaItem item = new UriMediaItem.Builder(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")).setMetadata(mediaMetaData).build();
        MediaPlayer mediaPlayer = new MediaPlayer(this);
        videoView.setPlayer(mediaPlayer);
        mediaPlayer.setMediaItem(item);
        mediaPlayer.prepare();
        mediaPlayer.play();
    }

}
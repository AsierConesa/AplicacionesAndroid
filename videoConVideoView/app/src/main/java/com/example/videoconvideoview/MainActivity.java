package com.example.videoconvideoview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button botonReproducirVideo;
    VideoView mvideoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonReproducirVideo = findViewById(R.id.botonReproducirVideo);
        mvideoView = findViewById(R.id.video_view);

        mvideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mvideoView.start();
                mvideoView.requestFocus();
                ponerControles(mvideoView);
                botonReproducirVideo.setEnabled(false);
            }
        });
        mvideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                botonReproducirVideo.setEnabled(true);
            }
        });
    }
    public void cargarMultimedia(View v){
        mvideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video));
    }
    public void ponerControles(VideoView videoView){
        mediaController = new MediaController(this);
        mediaController.setAnchorView((View) videoView.getParent());
        videoView.setMediaController(mediaController);
    }
}
package com.example.reproducirsoundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    Button buttonReproducir = null;
    private int idSonido, idSonido2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonReproducir = findViewById(R.id.botonCargar);
    }

    public void cargarMultimedia(View v){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();
        idSonido = soundPool.load(this,R.raw.sonido1, 0);
        idSonido2 = soundPool.load(this,R.raw.sonido2, 0);
    }

    public void playSonido1(View v){
        soundPool.play(idSonido, 1, 1, 1, 0, 1);
    }
    public void playSonido2(View v){
        soundPool.play(idSonido2, 1, 1, 1, 0, 1);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
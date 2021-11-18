package com.example.myappmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class PlaySong extends AppCompatActivity {
    ImageView play, prev, next, imageView;
    TextView songTitle, songSinger;
    SeekBar mSeekBarTime, mSeekBarVol;
    List<Song> mSongs;
    static MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        play = findViewById(R.id.imgPlay);
        prev = findViewById(R.id.imgLui);
        next = findViewById(R.id.imgTien);
        imageView = findViewById(R.id.imageView);
        songTitle = findViewById(R.id.txtNameSong);
        songSinger = findViewById(R.id.txtSinger);
        mSeekBarTime = findViewById(R.id.seekBar);

        mSongs = (List<Song>) getIntent().getSerializableExtra("listMusic");
        Song song = (Song) getIntent().getSerializableExtra("song");
        currentIndex = getIntent().getIntExtra("index", 0);

        if (song != null) {
            Log.e("song","Không null");
            imageView.setImageResource(song.getImgSong());
            songTitle.setText(song.getTitle());
            songSinger.setText(song.getSinger());
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResourece());
        }else{
            Log.e("song","Không null");
        }



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                //    clickStopService();//notification
                    play.setImageResource(R.drawable.stop);
                }else{
                    mMediaPlayer.start();
                 //   clickStartService();//notification
                    play.setImageResource(R.drawable.play);
                }
                SongNames();
            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.play);
                }
                if(currentIndex < mSongs.size() - 1){
                    currentIndex++;
                }else{
                    currentIndex = 0;
                }
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getResourece());
                mMediaPlayer.start();
                SongNames();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.play);
                }
                if(currentIndex > 0){
                    currentIndex--;
                }else{
                    currentIndex = mSongs.size()-1;
                }
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getResourece());
                mMediaPlayer.start();
                SongNames();
            }
        });


    }
    //notification
    private void clickStartService() {
        mSongs = (List<Song>) getIntent().getSerializableExtra("listMusic");
        Song song = (Song) getIntent().getSerializableExtra("song");
        currentIndex =  getIntent().getIntExtra("index",0);

        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song",song);

        intent.putExtras(bundle);

        startService(intent);
    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


    private void SongNames(){
        Song song = mSongs.get(currentIndex);
        imageView.setImageResource(song.getImgSong());
        songTitle.setText(song.getTitle());
        songSinger.setText(song.getSinger());

        //seek bar duration
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mMediaPlayer.seekTo(progress);
                    mSeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer!= null){
                    try {
                        if(mMediaPlayer.isPlaying()){
                            Message message = new Message();

                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @SuppressLint("Handle Leak")
    Handler handler = new Handler () {
        @Override
        public void handleMessage (Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };


}
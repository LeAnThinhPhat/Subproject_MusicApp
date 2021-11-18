package com.example.myappmusic;

        import  androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.widget.SeekBar;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.List;

public class List_Music extends AppCompatActivity implements OnClickListener {
    SeekBar mSeekBarTime, mSeekBarVol;
    static MediaPlayer sMediaPlayer;
    private Runnable mRunnable;
    int currentIndex = 0;

    RecyclerView rcv;
    CustomRecyclerView adt;
    List<Song> mSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);

        rcv = findViewById(R.id.rcv);
        mSongs = new ArrayList<Song>();

        mSongs.add(new Song("Rap Viet","De Choat",R.drawable.rap,R.raw.rapdiet));
        mSongs.add(new Song("Nhac Tre","Anh Quan",R.drawable.songjus,R.raw.ngay));
        mSongs.add(new Song("Tru Tinh","GIl",R.drawable.songgil,R.raw.trutinh));
        mSongs.add(new Song("Rap Viet","De Choat",R.drawable.rap,R.raw.rapdiet));
        mSongs.add(new Song("Nhac Tre","Anh Quan",R.drawable.songjus,R.raw.ngay));
        mSongs.add(new Song("Tru Tinh","GIl",R.drawable.songgil,R.raw.trutinh));

        adt = new CustomRecyclerView(mSongs,this);
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adt);
        rcv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
    @Override
    public void clickItem(Song song) {
        Intent intent  = new Intent(List_Music.this,PlaySong.class);
        intent.putExtra("song",song);
        intent.putExtra("listMusic", (Serializable) mSongs);
        intent.putExtra("index",mSongs.indexOf(song));
        startActivity(intent);
    }
}

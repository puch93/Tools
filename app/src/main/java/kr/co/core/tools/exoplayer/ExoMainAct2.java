package kr.co.core.tools.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

import kr.co.core.tools.R;
import kr.co.core.tools.util.Common;

public class ExoMainAct2 extends AppCompatActivity {
    Activity act;
    PlayerView playerView;
    SimpleExoPlayer player;

    private static final String TAG = "TEST_HOME";

    int current = RESOURCE_01;
    private static final int RESOURCE_01 = 101;
    private static final int RESOURCE_02 = 102;
    private static final int RESOURCE_03 = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_main_act2);
        act = this;

        playerView = (PlayerView) findViewById(R.id.playerView);

        player = ExoPlayerFactory.newSimpleInstance(act);
        playerView.setPlayer(player);

        player.addListener(new Player.EventListener() {
            /**
             * @param playWhenReady - Whether playback will proceed when ready.
             * @param playbackState - One of the STATE constants.
             */
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {

                    case Player.STATE_IDLE: // 1
                        //재생 실패
                        Log.e(TAG, "재생 실패");
                        break;
                    case Player.STATE_BUFFERING: // 2
                        // 재생 준비
                        Log.e(TAG, "재생 준비");
                        break;
                    case Player.STATE_READY: // 3
                        // 재생 준비 완료
                        Log.e(TAG, "재생 준비 완료");
                        break;
                    case Player.STATE_ENDED: // 4
                        // 재생 마침
                        Log.e(TAG, "재생 마침");
                        checkState();

                        break;
                    default:
                        break;
                }
            }
        });

        playExo(R.raw.police4);
    }

    private void checkState() {
        switch (current) {
            case RESOURCE_01:
                current = RESOURCE_02;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playExo(R.raw.soldier1);
                    }
                }, 3000);
                break;

            case RESOURCE_02:
                current = RESOURCE_03;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playExo(R.raw.police4);
                    }
                }, 3000);
                break;

            case RESOURCE_03:
                Common.showToast(act, "재생완료");
                break;
        }
    }

    private void playExo(int resid) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(act,
                Util.getUserAgent(act, "kr.co.core.tools"));

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(RawResourceDataSource.buildRawResourceUri(resid));

        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release();
            player = null;
        }
    }
}

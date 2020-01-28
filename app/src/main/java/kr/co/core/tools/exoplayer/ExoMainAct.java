package kr.co.core.tools.exoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityExoMainBinding;

public class ExoMainAct extends AppCompatActivity {
    ActivityExoMainBinding binding;

    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;

    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private Long playbackPosition = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_main, null);

        exoPlayerView = binding.exoPlayerView;
        initializePlayer();

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
                        break;
                    case Player.STATE_BUFFERING: // 2
                        // 재생 준비
                        break;
                    case Player.STATE_READY: // 3
                        // 재생 준비 완료
                        break;
                    case Player.STATE_ENDED: // 4
                        // 재생 마침
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void initializePlayer() {
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this.getApplicationContext());
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    this.getApplicationContext(),
                    renderersFactory,
                    trackSelector,
                    loadControl);

            //플레이어 연결
            exoPlayerView.setPlayer(player);
        }

//        String sample = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        String sample = "rtsp://masds03.htc.com.tw/Bird_On_Aware_QCIF_5fps_mpeg4_23k_amr-nb_12k.mp4";
        MediaSource mediaSource = buildMediaSource(Uri.parse(sample));

        //prepare
        player.prepare(mediaSource, true, false);

        //start, stop
        player.setPlayWhenReady(playWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = Util.getUserAgent(this, "blackJin");

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {

            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);

        } else if (uri.getLastPathSegment().contains("m3u8")) {

            //com.google.android.exoplayer:exoplayer-hls 확장 라이브러리를 빌드 해야 합니다.
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);

        } else {

            return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent))
                    .createMediaSource(uri);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();

            exoPlayerView.setPlayer(null);
            player.release();
            player = null;
        }
    }
}

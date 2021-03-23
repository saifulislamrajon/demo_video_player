package com.example.demovideoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toastinglibrary.ToastingMessage;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.demovideoplayer.app.AppKey.NOTHING_ELSE_MATTER;
import static com.example.demovideoplayer.app.AppUrl.URL_CENTURIONS_VIDEO;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*https://www.youtube.com/watch?v=MdQOXoEMLOs&ab_channel=AndroidCoding*/
    /*https://medium.com/fungjai/playing-video-by-exoplayer-b97903be0b33*/
    /*https://github.com/mikkipastel/VideoPlanet/tree/develop*/
    /*D:\AndroidStudioProjects04-05-2019\AndroidStudioProjectsForGitHub\VideoPlanet*/

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView tvToolbarTitle;

    @BindView(R.id.video1)
    Button video1;

    @BindView(R.id.video2)
    Button video2;

    @BindView(R.id.player_view)
    PlayerView playerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.bt_setting)
    ImageView btSetting;

    @BindView(R.id.bt_fullscreen)
    ImageView btFullScreen;

    @BindView(R.id.btnPlay)
    ImageView btnPlay;

    @BindView(R.id.rlMainSection)
    RelativeLayout rlMainSection;

    @BindView(R.id.rlV1V2)
    RelativeLayout rlV1V2;

    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;

    Uri videoUri;

    //Setting dialog
    RadioGroup radioGroup;
    RadioButton radioAuto;
    RadioButton radio240p;
    RadioButton radio360p;
    RadioButton radio480p;
    RadioButton radio720p;
    Button btnDone;

    String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        videoUri = Uri.parse(URL_CENTURIONS_VIDEO);
//        initExoplayer();
        initView();

    }

    private void initToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbarTitle.setText(NOTHING_ELSE_MATTER);
    }

    private void initExoplayer() {

        //Make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Video url
        /*Uri videoUrl = Uri.parse("https://i.imgur.com/7bMqysJ.mp4");
        Uri videoUrl2 = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");*/


        //Initialize load control
        LoadControl loadControl = new DefaultLoadControl();
        //Initialized band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //Initialize track selector
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        //Initialize simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(MainActivity.this, trackSelector, loadControl);
        //Initialize data source factory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        //Initialize extractors factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //Initialize media source
        MediaSource mediaSource = new ExtractorMediaSource(videoUri, factory, extractorsFactory, null, null);
        /*//Initialize MediaItem
        MediaItem mediaItem = MediaItem.fromUri(videoUri);*/
        //Set player
        playerView.setPlayer(simpleExoPlayer);
        //Keep screen on
        playerView.setKeepScreenOn(true);
        //Prepare media
        simpleExoPlayer.prepare(mediaSource);
        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Check condition
                if (playbackState == Player.STATE_BUFFERING) {
                    //When buffering
                    //Show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    //When ready
                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingDialog();
            }
        });

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
                //Check condition
                if (flag) {
                    //When flag is true
                    //Set enter full sreen image
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    //Set portrait orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    //Set flag value is false
                    flag = false;
                } else {
                    //When flag is false
                    //Set exit full screen image
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    //Set landscape orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    //Set flag value is true
                    flag = true;
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        //Get playback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        //Get playback state
        simpleExoPlayer.getPlaybackState();
    }

    private void initView() {
        video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*videoUrl = Uri.parse("https://i.imgur.com/7bMqysJ.mp4");
                initExoplayer();*/
                /*simpleExoPlayer.setPlayWhenReady(false);
                simpleExoPlayer.getPlaybackState();*/

                startActivity(new Intent(MainActivity.this, DemoKotlinActivity.class));

            }
        });

        video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*videoUrl = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
                 *//*videoUrl = Uri.parse("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");*//*
                initExoplayer();*/
                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.getPlaybackState();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlMainSection.setVisibility(View.VISIBLE);
                initExoplayer();
            }
        });

    }

    private void ownLibraryCall() {
        //        ToastingMessage.s(this,"hi man");
        ToastingMessage.toastMessageShortOrLong(this, true, "hi mannnnnnn");
    }

    private void showSettingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_setting);

        radioAuto = dialog.findViewById(R.id.radioAuto);
        radio240p = dialog.findViewById(R.id.radio240);
        radio360p = dialog.findViewById(R.id.radio360);
        radio480p = dialog.findViewById(R.id.radio480);
        radio720p = dialog.findViewById(R.id.radio720);
        btnDone = dialog.findViewById(R.id.btnDone);

        radioAuto.setOnClickListener(this);
        radio240p.setOnClickListener(this);
        radio360p.setOnClickListener(this);
        radio480p.setOnClickListener(this);
        radio720p.setOnClickListener(this);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioAuto:
                selectedValue = "auto";
                radio240p.setChecked(false);
                radio360p.setChecked(false);
                radio480p.setChecked(false);
                radio720p.setChecked(false);
                break;
            case R.id.radio240:
                selectedValue = "240";
                radioAuto.setChecked(false);
                radio360p.setChecked(false);
                radio480p.setChecked(false);
                radio720p.setChecked(false);
                break;
            case R.id.radio360:
                selectedValue = "360";
                radio240p.setChecked(false);
                radioAuto.setChecked(false);
                radio480p.setChecked(false);
                radio720p.setChecked(false);
                break;
            case R.id.radio480:
                selectedValue = "480";
                radio240p.setChecked(false);
                radio360p.setChecked(false);
                radioAuto.setChecked(false);
                radio720p.setChecked(false);
                break;
            case R.id.radio720:
                selectedValue = "720";
                radio240p.setChecked(false);
                radio360p.setChecked(false);
                radio480p.setChecked(false);
                radioAuto.setChecked(false);
                break;
            case R.id.btnDone:

                break;
        }
    }


}

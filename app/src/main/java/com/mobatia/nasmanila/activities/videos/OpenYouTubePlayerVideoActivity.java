package com.mobatia.nasmanila.activities.videos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by krishnaraj on 04/07/18.
 */

public class OpenYouTubePlayerVideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    YouTubePlayerView playerView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    boolean fullScreen = false;
    Bundle extras;
    int position;
    String part1="AIzaS";
    String part2="yCKPCJY";
    String part3="sfWa2KV1MFo2l6X";
    String part4="Qo6ACc7PrC-8";
    String API_KEY="";
    ImageView back,home;
    Activity mActivity=this;
    Context mContext;
    TextView textcontent;
String video_url="";
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube);
mContext=this;
        extras = getIntent().getExtras();

        if (extras != null) {
            video_url = extras.getString("video_url");

        }
//url = getYoutubeVideoId(url);
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(OpenYouTubePlayerVideoActivity.this, "Video");
        headermanager.getHeader(relativeHeader, 1);
        back = headermanager.getLeftButton();
        home = headermanager.getLogoButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, HomeListAppCompatActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });

        playerView = (YouTubePlayerView) findViewById(R.id.player_view);

// initializes the YouTube player view
        playerView.initialize(part1+part2+part3+part4, this);
// playerView.set
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorResult) {

// shows dialog if user interaction may fix the error
        if (errorResult.isUserRecoverableError()) {
            errorResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
// displays the error occured during the initialization
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String error = String.format(getResources().getString(R.string.common_error),
                    errorResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Called when initialization of Player is successful
     *
     * @param provider
     *            Provider used to initialize the Player
     * @param player
     *            Player instance used to control the video playback
     * @param wasRestored
     *            Depicts whether the video is restored from a previous state.
     *            Returns true if video is resumed from the last paused state,
     *            else returns false
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        final YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {

// Use cueVideo() method, if you don't want to play it automatically
// loadVideo() will auto play video

// player.loadVideo(Config.VIDEO_CODE);
            player.cueVideo(getYoutubeVideoId(video_url));

// handling fullscreen and exit full screen

            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                @Override
                public void onFullscreen(boolean fullscreen) {
                    if (fullscreen) {
                        player.play();
// fltop.setVisibility(View.GONE);
                    } else {
                        player.play();
// fltop.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {

// initializes the YouTube player view
            getYouTubePlayerProvider().initialize(part1+part2+part3+part4, this);
        }
    }

    // Returns Player view defined in xml file
    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.player_view);
    }

    /**
     *
     */
    private final class EventListener implements
            YouTubePlayer.PlaybackEventListener {

        /**
         * Called when video starts playing
         */
        @Override
        public void onPlaying() {
            Log.e("Status", "Playing");
        }

        /**
         * Called when video stops playing
         */
        @Override
        public void onPaused() {
            Log.e("Status", "Paused");
        }

        /**
         * Called when video stopped for a reason other than paused
         */
        @Override
        public void onStopped() {
            Log.e("Status", "Stopped");
        }

        /**
         * Called when buffering of video starts or ends
         *
         * @param b
         *            True if buffering is on, else false
         */
        @Override
        public void onBuffering(boolean b) {
        }

        /**
         * Called when jump in video happens. Reason can be either user
         * scrubbing or seek method is called explicitely
         *
         * @param i
         */
        @Override
        public void onSeekTo(int i) {
        }
    }

    private final class StateChangeListener implements
            YouTubePlayer.PlayerStateChangeListener {

        /**
         * Called when player begins loading a video. During this duration,
         * player won't accept any command that may affect the video playback
         */
        @Override
        public void onLoading() {
        }

        /**
         * Called when video is loaded. After this player can accept the
         * playback affecting commands
         *
         * @param s
         *            Video Id String
         */
        @Override
        public void onLoaded(String s) {
        }

        /**
         * Called when YouTube ad is started
         */
        @Override
        public void onAdStarted() {
        }

        /**
         * Called when video starts playing
         */
        @Override
        public void onVideoStarted() {
        }

        /**
         * Called when video is ended
         */
        @Override
        public void onVideoEnded() {
        }

        /**
         * Called when any kind of error occurs
         *
         * @param errorReason
         *            Error string showing the reason behind it
         */
        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0
                && youtubeUrl.startsWith("http")
                || (youtubeUrl.startsWith("https"))) {

            String expression = "^.*((youtu.be"
                    + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var
// regExp
// =
// /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
            }
        }
        System.out.println("url---" + video_id);

        return video_id;
    }

    private String youTubeVideoId(String link) {
        String[] spltStrings = null;
        String pattern = "(?:videos\\/|v=)([\\w-]+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(link);
        if (matcher.find()) {
            String splitString = matcher.group();
            spltStrings = splitString.split("=");
            System.out.println("videoid" + spltStrings[1]);
        }
        return spltStrings[1];
    }
}

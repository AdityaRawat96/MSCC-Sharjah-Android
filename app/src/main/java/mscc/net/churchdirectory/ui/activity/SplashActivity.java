package mscc.net.churchdirectory.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.VideoView;
import mscc.net.churchdirectory.LoginActivity;
import mscc.net.churchdirectory.MainActivity;
import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.session.SessionManager;

public class SplashActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private VideoView mVV;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.video_player_activity);

        int fileRes=this.getResources().getIdentifier("splash", "raw", getPackageName());
        mVV = (VideoView)findViewById(R.id.myvideoview);
        mVV.setOnCompletionListener(this);
        mVV.setOnPreparedListener(this);

        if (!playFileRes(fileRes)) return;

        mVV.start();

    }


    private boolean playFileRes(int fileRes) {
        if (fileRes==0) {
            stopPlaying();
            return false;
        } else {
            mVV.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + fileRes));
            return true;
        }
    }

    public void stopPlaying() {
        mVV.stopPlayback();
        this.finish();
        if (!SessionManager.getInstance().isLoggedIn(this))
        {
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }



    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!SessionManager.getInstance().isLoggedIn(this))
        {
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(false);
    }

}
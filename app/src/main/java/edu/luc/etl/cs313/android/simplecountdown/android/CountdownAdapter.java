package edu.luc.etl.cs313.android.simplecountdown.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import edu.luc.etl.cs313.android.simplecountdown.R;
import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelListener;
import edu.luc.etl.cs313.android.simplecountdown.model.ConcreteCountdownModelFacade;
import edu.luc.etl.cs313.android.simplecountdown.model.CountdownModelFacade;

/**
 * A thin adapter component for the stopwatch.
 *Sam Tiku
 * @author laufer
 */
public class CountdownAdapter extends Activity implements CountdownModelListener {

    private static String TAG = "stopwatch-android-activity";

    /**
     * The state-based dynamic model.
     */
    private CountdownModelFacade model;
    private MediaPlayer mediaPlayer;

    protected void setModel(final CountdownModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        //It will call initial notfication method.
        playDefaultNotification();
        //playSound(R.raw.start, false);
        // inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteCountdownModelFacade());
        // inject dependency on this into model to register for UI updates
        model.setModelListener(this);
        findViewById(R.id.seconds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isInitialState()) {
                    showInputDialog();
                }
            }
        });
    }

    /** Plays the default notification sound. */
    protected void playDefaultNotification() {
        final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final Context context = getApplicationContext();

        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.start();
    }

    // TODO remaining lifecycle methods

    /**
     * Updates the seconds and minutes in the UI.
     * @param time
     */
    public void onTimeUpdate(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = findViewById(R.id.seconds);
            final var locale = Locale.getDefault();
            tvS.setText(String.format(locale,"%02d", time ));
                   });
    }

    /**
     * Updates the state name in the UI.
     * @param stateId
     */
    public void onStateUpdate(final int stateId) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView stateName = findViewById(R.id.stateName);
            stateName.setText(getString(stateId));
        });
    }

    @Override
    public void onPlayBeep() {
        playSound(R.raw.beep, false);
    }

    @Override
    public void onStartAlarm() {
        playSound(R.raw.alarm, true);
    }

    @Override
    public void onStopAlarm() {
        stopSound();
    }

    // forward event listener methods to the model
    public void onSetStop(final View view) {
        model.onSetStop();
    }


    // play sounds
    protected void playSound(int resource, boolean isRepeat) {
        mediaPlayer = MediaPlayer.create(this,resource);
        mediaPlayer.setLooping(isRepeat);
        mediaPlayer.start();
    }

    // to stop the alarm
    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    // show dialog to take initial value for the time
    private void showInputDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        EditText userInput = promptsView.findViewById(R.id.etInitialValue);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String entryValue = userInput.getText().toString();
                            int time = Integer.parseInt(entryValue);
                            model.initializeTime(time);
                            onTimeUpdate(time);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}

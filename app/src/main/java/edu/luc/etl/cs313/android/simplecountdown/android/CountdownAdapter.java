// Package declaration and imports
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
 * CountdownAdapter acts as a thin adapter layer between the view (UI) and the model.
 * It extends Android's Activity class, enabling it to manage Android lifecycle events and handle user interactions.
 */
public class CountdownAdapter extends Activity implements CountdownModelListener {

    private static String TAG = "stopwatch-android-activity"; // Log tag, mostly used for debugging purposes.

    private CountdownModelFacade model; // Interface to the countdown model.
    private MediaPlayer mediaPlayer; // Media player for playing sounds.

    protected void setModel(final CountdownModelFacade model) {
        this.model = model; // Dependency injection method for setting the model.
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the layout for this activity.
        playDefaultNotification(); // Plays a default notification sound at the start.
        this.setModel(new ConcreteCountdownModelFacade()); // Sets up the model for this activity.
        model.setModelListener(this); // Registers this activity as a listener for model updates.
        findViewById(R.id.seconds).setOnClickListener(new View.OnClickListener() { // Sets up a click listener for the seconds view.
            @Override
            public void onClick(View view) {
                if (model.isInitialState()) {
                    showInputDialog(); // Shows a dialog to input the initial countdown time if in the initial state.
                }
            }
        });
    }

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
            mediaPlayer.prepare(); // Prepares the media player to play the audio.
            mediaPlayer.setOnCompletionListener(MediaPlayer::release); // Releases the media player after playing the sound.
            mediaPlayer.start(); // Starts playing the sound.
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu); // Inflates the options menu.
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.start(); // Starts the model.
    }

    public void onTimeUpdate(final int time) {
        runOnUiThread(() -> {
            final TextView tvS = findViewById(R.id.seconds);
            final var locale = Locale.getDefault();
            tvS.setText(String.format(locale,"%02d", time )); // Updates the seconds display on the UI.
        });
    }

    public void onStateUpdate(final int stateId) {
        runOnUiThread(() -> {
            final TextView stateName = findViewById(R.id.stateName);
            stateName.setText(getString(stateId)); // Updates the state display on the UI.
        });
    }

    @Override
    public void onPlayBeep() {
        playSound(R.raw.beep, false); // Plays a beep sound.
    }

    @Override
    public void onStartAlarm() {
        playSound(R.raw.alarm, true); // Plays an alarm sound.
    }

    @Override
    public void onStopAlarm() {
        stopSound(); // Stops playing the sound.
    }

    public void onSetStop(final View view) {
        model.onSetStop(); // Sends a stop signal to the model.
    }

    protected void playSound(int resource, boolean isRepeat) {
        mediaPlayer = MediaPlayer.create(this, resource);
        mediaPlayer.setLooping(isRepeat); // Sets the looping status based on the parameter.
        mediaPlayer.start(); // Starts playing the specified sound.
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop(); // Stops the media player if it is currently playing.
        }
    }

    private void showInputDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_dialog_box, null); // Inflates the input dialog box layout.
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
                            model.initializeTime(time); // Initializes the model's time with the user input.
                            onTimeUpdate(time); // Updates the UI with the new time.
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); // Cancels the dialog.
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show(); // Shows the dialog.
    }
}

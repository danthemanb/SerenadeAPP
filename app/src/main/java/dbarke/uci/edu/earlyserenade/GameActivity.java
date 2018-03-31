package dbarke.uci.edu.earlyserenade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

       final SingleGame currentGame = new SingleGame(false, true, 10);

        final long guessTimeSec = 30;    // Set the amount of time given to guess
        final long guessTimeMin = 0;
        // Playing with Chronometer
        final Chronometer timer = (Chronometer) findViewById(R.id.timerChron);
        timer.setCountDown(true);
        //timer.setBase(SystemClock.elapsedRealtime() - (guessTimeMin * 60000 + guessTimeSec * 1000));  //(nr_of_min * 60000 + nr_of_sec * 1000)

        // Playing with the Start Button
        Button timerButton = (Button) findViewById(R.id.timerBtn);
        Button startButton = (Button) findViewById(R.id.startBtn);

        final TextView testTxtView = (TextView) findViewById(R.id.testTxt);
        final TextView turnDisplay = (TextView) findViewById(R.id.turnDisplayTxt);
        final TextView bluePtsDisplay = (TextView) findViewById(R.id.blueTeamPts);
        final TextView redPtsDisplay = (TextView) findViewById(R.id.redTeamPts);
        bluePtsDisplay.setText("" + currentGame.getBlueScore());
        redPtsDisplay.setText("" + currentGame.getRedScore());

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long testTime = -(((SystemClock.elapsedRealtime() - chronometer.getBase())/1000));
                if (testTime <= 0) {
                    timer.stop();
                    currentGame.updateScore();
                    redPtsDisplay.setText("" + currentGame.getRedScore());
                    bluePtsDisplay.setText("" + currentGame.getBlueScore());
                    testTxtView.setText("" + currentGame.testGameOver());
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                // Start The clock

            }
        });

        timerButton.setOnTouchListener(new View.OnTouchListener() {
            //@SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean returnValue = false;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        if(currentGame.isTicking()){
                            testTxtView.setText("Action_Down, IsTicking = 1");
                            timer.stop();               // Start Singing a song
                            currentGame.setTicking(false);
                        }
                        else {
                            testTxtView.setText("Action_Down, IsTicking = 0");
                            timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + guessTimeSec * 1000)); // Game start pt 1
                        }
                        returnValue = true;
                        break; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        if(!currentGame.isTicking()){         // If the clock is not currently ticking, start ticking
                            testTxtView.setText("Action_Up, IsTicking = 0");
                            timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + guessTimeSec * 1000));
                            timer.start();
                            currentGame.setTicking(true);
                        }
                        else{
                            testTxtView.setText("Action_Up, IsTicking = 1");        //Game Start pt 2
                            timer.start();
                            currentGame.setTicking(true);
                            if(currentGame.isRedsTurn())
                                turnDisplay.setText("It is Red's Turn");
                            else
                                turnDisplay.setText("It is Blue's Turn");
                        }
                        if(currentGame.isRedsTurn()){               // Display and update the current turn
                            currentGame.setRedsTurn(false);
                            turnDisplay.setText("It is Blue's Turn");
                        }
                        else{
                            currentGame.setRedsTurn(true);
                            turnDisplay.setText("It is Red's Turn");
                        }
                        returnValue = true;
                        break; // if you want to handle the touch event
                    default:
                }
                return returnValue;
            }
        });

        // Temp return to main screen
        Button pauseBtn = (Button) findViewById(R.id.pauseBtn);
        /*pauseBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent tempReturnToMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                // How to pass information to another activity
                startActivity(tempReturnToMainIntent);
            }
        });
        */

        pauseBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent startPauseIntent = new Intent(getApplicationContext(), GamePauseActivity.class);
                // How to pass information to another activity
                startActivity(startPauseIntent);
            }
        });

    }
}

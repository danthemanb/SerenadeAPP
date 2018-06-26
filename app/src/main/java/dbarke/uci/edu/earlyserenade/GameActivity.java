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

        // Set the default amount of time given to guess
        final long guessTimeSec = 30;
        final long guessTimeMin = 0;
        final int gamePoint = 10;

        // Chronometer Creation
        final Chronometer timer = (Chronometer) findViewById(R.id.timerChron);
        timer.setCountDown(true);
        //timer.setVisibility(View.GONE);
        final TextView timeDisplay = (TextView) findViewById(R.id.timeDisplay);

        final SingleGame currentGame = new SingleGame(true, 0, 0, gamePoint);
        /*    Check to see if pause bundle exists    */
        Intent intent = getIntent();
        if(intent.hasExtra("secondsLeft"))
        {
            Bundle bundle = getIntent().getExtras();
            currentGame.setRedsTurn(bundle.getBoolean("isRedsTurn"));
            currentGame.setRedScore(bundle.getInt("redScore"));
            currentGame.setBlueScore(bundle.getInt("blueScore"));
            currentGame.setGamePoint(bundle.getInt("gamePoint"));
            currentGame.setSeconds(bundle.getLong("secondsLeft"));

            timer.setBase(SystemClock.elapsedRealtime() + ((currentGame.getSeconds()+1) * 1000));
            timer.start();
        }

        // Playing with the Start Button
        final Button timerButton = (Button) findViewById(R.id.timerBtn);
        timerButton.setVisibility(View.GONE);           // Time starts as invisible
        final Button startButton = (Button) findViewById(R.id.startBtn);


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
                currentGame.setSeconds(testTime);
                timeDisplay.setText("" + testTime);
                if (testTime <= 0) {
                    timer.stop();
                    timerButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.VISIBLE);
                    currentGame.updateScore();
                    redPtsDisplay.setText("" + currentGame.getRedScore());
                    bluePtsDisplay.setText("" + currentGame.getBlueScore());
                    testTxtView.setText("" + currentGame.testGameOver());
                    // check if game over
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                // Start the current round
                timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + (guessTimeSec+1) * 1000));
                timer.start();
                startButton.setVisibility(View.GONE);
                timerButton.setVisibility(View.VISIBLE);
                if(currentGame.isRedsTurn())
                    turnDisplay.setText("It is Red's Turn");
                else
                    turnDisplay.setText("It is Blue's Turn");
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
                            v.setPressed(true);
                            timer.stop();
                            timeDisplay.setText("SING!");
                        returnValue = true;
                        break; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                            v.setPressed(false);
                            timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + (guessTimeSec+1) * 1000));
                            timer.start();

                        if(currentGame.isRedsTurn()){               // Display and update the current turn
                            currentGame.setRedsTurn(false);
                            turnDisplay.setText("It is Blue's Turn");
                        }
                        else {
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

        // Open Pause Menu
        Button pauseBtn = (Button) findViewById(R.id.pauseBtn);

        pauseBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                timer.stop();
                Intent startPauseIntent = new Intent(getApplicationContext(), GamePauseActivity.class);
                startPauseIntent.putExtra("secondsLeft", currentGame.getSeconds());
                startPauseIntent.putExtra("redScore", currentGame.getRedScore());
                startPauseIntent.putExtra("blueScore", currentGame.getBlueScore());
                startPauseIntent.putExtra("gamePoint", currentGame.getGamePoint());
                startPauseIntent.putExtra("isRedsTurn", currentGame.isRedsTurn());
                // How to pass information to another activity
                startActivity(startPauseIntent);
            }
        });
    }
}

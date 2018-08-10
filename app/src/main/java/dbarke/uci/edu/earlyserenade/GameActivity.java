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
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up start Animation
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);

        // Default Things
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Set the default amount of time given to guess
        final long guessTimeSec = 20;
        final long guessTimeMin = 0;
        final int gamePoint = 10;

        // Chronometer Creation
        final Chronometer timer = (Chronometer) findViewById(R.id.timerChron);
        timer.setCountDown(true);
        //timer.setVisibility(View.GONE);
        final TextView timeDisplay = (TextView) findViewById(R.id.timeDisplay);

        final SingleGame currentGame = new SingleGame(true, 0, 0, gamePoint, guessTimeSec);

        // Create the songlist and put it into currentGame
        ManageFiles songList = new ManageFiles(this.getApplicationContext());
        currentGame.setSongs(songList.getSongs());

        //create a second SingleGame object to hold the previous turn
        final SingleGame lastTurn = new SingleGame();
        lastTurn.fillSingleGame(currentGame);

        // Initializing Buttons
        final Button timerButton = (Button) findViewById(R.id.timerBtn);
        timerButton.setVisibility(View.GONE);           // Time starts as invisible
        final Button startButton = (Button) findViewById(R.id.startBtn);
        final Button undoButton = (Button) findViewById(R.id.undoButton);

        // Initialize Views
        final TextView testTxtView = (TextView) findViewById(R.id.testTxt);
        final TextView turnDisplay = (TextView) findViewById(R.id.turnDisplayTxt);
        final TextView bluePtsDisplay = (TextView) findViewById(R.id.blueTeamPts);
        final TextView redPtsDisplay = (TextView) findViewById(R.id.redTeamPts);

        /*    Check to see if pause bundle exists    */
        Intent intent = getIntent();
        if(intent.hasExtra("returnGameInfo"))
        {
            Bundle bundle = getIntent().getExtras();
            currentGame.fillSingleGame((SingleGame)bundle.getSerializable("returnGameInfo"));
            lastTurn.fillSingleGame((SingleGame)bundle.getSerializable("returnLastTurn"));
            testTxtView.setText(currentGame.getWord());

            timer.setBase(SystemClock.elapsedRealtime() + ((currentGame.getSeconds()+1) * 1000));
            timer.start();
            startButton.setVisibility(View.GONE);
            timerButton.setVisibility(View.VISIBLE);
            turnDisplay.setText("It is " + currentGame.whosTurn() + " turn.");
        }

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
                    lastTurn.fillSingleGame(currentGame.isRedsTurn(), lastTurn.getRedScore(), lastTurn.getBlueScore(), lastTurn.getGamePoint(), guessTimeSec, currentGame.getSongs(), currentGame.getCurrent());
                    currentGame.newWord();
                    currentGame.setSeconds(guessTimeSec);
                    timerButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.VISIBLE);
                    currentGame.updateScore();
                    currentGame.toggleTurn();
                    redPtsDisplay.setText("" + currentGame.getRedScore());
                    bluePtsDisplay.setText("" + currentGame.getBlueScore());
                    //testTxtView.setText("" + currentGame.testGameOver());
                    // check if game over
                    if(currentGame.testGameOver() > 0)
                    {
                        Intent endGameIntent = new Intent(getApplicationContext(), EndGameActivity.class);
                        endGameIntent.putExtra("CurrentGame", currentGame);
                        // How to pass information to another activity
                        startActivity(endGameIntent);
                    }
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                // Start the current round
                timer.setBase(SystemClock.elapsedRealtime() + ((currentGame.getSeconds()) * 1000));
                timer.start();
                startButton.setVisibility(View.GONE);
                timerButton.setVisibility(View.VISIBLE);
                turnDisplay.setText("It is " + currentGame.whosTurn() + " turn.");
                testTxtView.setText(currentGame.getCurrent());
                //Enable previous turn
                //lastTurn.fillSingleGame(currentGame);
                //undoButton.setEnabled(true);
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
                            undoButton.setEnabled(false);
                            timer.stop();
                            timeDisplay.setText("SING!");
                        returnValue = true;
                        break; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                            v.setPressed(false);
                            lastTurn.fillSingleGame(currentGame);
                            undoButton.setEnabled(true);
                            timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + (guessTimeSec+1) * 1000));
                            timer.start();
                            currentGame.toggleTurn();
                            turnDisplay.setText("it is " + currentGame.whosTurn() + " turn.");
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
                startPauseIntent.putExtra("CurrentGame", currentGame);
                startPauseIntent.putExtra("LastTurn", lastTurn);
                // How to pass information to another activity
                startActivity(startPauseIntent);
            }
        });

        Button clearBtn = (Button) findViewById(R.id.clearBtn);

        clearBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                timer.stop();
                lastTurn.fillSingleGame(currentGame.isRedsTurn(), lastTurn.getRedScore(), lastTurn.getBlueScore(), lastTurn.getGamePoint(), guessTimeSec, currentGame.getSongs(), currentGame.getCurrent());
                currentGame.newWord();
                currentGame.setSeconds(guessTimeSec);
                timerButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                currentGame.updateScore();
                currentGame.toggleTurn();
                timeDisplay.setText("0");
                redPtsDisplay.setText("" + currentGame.getRedScore());
                bluePtsDisplay.setText("" + currentGame.getBlueScore());
                //testTxtView.setText("" + currentGame.testGameOver());
                // check if game over
                if(currentGame.testGameOver() > 0)
                {
                    Intent endGameIntent = new Intent(getApplicationContext(), EndGameActivity.class);
                    endGameIntent.putExtra("CurrentGame", currentGame);
                    // How to pass information to another activity
                    startActivity(endGameIntent);
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                // Pause gamestate and reset buttons
                timer.stop();
                undoButton.setEnabled(false);
                timerButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);

                // Reset currentGame to backup
                currentGame.fillSingleGame(lastTurn);

                // Update textviews to show user
                redPtsDisplay.setText("" + currentGame.getRedScore());
                bluePtsDisplay.setText("" + currentGame.getBlueScore());
                testTxtView.setText("" + currentGame.getCurrent());
                timeDisplay.setText("" + currentGame.getSeconds());
                turnDisplay.setText("it is " + currentGame.whosTurn() + "turn.");
            }
        });
    }
}

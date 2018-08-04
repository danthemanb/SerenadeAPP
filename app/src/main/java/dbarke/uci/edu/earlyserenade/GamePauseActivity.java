package dbarke.uci.edu.earlyserenade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GamePauseActivity extends AppCompatActivity {

    private View incRed;
    private View decRed;
    private View incBlue;
    private View decBlue;
    private View switchTurn;
    private View setTime;
    private View getTime;
    private View editBtn;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up exit animations
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Fade());

        // Default Stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_pause);

        // Set up popup metrics
        DisplayMetrics pauseActivitySize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(pauseActivitySize);
        int width = pauseActivitySize.widthPixels;
        int height = pauseActivitySize.heightPixels;
        width *= .8;
        height *= .6;
        getWindow().setLayout(width,height);

        // Get information from GameActivity
        final Bundle bundle = getIntent().getExtras();
        final SingleGame currentGame = (SingleGame) bundle.getSerializable("CurrentGame");
        final SingleGame lastTurn = (SingleGame) bundle.getSerializable("LastTurn");

        // Set up edit buttons
        editBtn = findViewById(R.id.editBtn);
        incRed = findViewById(R.id.incRed);
        decRed = findViewById(R.id.decRed);
        incBlue = findViewById(R.id.incBlue);
        decBlue = findViewById(R.id.decBlue);
        switchTurn = findViewById(R.id.switchTurn);
        setTime = findViewById(R.id.setTime);
        getTime = findViewById(R.id.getTime);

        // Hide Initially Hidden Buttons
        incRed.setVisibility(View.GONE);
        decRed.setVisibility(View.GONE);
        incBlue.setVisibility(View.GONE);
        decBlue.setVisibility(View.GONE);
        switchTurn.setVisibility(View.GONE);
        getTime.setVisibility(View.GONE);
        setTime.setVisibility(View.GONE);

        // Get System Default Animation Time
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final TextView resultText = (TextView) findViewById(R.id.resultText);

        Button endGameButton = (Button) findViewById(R.id.endGameBtn);
        endGameButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent endGameIntent = new Intent(getApplicationContext(), MainActivity.class);
                // How to pass information to another activity
                startActivity(endGameIntent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                resultText.setText("Edits will be not shown until game is resumed.");
                showOptions();
            }
        });

        Button returnToGameBtn = (Button) findViewById(R.id.resumeBtn);
        returnToGameBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(GamePauseActivity.this);
                Intent resumeIntent = new Intent(getApplicationContext(), GameActivity.class);
                resumeIntent.putExtra("returnGameInfo", currentGame);
                resumeIntent.putExtra("returnLastTurn", lastTurn);
                // How to pass information to another activity
                startActivity(resumeIntent, options.toBundle());
            }
        });

        incRed.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                currentGame.incRed();
                resultText.setText("Red Score Incremented to " + currentGame.getRedScore());
            }
        });

        decRed.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                currentGame.decRed();
                resultText.setText("Red Score Decremented to " + currentGame.getRedScore());
            }
        });

        incBlue.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                currentGame.incBlue();
                resultText.setText("Blue Score Incremented to " + currentGame.getBlueScore());
            }
        });

        decBlue.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                currentGame.decBlue();
                resultText.setText("Blue Score Decremented to " + currentGame.getBlueScore());
            }
        });

        switchTurn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                currentGame.toggleTurn();
                resultText.setText("It is now " + currentGame.whosTurn() + "turn.");
            }
        });

        final TextView timeInfo = (TextView) getTime;
        setTime.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                long newTime;
                try {
                    newTime = Long.parseLong(timeInfo.getText().toString());
                    currentGame.setSeconds(newTime);
                    resultText.setText("" + currentGame.whosTurn() + " team now has " + currentGame.getSeconds() + " seconds.");
                }
                catch(NumberFormatException e){
                    resultText.setText("Invalid Number Format");
                }
            }
        });

    }
    private void showOptions(){
        // Set resources to be shown to translucent but visible (so that they can be become visible)
        incRed.setAlpha(0f);
        incRed.setVisibility(View.VISIBLE);
        decRed.setAlpha(0f);
        decRed.setVisibility(View.VISIBLE);
        incBlue.setAlpha(0f);
        incBlue.setVisibility(View.VISIBLE);
        decBlue.setAlpha(0f);
        decBlue.setVisibility(View.VISIBLE);
        switchTurn.setAlpha(0f);
        switchTurn.setVisibility(View.VISIBLE);
        getTime.setAlpha(0f);
        getTime.setVisibility(View.VISIBLE);
        setTime.setAlpha(0f);
        setTime.setVisibility(View.VISIBLE);

        // Perform the animation to hide the original options button
        editBtn.animate().alpha(0f).setDuration(shortAnimationDuration).setListener(null);
        editBtn.setVisibility(View.GONE);

        // Perform the Animation to show hidden variables
        incRed.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        decRed.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        incBlue.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        decBlue.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        switchTurn.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        getTime.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        setTime.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);

    }

}
